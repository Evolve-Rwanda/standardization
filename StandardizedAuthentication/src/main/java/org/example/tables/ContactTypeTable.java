package org.example.tables;

import org.example.columns.Column;
import java.util.List;

public class ContactTypeTable extends Table {

    public ContactTypeTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the acceptable contacts" +
                "that the different users and entities in the system or application can provide without strict limits";
    }
}
