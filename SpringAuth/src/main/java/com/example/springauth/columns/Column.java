package com.example.springauth.columns;

import java.util.Objects;


public class Column implements Cloneable{


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


    public Column(String tableName, String name, int number, String dataType, long precision, long scale, String defaultValue, boolean isNullable, boolean isAFactBasedColumn, boolean isEncrypted, boolean isPK, boolean isFK, boolean isIndexed, String referenceTableName, String referenceColumnName, String onUpdateAction, String onDeleteAction, String description, String createdAt, String deletedAt) {
        this.tableName = tableName;
        this.name = name;
        this.number = number;
        this.dataType = dataType;
        this.precision = precision;
        this.scale = scale;
        this.defaultValue = defaultValue;
        this.isNullable = isNullable ? "true" : "false";
        this.isAFactBasedColumn = isAFactBasedColumn ? "true" : "false";
        this.isEncrypted = isEncrypted ? "true" : "false";
        this.isPK = isPK ? "true" : "false";
        this.isFK = isFK ? "true" : "false";
        this.isIndexed = isIndexed ? "true" : "false";
        this.referenceTableName = referenceTableName;
        this.referenceColumnName = referenceColumnName;
        this.onUpdateAction = onUpdateAction;
        this.onDeleteAction = onDeleteAction;
        this.description = description;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Column column)) return false;
        return Objects.equals(tableName, column.tableName) && Objects.equals(name, column.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Column clone() throws CloneNotSupportedException {
        Object object = super.clone();
        Column column = (Column) object;
        column.setTableName(this.tableName);
        column.setName(this.name);
        column.setNumber(this.number);
        column.setDataType(this.dataType);
        column.setSpecifiesPrecision(this.specifiesPrecision);
        column.setPrecision(this.precision);
        column.setSpecifiesScale(this.specifiesScale);
        column.setScale(this.scale);
        column.setSpecifiesLength(this.specifiesLength);
        column.setSpecifiesNothing(this.specifiesNothing);
        column.setDefaultValue(this.defaultValue);
        column.setIsNullable(this.isNullable);
        column.setIsAFactBasedColumn(this.isAFactBasedColumn);
        column.setIsEncrypted(this.isEncrypted);
        column.setIsPK(this.isPK);
        column.setIsFK(this.isFK);
        column.setIsIndexed(this.isIndexed);
        column.setReferenceTableName(this.referenceTableName);
        column.setReferenceColumnName(this.referenceColumnName);
        column.setOnUpdateAction(this.onUpdateAction);
        column.setOnDeleteAction(this.onDeleteAction);
        column.setDescription(this.description);
        column.setCreatedAt(this.createdAt);
        column.setDeletedAt(this.deletedAt);
        return column;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsAFactBasedColumn(String isAFactBasedColumn) {
        this.isAFactBasedColumn = isAFactBasedColumn;
    }

    public void setIsEncrypted(String isEncrypted) {
        this.isEncrypted = isEncrypted;
    }

    public void setIsFK(String isFK) {
        this.isFK = isFK;
    }

    public void setIsIndexed(String isIndexed) {
        this.isIndexed = isIndexed;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public void setIsPK(String isPK) {
        this.isPK = isPK;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOnDeleteAction(String onDeleteAction) {
        this.onDeleteAction = onDeleteAction;
    }

    public void setOnUpdateAction(String onUpdateAction) {
        this.onUpdateAction = onUpdateAction;
    }

    public void setPrecision(long precision) {
        this.precision = precision;
    }

    public void setReferenceColumnName(String referenceColumnName) {
        this.referenceColumnName = referenceColumnName;
    }

    public void setReferenceTableName(String referenceTableName) {
        this.referenceTableName = referenceTableName;
    }

    public void setScale(long scale) {
        this.scale = scale;
    }

    public void setSpecifiesLength(String specifiesLength) {
        this.specifiesLength = specifiesLength;
    }

    public void setSpecifiesPrecision(String specifiesPrecision) {
        this.specifiesPrecision = specifiesPrecision;
    }

    public void setSpecifiesScale(String specifiesScale) {
        this.specifiesScale = specifiesScale;
    }

    public void setSpecifiesNothing(String specifiesNothing){
        this.specifiesNothing = specifiesNothing;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }


    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public String getDataType() {
        return dataType;
    }

    public long getPrecision() {
        return precision;
    }

    public long getScale() {
        return scale;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public String getIsAFactBasedColumn() {
        return isAFactBasedColumn;
    }

    public String getIsEncrypted() {
        return isEncrypted;
    }

    public String getIsIndexed(){
        return this.isIndexed;
    }

    public String getDescription(){
        return this.description;
    }

    public String getIsPK() {
        return isPK;
    }

    public String getIsFK() {
        return isFK;
    }

    public String getReferenceTableName() {
        return referenceTableName;
    }

    public String getReferenceColumnName() {
        return referenceColumnName;
    }

    public String getOnUpdateAction() {
        return onUpdateAction;
    }

    public String getOnDeleteAction() {
        return onDeleteAction;
    }

    public String getCreatedAt() {
        return createdAt;
    }

}
