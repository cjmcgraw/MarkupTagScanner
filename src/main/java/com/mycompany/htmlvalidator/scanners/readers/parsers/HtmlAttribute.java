package com.mycompany.htmlvalidator.scanners.readers.parsers;

public class HtmlAttribute {
    private String attributeName;
    private String attributeValue;
    
    public HtmlAttribute() {}
    
    public HtmlAttribute(String name) {
        this(name, null);
    }
    
    public HtmlAttribute(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }
    
    public String getName() {
        return this.attributeName;
    }
    
    public void setName(String name) {
        this.attributeName = name;
    }
    
    public String getValue() {
        return this.attributeValue;
    }
    
    public void setValue(String value) {
        this.attributeValue = value;
    }
    
    public boolean isFlag() {
        return this.attributeName != null && this.attributeValue == null;
    }
}
