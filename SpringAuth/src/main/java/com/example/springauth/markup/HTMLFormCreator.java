package com.example.springauth.markup;

import com.example.springauth.models.app.EntityPropModel;
import com.example.springauth.models.utility.ColumnMarkupElementModel;
import com.example.springauth.models.utility.ColumnValueOptionModel;

import java.util.ArrayList;
import java.util.List;



public class HTMLFormCreator {



    private final String formActionAttribValue;
    private final List<EntityPropModel> entityPropModelList;


    public HTMLFormCreator(
            String formActionAttribValue,
            List<EntityPropModel> entityPropModelList
    ) {
        this.formActionAttribValue = formActionAttribValue;
        this.entityPropModelList = entityPropModelList;
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
        String thObject = "${createUserForm}";
        String formOpeningTag = String.format("\n\t<form th:action=\"@{/%s}\" method=\"post\" th:object=\"%s\">\n", this.formActionAttribValue, thObject);
        formBuilder.append(formOpeningTag);

        StringBuilder tableBuilder = new StringBuilder();

        for (EntityPropModel entityPropModel : entityPropModelList) {

            String propertyName = entityPropModel.getName();
            String propertyValue = entityPropModel.getValue();
            ColumnMarkupElementModel cmem = entityPropModel.getColumnMarkupElementModel();

            String columnId = cmem.getColumnId();
            String tagName = cmem.getTagName();
            String typeAttributeValue = cmem.getTypeAttributeValue();
            boolean isMutuallyExclusive = cmem.isMutuallyExclusive();

            List<ColumnValueOptionModel> columnValueOptionModelList = entityPropModel.getColumnValueOptionModels();
            String tagLabel =getTagLabel(columnId);

            if (isMutuallyExclusive &&
                    (tagName.equalsIgnoreCase("input")
                    || tagName.equalsIgnoreCase("select"))
            ) {

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
                // select tag has no "type" attrib, so we instead rely on the tagName to make this check
                if (tagName.equalsIgnoreCase("select")) {
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
        }
        // add a form submission tag
        String tableRows = createLabelInputElementRowPair(" ", getFormSubmissionButton());
        tableBuilder.append(tableRows);
        formBuilder.append(this.createFormTable(tableBuilder.toString()));
        // create a form closing tag
        formBuilder.append("\n\t</form>");

        return this.createHTMLPage(formBuilder.toString());
    }

    private String createHTMLPage(String htmlMarkup){
        StringBuilder pageBuilder = new StringBuilder();
        return pageBuilder.append("<!DOCTYPE html>")
                   .append("\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"https://www.thymeleaf.org\">")
                   .append("\n<head>")
                   .append("\n\t<title>User profile</title>")
                   .append("\n\t<link rel=\"stylesheet\"  th:href=\"@{css/forms.css}\" />")
                   .append("\n</head>")
                   .append("\n<body style=\"margin: 0; padding: 0;\">")
                   .append(htmlMarkup)
                   .append("\n</body>")
                   .append("\n</html>")
                   .toString();
    }

    private String createFormTable(String tableRows) {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("\n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"40%\" align=\"center\">")
                    .append(tableRows)
                    .append("\n\t</table>");
        return tableBuilder.toString();
    }

    private String createLabelInputElementRowPair(String label, String InputElement) {
        StringBuilder rowBuilder = new StringBuilder();
        String tagWrappedLabel = "<label class=\"field_label\">" + label + "</label>";
        rowBuilder.append("\n\t\t<tr>")
                  .append("\n\t\t\t<td>").append(tagWrappedLabel).append("\n\t\t\t</td>")
                  .append("\n\t\t</tr>")
                  .append("\n\t\t<tr>")
                  .append("\n\t\t\t<td align=\"center\">").append(InputElement).append("\n\t\t\t</td>")
                  .append("\n\t\t</tr>")
                  .append("\n\t\t<tr>")
                  .append("\n\t\t\t<td><br /></td>")
                  .append("\n\t\t</tr>");
        return rowBuilder.toString();
    }

    private String getFormSubmissionButton(){
        return "\n\t\t\t\t\t<input type=\"submit\" value=\"Submit\" /><br />";
    }

    private String createInputTag(ColumnMarkupElementModel cmem) {
        StringBuilder tagBuilder = new StringBuilder();
        String[] columnFQNParts = cmem.getColumnId().split("\\.");
        String type = cmem.getTypeAttributeValue();
        String name = columnFQNParts[columnFQNParts.length - 1];
        String thymleafField = String.format("name=\"%s\" value=\"%s\"", "propertyName", name);
        String thymleafValue = String.format("name=\"%s\"", "propertyValue");
        String propertyNameTag = String.format("\n\t\t\t\t\t<input type=\"%s\" %s />", "hidden", thymleafField);
        String propertyValueTag = String.format("\n\t\t\t\t\t<input type=\"%s\" %s />", type, thymleafValue);
        tagBuilder.append("\n\t\t\t\t<label>")
                  .append(propertyNameTag)
                  .append(propertyValueTag)
                  .append("\n\t\t\t\t</label>");
        return tagBuilder.toString();
    }

    private String createRadioButtons(List<ColumnValueOptionModel> columnValueOptionModelList) {
        StringBuilder radioBuilder = new StringBuilder();
        for (ColumnValueOptionModel columnValueOptionModel : columnValueOptionModelList) {
            String label = columnValueOptionModel.getOptionalValue();
            String[] columnFQN = columnValueOptionModel.getColumnId().split("\\.");
            String columnName = columnFQN[columnFQN.length - 1];
            String nameAttribAndValue = String.format("name=\"%s\"", columnName);
            String inputTag = String.format("\n\t\t\t\t\t\t<input type=\"radio\" %s /> ", nameAttribAndValue);
            radioBuilder.append("\n\t\t\t\t\t<label>")
                        .append(inputTag)
                        .append(label)
                        .append("\n\t\t\t\t\t</label>");
        }
        return radioBuilder.toString();
    }

    private String createSelectionDropdown(List<ColumnValueOptionModel> columnValueOptionModelList) {
        StringBuilder selectionBuilder = new StringBuilder();
        String[] columnFQN = columnValueOptionModelList.getFirst().getColumnId().split("\\.");
        String columnName = columnFQN[columnFQN.length - 1];
        selectionBuilder.append("\n\t\t\t\t<label>");
        String selectTag = String.format("\n\t\t\t\t\t<select name =\"%s\">", columnName);
        selectionBuilder.append(selectTag)
                        .append("\n\t\t\t\t\t\t<option selected disabled>select</option>");
        for (ColumnValueOptionModel columnValueOptionModel : columnValueOptionModelList) {
            String optionValue = columnValueOptionModel.getOptionalValue();
            String optionTag = String.format("\n\t\t\t\t\t\t<option value=\"%s\">%s</option>", optionValue, optionValue);
            selectionBuilder.append(optionTag);
        }
        selectionBuilder.append("\n\t\t\t\t\t</select>")
                        .append("\n\t\t\t\t</label>");
        return selectionBuilder.toString();
    }

    private String createACheckbox(ColumnValueOptionModel columnValueOptionModel) {
        StringBuilder checkboxBuilder = new StringBuilder();
        String[] columnFQN = columnValueOptionModel.getColumnId().split("\\.");
        String label = columnFQN[columnFQN.length - 1].replace("_", " ");
        String optionString = columnValueOptionModel.getOptionalValue();

        String nameAttribAndValue = String.format("name=\"%s\"", optionString);
        String checkboxTag = String.format("\n\t\t\t\t\t<input type=\"checkbox\" %s />", nameAttribAndValue);
        checkboxBuilder.append("\n\t\t\t\t<label>")
                       .append(checkboxTag)
                       .append(label)
                       .append("\n\t\t\t\t</label>");
        return checkboxBuilder.toString();
    }

    private String getTagLabel(String columnFQN) {
        String[] tagLabelParts = columnFQN.split("\\.");
        int length = tagLabelParts.length;
        String intermediateTagLabel = tagLabelParts[length - 1].replaceAll("_", " ");
        return intermediateTagLabel.substring(0, 1).toUpperCase() + intermediateTagLabel.substring(1);
    }

}
