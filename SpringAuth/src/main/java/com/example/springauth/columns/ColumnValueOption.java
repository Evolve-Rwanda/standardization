package com.example.springauth.columns;

import java.util.Objects;




public class ColumnValueOption {


    private String columnId;
    private String optionValue;

    public ColumnValueOption(String columnId, String optionValue) {
        this.columnId = columnId;
        this.optionValue = optionValue;
    }

    @Override
    public String toString() {
        return "ColumnValueOption{" + "columnId=" + columnId + ", optionValue='" + optionValue + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColumnValueOption that)) return false;
        return columnId == that.columnId && Objects.equals(optionValue, that.optionValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, optionValue);
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
}
