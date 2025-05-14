package com.example.springauth.models.app;

import java.util.List;


public class UserModel {


    private List<EntityPropModel> entityPropModelList;

    public UserModel() {
    }

    public UserModel(List<EntityPropModel> entityPropModelList) {
        this.entityPropModelList = entityPropModelList;
    }

    public List<EntityPropModel> getUserPropModelList() {
        return entityPropModelList;
    }

    public void setUserPropModelList(List<EntityPropModel> entityPropModelList) {
        this.entityPropModelList = entityPropModelList;
    }
}
