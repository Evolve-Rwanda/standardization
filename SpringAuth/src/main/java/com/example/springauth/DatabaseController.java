package com.example.springauth;

import com.example.springauth.documentation.DatabaseDocumentation;
import com.example.springauth.dialects.postgres.DatabaseCredentials;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaGenerator;

import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.tables.Table;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class DatabaseController {

    String databaseName = "standardization";
    String jdbcUrl = "jdbc:postgresql://localhost:5432/" + databaseName;
    String username = "postgres";
    String password = "evolve";

    // Initialize the SQL dialect to be used. This impacts everything onwards.
    String sqlDialect = "POSTGRES";
    DatabaseCredentials databaseCredentials = new DatabaseCredentials(jdbcUrl, username, password);
    QueryExecutor queryExecutor = new QueryExecutor(databaseCredentials);

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    Map<String, Schema> nameSchemaMap = schemaGenerator.getNameSchemaMap();
    Schema databaseDocumentationSchema = nameSchemaMap.get(SchemaNameGiver.getDocumentationSchemaName());


    @PostMapping("/add_schema")
    public String addSchema(@RequestParam("schema") String schema) {
        return "Schema added successfully";
    }

    @GetMapping("/schemas")
    public String getSchemaList(){
        return "Schema List is available";
    }

    @PostMapping("/add_table")
    public String addTable(@RequestParam("table") String table) {
        return "Table added successfully";
    }

    @GetMapping("/tables")
    public String getTableList(){
        return "Table List is available";
    }

    @PostMapping("/add_relationship")
    public String addRelationship(@RequestParam("from") String from, @RequestParam("to") String to) {
        return "Relationship added successfully";
    }

    @GetMapping("/relationships")
    public String getRelationshipList(){
        return "Relationship List is available";
    }

    @PostMapping("/add_column")
    public String addColumn(@RequestParam("column") String column) {
        return "Column added successfully";
    }

    @GetMapping("/columns")
    public String getColumnList(){
        return "Column List is available";
    }

    @GetMapping("/documentation")
    public String getDocumentation(Model model) {
        /* Deliverable - 1/2. Obtain database documentation */
        DatabaseDocumentation databaseDocumentation = new DatabaseDocumentation(sqlDialect, queryExecutor, databaseDocumentationSchema);
        String documentationMarkup = databaseDocumentation.generateDatabaseDocumentation();
        Schema documentationSchema = nameSchemaMap.get(SchemaNameGiver.getDocumentationSchemaName());
        List<String> schemaNameList = databaseDocumentation.getSchemaNameList();
        List<Schema> schemaList = databaseDocumentation.getSchemaList();
        Map<String, List<String>> schemaNameTableNameListMap = databaseDocumentation.getSchemaNameTableNameMap();
        List<Table> tableList = databaseDocumentation.getTableList();
        model.addAttribute("documentation_schema", documentationSchema);
        model.addAttribute("app_schema_list", schemaList);
        model.addAttribute("other_schema_list", schemaNameList);
        model.addAttribute("schema_name_table_list_map", schemaNameTableNameListMap);
        System.out.println(tableList);
        model.addAttribute("table_list", tableList);
        //model.addAttribute("documentation", documentationMarkup);
        return "home";
    }

    @GetMapping("/initialize_application")
    public String initializeApplication(){
        return "Database successfully initialized";
    }


    public String getSchemaListJSON(){
        // for every schema object, call its toJSON() method
        return null;
    }

    public List<String> getTableListJSON(){
        // for every table object, call its toJSON method
        return null;
    }

    public List<String> getRelationshipListJSON(){
        // for every relationship object, call its toJSON method
        return null;
    }

    public List<String> getColumnListJSON(){
        // for every relationship object, call its toJSON method
        return null;
    }
}
