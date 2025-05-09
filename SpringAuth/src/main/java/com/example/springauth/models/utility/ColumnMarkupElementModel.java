package com.example.springauth.models.utility;

import java.util.Objects;



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

    @Override
    public String toString() {
        return "ColumnMarkupElementModel{" +
                "columnId='" + columnId + '\'' +
                ", tagName='" + tagName + '\'' +
                ", typeAttributeValue='" + typeAttributeValue + '\'' +
                ", nameAttributeValue='" + nameAttributeValue + '\'' +
                ", isMutuallyExclusive=" + isMutuallyExclusive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColumnMarkupElementModel that)) return false;
        return Objects.equals(columnId, that.columnId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(columnId);
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

    public void setIsMutuallyExclusive(boolean isMutuallyExclusive) {
        this.isMutuallyExclusive = isMutuallyExclusive;
    }

}
