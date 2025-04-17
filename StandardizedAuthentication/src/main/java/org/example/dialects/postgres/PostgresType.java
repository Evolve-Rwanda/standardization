package org.example.dialects.postgres;



public class PostgresType {


    private static final String[] numericTypeArray = {
            "smallint", "integer", "bigint",
            "decimal", "numeric", "real",
            "double precision", "smallserial", "serial",
            "bigserial", "money"
    };
    private static final String[] nonNumericTypeArray = {
            "bit", "bit varying", "boolean",
            "char", "character varying", "character",
            "varchar", "date", "timestamp",
            "text", "time"
    };
    private static final String[] numericWithScaleTypeArray = {"decimal", "numeric"};

    private static String[] withoutPrecisionTypeArray = {"date", "timestamp", "text", "time", "serial", "smallint"};

    public static String[] getTypesWithoutPrecisionArray() {
        return withoutPrecisionTypeArray;
    }

    public static boolean isANumericType(String type){
        return contains(numericTypeArray, type);
    }

    public static boolean isANonNumericType(String type){
        return contains(nonNumericTypeArray, type);
    }

    public static boolean hasPrecisionAndScale(String type){
        boolean isNumeric = isANumericType(type);
        boolean hasPrecisionAndScale = contains(numericWithScaleTypeArray, type);
        return (isNumeric && hasPrecisionAndScale);
    }

    // Type specifies no length
    public static boolean hasNoPrecision(String type){
        return contains(withoutPrecisionTypeArray, type);
    }

    protected static boolean contains(String[] stack, String needle){
        for(String t: stack)
            if(t.equalsIgnoreCase(needle))
                return true;
        return false;
    }
}
