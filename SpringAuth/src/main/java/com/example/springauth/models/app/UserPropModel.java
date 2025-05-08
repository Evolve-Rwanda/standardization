package com.example.springauth.models.app;

public class UserPropModel {

    private String propertyName;
    private String htmlLabelText;
    private String propertyValue;

    public UserPropModel() {
    }

    public UserPropModel(String propertyName, String htmlLabelText, String propertyValue) {
        this.propertyName = propertyName;
        this.htmlLabelText = htmlLabelText;
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getHtmlLabelText() {
        return htmlLabelText;
    }

    public void setHtmlLabelText(String htmlLabelText) {
        this.htmlLabelText = htmlLabelText;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
