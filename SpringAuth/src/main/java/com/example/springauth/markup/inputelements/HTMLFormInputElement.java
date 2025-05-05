package com.example.springauth.markup.inputelements;


public class HTMLFormInputElement {


    private String tagName;
    private String typeAttributeValue;
    private String nameAttributeValue;
    private boolean isMutext;


    public HTMLFormInputElement(String tagName, String typeAttributeValue, String nameAttributeValue, boolean isMutext) {
        this.tagName = tagName;
        this.typeAttributeValue = typeAttributeValue;
        this.nameAttributeValue = nameAttributeValue;
        this.isMutext = isMutext;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTypeAttributeValue() {
        return typeAttributeValue;
    }

    public void setTypeAttributeValue(String typeAttributeValue) {
        this.typeAttributeValue = typeAttributeValue;
    }

    public String getNameAttributeValue() {
        return nameAttributeValue;
    }

    public void setNameAttributeValue(String nameAttributeValue) {
        this.nameAttributeValue = nameAttributeValue;
    }

    public boolean isMutext() {
        return isMutext;
    }

    public void setMutext(boolean mutext) {
        isMutext = mutext;
    }

}
