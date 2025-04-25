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
    private boolean isNullable;
    private boolean isAFactBasedColumn;
    private boolean isEncrypted;
    private boolean isPK;
    private boolean isFK;
    private boolean isIndexed;
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

    public boolean getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public boolean getIsAFactBasedColumn() {
        return isAFactBasedColumn;
    }

    public void setIsAFactBasedColumn(boolean isAFactBasedColumn) {
        this.isAFactBasedColumn = isAFactBasedColumn;
    }

    public boolean getIsEncrypted() {
        return isEncrypted;
    }

    public void setIsEncrypted(boolean isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public boolean getIsPK() {
        return isPK;
    }

    public void setIsPK(boolean isPK) {
        this.isPK = isPK;
    }

    public boolean getIsFK() {
        return isFK;
    }

    public void setIsFK(boolean isFK) {
        this.isFK = isFK;
    }

    public boolean getIsIndexed() {
        return isIndexed;
    }

    public void setIsIndexed(boolean isIndexed) {
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
