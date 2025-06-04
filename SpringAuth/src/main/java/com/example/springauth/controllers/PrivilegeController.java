package com.example.springauth.controllers;

import com.example.springauth.dialects.postgres.ConnectionSettings;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.dialects.postgres.SchemaSettings;
import com.example.springauth.models.app.PrivilegeModel;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.PrivilegeTable;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.PrivilegeIdGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class PrivilegeController {


    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema userManagementSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getUserManagementSchemaName());


    // Receive json instead, eventually to decouple the front and backend
    // this approach is strongly tied to thymleaf
    @GetMapping("/create_user_privilege")
    public String createUserPrivilege(Model model) {
        model.addAttribute("privilegeForm", new PrivilegeModel());
        return "privilege";
    }

    @PostMapping("/create_user_privilege")
    public String createUserPrivilege(
            @ModelAttribute("privilegeForm")
            PrivilegeModel privilegeModel,
            Model model
    ) {
        String id = PrivilegeIdGenerator.generatePrivilegeID();
        String code = privilegeModel.getCode();
        String name = privilegeModel.getName();
        String description = privilegeModel.getDescription();
        String approvalMethod = privilegeModel.getApprovalMethod();
        String createdBy = "SUPER_ADMIN";
        String status = privilegeModel.getStatus();
        String createdAt = DateTime.getTimeStamp();
        String lastUpdatedAt = "";
        String deletedAt = "";
        PrivilegeModel newPrivilegeModel = new PrivilegeModel(id, code, name, description, approvalMethod, createdBy, status, createdAt, lastUpdatedAt, deletedAt);

        PrivilegeTable privilegeTable = new PrivilegeTable();
        privilegeTable.setSchema(userManagementSchema);
        privilegeTable.setQueryExecutor(queryExecutor);
        privilegeTable.setSqlDialect(sqlDialect);
        PrivilegeModel insertedRole = privilegeTable.insertPrivilege(newPrivilegeModel);
        model.addAttribute("privilege", "successfully added a privilege: " + insertedRole.getName());
        model.addAttribute("privilegeForm", new PrivilegeModel());

        return "privilege";
    }

}
