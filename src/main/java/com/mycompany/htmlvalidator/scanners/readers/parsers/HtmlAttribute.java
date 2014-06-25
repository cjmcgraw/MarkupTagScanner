package com.mycompany.htmlvalidator.scanners.readers.parsers;

public class HtmlAttribute {
    public static final String scriptAttributesName = "Script";
    public static final char attributeSeparator = ' ';
    public static final char attributeSplitter = '=';
    
    public static final String CLOSING_TAG = "" + HtmlParser.CLOSING_TAG;
    public static final String DEFAULT_CLOSING_NAME = "self-closing";
    public static final String DEFAULT_CLOSING_VALUE = "";
    
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
        if (name.equals(CLOSING_TAG))
            this.attributeName = DEFAULT_CLOSING_NAME;
        else
            this.attributeName = name;
    }
    
    public String getValue() {
        return this.attributeValue;
    }
    
    public void setValue(String value) {
        if (this.attributeName.equals(DEFAULT_CLOSING_NAME))
            this.attributeValue = DEFAULT_CLOSING_VALUE;
        else
            this.attributeValue = value.trim();
    }
    
    public boolean isFlag() {
        return this.attributeName != null && this.attributeValue == null;
    }
    
    public boolean equals(Object other) {
        if (other instanceof HtmlAttribute)
            return equals( (HtmlAttribute) other);
        else
            return super.equals(other);
    }
    
    public boolean equals(HtmlAttribute other) {
        boolean result = (this.attributeName.equals(other.attributeName) && 
                          this.attributeValue.equals(other.attributeValue));
        return result;
    }
    
    public String toString() {
        return this.attributeName + ((this.hasAttributeValue()) ? attributeSplitter + this.attributeValue : "");
    }
    
    private boolean hasAttributeValue() {
        return this.attributeValue != "";
    }
}
