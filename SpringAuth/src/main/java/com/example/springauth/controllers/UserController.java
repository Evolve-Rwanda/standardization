package com.example.springauth.controllers;

import com.example.springauth.authentication.AuthenticationUtility;
import com.example.springauth.columns.Column;
import com.example.springauth.dialects.postgres.*;
import com.example.springauth.documentation.DatabaseDocumentation;
import com.example.springauth.markup.HTMLFormCreator;
import com.example.springauth.models.app.AdminModel;
import com.example.springauth.models.app.EntityPropModel;
import com.example.springauth.models.app.UserModel;
import com.example.springauth.models.app.UserPropModelList;
import com.example.springauth.models.jpa.AppUser;
import com.example.springauth.models.json.EntityPropJSONModel;
import com.example.springauth.models.utility.ColumnMarkupElementModel;
import com.example.springauth.models.utility.ColumnValueOptionModel;
import com.example.springauth.repositories.AppUserRepository;
import com.example.springauth.schemas.Schema;
import com.example.springauth.schemas.SchemaNameGiver;
import com.example.springauth.services.AppUserService;
import com.example.springauth.tables.Table;
import com.example.springauth.tables.TableNameGiver;
import com.example.springauth.utilities.CustomFileWriter;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.UserIDGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.*;


@Controller
public class UserController {



    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AppUserService appUserService;

    @Autowired
    private AppUserRepository appUserRepository;

