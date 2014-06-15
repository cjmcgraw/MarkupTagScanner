package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MutableHtmlData implements HtmlData{
    private final static char closeTag = AbstractHtmlParser.CLOSE_TAG_ENCLOSURE;
    private final static char openTag = AbstractHtmlParser.OPEN_TAG_ENCLOSURE;
   
    private final static int maxLengthOfData = 30;
    
    private boolean hasOpeningTag;
    private boolean hasClosingTag;
    private List<HtmlAttribute> attributes;
    private boolean isClosing;
    private StringBuilder name;
    
    public MutableHtmlData() {
        this.name = new StringBuilder();
        this.isClosing = false;
        
        this.hasOpeningTag = false;
        this.hasOpeningTag = false;
        
        this.attributes = new ArrayList<>();
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
    
    public List<HtmlAttribute> getAttributes() {
        return this.attributes;
    }
    
    public void setAttributes(Collection<HtmlAttribute> data) {
        this.attributes = new ArrayList<>();
        this.updateAttributes(data);
    }
    
    public void updateAttributes(Collection<HtmlAttribute> data) {
        this.attributes.addAll(data);
    }
    
    public void updateAttributes(HtmlAttribute data) {
        this.attributes.add(data);
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
        
        for (int i = 0; (i < this.attributes.size()) && (result.length() < maxLengthOfData); i++)
            result += attributes.get(i);
        
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
                this.getAttributes().equals(other.getAttributes()) &&
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
