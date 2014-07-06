package com.mycompany.htmlvalidator.scanners;

public enum MarkupTagNames {   
    SCRIPT_TAG      ("script"),
    COMMENT_TAG     ("!--", "--");
    
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
        return this.beginName.equals(name);
    }
    
    public String getEndName() {
        return this.endName;
    }
    
    public boolean hasEndName() {
        return !this.endName.equals(EMPTY_NAME);
    }
}
