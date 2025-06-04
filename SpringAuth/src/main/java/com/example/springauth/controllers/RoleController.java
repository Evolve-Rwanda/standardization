package com.example.springauth.controllers;

import com.example.springauth.dialects.postgres.ConnectionSettings;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.dialects.postgres.SchemaSettings;
import com.example.springauth.models.app.RoleModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.RoleTable;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.RoleIdGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RoleController {


    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema userManagementSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getUserManagementSchemaName());


    @GetMapping("/create_user_role")
    public String createUserProfile(Model model) {
        model.addAttribute("roleForm", new RoleModel());
        return "role";
    }

    @PostMapping("/create_user_role")
    public String createUserProfile(
            @ModelAttribute("roleForm")
            RoleModel roleModel,
            Model model
    ) {
        String id = RoleIdGenerator.generateRoleID();
        String code = roleModel.getCode();
        String name = roleModel.getName();
        String description = roleModel.getDescription();
        String createdBy = "SUPER_ADMIN";
        String status = roleModel.getStatus();
        String createdAt = DateTime.getTimeStamp();
        String lastUpdatedAt = "";
        String deletedAt = "";
        RoleModel newRoleModel = new RoleModel(id, code, name, description, createdBy, status, createdAt, lastUpdatedAt, deletedAt);

        RoleTable roleTable = new RoleTable();
        roleTable.setSchema(userManagementSchema);
        roleTable.setQueryExecutor(queryExecutor);
        roleTable.setSqlDialect(sqlDialect);

        RoleModel insertedRole = roleTable.insertRole(newRoleModel);

        model.addAttribute("role", "successfully added role: " + insertedRole.getName());
        model.addAttribute("roleForm", new RoleModel());
        return "role";
    }


}
