package org.example.tables;

import org.example.columns.Column;
import java.util.List;

public class InvolvedEntityTable extends Table {

    public InvolvedEntityTable(String name, List<Column> columnList){
        super(name, columnList);
    }

    @Override
    public void setDescription(String description){
        this.description = "This table contains information about all the " +
                "different entities in the system or application. " +
                "These can be suppliers, buyers, partners, and any other stakeholders.";
    }
}
