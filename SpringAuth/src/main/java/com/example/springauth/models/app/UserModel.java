package com.example.springauth.models.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserModel {


    private List<EntityPropModel> entityPropModelList;
    private Map<String, String> nameValueMap;

    public UserModel() {
        nameValueMap = new HashMap<>();
    }

    public UserModel(List<EntityPropModel> entityPropModelList) {
        this.entityPropModelList = entityPropModelList;
    }

    public List<EntityPropModel> getUserPropModelList() {
        return entityPropModelList;
    }

    public void setUserPropModelList(List<EntityPropModel> entityPropModelList) {
        for (EntityPropModel entityPropModel : entityPropModelList) {
            nameValueMap.put(entityPropModel.getName(), entityPropModel.getValue());
        }
        this.entityPropModelList = entityPropModelList;
    }

    public String getPropertyValue(String name) {
        return nameValueMap.get(name);
    }

    public void setPropertyValue(String name, String value) {
        for (EntityPropModel entityPropModel : this.entityPropModelList) {
            if (entityPropModel.getName().equalsIgnoreCase(name)) {
                // keep the prop list and map equally updated
                // this approach is slow for long prop lists, perhaps use a hashset instead of a list
                entityPropModel.setValue(value);
                break;
            }
        }
        nameValueMap.put(name, value);
    }
}
