package com.example.springauth.utilities.decoders.json;

import com.example.springauth.models.app.EntityPropModel;
import com.example.springauth.models.app.UserModel;
import com.example.springauth.models.json.EntityPropJSONModel;
import com.example.springauth.utilities.DateTime;
import com.example.springauth.utilities.UserIDGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


public class UserModelDecoder {


    private final String jsonEncodedEntityPropsArrayString;

    public UserModelDecoder(String jsonEncodedEntityPropsArrayString){
        this.jsonEncodedEntityPropsArrayString = jsonEncodedEntityPropsArrayString;
    }

    public UserModel decode() {

        List<EntityPropModel> entityPropModelList = new ArrayList<>();
        // Object mapper object to parse the JSON into property-name property-value pairs
        ObjectMapper objectMapper = new ObjectMapper();
        UserModel userModel = new UserModel();

        try{
            List<EntityPropJSONModel> entityPropJSONModelList = objectMapper.readValue(
                    jsonEncodedEntityPropsArrayString,
                    new TypeReference<>() {}
            );

            // Generate a user ID (pk) - mandatory, not needed when updating and should be over-written
            boolean hasIdPropertyAndValue = false;
            boolean hasCreatedAt = false;

            for (EntityPropJSONModel entityPropJSONModel : entityPropJSONModelList) {
                String propertyName = entityPropJSONModel.getPropertyName();
                String propertyValue = entityPropJSONModel.getPropertyValue();
                boolean hasValue = (propertyValue != null) && (!propertyValue.isEmpty());
                if(propertyName.equalsIgnoreCase("id") && hasValue)
                    hasIdPropertyAndValue = true;
                if(propertyName.equalsIgnoreCase("created_at") && hasValue)
                    hasCreatedAt = true;
                EntityPropModel entityPropModel = new EntityPropModel(propertyName, propertyValue);
                entityPropModelList.add(entityPropModel);
            }
            // Add a creation timestamp - mandatory
            if (!hasIdPropertyAndValue)
                entityPropModelList.add(new EntityPropModel("id", UserIDGenerator.generateUserID()));
            if (!hasCreatedAt)
                entityPropModelList.add(new EntityPropModel("created_at", DateTime.getTimeStamp()));

            userModel.setUserPropModelList(entityPropModelList);

        }catch (Exception e) {
            // log error
            System.out.println("Error creating a user profile: " + e.getMessage());
        }
        return userModel;
    }

}