    String sqlDialect = "POSTGRES";
    QueryExecutor queryExecutor = ConnectionSettings.getConnectionSettings().getQueryExecutor();
    Schema databaseDocumentationSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getDocumentationSchemaName());
    Schema userManagementSchema = SchemaSettings.getInstance().getSchema(SchemaNameGiver.getUserManagementSchemaName());


    @GetMapping("/create_user_profile")
    public String createUserProfile(
            @ModelAttribute("createUserProfileForm")
            UserModel userModel,
            Model model
    ){

        // move this source code to the initialization zone and only return the markup file in this area
        DatabaseDocumentation databaseDocumentation = new DatabaseDocumentation(sqlDialect, queryExecutor, databaseDocumentationSchema);
        databaseDocumentation.generateDatabaseDocumentation();
        List<Table> tableList = databaseDocumentation.getTableList();
        List<ColumnValueOptionModel> columnValueOptionModelList = databaseDocumentation.getColumnValueOptionModelList();
        List<ColumnMarkupElementModel> columnMarkupElementModelList = databaseDocumentation.getColumnMarkupElementModelList();

        String userTableName = TableNameGiver.getUserTableName();
        List<EntityPropModel> userPropModelList = this.generateEntityPropModelList(
                userTableName,
                tableList,
                columnMarkupElementModelList,
                columnValueOptionModelList
        );
        UserPropModelList userPropModelList1 = new UserPropModelList(userPropModelList);
        model.addAttribute("userPropModelList", new EntityPropModel());
        model.addAttribute("userPropModelList1", userPropModelList1);

        String userFormMarkup = this.generateThymeleafViewMarkup("create_user_profile", userPropModelList, false);
        model.addAttribute("createUserForm", userFormMarkup);
        String resourcePath = "src/main/resources/templates/";
        this.generateFile(resourcePath + "user_profile.html", userFormMarkup);
        return "user_profile";
    }

    @PostMapping(value ="/create_user_profile", consumes = "application/json")
    public String adminRegistration(
            @RequestBody
            String entityPropModelJSONArray,
            Model model
    ) {

        UserModel userModel = this.decodeUserModel(entityPropModelJSONArray);

        if(!userModel.getUserPropModelList().isEmpty()){
            if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
                String userTableName = TableNameGiver.getUserTableName();
                var insertionObj = new PostgresDialectInsertion(queryExecutor, userManagementSchema, userTableName);
                UserModel returnedUserModel = insertionObj.insertUser(userModel);
                model.addAttribute(
                        "user_profile_submission",
                        "successfully created a user profile"
                );
            }
        }

        AppUser appUser = this.createAppUser(userModel);
        AppUser returnedUser = (appUser != null) ? appUserService.createUser(appUser) : null;

        if (returnedUser != null) {

            model.addAttribute(
                    "successfulRegistration",
                    String.format(
                            "successfully created admin user with email %s",
                            returnedUser.getUsername()
                    )
            );

        }else {
            model.addAttribute(
                    "unsuccessfulRegistration",
                    "Provided passwords do not match"
            );
        }

        model.addAttribute(
                "adminRegistrationForm",
                new AdminModel()
        );

        return "user_profile";
    }


    @PostMapping("/change_password")
    public String changePassword(){
        return "change_password";
    }


    @GetMapping("/update_user_profile")
    public String updateUserProfile(
            Model model
    ){
        this.setModelAttributes(model);
        return "update_user_profile";
    }

    @PostMapping(value ="/update_user_profile", consumes = "application/json")
    public String updateUserProfile(
            @RequestBody
            String entityPropModelJSONArray,
            Model model
    ){
        UserModel currentUserModel = this.getCurrentUserModel();
        UserModel userModel = this.decodeUserModel(entityPropModelJSONArray);

        if(!userModel.getUserPropModelList().isEmpty()){
            if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
                if (currentUserModel == null) {
                    String userTableName = TableNameGiver.getUserTableName();
                    var insertionObj = new PostgresDialectInsertion(queryExecutor, userManagementSchema, userTableName);
                    UserModel returnedUserModel = insertionObj.insertUser(userModel);
                    model.addAttribute(
                            "user_profile_submission",
                            "successfully created a user profile" +
                                    returnedUserModel.getPropertyValue("current_email")
                    );
                }else{
                    String currentUserId = currentUserModel.getPropertyValue("id");
                    String currentCreatedAt = currentUserModel.getPropertyValue("created_at");
                    // Restore the id and creation date generated with the assumption that the user may not exist.
                    userModel.setPropertyValue("id", currentUserId);
                    userModel.setPropertyValue("created_at", currentCreatedAt);
                    PostgresUpdate update = new PostgresUpdate(queryExecutor, userManagementSchema);
                    //update.update(userModel);
                }
            }
        }
        // rebuild the user model
        // update the user model based on the provided properties
        // user model properties directly map to columns in the user table in the UM schema in the DB
        // to check whether certain properties exist as means of verification, use the table of columns
        this.setModelAttributes(model);
        return "update_user_profile";
    }

    private void setModelAttributes(Model model) {
        DatabaseDocumentation databaseDocumentation = new DatabaseDocumentation(
                sqlDialect,
                queryExecutor,
                databaseDocumentationSchema
        );
        Map<String, String> propNameValueMap = new HashMap<>();
        List<Table> tableList = databaseDocumentation.getTableList();
        List<ColumnValueOptionModel> columnValueOptionModelList = databaseDocumentation.getColumnValueOptionModelList();
        List<ColumnMarkupElementModel> columnMarkupElementModelList = databaseDocumentation.getColumnMarkupElementModelList();

        // Get the current user using the context
        String sessionUsername = AuthenticationUtility.getCurrentUsername();
        if (sqlDialect.equalsIgnoreCase("POSTGRES")) {
            PostgresDialectSelection selection = new PostgresDialectSelection(queryExecutor, userManagementSchema);
            try {
                UserModel userModel = selection.selectUserByUsername(sessionUsername);
                if(userModel != null){
                    List<EntityPropModel> entityPropModelList = userModel.getUserPropModelList();
                    if (entityPropModelList != null && !entityPropModelList.isEmpty()) {
                        for (EntityPropModel entityPropModel : entityPropModelList) {
                            propNameValueMap.put(entityPropModel.getName(), entityPropModel.getValue());
                        }
                    }
                } else{
                    // check if the super admin or users in the app_user table that
                    AppUser appUser = this.getAppUser(sessionUsername);
                    if (appUser != null) {
                        propNameValueMap = this.setKnownProperties(appUser);
                    }
                    // set the well known properties
                }
            }catch (SQLException e) {
                // log the error
                System.out.println("Error retrieving session user information: " + e.getMessage());
            }
        }
        // rebuild the user model
        // update the user model based on the context
        // user model properties directly map to columns in the user table in the UM schema in the DB
        // to check whether certain properties exist as means of verification, use the table of columns
        String userTableName = TableNameGiver.getUserTableName();
        List<EntityPropModel> userPropModelList = this.generateEntityPropModelList(
                userTableName,
                tableList,
                columnMarkupElementModelList,
                columnValueOptionModelList
        );
        for(EntityPropModel entityPropModel : userPropModelList){
            String propName = entityPropModel.getName();
            if (!propNameValueMap.containsKey(propName)) {
                propNameValueMap.put(propName, " ");
            }
        }
        model.addAttribute("user_details", propNameValueMap);
        model.addAttribute("updateUserForm", " ");
        return;
    }

    private UserModel getCurrentUserModel(){
        PostgresDialectSelection selection = new PostgresDialectSelection(queryExecutor, userManagementSchema);
        UserModel retrievedUserModel = null;
        try {
            retrievedUserModel = selection.selectUserByUsername(AuthenticationUtility.getCurrentUsername());
        }catch(SQLException e) {
            // log error
            System.out.println("Error getting user profile: " + e.getMessage());
        }
        return retrievedUserModel;
    }

    private List<EntityPropModel> generateEntityPropModelList(
            String targetTableName,
            List<Table> tableList,
            List<ColumnMarkupElementModel> columnMarkupElementModelList,
            List<ColumnValueOptionModel> columnValueOptionModelList
    ) {
        Table entityTable = getTableByName(targetTableName, tableList);
        return this.getEntityPropModelList(
                entityTable,
                columnMarkupElementModelList,
                columnValueOptionModelList
        );
    }

    private Table getTableByName(String tableName, List<Table> tableList) {
        for (Table table : tableList) {
            if (table.getName().equalsIgnoreCase(tableName)) {
                return table;
            }
        }
        return null;
    }

    private List<EntityPropModel> getEntityPropModelList(
            Table entityTable,
            List<ColumnMarkupElementModel> columnMarkupElementModelList,
            List<ColumnValueOptionModel> columnValueOptionModelList
    ) {
        List<Column> tableColumns = null;
        List<EntityPropModel> entityPropModelList = new ArrayList<>();

        // M<K, V> CDMap, K=ColumnName, V=DataType
        try {

            tableColumns = entityTable.getUniversalColumnList();
            List<String> excludedColumnList = this.getExcludedFormFields();

            for (Column column : tableColumns) {

                String tableName = column.getTableName();
                String columnName = column.getName();

                // action is to create, simply hide the some fields when the action is to update
                if (excludedColumnList.contains(columnName.toLowerCase())) {
                    continue;
                }
                String columnFullyQualifiedName = tableName + "." + columnName;
                String dataType = column.getDataType();
                EntityPropModel entityPropModel = new EntityPropModel(columnName, null); // set the value only in the case of updating profiles

                ColumnMarkupElementModel columnMarkupElementModel = null;
                for (ColumnMarkupElementModel cmem : columnMarkupElementModelList) {
                    if (cmem.getColumnId().equalsIgnoreCase(columnFullyQualifiedName)) {
                        columnMarkupElementModel = cmem;
                        entityPropModel.setColumnMarkupElementModel(columnMarkupElementModel);
                        break;
                    }
                }
                if (columnMarkupElementModel != null) {
                    String columnMarkupColumnId = columnMarkupElementModel.getColumnId();
                    for (ColumnValueOptionModel cvoml : columnValueOptionModelList) {
                        String cvomlColumnId = cvoml.getColumnId();
                        if (columnMarkupColumnId.equalsIgnoreCase(cvomlColumnId)) {
                            entityPropModel.addColumnValueOptionModel(cvoml);
                        }
                    }
                }
                // if no UI input element is provided, the input-text element is the default input
                // and is assigned to the particular field/column here
                if (columnMarkupElementModel == null) {
                    String columnId = column.getTableName() + "." + column.getName();
                    String nameAttributeValue = column.getName().toLowerCase();
                    columnMarkupElementModel = new ColumnMarkupElementModel(columnId, "input", "text", nameAttributeValue, false);
                    entityPropModel.setColumnMarkupElementModel(columnMarkupElementModel);
                }
                entityPropModelList.add(entityPropModel);
            }

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        return entityPropModelList;
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

    private String generateThymeleafViewMarkup(
            String formAction,
            List<EntityPropModel> entityPropModelList,
            boolean isUpdateForm
    ){
        HTMLFormCreator htmlFormCreator = new HTMLFormCreator(formAction, entityPropModelList, isUpdateForm);
        return htmlFormCreator.create();
    }

    private void generateFile(String fileName, String contents) {
        CustomFileWriter customFileWriter = new CustomFileWriter();
        customFileWriter.writeFile(fileName, contents);
    }

    private AppUser getAppUser(String username) {
        Optional<AppUser> appSetupUser = appUserRepository.findByUsername(username);
        return appSetupUser.orElse(null);
    }

    private Map<String, String> setKnownProperties(AppUser appUser) {
        // the fields here are highly dependent on knowing the underlying model,
        // the assumption is that the super admin's schema needs not to change from project to project
        // and so it not supposed to be a big issue when setting up.
        // Any changes to the schema will necessitate changes to the number and properties set here.
        Map<String, String> propertyValueMap = new HashMap<>();
        propertyValueMap.put("current_email", appUser.getUsername());
        propertyValueMap.put("current_password", appUser.getPassword());
        propertyValueMap.put("first_name", appUser.getFirstName());
        propertyValueMap.put("last_name", appUser.getLastName());
        propertyValueMap.put("other_names", appUser.getUsername());
        propertyValueMap.put("current_phone_number", appUser.getPhoneNumber());
        return propertyValueMap;
    }

    // create classes under utilities for json decoders in a decoders sub-package
    public UserModel decodeUserModel(String jsonEncodedEntityPropsArrayString) {

        List<EntityPropModel> entityPropModelList = new ArrayList<>();
        // Object mapper object to parse the JSON into property-name property-value pairs
        ObjectMapper objectMapper = new ObjectMapper();
        UserModel userModel = new UserModel();

        try{

            List<EntityPropJSONModel> entityPropJSONModelList = objectMapper.readValue(
                    jsonEncodedEntityPropsArrayString,
                    new TypeReference<>() {}
            );

            // Generate a user ID (pk) - mandatory, not needed since we are updating
            entityPropModelList.add(new EntityPropModel("id", UserIDGenerator.generateUserID()));
            for (EntityPropJSONModel entityPropJSONModel : entityPropJSONModelList) {
                String propertyName = entityPropJSONModel.getPropertyName();
                String propertyValue = entityPropJSONModel.getPropertyValue();
                EntityPropModel entityPropModel = new EntityPropModel(propertyName, propertyValue);
                entityPropModelList.add(entityPropModel);
            }
            // Add a creation timestamp - mandatory
            entityPropModelList.add(new EntityPropModel("created_at", DateTime.getTimeStamp()));

            userModel.setUserPropModelList(entityPropModelList);

        }catch (Exception e) {
            // log error
            System.out.println("Error creating a user profile: " + e.getMessage());
        }
        return userModel;
    }

    private AppUser createAppUser(UserModel userModel) {
        String firstName = userModel.getPropertyValue("first_name");
        String lastName = userModel.getPropertyValue("last_name");
        String otherNames = userModel.getPropertyValue("other_names");
        String phoneNumber = userModel.getPropertyValue("current_phone_number");
        String username = userModel.getPropertyValue("current_email");
        String password = userModel.getPropertyValue("current_password");
        //String confirmFormPassword = userModel.getPropertyValue("confirm_password");
        String role = "ENTITY_ADMIN";
        String passwordCipher = passwordEncoder.encode(password);

        //boolean passwordsMatch = formPassword.equals(confirmFormPassword);

        if (!passwordCipher.isEmpty()) {

            AppUser appUser = new AppUser(
                    firstName,
                    lastName,
                    otherNames,
                    phoneNumber,
                    username,
                    passwordCipher,
                    role
            );
            return appUser;
        }
        return null;
    }

}
