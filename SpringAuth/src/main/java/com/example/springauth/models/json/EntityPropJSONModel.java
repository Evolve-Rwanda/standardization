package com.example.springauth.models.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityPropJSONModel {

    @JsonProperty("property_name")
    private String propertyName;
    @JsonProperty("property_value")
    private String propertyValue;

    public EntityPropJSONModel() {}

    public EntityPropJSONModel(String propertyName, String propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
