package org.example.tables;


import org.example.columns.Column;
import java.util.List;


public class AddressTable extends Table {
    public AddressTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains address information about all the " +
                "different users and entities in the system or application. " +
                "Multiple addresses for both users and other entities can be provided";
    }
}
