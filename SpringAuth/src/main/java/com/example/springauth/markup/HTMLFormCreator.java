package com.example.springauth.markup;

import com.example.springauth.models.app.EntityPropModel;
import com.example.springauth.models.utility.ColumnMarkupElementModel;
import com.example.springauth.models.utility.ColumnValueOptionModel;

import java.util.Arrays;
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

        List<String> inputTypeAttribValueList = Arrays.asList(
                "text", "password", "checkbox", "number", "email", "date", "datetime", "file"
        );

        StringBuilder formBuilder = new StringBuilder();

        // create a form opening tag
        String thObject = "${createUserForm}";
        String formOpeningTag = String.format(
                "\n\t<form th:action=\"@{/%s}\" method=\"post\" th:object=\"%s\" enctype=\"multipart/form-data\">\n",
                this.formActionAttribValue, thObject
        );
        formBuilder.append(formOpeningTag);

        StringBuilder tableBuilder = new StringBuilder();

        for (EntityPropModel entityPropModel : entityPropModelList) {

            String propertyName = entityPropModel.getName();
            String propertyValue = entityPropModel.getValue();
            ColumnMarkupElementModel cmem = entityPropModel.getColumnMarkupElementModel();

            String tagName = cmem.getTagName();
            String typeAttributeValue = cmem.getTypeAttributeValue();
            boolean isMutuallyExclusive = cmem.isMutuallyExclusive();

            List<ColumnValueOptionModel> columnValueOptionModelList = entityPropModel.getColumnValueOptionModels();

            if (isMutuallyExclusive &&
                    (tagName.equalsIgnoreCase("input")
                    || tagName.equalsIgnoreCase("select"))
            ) {

                if (typeAttributeValue.equalsIgnoreCase("radio")) {
                    // create a radio button group with radio buttons using optional values
                    String radioButtonsElementString = createRadioButtons(cmem, columnValueOptionModelList);
                    String tableRows = createLabelInputElementRowPair(cmem, radioButtonsElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                if (typeAttributeValue.equalsIgnoreCase("checkbox")) {
                    // create a single checkbox, cannot have more than a single option
                    String checkboxElementString = createACheckbox(columnValueOptionModelList.getFirst());
                    String tableRows = createLabelInputElementRowPair(cmem, checkboxElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                // select tag has no "type" attrib, so we instead rely on the tagName to make this check
                if (tagName.equalsIgnoreCase("select")) {
                    // create a single select dropdown
                    String selectElementString = createSelectionDropdown(cmem, columnValueOptionModelList);
                    String tableRows = createLabelInputElementRowPair(cmem, selectElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }

            }else{

                if (typeAttributeValue.equalsIgnoreCase("checkbox")) {
                    // can create multiple checkboxes
                    String checkboxElementString = createACheckbox(columnValueOptionModelList.getFirst());
                    String tableRows = createLabelInputElementRowPair(cmem, checkboxElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                if (typeAttributeValue.equalsIgnoreCase("select")) {
                    // create a single select dropdown, add multiple selection attrib
                    String selectElementString = createSelectionDropdown(cmem, columnValueOptionModelList);
                    String tableRows = createLabelInputElementRowPair(cmem, selectElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
                if (inputTypeAttribValueList.contains(typeAttributeValue.toLowerCase())) {
                    if (typeAttributeValue.equalsIgnoreCase("file")) {
                        String inputElementString = this.createFileInputTag(cmem);
                        String tableRows = this.createLabelInputElementRowPair(cmem, inputElementString);
                        tableBuilder.append(tableRows);
                        continue;
                    }
                    // create a single text, number, date, datetime, file input tag
                    String inputElementString = this.createInputTag(cmem);
                    String tableRows = this.createLabelInputElementRowPair(cmem, inputElementString);
                    tableBuilder.append(tableRows);
                    continue;
                }
            }
        }
        // add a form submission tag
        String tableRows = createLabelInputElementRowPair(null, getFormSubmissionButton());
        tableBuilder.append(tableRows);
        formBuilder.append(this.createFormTable(tableBuilder.toString()));
        // create a form closing tag
        formBuilder.append("\n\t</form>");

        return this.createHTMLPage(formBuilder.toString());
    }

    private String createHTMLPage(String htmlMarkup){
        StringBuilder pageBuilder = new StringBuilder();
        String submissionEventJavascript = this.getFormSubmissionJavascript();
        return pageBuilder.append("<!DOCTYPE html>")
                   .append("\n<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"https://www.thymeleaf.org\">")
                   .append("\n<head>")
                   .append("\n\t<title>User profile</title>")
                   .append("\n\t<link rel=\"stylesheet\"  th:href=\"@{css/forms.css}\" />")
                   .append("\n</head>")
                   .append("\n<body style=\"margin: 0; padding: 0;\">")
                   .append(htmlMarkup)
                   .append(submissionEventJavascript)
                   .append("\n</body>")
                   .append("\n</html>")
                   .toString();
    }

    private String getFormSubmissionJavascript() {
        StringBuilder javascriptBuilder = new StringBuilder();
        javascriptBuilder.append("\n\t<script type=\"application/javascript\">")
                .append("\n\n\t\tvar submitButton = document.getElementById('").append("submit_user_profile_button").append("');")
                .append("\n\n\t\tsubmitButton.addEventListener(\"click\", () => {\n")
                .append("\n\t\t\tconst propNameElements = document.getElementsByClassName(\"property_name\");")
                .append("\n\t\t\tconst propValueElements = document.getElementsByClassName(\"property_value\");")
                .append("\n\t\t\tconst radioButtonElements = document.getElementsByClassName(\"radio_button\");")
                .append("\n\t\t\tconst checkboxButtonElements = document.getElementsByClassName(\"checkbox_button\");")
                .append("\n\t\t\tconst radioButtonNames = [];")
                .append("\n\t\t\tconst checkboxButtonNames = [];\n")
                .append("\n\t\t\t// radio button submissions")
                .append("\n\t\t\tconst radioSubmissionList = [];")
                .append("\n\t\t\tlet x = 0;")
                .append("\n\t\t\tfor(let r=0; r<radioButtonElements.length; r++) {")
                .append("\n\t\t\t\tif (radioButtonElements[r].checked) {")
                .append("\n\t\t\t\t\tradioSubmissionList[x] = {")
                .append("\n\t\t\t\t\t\tproperty_name: radioButtonElements[r].name,")
                .append("\n\t\t\t\t\t\tproperty_value: radioButtonElements[r].value")
                .append("\n\t\t\t\t\t};")
                .append("\n\t\t\t\t\tradioButtonNames[x] = radioSubmissionList[x].property_name;")
                .append("\n\t\t\t\t\tx++;")
                .append("\n\t\t\t\t}")
                .append("\n\t\t\t}\n")

                .append("\n\t\t\t// radio button submissions")
                .append("\n\t\t\tconst checkSubmissionList = [];")
                .append("\n\t\t\tlet y = 0;")
                .append("\n\t\t\tfor(let c=0; c<checkboxButtonElements.length; c++) {")
                .append("\n\t\t\t\tif (checkboxButtonElements[r].checked) {")
                .append("\n\t\t\t\t\tcheckSubmissionList[y] = {")
                .append("\n\t\t\t\t\t\tproperty_name: checkboxButtonElements[c].name,")
                .append("\n\t\t\t\t\t\tproperty_value: checkboxButtonElements[c].value")
                .append("\n\t\t\t\t\t};")
                .append("\n\t\t\t\t\tcheckboxButtonNames[y] = checkSubmissionList[y].property_name;")
                .append("\n\t\t\t\t\ty++;")
                .append("\n\t\t\t\t}")
                .append("\n\t\t\t}\n")

                .append("\n\t\t\tconst submission = [];")
                .append("\n\t\t\tlet t = 0;")
                .append("\n\t\t\tfor(let i=0; i<propNameElements.length; i++){")
                .append("\n\t\t\t\tconst propName = propNameElements[i].value;")
                .append("\n\t\t\t\tif (radioButtonNames.includes(propName) || checkboxButtonNames.includes(propName))")
                .append("\n\t\t\t\t\tcontinue;")
                .append("\n\t\t\t\tvar propValue = document.getElementById(propName + '_id').value;")
                .append("\n\t\t\t\tsubmission[t] = {")
                .append("\n\t\t\t\t\tproperty_name: propName,")
                .append("\n\t\t\t\t\tproperty_value: propValue")
                .append("\n\t\t\t\t};")
                .append("\n\t\t\t\tt++;")
                .append("\n\t\t\t}\n")

                .append("\n\t\t\tfor(let r=0; r<radioSubmissionList.length; r++){")
                .append("\n\t\t\t\t\tsubmission[t] = {")
                .append("\n\t\t\t\t\t\tproperty_name: radioSubmissionList[r].property_name,")
                .append("\n\t\t\t\t\t\tproperty_value: radioSubmissionList[r].property_value")
                .append("\n\t\t\t\t\t};")
                .append("\n\t\t\t\t\tt++;")
                .append("\n\t\t\t\t}")

                .append("\n\t\t\tfor(let c=0; c<checkSubmissionList.length; c++){")
                .append("\n\t\t\t\t\tsubmission[t] = {")
                .append("\n\t\t\t\t\t\tproperty_name: checkSubmissionList[c].property_name,")
                .append("\n\t\t\t\t\t\tproperty_value: checkSubmissionList[c].property_value")
                .append("\n\t\t\t\t\t};")
                .append("\n\t\t\t\t\tt++;")
                .append("\n\t\t\t\t}")

                .append("\n\t\t\tfetch('/").append(this.formActionAttribValue).append("',")
                .append("\n\t\t\t\t{")
                .append("\n\t\t\t\t\tmethod: 'POST',")
                .append("\n\t\t\t\t\theaders: {'Content-Type': 'application/json'},")
                .append("\n\t\t\t\t\tbody: JSON.stringify(submission)")
                .append("\n\t\t\t\t}")
                .append("\n\t\t\t);")
                .append("\n\t\t});")
                .append("\n\t</script>");
        return javascriptBuilder.toString();
    }

    private String createFormTable(String tableRows) {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("\n\t<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"40%\" align=\"center\">")
                    .append(tableRows)
                    .append("\n\t</table>");
        return tableBuilder.toString();
    }

    private String createLabelInputElementRowPair(ColumnMarkupElementModel cmem, String inputElement) {
        String columnId = (cmem != null) ? cmem.getColumnId() : "";
        String label = (cmem != null) ? this.getTagLabel(columnId) : "";
        String id = (cmem != null) ? this.generateLabelForAttribValue(this.extractColumnName(cmem)) : "";
        StringBuilder rowBuilder = new StringBuilder();
        String tagWrappedLabel = String.format(
                "\n\t\t\t\t<label class=\"field_label\" for=\"%s\" style=\"cursor: pointer;\">" + label + "</label>",
                id);
        rowBuilder.append("\n\t\t<tr>")
                  .append("\n\t\t\t<td>").append(tagWrappedLabel).append("\n\t\t\t</td>")
                  .append("\n\t\t</tr>")
                  .append("\n\t\t<tr>")
                  .append("\n\t\t\t<td align=\"center\">").append(inputElement).append("\n\t\t\t</td>")
                  .append("\n\t\t</tr>")
                  .append("\n\t\t<tr>")
                  .append("\n\t\t\t<td><br /></td>")
                  .append("\n\t\t</tr>");
        return rowBuilder.toString();
    }

    private String getFormSubmissionButton(){
        return "\n\t\t\t\t\t<input type=\"button\" class=\"add_button\" id=\"submit_user_profile_button\" value=\"Submit\" /><br />";
    }

    private String createInputTag(ColumnMarkupElementModel cmem) {
        String type = cmem.getTypeAttributeValue();
        String name = this.extractColumnName(cmem);
        String id = this.generateLabelForAttribValue(name);

        StringBuilder tagBuilder = new StringBuilder();

        String thymleafField = String.format("class=\"%s\" value=\"%s\"", "property_name", name);
        String thymleafValue = String.format("class=\"%s\" name=\"%s\"", "property_value", name);
        String propertyNameTag = String.format("\n\t\t\t\t\t<input type=\"%s\" %s />", "hidden", thymleafField);
        String propertyValueTag = String.format(
                "\n\t\t\t\t\t<input type=\"%s\" %s id=\"%s\" value=\"\" />",
                type, thymleafValue, id);
        tagBuilder.append("\n\t\t\t\t<label>")
                  .append(propertyNameTag)
                  .append(propertyValueTag)
                  .append("\n\t\t\t\t</label>");
        return tagBuilder.toString();
    }

    private String createFileInputTag(ColumnMarkupElementModel cmem) {

        String type = cmem.getTypeAttributeValue();
        String name = this.extractColumnName(cmem);
        String id = this.generateLabelForAttribValue(name);

        StringBuilder tagBuilder = new StringBuilder();
        String thymleafField = String.format("class=\"%s\" value=\"%s\"", "property_name", name);
        String thymleafValue = String.format("class=\"%s\" name=\"%s\"", "property_value", name);
        String propertyNameTag = String.format(
                "\n\t\t\t\t\t<input type=\"%s\" %s />", "hidden",
                thymleafField
        );
        String propertyValueTag = String.format(
                "\n\t\t\t\t\t<input type=\"%s\" %s id=\"%s\" style=\"display: none;\" />",
                type, thymleafValue, id);
        tagBuilder.append("\n\t\t\t\t<label>")
                .append(propertyNameTag)
                .append(propertyValueTag)
                .append("\n\t\t\t\t</label>");
        return tagBuilder.toString();
    }

    private String createRadioButtons(
            ColumnMarkupElementModel cmem,
            List<ColumnValueOptionModel> columnValueOptionModelList
    ) {
        String type = cmem.getTypeAttributeValue();
        String name = this.extractColumnName(cmem);
        String id = this.generateLabelForAttribValue(name);

        String thymleafField = String.format(
                "class=\"%s\" value=\"%s\"",
                "property_name", name
        );

        String propertyNameTag = String.format(
                "\n\t\t\t\t\t<input type=\"%s\" %s />",
                "hidden", thymleafField
        );

        StringBuilder radioBuilder = new StringBuilder();
        radioBuilder.append(propertyNameTag);
        for (ColumnValueOptionModel columnValueOptionModel : columnValueOptionModelList) {
            String label = columnValueOptionModel.getOptionalValue();
            String thymleafValue = String.format(
                    "class=\"%s\" name=\"%s\" value=\"%s\"",
                    "property_value radio_button", name, label
            );
            String inputTag = String.format(
                    "\n\t\t\t\t\t\t<input type=\"radio\" %s /> ",
                    thymleafValue);
            radioBuilder.append("\n\t\t\t\t\t<label>")
                        .append(inputTag)
                        .append(label)
                        .append("\n\t\t\t\t\t</label>");
        }
        return radioBuilder.toString();
    }

    private String createSelectionDropdown(
            ColumnMarkupElementModel cmem,
            List<ColumnValueOptionModel> columnValueOptionModelList
    ) {

        String type = cmem.getTypeAttributeValue();
        String name = this.extractColumnName(cmem);
        String id = this.generateLabelForAttribValue(name);

        String thymleafField = String.format("class=\"%s\" value=\"%s\"", "property_name", name);
        String propertyNameTag = String.format(
                "\n\t\t\t\t<input type=\"%s\" %s />", "hidden",
                thymleafField
        );
        String thymleafValue = String.format("class=\"%s\" name=\"%s\" id=\"%s\"", "property_value", name, id);

        StringBuilder selectionBuilder = new StringBuilder();
        selectionBuilder.append(propertyNameTag);
        selectionBuilder.append("\n\t\t\t\t<label>");
        String selectTag = String.format("\n\t\t\t\t\t<select %s>", thymleafValue);
        selectionBuilder.append(selectTag)
                        .append("\n\t\t\t\t\t\t<option selected disabled>select</option>");
        for (ColumnValueOptionModel columnValueOptionModel : columnValueOptionModelList) {
            String optionValue = columnValueOptionModel.getOptionalValue();
            String optionTag = String.format(
                    "\n\t\t\t\t\t\t<option value=\"%s\">%s</option>",
                    optionValue, optionValue
            );
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
        String checkboxTag = String.format(
                "\n\t\t\t\t\t<input type=\"checkbox\" %s />",
                nameAttribAndValue);
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

    private String extractColumnName(ColumnMarkupElementModel cmem) {
        String[] columnFQNParts = cmem.getColumnId().split("\\.");
        return columnFQNParts[columnFQNParts.length - 1];
    }

    private String generateLabelForAttribValue(String columnName){
        return columnName + "_id";
    }

}
