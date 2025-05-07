package com.example.springauth.models.utility;


public class ColumnMarkupElementModel {


    private String columnId;
    private String tagName;
    private String typeAttributeValue;
    private String nameAttributeValue;
    private boolean isMutuallyExclusive;


    public ColumnMarkupElementModel() {
    }

    public ColumnMarkupElementModel(String columnId, String tagName, String typeAttributeValue, String nameAttributeValue, boolean isMutuallyExclusive) {
        this.columnId = columnId;
        this.tagName = tagName;
        this.typeAttributeValue = typeAttributeValue;
        this.nameAttributeValue = nameAttributeValue;
        this.isMutuallyExclusive = isMutuallyExclusive;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTypeAttributeValue() {
        return typeAttributeValue;
    }

    public void setTypeAttributeValue(String typeAttributeValue) {
        this.typeAttributeValue = typeAttributeValue;
    }

    public String getNameAttributeValue() {
        return nameAttributeValue;
    }

    public void setNameAttributeValue(String nameAttributeValue) {
        this.nameAttributeValue = nameAttributeValue;
    }

    public boolean isMutuallyExclusive() {
        return isMutuallyExclusive;
    }

    public void setMutuallyExclusive(boolean mutuallyExclusive) {
        isMutuallyExclusive = mutuallyExclusive;
    }

}
