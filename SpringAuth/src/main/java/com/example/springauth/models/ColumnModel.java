package com.example.springauth.models;

public class ColumnModel {


    private String tableName;
    private String name;
    private int number;
    private String dataType;
    private String specifiesPrecision;
    private long precision;
    private String specifiesScale;
    private long scale;
    private String specifiesLength;
    private String specifiesNothing;
    private String defaultValue;
    private String isNullable;
    private String isAFactBasedColumn;
    private String isEncrypted;
    private String isPK;
    private String isFK;
    private String isIndexed;
    private String referenceTableName;
    private String referenceColumnName;
    private String onUpdateAction;
    private String onDeleteAction;
    private String description;
    private String createdAt;
    private String deletedAt;

    public ColumnModel() {}

    @Override
    public String toString() {
        return "ColumnModel{" +
                "tableName='" + tableName + '\'' +
                ", name='" + name + '\'' +
                ", number=" + number +
                '}';
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSpecifiesPrecision() {
        return specifiesPrecision;
    }

    public void setSpecifiesPrecision(String specifiesPrecision) {
        this.specifiesPrecision = specifiesPrecision;
    }

    public long getPrecision() {
        return precision;
    }

    public void setPrecision(long precision) {
        this.precision = precision;
    }

    public String getSpecifiesScale() {
        return specifiesScale;
    }

    public void setSpecifiesScale(String specifiesScale) {
        this.specifiesScale = specifiesScale;
    }

    public long getScale() {
        return scale;
    }

    public void setScale(long scale) {
        this.scale = scale;
    }

    public String getSpecifiesLength() {
        return specifiesLength;
    }

    public void setSpecifiesLength(String specifiesLength) {
        this.specifiesLength = specifiesLength;
    }

    public String getSpecifiesNothing() {
        return specifiesNothing;
    }

    public void setSpecifiesNothing(String specifiesNothing) {
        this.specifiesNothing = specifiesNothing;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public String getIsAFactBasedColumn() {
        return isAFactBasedColumn;
    }

    public void setIsAFactBasedColumn(String isAFactBasedColumn) {
        this.isAFactBasedColumn = isAFactBasedColumn;
    }

    public String getIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(String isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public String getIsPK() {
        return isPK;
    }

    public void setIsPK(String isPK) {
        this.isPK = isPK;
    }

    public String getIsFK() {
        return isFK;
    }

    public void setIsFK(String isFK) {
        this.isFK = isFK;
    }

    public String getIsIndexed() {
        return isIndexed;
    }

    public void setIsIndexed(String isIndexed) {
        this.isIndexed = isIndexed;
    }

    public String getReferenceTableName() {
        return referenceTableName;
    }

    public void setReferenceTableName(String referenceTableName) {
        this.referenceTableName = referenceTableName;
    }

    public String getReferenceColumnName() {
        return referenceColumnName;
    }

    public void setReferenceColumnName(String referenceColumnName) {
        this.referenceColumnName = referenceColumnName;
    }

    public String getOnUpdateAction() {
        return onUpdateAction;
    }

    public void setOnUpdateAction(String onUpdateAction) {
        this.onUpdateAction = onUpdateAction;
    }

    public String getOnDeleteAction() {
        return onDeleteAction;
    }

    public void setOnDeleteAction(String onDeleteAction) {
        this.onDeleteAction = onDeleteAction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
