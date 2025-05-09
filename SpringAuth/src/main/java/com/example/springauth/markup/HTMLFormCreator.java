package com.example.springauth.markup;

import com.example.springauth.models.app.UserPropModel;
import com.example.springauth.models.utility.ColumnMarkupElementModel;
import com.example.springauth.models.utility.ColumnValueOptionModel;

import java.util.ArrayList;
import java.util.List;



public class HTMLFormCreator {


    private String formActionAttribValue;
    private List<UserPropModel> userPropModelList;


    public HTMLFormCreator(
            String formActionAttribValue,
            List<UserPropModel> userPropModelList
    ) {
        this.formActionAttribValue = formActionAttribValue;
        this.userPropModelList = userPropModelList;
    }

    public String create() {

        List<String> inputTypeAttribValueList = new ArrayList<>();
        inputTypeAttribValueList.add("text");
        inputTypeAttribValueList.add("password");
        inputTypeAttribValueList.add("checkbox");
        inputTypeAttribValueList.add("number");
        inputTypeAttribValueList.add("date");
        inputTypeAttribValueList.add("datetime");
        inputTypeAttribValueList.add("file");

        StringBuilder formBuilder = new StringBuilder();

        // create a form opening tag
        formBuilder.append("<form action=\"\" method=\"post\">");

        StringBuilder tableBuilder = new StringBuilder();

        for (UserPropModel userPropModel : userPropModelList) {

            ColumnMarkupElementModel cmem = userPropModel.getColumnMarkupElementModel();
            List<ColumnValueOptionModel> columnValueOptionModelList = userPropModel.getColumnValueOptionModels();
            String tagName = cmem.getTagName();
            String tagAttribName = userPropModel.getPropertyName();
            String tagLabel = tagAttribName.replace("_", " ");
            String typeAttributeValue = cmem.getTypeAttributeValue();
            boolean isMutuallyExclusive = cmem.isMutuallyExclusive();

            if (isMutuallyExclusive && tagName.equalsIgnoreCase("input")) {

                if (typeAttributeValue.equalsIgnoreCase("radio")) {
                    // create a radio button group with radio buttons using optional values
                    String radioButtonsElementString = createRadioButtons(columnValueOptionModelList);
                    String tableRows = createLabelInputElementRowPair(tagLabel, radioButtonsElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                if (typeAttributeValue.equalsIgnoreCase("checkbox")) {
                    // create a single checkbox, cannot have more than a single option
                    String checkboxElementString = createACheckbox(columnValueOptionModelList.getFirst());
                    String tableRows = createLabelInputElementRowPair(tagLabel, checkboxElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                if (typeAttributeValue.equalsIgnoreCase("select")) {
                    // create a single select dropdown
                    String selectElementString = createSelectionDropdown(columnValueOptionModelList);
                    String tableRows = createLabelInputElementRowPair(tagLabel, selectElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }

            }else{

                if (typeAttributeValue.equalsIgnoreCase("checkbox")) {
                    // can create multiple checkboxes
                    String checkboxElementString = createACheckbox(columnValueOptionModelList.getFirst());
                    String tableRows = createLabelInputElementRowPair(tagLabel, checkboxElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                if (typeAttributeValue.equalsIgnoreCase("select")) {
                    // create a single select dropdown, add multiple selection attrib
                    String selectElementString = createSelectionDropdown(columnValueOptionModelList);
                    String tableRows = createLabelInputElementRowPair(tagLabel, selectElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                if (inputTypeAttribValueList.contains(typeAttributeValue.toLowerCase())) {
                    // create a single text, number, date, datetime, file input tag
                    String inputElementString = createInputTag(cmem);
                    String tableRows = createLabelInputElementRowPair(tagLabel, inputElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
            }
            formBuilder.append(tableBuilder.toString());
            formBuilder.append("</form>");
        }
        // create form closing tag
        // add a form submission tag
        return formBuilder.toString();
    }

    private String createFormTable(String tableRows) {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">");
        tableBuilder.append(tableRows);
        tableBuilder.append("</table>");
        return tableBuilder.toString();
    }

    private String createLabelInputElementRowPair(String label, String InputElement) {
        StringBuilder rowBuilder = new StringBuilder();
        rowBuilder.append("<tr>");
        rowBuilder.append("<td>").append(label).append("</td>");
        rowBuilder.append("</tr>");
        rowBuilder.append("<tr>");
        rowBuilder.append("<td>").append(InputElement).append("</td>");
        rowBuilder.append("</tr>");
        return rowBuilder.toString();
    }

    private String getFormSubmissionButton(){
        return "<input type=\"submit\" value=\"Submit\" /><br />";
    }

    private String createInputTag(ColumnMarkupElementModel cmem) {
        StringBuilder tagBuilder = new StringBuilder();
        String type = cmem.getTypeAttributeValue();
        String name = cmem.getNameAttributeValue();
        String inputTag = String.format("<input type=\"%s\" name=\"%s\" />", type, name);
        tagBuilder.append("<label>").append(inputTag).append("</label>");
        return tagBuilder.toString();
    }

    private String createRadioButtons(List<ColumnValueOptionModel> columnValueOptionModelList) {
        StringBuilder radioBuilder = new StringBuilder();
        for (ColumnValueOptionModel columnValueOptionModel : columnValueOptionModelList) {
            String label = columnValueOptionModel.getOptionalValue();
            String[] columnFQN = columnValueOptionModel.getColumnId().split("\\.");
            String columnName = columnFQN[columnFQN.length - 1];
            radioBuilder.append("<label>");
            String nameAttribAndValue = String.format("name=\"%s\"", columnName);
            String inputTag = String.format("<input type=\"radio\" %s />", nameAttribAndValue);
            radioBuilder.append(inputTag);
            radioBuilder.append(label);
            radioBuilder.append("</label>");
        }
        return radioBuilder.toString();
    }

    private String createSelectionDropdown(List<ColumnValueOptionModel> columnValueOptionModelList) {
        StringBuilder selectionBuilder = new StringBuilder();
        String[] columnFQN = columnValueOptionModelList.getFirst().getColumnId().split("\\.");
        String columnName = columnFQN[columnFQN.length - 1];
        selectionBuilder.append("<label>");
        String selectTag = String.format("<select name =\"%s\">", columnName);
        selectionBuilder.append(selectTag);
        selectionBuilder.append("<option>select</option>");
        for (ColumnValueOptionModel columnValueOptionModel : columnValueOptionModelList) {
            String optionValue = columnValueOptionModel.getOptionalValue();
            String optionTag = String.format("<option value=\"%s\">%s</option>", optionValue, optionValue);
            selectionBuilder.append(optionTag);
        }
        selectionBuilder.append("</select>");
        selectionBuilder.append("</label>");
        return selectionBuilder.toString();
    }

    private String createACheckbox(ColumnValueOptionModel columnValueOptionModel) {
        StringBuilder checkboxBuilder = new StringBuilder();
        String[] columnFQN = columnValueOptionModel.getColumnId().split("\\.");
        String label = columnFQN[columnFQN.length - 1].replace("_", " ");
        String optionString = columnValueOptionModel.getOptionalValue();
        checkboxBuilder.append("<label>");
        String nameAttribAndValue = String.format("name=\"%s\"", optionString);
        String checkboxTag = String.format("<input type=\"checkbox\" %s />", nameAttribAndValue);
        checkboxBuilder.append(checkboxTag);
        checkboxBuilder.append(label);
        checkboxBuilder.append("</label>");
        return checkboxBuilder.toString();
    }

}
