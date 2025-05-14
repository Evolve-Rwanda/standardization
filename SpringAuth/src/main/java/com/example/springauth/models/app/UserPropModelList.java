package com.example.springauth.models.app;

import java.util.List;


public class UserPropModelList {


    private List<EntityPropModel> entityPropModels;

    public UserPropModelList() {
    }

    public UserPropModelList(List<EntityPropModel> entityPropModels) {
        this.entityPropModels = entityPropModels;
    }

    public List<EntityPropModel> getUserPropModels() {
        return entityPropModels;
    }

    public void setUserPropModels(List<EntityPropModel> entityPropModels) {
        this.entityPropModels = entityPropModels;
    }

}
