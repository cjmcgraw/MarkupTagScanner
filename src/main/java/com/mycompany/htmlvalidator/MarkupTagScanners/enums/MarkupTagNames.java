package com.mycompany.htmlvalidator.MarkupTagScanners.enums;

public enum MarkupTagNames {   
    COMMENT_TAG     ("!--", "--"),
    ENTITY_TAG      ("!");
    
    private static final String EMPTY_NAME = "";
    
    private String beginName;
    private String endName;
    
    MarkupTagNames(String beginName, String endName) {
        this.beginName = beginName;
        this.endName = endName; 
    }
    
    MarkupTagNames(String beginName) {
        this.beginName = beginName;
        this.endName = EMPTY_NAME;
    }
    
    public boolean equals(String name) {
        return this.isOpening(name);
    }
    
    public boolean isOpening(String name) {
        return this.beginName.equals(name);
    }
    
    public boolean isClosing(String name) {
        return this.endName.equals(name);
    }
    
    public String getBeginName() {
        return this.beginName;
    }
    
    public String getEndName() {
        return this.endName;
    }
    
    public boolean hasEndName() {
        return !this.endName.equals(EMPTY_NAME);
    }

    public static boolean contains(String s) {
        return s.equals(COMMENT_TAG.getBeginName()) ||
               s.startsWith(ENTITY_TAG.getBeginName());
    }
}
