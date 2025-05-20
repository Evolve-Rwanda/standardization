package com.example.springauth.controllers;


import com.example.springauth.dialects.postgres.ConnectionSettings;
import com.example.springauth.dialects.postgres.PostgresDialectInsertion;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.dialects.postgres.SchemaSettings;
import com.example.springauth.models.app.PrivilegeModel;
import com.example.springauth.models.app.RoleModel;
import com.example.springauth.models.app.RolePrivilegeMapModel;
import com.example.springauth.models.json.RolePrivilegeJSONModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.PrivilegeTable;
import com.example.springauth.tables.RoleTable;
import com.example.springauth.tables.TableNameGiver;
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
public class RolePrivilegeMappingController {


    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema userManagementSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getUserManagementSchemaName());


    @GetMapping("/create_role_privilege_mappings")
    public String createRolePrivilegeMapping(Model model) {
        attributeSetup(model);
        return "role_privilege";
    }

    @PostMapping(value = "/create_role_privilege_mappings", consumes = "application/json")
    public String createRolePrivilegeMapping(
            @RequestBody String rolePrivilegeJSONArray,
            Model model
    ) {
        List<RolePrivilegeMapModel> rolePrivilegeMapModelList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            List<RolePrivilegeJSONModel> decodedRolePrivilegeList = objectMapper.readValue(rolePrivilegeJSONArray, new TypeReference<>() {});

            for(RolePrivilegeJSONModel rolePrivilege : decodedRolePrivilegeList){

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
        return "role_privilege";
    }

    private void attributeSetup(Model model) {
        List<RolePrivilegeMapModel> rolePrivilegeMapModelList = new ArrayList<>();
        PrivilegeTable privilegeTable = new PrivilegeTable();
        privilegeTable.setSchema(userManagementSchema);
        privilegeTable.setQueryExecutor(queryExecutor);
        privilegeTable.setSqlDialect(sqlDialect);
        List<PrivilegeModel> privilegeModelList = null;
        try {
            privilegeModelList = privilegeTable.selectPrivileges();
            model.addAttribute("privilege_list", privilegeModelList);
        }catch (SQLException e){
            // log errors and exceptions
            System.out.println(e.getMessage());
        }finally {
            model.addAttribute("privilege_list", new ArrayList<PrivilegeModel>());
        }
        RoleTable roleTable = new RoleTable();
        roleTable.setSchema(userManagementSchema);
        roleTable.setQueryExecutor(queryExecutor);
        roleTable.setSqlDialect(sqlDialect);
        List<RoleModel> roleModelList = null;
        try {
            roleModelList = roleTable.selectRoles();
            model.addAttribute("role_list", roleModelList);
        }catch (SQLException e){
            // log errors and exceptions
            System.out.println(e.getMessage());
        }finally {
            if((roleModelList != null && !roleModelList.isEmpty())
                    && (privilegeModelList != null && !privilegeModelList.isEmpty())
            ){
                for (RoleModel roleModel : roleModelList) {
                    for (PrivilegeModel privilegeModel : privilegeModelList) {
                        RolePrivilegeMapModel rolePrivilegeMapModel1 = new RolePrivilegeMapModel(roleModel, privilegeModel, "");
                        rolePrivilegeMapModelList.add(rolePrivilegeMapModel1);
                    }
                }
                model.addAttribute("role_privilege_list", rolePrivilegeMapModelList);
            }
        }
    }


}
