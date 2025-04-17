package com.example.springauth;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DatabaseController {

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

    @PostMapping("add_column")
    public String addColumn(@RequestParam("column") String column) {
        return "Column added successfully";
    }

    @GetMapping("columns")
    public String getColumnList(){
        return "Column List is available";
    }

    @GetMapping("/documentation")
    public String getDocumentation(){
        return "Database documentation is available";
    }

    @GetMapping("initialize_application")
    public String initializeApplication(){
        return "Database successfully initialized";
    }
}
