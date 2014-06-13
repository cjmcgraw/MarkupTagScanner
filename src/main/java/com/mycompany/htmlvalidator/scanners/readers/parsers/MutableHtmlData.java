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
    private StringBuilder name;
    
    public MutableHtmlData() {
        this.name = new StringBuilder();
        this.isClosing = false;
        
        this.hasOpeningTag = false;
        this.hasOpeningTag = false;
        
        this.data = new ArrayList<String>();
    }
    
    public String getName() {
        return this.name.toString();
    }
    
    public void confirmOpeningTag() {
        this.hasOpeningTag = true;
    }
    
    public void confirmClosingTag() {
        this.hasClosingTag = true;
    }
    
    public void setName(String name) {
        this.name = new StringBuilder(name);
    }
    
    public void updateName(char c) {
        this.name.append(c);
    }
    
    public void updateName(String s) {
        this.name.append(s);
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
    
    public boolean equals(Object obj) {
        if (obj instanceof MutableHtmlData) {
            return equals((MutableHtmlData) obj);
        } else if (obj instanceof HtmlData) {
            return equals((HtmlData) obj);
        }
        return false;
    }
    
    public boolean equals(HtmlData other) {
        boolean result = (
                this.getName().equals(other.getName()) &&
                this.getData().equals(other.getData()) &&
                this.isClosing == other.isClosing()
                );
        
        return result;
    }
    
    public boolean equals(MutableHtmlData other) {
        boolean result = (
                this.hasClosingTag == other.hasClosingTag &&
                this.hasOpeningTag == other.hasOpeningTag &&
                this.equals((HtmlData) other));
        return result;
    }
}
