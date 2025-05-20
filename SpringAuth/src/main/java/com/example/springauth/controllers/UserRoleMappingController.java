package com.example.springauth.controllers;

import com.example.springauth.dialects.postgres.ConnectionSettings;
import com.example.springauth.dialects.postgres.PostgresDialectInsertion;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.dialects.postgres.SchemaSettings;
import com.example.springauth.models.app.PrivilegeModel;
import com.example.springauth.models.app.RoleModel;
import com.example.springauth.models.app.RolePrivilegeMapModel;
import com.example.springauth.models.app.UserRoleMapModel;
import com.example.springauth.models.json.RolePrivilegeJSONModel;
import com.example.springauth.models.json.UserRoleJSONModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.TableNameGiver;
import com.example.springauth.utilities.DateTime;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserRoleMappingController {


    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema userManagementSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getUserManagementSchemaName());


    @GetMapping("/create_user_role_mappings")
    public String createRolePrivilegeMapping(Model model) {
        attributeSetup(model);
        return "user_role";
    }

    @PostMapping(value = "/user_role_mapping", consumes = "application/json")
    public String createUserRoleMapping(
            @RequestBody String userRoleJSONArray,
            Model model
    ) {
        System.out.println("user role mappings created" + userRoleJSONArray);

        List<UserRoleMapModel> rolePrivilegeMapModelList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            List<UserRoleJSONModel> decodedUserRoleList = objectMapper.readValue(userRoleJSONArray, new TypeReference<>() {});

            for(UserRoleJSONModel userRole : decodedUserRoleList){

                boolean isMapped =  rolePrivilege.getStatus().equalsIgnoreCase("true");
                if(!isMapped)
                    continue;
                RoleModel roleModel = new RoleModel();
                roleModel.setId(rolePrivilege.getRoleId());
                PrivilegeModel privilegeModel = new PrivilegeModel();
                privilegeModel.setId(rolePrivilege.getPrivilegeId());
                String createdAt = DateTime.getTimeStamp();
                var rolePrivilegeMapModel = new RolePrivilegeMapModel(roleModel, privilegeModel, createdAt);
                rolePrivilegeMapModelList.add(rolePrivilegeMapModel);
            }
            if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
                String rolePrivilegeMappingTableName = TableNameGiver.getRolePrivilegeMapTableName();
                var insertionObj = new PostgresDialectInsertion(queryExecutor, userManagementSchema, rolePrivilegeMappingTableName);
                List<RolePrivilegeMapModel> returnedRolePrivilegeMapModelList = insertionObj.insertRolePrivilegeMappings(rolePrivilegeMapModelList);
                model.addAttribute(
                        "role_model_submission",
                        String.format("successfully created %d role privilege mappings", returnedRolePrivilegeMapModelList.size())
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
        return;
    }

}
