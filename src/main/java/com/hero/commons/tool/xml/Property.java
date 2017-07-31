package com.hero.commons.tool.xml;


/**
 * <p/>
 *
 * @author sun.jun
 */
public class Property implements XmlNode {

    private java.lang.String name;
    private java.lang.String type;
    private java.lang.String value;

    public Property() {
    }

    public Property(String type, String name, String value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

    public Property( String name, String value) {
        this.name = name;
        this.value = value;
    }

    public java.lang.String getName() {
        return this.name;
    }

    public java.lang.String getType() {
        return this.type;
    }

    public java.lang.String getValue() {
        return this.value;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }

    public void setValue(java.lang.String value) {
        this.value = value;
    }

}
