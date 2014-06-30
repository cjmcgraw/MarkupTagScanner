package com.mycompany.htmlvalidator.scanners.tokens;

import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.ParsingException;

public class MutableHtmlTag implements HtmlTag{
    public final static Set<String> SELF_CLOSING_TAGS = new HashSet<String>(
            Arrays.asList("area","base", "br", "col", "command", "hr", "img",
                          "input", "keygen","link", "meta", "param", "source", 
                          "track", "wbr", "!DOCTYPE", "!--"));
    
    private boolean isSelfClosing;
    private HtmlData data;
    
    public MutableHtmlTag() {
        this.isSelfClosing = false;
        this.data = null;
    }
    
    public MutableHtmlTag(HtmlData data) {
        this.setData(data);
    }
    
    @Override
    public HtmlData getData() {
        return this.data;
    }

    @Override
    public boolean isSelfClosing() {
        return this.isSelfClosing;
    }

    @Override
    public String getName() {
        return this.data.getName();
    }

    @Override
    public List<HtmlAttribute> getAttributes() {
        return this.data.getAttributes();
    }

    @Override
    public boolean isClosing() {
        return this.data.isClosing() || this.isSelfClosing();
    }

    @Override
    public boolean hasOpeningTag() {
        return this.data.hasOpeningTag();
    }

    @Override
    public boolean hasClosingTag() {
        return this.data.hasClosingTag();
    }
    
    @Override
    public boolean validate() {
        return (this.hasOpeningTag() &&
                this.hasClosingTag() &&
                !(this.data instanceof ParsingException));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof HtmlTag)
            return this.equals((HtmlTag) other);
        else
            return super.equals(other);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + (isSelfClosing ? 1231 : 1237);
        return result;
    }
    
    @Override
    public boolean equals(HtmlTag other) {
        return (this.elementsEqual(other) &&
                this.tagsEqual(other) &&
                this.isSelfClosing() == other.isSelfClosing() &&
                this.hasClosingTag() == other.hasClosingTag() &&
                this.hasOpeningTag() == other.hasOpeningTag() && 
                this.getAttributes().equals(other.getAttributes()) &&
                this.validate() == other.validate());
    }

    @Override
    public boolean matches(HtmlTag other) {
        return (this.elementsEqual(other) &&
                this.tagEnclosuresMatch(other) &&
                this.oppositeTag(other) && 
                this.validate() && other.validate());
    }
    
    public void setData(HtmlData data) {
        this.setState(data);
    }
    
    private void setState(HtmlData data) {
        this.isSelfClosing = SELF_CLOSING_TAGS.contains(data.getName());
        this.data = data;
    }
    
    private boolean elementsEqual(HtmlTag other) {
        return this.getName().equals(other.getName());
    }
    
    private boolean tagsEqual(HtmlTag other) {
        return ((this.isClosing() == other.isClosing()) && 
                (this.isSelfClosing() == other.isSelfClosing()));
    }
    
    private boolean tagEnclosuresMatch(HtmlTag other) {
        return (this.hasClosingTag() == other.hasClosingTag() &&
                this.hasOpeningTag() == other.hasOpeningTag() &&
                this.validate() && other.validate());
    }
    
    private boolean oppositeTag(HtmlTag other) {
        return !this.isClosing() && other.isClosing();
    }
    
    @Override
    public String toString() {
        return this.data.toString();
    }
}
