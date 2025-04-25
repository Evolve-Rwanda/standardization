package com.example.springauth;

import com.example.springauth.columns.Column;
import com.example.springauth.documentation.DatabaseDocumentation;
import com.example.springauth.dialects.postgres.DatabaseCredentials;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.models.ColumnModel;
import com.example.springauth.models.RelationshipModel;
import com.example.springauth.relationships.Relationship;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaGenerator;

import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.specialtables.TableOfColumns;
import com.example.springauth.specialtables.TableOfRelationships;
import com.example.springauth.specialtables.TableOfSchemas;
import com.example.springauth.specialtables.TableOfTables;
import com.example.springauth.tables.Table;
import com.example.springauth.models.TableModel;
import com.example.springauth.utilities.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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


    @GetMapping("/")
    public String home(Model model) {
        attributeSetup(model);
        return "home";
    }

    @PostMapping("/add_schema")
    public String addSchema(@ModelAttribute("schemaForm") Schema schema, Model model) {
        schema.setCreatedAt(DateTime.getTimeStamp());
        schema.setDeletedAt("");
        List<Schema> schemaList = new ArrayList<>();
        schemaList.add(schema);
        var schemaTable = new TableOfSchemas(queryExecutor, sqlDialect, databaseDocumentationSchema);
        schemaTable.documentSchemas(schemaList);
        model.addAttribute("schemaAddedMessage", "Schema registered successfully");
        attributeSetup(model);
        return "home";
    }

    @PostMapping("/add_table")
    public String addTable(@ModelAttribute("tableForm") TableModel tableModel, Model model) {
        var tableOfTables = new TableOfTables(queryExecutor, sqlDialect, databaseDocumentationSchema);
        String schemaName =  tableModel.getSchema();
        String tableName = tableModel.getName();
        String description = tableModel.getDescription();
        Schema schema = new Schema(schemaName, "", "", "");
        Table table = new Table(tableName, null); // columns registered afterwards
        table.setSchema(schema);
        table.setDescription(description);
        List<Table> tableList = new ArrayList<>();
        tableList.add(table);
        tableOfTables.documentTables(tableList);
        model.addAttribute("tableAddedMessage", "You have successfully registered a table.");
        attributeSetup(model);
        return "home";
    }

    @PostMapping("/add_relationship")
    public String addRelationship(@ModelAttribute("relationshipForm") RelationshipModel relationshipModel, Model model) {
        var tableOfRelationships = new TableOfRelationships(queryExecutor, sqlDialect, databaseDocumentationSchema);
        String leftTableName = relationshipModel.getLeftTableName();
        String rightTableName = relationshipModel.getRightTableName();
        String type = relationshipModel.getType();
        String description = relationshipModel.getDescription();
        String[] left = leftTableName.split(".");
        String[] right = rightTableName.split(".");
        String leftTableSchemaName = left.length > 0 ? left[0] : "";
        String rightTableSchemaName = right.length > 0 ? right[0] : "";
        Schema leftSchema = new Schema(leftTableSchemaName, "", "", "");
        Schema rightSchema = new Schema(rightTableSchemaName, "", "", "");
        Table leftTable = new Table(leftTableName, null);
        leftTable.setSchema(leftSchema);
        Table rightTable = new Table(rightTableName, null);
        rightTable.setSchema(rightSchema);
        Relationship relationship = new Relationship(leftTable, rightTable, type);
        relationship.setDescription(description);
        List<Relationship> relationshipList = new ArrayList<>();
        relationshipList.add(relationship);
        tableOfRelationships.documentRelationships(relationshipList);
        model.addAttribute("relationshipAddedMessage", "You have successfully registered a relationship.");
        attributeSetup(model);
        return "home";
    }

    @PostMapping("/add_column")
    public String addColumn(@ModelAttribute("columnForm") ColumnModel columnModel, Model model) {
        var tableOfColumns = new TableOfColumns(queryExecutor, sqlDialect, databaseDocumentationSchema);
        String tableName = columnModel.getTableName();
        String name = columnModel.getName();
        int number = columnModel.getNumber();
        String dataType = columnModel.getDataType();
        long precision = columnModel.getPrecision();
        long scale = columnModel.getScale();
        String defaultValue = columnModel.getDefaultValue();
        boolean isNullable = columnModel.getIsNullable();
        boolean isAFactBasedModel = columnModel.getIsAFactBasedColumn();
        boolean isEncrypted = columnModel.getIsEncrypted();
        boolean isPK = columnModel.getIsPK();
        boolean isFK = columnModel.getIsFK();
        boolean isIndexed = columnModel.getIsIndexed();
        String referenceTableName = columnModel.getReferenceTableName();
        String referenceColumnName = columnModel.getReferenceColumnName();
        String onUpdateAction = columnModel.getOnUpdateAction();
        String onDeleteAction = columnModel.getOnDeleteAction();
        String description = columnModel.getDescription();
        String createdAt = DateTime.getTimeStamp();
        String deletedAt = columnModel.getDeletedAt();
        Column column = new Column(
                tableName,
                name,
                number,
                dataType,
                precision,
                scale,
                defaultValue,
                isNullable,
                isAFactBasedModel,
                isEncrypted,
                isPK,
                isFK,
                isIndexed,
                referenceTableName,
                referenceColumnName,
                onUpdateAction,
                onDeleteAction,
                description,
                createdAt,
                ""
        );
        List<Column> columnList = new ArrayList<>();
        columnList.add(column);
        tableOfColumns.documentColumns(columnList);
        model.addAttribute("columnAddedMessage", "You have successfully registered a column.");
        attributeSetup(model);
        return "home";
    }


    @GetMapping("/documentation")
    public String getDocumentation(Model model) {
        attributeSetup(model);
        return "home";
    }

    private void attributeSetup(Model model) {
        /* Deliverable - 1/2. Obtain database documentation */
        DatabaseDocumentation databaseDocumentation = new DatabaseDocumentation(sqlDialect, queryExecutor, databaseDocumentationSchema);
        databaseDocumentation.generateDatabaseDocumentation();
        Schema documentationSchema = nameSchemaMap.get(SchemaNameGiver.getDocumentationSchemaName());
        List<String> schemaNameList = databaseDocumentation.getSchemaNameList();
        List<Schema> schemaList = databaseDocumentation.getSchemaList();
        Map<String, List<String>> schemaNameTableNameListMap = databaseDocumentation.getSchemaNameTableNameMap();
        List<Table> tableList = databaseDocumentation.getTableList();
        List<Relationship> relationshipList = databaseDocumentation.getRelationshipList();
        model.addAttribute("documentation_schema", documentationSchema.getName());
        model.addAttribute("app_schema_list", schemaList);
        model.addAttribute("all_schema_list", schemaNameList);
        model.addAttribute("schema_name_table_list_map", schemaNameTableNameListMap);
        model.addAttribute("table_list", tableList);
        model.addAttribute("relationship_list", relationshipList);
        model.addAttribute("schemaForm", new Schema());
        model.addAttribute("tableForm", new TableModel());
        model.addAttribute("relationshipForm", new RelationshipModel());
        model.addAttribute("columnForm", new ColumnModel());
    }


}
