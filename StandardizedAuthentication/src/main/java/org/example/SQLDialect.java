package org.example;

import java.util.ArrayList;
import java.util.List;

public class SQLDialect {

    protected String[] numericTypeArray = {};
    protected String[] nonNumericTypeArray = {};
    protected String[] numericWithScaleTypeArray = {};
    protected String[] precisionlessTypeArray = {};
    protected Table table;

    public SQLDialect(){
    }

    public SQLDialect(Table table){
        this.table = table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String[] getNumericTypeArray() {
        return numericTypeArray;
    }

    public String[] getNonNumericTypeArray() {
        return nonNumericTypeArray;
    }

    public String[] getNumericWithScaleTypeArray() {
        return numericWithScaleTypeArray;
    }

    public String[] getPrecisionlessTypeArray() {
        return precisionlessTypeArray;
    }

    public boolean isANumericType(String type){
        return this.contains(this.numericTypeArray, type);
    }

    public boolean isANonNumericType(String type){
        return this.contains(this.nonNumericTypeArray, type);
    }

    public boolean hasPrecisionAndScale(String type){
        boolean isNumeric = this.isANumericType(type);
        boolean hasPrecisionAndScale = this.contains(this.numericWithScaleTypeArray, type);
        return (isNumeric && hasPrecisionAndScale);
    }

    // Type specifies no length
    public boolean hasNoPrecision(String type){
        return contains(this.getPrecisionlessTypeArray(), type);
    }

    protected boolean contains(String[] stack, String needle){
        for(String t: stack)
            if(t.equalsIgnoreCase(needle))
                return true;
        return false;
    }

    protected String getDialectTableDDLQuery(){
        return null;
    }
}