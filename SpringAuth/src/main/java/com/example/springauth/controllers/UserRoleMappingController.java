package com.example.springauth.controllers;

import com.example.springauth.dialects.postgres.ConnectionSettings;
import com.example.springauth.dialects.postgres.PostgresDialectInsertion;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.dialects.postgres.SchemaSettings;
import com.example.springauth.models.app.*;
import com.example.springauth.models.json.UserRoleJSONModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.RoleTable;
import com.example.springauth.tables.TableNameGiver;
import com.example.springauth.tables.UserTable;
import com.example.springauth.utilities.DateTime;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserRoleMappingController {


    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema userManagementSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getUserManagementSchemaName());


    @GetMapping("/user_role_mapping")
    public String createRolePrivilegeMapping(Model model) {
        attributeSetup(model);
        return "user_role";
    }

    @PostMapping(value = "/user_role_mapping", consumes = "application/json")
    public String createUserRoleMapping(
            @RequestBody String userRoleJSONArray,
            Model model
    ) {

        List<UserRoleMapModel> userRoleMapModelList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            List<UserRoleJSONModel> decodedUserRoleList = objectMapper.readValue(userRoleJSONArray, new TypeReference<>() {});

            for(UserRoleJSONModel userRole : decodedUserRoleList){

                boolean isMapped =  userRole.getStatus().equalsIgnoreCase("true");
                if(!isMapped)
                    continue;
                UserPropModel userPropModel = new UserPropModel();
                userPropModel.setId(userRole.getUserId());
                RoleModel roleModel = new RoleModel();
                roleModel.setId(userRole.getRoleId());
                String createdAt = DateTime.getTimeStamp();
                var userRoleMapModel = new UserRoleMapModel(userPropModel.getId(), roleModel.getId(), createdAt);
                userRoleMapModelList.add(userRoleMapModel);
            }
            if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
                String userRoleMappingTableName = TableNameGiver.getUserRoleMapTableName();
                var insertionObj = new PostgresDialectInsertion(queryExecutor, userManagementSchema, userRoleMappingTableName);
                List<UserRoleMapModel> returnedUserRoleMapModelList = insertionObj.insertUserRoleMappings(userRoleMapModelList);
                model.addAttribute(
                        "role_model_submission",
                        String.format(
                                "successfully created %d role privilege mappings",
                                returnedUserRoleMapModelList.size()
                        )
                );
            }
        } catch (Exception e) {
            // log errors
            System.out.println(e.getMessage());
        }

        attributeSetup(model);
        return "user_role";
    }

    private void attributeSetup(Model model) {

        List<UserRoleMapModel> userRoleMapModelList = new ArrayList<>();
        RoleTable roleTable = new RoleTable();
        roleTable.setSchema(userManagementSchema);
        roleTable.setQueryExecutor(queryExecutor);
        roleTable.setSqlDialect(sqlDialect);
        List<RoleModel> roleModelList = null;

        // Get a list of all roles in the system.
        try {
            roleModelList = roleTable.selectRoles();
            model.addAttribute("role_list", roleModelList);
        }catch (SQLException e){
            // log errors and exceptions
            model.addAttribute("role_list", new ArrayList<RoleModel>());
            System.out.println(e.getMessage());
        }

        // Get a list of all users in the system.
        UserTable userTable = new UserTable();
        userTable.setSchema(userManagementSchema);
        userTable.setQueryExecutor(queryExecutor);
        userTable.setSqlDialect(sqlDialect);
        List<UserModel> userModelList = null;
        try {
            userModelList = userTable.selectUsers();
            List<UserPropModel> userPropModelList = this.convertToUserPropModelList(userModelList);
            model.addAttribute("user_list", userPropModelList);
        }catch (SQLException e){
            // log errors and exceptions
            System.out.println(e.getMessage());
        }finally {
            if(
                    (userModelList != null && !userModelList.isEmpty())
                    && (roleModelList != null && !roleModelList.isEmpty())
            ){
                for (UserModel userModel : userModelList) {
                    for (RoleModel roleModel : roleModelList) {
                        UserRoleMapModel userRoleMapModel = new UserRoleMapModel(
                                userModel.getPropertyValue("id"), roleModel.getId(), ""
                        );
                        userRoleMapModelList.add(userRoleMapModel);
                    }
                }
                model.addAttribute("user_role_list", userRoleMapModelList);
            }
        }
        model.addAttribute("userRoleForm", ""); // sends json instead of an object
    }

    private List<UserPropModel> convertToUserPropModelList(List<UserModel> userModelList) {
        List<UserPropModel> userPropModelList = new ArrayList<>();
        for (UserModel userModel : userModelList) {
            List<EntityPropModel> entityPropModelList = userModel.getUserPropModelList();
            UserPropModel userPropModel = new UserPropModel();
            for (EntityPropModel entityPropModel : entityPropModelList) {
                if (entityPropModel.getName().equalsIgnoreCase("id"))
                    userPropModel.setId(entityPropModel.getValue());
                if (entityPropModel.getName().equalsIgnoreCase("first_name"))
                    userPropModel.setFirstName(entityPropModel.getValue());
                if (entityPropModel.getName().equalsIgnoreCase("last_name"))
                    userPropModel.setLastName(entityPropModel.getValue());
                if (entityPropModel.getName().equalsIgnoreCase("other_names"))
                    userPropModel.setOtherNames(entityPropModel.getValue());
                if (entityPropModel.getName().equalsIgnoreCase("current_email"))
                    userPropModel.setEmail(entityPropModel.getValue());
                if (entityPropModel.getName().equalsIgnoreCase("current_phone_number"))
                    userPropModel.setPhoneNumber(entityPropModel.getValue());
            }
            userPropModelList.add(userPropModel);
        }
        return userPropModelList;
    }

}
