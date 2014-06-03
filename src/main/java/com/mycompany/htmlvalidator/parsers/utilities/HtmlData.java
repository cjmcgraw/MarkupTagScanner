package com.mycompany.htmlvalidator.parsers.utilities;

public class HtmlData {
    private String elementData;
    private boolean isOpenTag;
    
    public HtmlData(String elementData, boolean isOpenTag) {
        this.elementData = elementData;
        this.isOpenTag = isOpenTag;
    }
    
    public String getElementData() {
        return this.elementData;
    }
    
    public boolean isOpenTag() {
        return isOpenTag;
    }
}
