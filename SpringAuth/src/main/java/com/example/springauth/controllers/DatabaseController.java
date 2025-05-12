package com.example.springauth.controllers;

import com.example.springauth.columns.Column;
import com.example.springauth.columns.ColumnInitializer;
import com.example.springauth.columns.ColumnMarkupElement;
import com.example.springauth.columns.ColumnValueOption;
import com.example.springauth.dialects.postgres.PostgresDialect;
import com.example.springauth.documentation.DatabaseDocumentation;
import com.example.springauth.dialects.postgres.DatabaseCredentials;
import com.example.springauth.dialects.postgres.QueryExecutor;
import com.example.springauth.markup.HTMLFormCreator;
import com.example.springauth.models.app.UserModel;
import com.example.springauth.models.app.UserPropModel;
import com.example.springauth.models.utility.*;
import com.example.springauth.relationships.Relationship;
import com.example.springauth.relationships.RelationshipResolver;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaGenerator;

import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.specialtables.*;
import com.example.springauth.tables.Table;
import com.example.springauth.tables.TableNameGiver;
import com.example.springauth.utilities.CustomFileWriter;
import com.example.springauth.utilities.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    Schema userManagementSchema = nameSchemaMap.get(SchemaNameGiver.getUserManagementSchemaName());


    @GetMapping("/")
    public String root(Model model) {
        return "index";
    }


    @GetMapping("/index")
    public String indexRequest(Model model) {
        return "index";
    }


    @GetMapping("/index.html")
    public String indexPageRequest(Model model) {
        return "index";
    }


    @GetMapping("/home")
    public String homePageRequest(Model model) {
        attributeSetup(model);
        return "home";
    }


    @PostMapping("/add_schema")
    public String addSchema(@ModelAttribute("schemaForm") SchemaModel schemaModel, Model model) {
        schemaModel.setCreatedAt(DateTime.getTimeStamp());
        schemaModel.setDeletedAt("");
        Schema schema = new Schema();
        schema.setName(schemaModel.getName());
        schema.setDescription(schemaModel.getDescription());
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
        String[] left = leftTableName.split("\\.");
        String[] right = rightTableName.split("\\.");
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

    @PostMapping("add_column_value_options")
    public String addColumnOptionalValue(@ModelAttribute("columnValueOptionsForm") ColumnValueOptionModel columnValueOptionModel, Model model) {
        var tableOfColumnValueOptions = new TableOfColumnValueOptions(queryExecutor, sqlDialect, databaseDocumentationSchema);
        String columnId = columnValueOptionModel.getColumnId();
        String optionalValue = columnValueOptionModel.getOptionalValue();
        List<ColumnValueOption> columnValueOptionList = new ArrayList<>();
        columnValueOptionList.add(new ColumnValueOption(columnId, optionalValue));
        tableOfColumnValueOptions.documentColumnValueOptions(columnValueOptionList);
        model.addAttribute("columnValueOptionAdded", "You have successfully registered a column value option.");
        attributeSetup(model);
        return "home";
    }

    @PostMapping("add_column_input_markup_element")
    public String addColumnInputMarkupElement(
            @ModelAttribute("columnInputElementMarkupForm")ColumnMarkupElementModel columnMarkupElementModel,
            Model model) {
        var tableOfColumnInputMarkupElements = new TableOfColumnInputMarkupElements(queryExecutor, sqlDialect, databaseDocumentationSchema);
        String columnId = columnMarkupElementModel.getColumnId();
        String[] tagAndTypeAttrib = columnMarkupElementModel.getTagName().split("-");
        String tagName = tagAndTypeAttrib.length > 0 ? tagAndTypeAttrib[0] : "";
        String typeAttributeValue = tagAndTypeAttrib.length > 1 ? tagAndTypeAttrib[1] : "";
        String nameAttributeValue = columnMarkupElementModel.getNameAttributeValue();
        boolean isMutuallyExclusive = columnMarkupElementModel.isMutuallyExclusive();
        List<ColumnMarkupElement> columnMarkupElementList = new ArrayList<>();
        columnMarkupElementList.add(new ColumnMarkupElement(columnId, tagName, typeAttributeValue, nameAttributeValue, isMutuallyExclusive));
        tableOfColumnInputMarkupElements.documentColumnMarkupElements(columnMarkupElementList);
        model.addAttribute("columnElementMarkupAdded", "You have successfully registered a column markup element.");
        attributeSetup(model);
        return "home";
    }


    @GetMapping("/documentation")
    public String getDocumentation(Model model) {
        attributeSetup(model);
        return "home";
    }

    @GetMapping("/initialize_authentication")
    public String initializeAuthentication(Model model) {
        attributeSetup(model);
        List<Schema> schemaList = this.getDatabaseSchemaList();
        // Creating physical schemas in the database. These are especially those registered or tailored to the project
        TableOfSchemas schemaTable = new TableOfSchemas(queryExecutor, sqlDialect, databaseDocumentationSchema);
        schemaTable.createDatabaseSchemas(schemaList);
        String tableOfTables = SpecialTableNameGiver.getTableOfTablesName();
        List<Table> tableList = getTableList(tableOfTables);
        tableList = removeSpecialTables(tableList);
        List<Relationship> relationshipList = this.getTableRelationships(SpecialTableNameGiver.getTableOfRelationshipsName());

        List<Table> enhancedTableList = this.ensureEntityAndReferentialIntegrity(relationshipList);
        tableList = this.replaceWithEnhancedTables(tableList, enhancedTableList);
        // Add common column list
        ColumnInitializer colInit = ColumnInitializer.getColumnInitializer(databaseDocumentationSchema, userManagementSchema);
        List<Column> commonColumnList = colInit.getCommonColumnList();

        List<Table> derivedTableList = getDerivedTables(relationshipList);
        derivedTableList = this.addCommonColumns(derivedTableList, commonColumnList);

        tableList = this.addCommonColumns(tableList, commonColumnList);
        // Replace all derived tables in the database with new ones generated from resolving relationships
        List<Table> newTableList = new ArrayList<>();
        for (Table table : tableList) {
            if(derivedTableList.contains(table))
                continue;
            newTableList.add(table);
        }
        newTableList.addAll(derivedTableList);
        newTableList = reorganizeTables(tableList);
        Table.createTables(newTableList);
        model.addAttribute("authInitializationMessage", "You have successfully initialized authentication.");
        return "home";
    }

    @PostMapping("/create_user_profile")
    public String createUserProfile(
            @ModelAttribute("createUserForm")
            UserPropModel userPropModel,
            Model model
    ) {
        System.out.println(userPropModel);
        attributeSetup(model);
        return "user_profile";
    }

    @GetMapping("/create_user_profile")
    public String createUserProfile(
            @ModelAttribute("updateUserProfileForm")
            UserModel userModel,
            Model model
    ){
        // rebuild the user model
        DatabaseDocumentation databaseDocumentation = new DatabaseDocumentation(sqlDialect, queryExecutor, databaseDocumentationSchema);
        databaseDocumentation.generateDatabaseDocumentation();
        List<Table> tableList = databaseDocumentation.getTableList();
        List<ColumnMarkupElementModel> columnMarkupElementModelList = databaseDocumentation.getColumnMarkupElementModelList();
        List<ColumnValueOptionModel> columnValueOptionModelList = databaseDocumentation.getColumnValueOptionModelList();

        Table userTable = null;
        for (Table table : tableList) {
            String userTableName = TableNameGiver.getUserTableName();
            if (table.getName().equalsIgnoreCase(userTableName)) {
                userTable = table;
                break;
            }
        }
        List<Column> userTableColumns = null;
        List<UserPropModel> userPropModelList = new ArrayList<>();

        try{

            userTableColumns = userTable.getUniversalColumnList();
            List<String> excludedColumnList = this.getExcludedFormFields();

            for (Column column : userTableColumns) {

                String tableName = column.getTableName();
                String columnName = column.getName();

                // action is to create, simply hide the some fields when the action is to update
                if (excludedColumnList.contains(columnName.toLowerCase())){
                    continue;
                }
                String columnFullyQualifiedName = tableName + "." + columnName;
                String dataType = column.getDataType();
                UserPropModel userPropModel = new UserPropModel(columnName, null); // set the value only in the case of updating profiles

                ColumnMarkupElementModel columnMarkupElementModel = null;
                for(ColumnMarkupElementModel cmem : columnMarkupElementModelList) {
                    if(cmem.getColumnId().equalsIgnoreCase(columnFullyQualifiedName)) {
                        columnMarkupElementModel = cmem;
                        userPropModel.setColumnMarkupElementModel(columnMarkupElementModel);
                        break;
                    }
                }
                if (columnMarkupElementModel != null) {
                    String columnMarkupColumnId = columnMarkupElementModel.getColumnId();
                    for (ColumnValueOptionModel cvoml: columnValueOptionModelList){
                        String cvomlColumnId = cvoml.getColumnId();
                        if (columnMarkupColumnId.equalsIgnoreCase(cvomlColumnId)) {
                            userPropModel.addColumnValueOptionModel(cvoml);
                        }
                    }
                }
                // if no UI input element is provided, the input-text element is the default input
                // and is assigned to the particular field/column here
                if (columnMarkupElementModel == null) {
                    String columnId = column.getTableName() + "." + column.getName();
                    String nameAttributeValue = column.getName().toLowerCase();
                    columnMarkupElementModel = new ColumnMarkupElementModel(columnId, "input", "text", nameAttributeValue, false);
                    userPropModel.setColumnMarkupElementModel(columnMarkupElementModel);
                }
                userPropModelList.add(userPropModel);
            }
            HTMLFormCreator htmlFormCreator = new HTMLFormCreator("create_user_profile", userPropModelList);
            String userProfileMarkup = htmlFormCreator.create();
            CustomFileWriter customFileWriter = new CustomFileWriter();
            String resourcePath = "src/main/resources/templates/";
            customFileWriter.writeFile(resourcePath + "user_profile.html", userProfileMarkup);
            System.out.println(htmlFormCreator.create());
            model.addAttribute("createUserProfileForm", htmlFormCreator.create());
            attributeSetup(model);

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        model.addAttribute("UserProfileCreatedMessage", "You have successfully created a user profile.");
        return "user_profile";
    }

    @PostMapping("/change_password")
    public String changePassword(){
        return "profile";
    }

    @PostMapping("/update_user_profile")
    public String updateUserProfile(
            @ModelAttribute("updateUserProfileForm")
            UserModel userModel,
            Model model
    ){
        model.addAttribute("UserProfileUpdatedMessage", "You have successfully updated user profile.");
        // rebuild the user model
        // update the user model based on the provided properties
        // user model properties directly map to columns in the user table in the UM schema in the DB
        // to check whether certain properties exist as means of verification, use the table of columns
        return "home";
    }

    @GetMapping("/initialize_logging")
    public String initializeLogging(Model model) {
        model.addAttribute("loggingInitializationMessage", "You have successfully initialized logging.");
        attributeSetup(model);
        return "home";
    }


    private List<Table> reorganizeTables(List<Table> tableList) {
        // Strong tables must be created first and weak tables created last for all SQL dialects
        // This is intended to avoid sql integrity errors.
        List<Table> reorganizedTableList = new ArrayList<>();
        List<Table> strongTableList = new ArrayList<>();
        List<Table> weakTableList = new ArrayList<>();
        for (Table table : tableList) {
            List<Column> foreignKeyList = table.getForeignKeyList();
            if(foreignKeyList != null && !foreignKeyList.isEmpty()) {
                weakTableList.add(table);
            }else {
                strongTableList.add(table);
            }
        }
        reorganizedTableList.addAll(strongTableList);
        reorganizedTableList.addAll(weakTableList);
        return reorganizedTableList;
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
        List<ColumnValueOptionModel> columnValueOptionModelList = databaseDocumentation.getColumnValueOptionModelList();
        List<ColumnMarkupElementModel> columnMarkupElementModelList = databaseDocumentation.getColumnMarkupElementModelList();

        model.addAttribute("documentation_schema", documentationSchema.getName());
        model.addAttribute("app_schema_list", schemaList);
        model.addAttribute("all_schema_list", schemaNameList);
        model.addAttribute("schema_name_table_list_map", schemaNameTableNameListMap);
        model.addAttribute("table_list", tableList);
        model.addAttribute("relationship_list", relationshipList);
        model.addAttribute("column_value_option_list", columnValueOptionModelList);
        model.addAttribute("column_markup_element_list", columnMarkupElementModelList);

        model.addAttribute("schemaForm", new SchemaModel());
        model.addAttribute("tableForm", new TableModel());
        model.addAttribute("relationshipForm", new RelationshipModel());
        model.addAttribute("columnForm", new ColumnModel());
        model.addAttribute("columnValueOptionsForm", new ColumnValueOptionModel());
        model.addAttribute("columnInputElementMarkupForm", new ColumnMarkupElementModel());
        model.addAttribute("createUserForm", new UserPropModel());
    }

    private List<Schema> getDatabaseSchemaList(){
        List<Schema> schemaList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor);
            schemaList = postgresDialect.getDatabaseSchemaList();
        }
        return schemaList;
    }

    private List<Table> getTableList(String searchTable){
        List<Table> tableList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, searchTable, databaseDocumentationSchema);
            tableList = postgresDialect.getTableList(searchTable);
        }
        return tableList;
    }

    private List<Table> removeSpecialTables(List<Table> tableList){
        List<String> specialTableNameList = SpecialTableNameGiver.getSpecialTableNameList();
        List<Table> nonSpecialTableList = new ArrayList<>();
        for (Table table : tableList) {
            if (!specialTableNameList.contains(table.getName())) {
                nonSpecialTableList.add(table);
            }
        }
        return nonSpecialTableList;
    }

    private List<Relationship> getTableRelationships(String searchTable){
        List<Relationship> relationshipList = new ArrayList<>();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialect postgresDialect = new PostgresDialect(queryExecutor, searchTable, databaseDocumentationSchema);
            relationshipList = postgresDialect.getTableRelationships(searchTable);
        }
        return relationshipList;
    }

    private List<Table> getDerivedTables(List<Relationship> relationshipList){
        RelationshipResolver relationshipResolver = new RelationshipResolver(relationshipList);
        List<Table> derivedTableList = relationshipResolver.resolveManyToManyRelationships();
        for(Table table : derivedTableList){
            table.setSqlDialect(sqlDialect);
            table.setQueryExecutor(queryExecutor);
        }
        return derivedTableList;
    }

    private List<String> getExcludedFormFields(){
        List<String> excludedFormFields = new ArrayList<>();
        excludedFormFields.add("id");
        excludedFormFields.add("authentication_hash");
        excludedFormFields.add("created_at");
        excludedFormFields.add("last_updated_at");
        excludedFormFields.add("deleted_at");
        return excludedFormFields;
    }

    private List<Table> ensureEntityAndReferentialIntegrity(List<Relationship> relationshipList){
        RelationshipResolver relationshipResolver = new RelationshipResolver(relationshipList);
        return relationshipResolver.ensureEntityAndReferentialIntegrity();
    }

    private List<Table> replaceWithEnhancedTables(List<Table> tableList, List<Table> enhancedTableList){
        List<Table> newTableList = new ArrayList<>(enhancedTableList);
        for (Table table: tableList)
            if (!newTableList.contains(table))
                newTableList.add(table);
        return newTableList;
    }


    private List<Table> addCommonColumns(List<Table> tableList, List<Column> commonColumnList){
        List<Table> newTableList = new ArrayList<>();
        for(Table table: tableList){
            String tableFQN = table.getFullyQualifiedName();
            List<Column> currentColumnList = table.getUniversalColumnList();
            List<Column> newCommonColumnList = new ArrayList<>();
            for (Column column: commonColumnList){
                try {
                    if(currentColumnList.contains(column))
                        continue;
                    Column clone = column.clone();
                    clone.setTableName(tableFQN);
                    newCommonColumnList.add(clone);
                }catch (CloneNotSupportedException e){
                    System.out.println(e.getMessage());
                }
            }
            table.setBaseColumnList(newCommonColumnList);
            newTableList.add(table);
        }
        return newTableList;
    }


}
