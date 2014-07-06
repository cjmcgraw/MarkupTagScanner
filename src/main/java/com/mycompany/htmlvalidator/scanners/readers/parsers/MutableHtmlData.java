package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mycompany.htmlvalidator.scanners.MarkupTag;

public class MutableHtmlData implements HtmlData{
    public final static String ELIPSIS = "...";
    public final int MAX_NUM_ATTR_IN_STRING = 2;
    
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
    
    public boolean hasOpeningTag() {
        return this.hasOpeningTag;
    }
    
    public void confirmClosingTag() {
        this.hasClosingTag = true;
    }
    
    public boolean hasClosingTag() {
        return this.hasClosingTag;
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
    
    public boolean isSelfClosing() {
        HtmlAttribute terminalAttr = this.attributes.get(this.attributes.size());
        return terminalAttr.isClosingFlag();
    }
    
    public void setIsClosing(boolean isClosing) {
        this.isClosing = isClosing;
    }
    
    public String toString() {
        String open = MarkupTag.OPENING_TAG.toString();
        String close = MarkupTag.CLOSING_TAG.toString();
        String close_attr = MarkupTag.CLOSING_ATTRIBUTE.toString();
        
        String result = this.hasOpeningTag ? "" + open : "[" + open + "]";  
                
        result += (this.isClosing) ? close_attr : "";
        result += this.getName();
        result += " ";
        result += getAttributeString();
        result += this.hasClosingTag ? close : "[" + close + "]";
        
        return result;
    }
    
    private String getAttributeString() {
        if(this.attributes.size() > MAX_NUM_ATTR_IN_STRING)
            return ELIPSIS;
        
        String result = "";
        for (HtmlAttribute data : this.attributes)
            result += data.toString() + " ";
        return result.trim();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + MAX_NUM_ATTR_IN_STRING;
        result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
        result = prime * result + (hasClosingTag ? 1231 : 1237);
        result = prime * result + (hasOpeningTag ? 1231 : 1237);
        result = prime * result + (isClosing ? 1231 : 1237);
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (obj instanceof HtmlData)
            return equals((HtmlData) obj);
        else
            return super.equals(obj);
      
    }
    
    public boolean equals(HtmlData other) {
        boolean result = (
                this.getName().equals(other.getName()) &&
                this.getAttributes().equals(other.getAttributes()) &&
                this.isClosing == other.isClosing() && 
                this.hasClosingTag == other.hasClosingTag() &&
                this.hasOpeningTag == other.hasOpeningTag()
                );
        
        return result;
    }
}
