package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MutableHtmlData implements HtmlData{
    private final static char closeTag = HtmlParser.CLOSE_TAG_ENCLOSURE;
    private final static char openTag = HtmlParser.OPEN_TAG_ENCLOSURE;
   
    private final static int maxLengthOfData = 30;
    
    private boolean hasOpeningTag;
    private boolean hasClosingTag;
    private List<String> data;
    private boolean isClosing;
    private String name;
    
    public MutableHtmlData() {
        this.name = "";
        this.isClosing = false;
        
        this.hasOpeningTag = false;
        this.hasOpeningTag = false;
        
        this.data = new ArrayList<String>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void confirmOpeningTag() {
        this.hasOpeningTag = true;
    }
    
    public void confirmClosingTag() {
        this.hasClosingTag = true;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<String> getData() {
        return this.data;
    }
    
    public void setData(Collection<String> data) {
        this.data.addAll(data);
    }
    
    public boolean isClosing() {
        return this.isClosing;
    }
    
    public void setIsClosing(boolean isClosing) {
        this.isClosing = isClosing;
    }
    
    public String toString() {
        String result = (this.hasOpeningTag) ? "" + openTag : "";  
                
        result += (this.isClosing) ? "/" : "";
        result += this.getName();
        result += " ";
        
        for (int i = 0; (i < this.data.size()) && (result.length() < maxLengthOfData); i++)
            result += data.get(i);
        
        if(result.length() < maxLengthOfData)
            return result.trim() + ((this.hasClosingTag) ? closeTag : ' ');
        else
            return result.substring(0, maxLengthOfData).trim() + "...";
    }
}
