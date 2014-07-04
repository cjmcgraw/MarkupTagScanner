package com.mycompany.htmlvalidator.scanners.readers.parsers;

public class HtmlAttribute {
    public static final String scriptAttributesName = "Script";
    public static final String commentAttributesName = "!--";
    public static final char attributeSeparator = ' ';
    public static final char attributeSplitter = '=';
    
    public static final String CLOSING_TAG = "" + HtmlParser.CLOSING_TAG;
    public static final String DEFAULT_CLOSING_NAME = "self-closing";
    public static final String DEFAULT_EMPTY_VALUES = "";
    
    private String attributeName;
    private String attributeValue;
    
    public HtmlAttribute() {
        this.attributeName = DEFAULT_EMPTY_VALUES;
        this.attributeValue = DEFAULT_EMPTY_VALUES;
    }
    
    public HtmlAttribute(String name) {
        this(name, DEFAULT_EMPTY_VALUES);
    }
    
    public HtmlAttribute(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }
    
    public String getName() {
        return this.attributeName;
    }
    
    public void setName(String name) {
        if (name.equals(CLOSING_TAG)) {
            this.attributeName = DEFAULT_CLOSING_NAME;
            this.setValue(DEFAULT_EMPTY_VALUES);
        }else
            this.attributeName = name;
    }
    
    public String getValue() {
        return this.attributeValue;
    }
    
    public void setValue(String value) {
        if (this.attributeName.equals(DEFAULT_CLOSING_NAME))
            this.attributeValue = DEFAULT_EMPTY_VALUES;
        else
            this.attributeValue = value.trim();
    }
    
    public boolean isFlag() {
        return this.hasName() && !this.hasValue();
    }
    
    public boolean isClosingFlag() {
        return this.isFlag() && this.nameIs(DEFAULT_CLOSING_NAME);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                 + ((attributeName == null) ? 0 : attributeName.hashCode());
        result = prime * result
                 + ((attributeValue == null) ? 0 : attributeValue.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other instanceof HtmlAttribute)
            return equals( (HtmlAttribute) other);
        else
            return super.equals(other);
    }
    
    public boolean equals(HtmlAttribute other) {
        boolean result = (this.nameIs(other.attributeName) &&
                          this.valueIs(other.attributeValue));
        return result;
    }
    
    public String toString() {
        String name = (this.isClosingFlag()) ? CLOSING_TAG : this.attributeName;
        String value = (this.hasValue()) ? attributeSplitter + this.attributeValue : "";
        return name + value;
    }
    
    private boolean hasValue() {
        return !this.valueIs(DEFAULT_EMPTY_VALUES);
    }
    
    private boolean hasName() {
        return !this.nameIs(DEFAULT_EMPTY_VALUES);
    }
    
    private boolean nameIs(String s) {
        return this.attributeName.equals(s);
    }
    
    private boolean valueIs(String s) {
        return this.attributeValue.equals(s);
    }
}
