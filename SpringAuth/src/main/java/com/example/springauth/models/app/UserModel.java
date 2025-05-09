package com.example.springauth.models.app;

import java.util.List;


public class UserModel {


    private List<UserPropModel> userPropModelList;

    public UserModel() {
    }

    public UserModel(List<UserPropModel> userPropModelList) {
        this.userPropModelList = userPropModelList;
    }

    public List<UserPropModel> getUserPropModelList() {
        return userPropModelList;
    }

    public void setUserPropModelList(List<UserPropModel> userPropModelList) {
        this.userPropModelList = userPropModelList;
    }
}
