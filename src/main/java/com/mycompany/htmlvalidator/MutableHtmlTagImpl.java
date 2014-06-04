package com.mycompany.htmlvalidator;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import com.mycompany.htmlvalidator.interfaces.HtmlTag;
import com.mycompany.htmlvalidator.interfaces.MutableHtmlTag;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;

public class MutableHtmlTagImpl implements MutableHtmlTag{
    public final static Set<String> selfClosingTags = new HashSet<String>(
            Arrays.asList("area","base", "br", "col", "command", "hr", "img",
                          "input", "keygen","link", "meta", "param", "source", 
                          "track", "wbr")
            );
    private boolean selfClosing;
    private boolean isOpen;
    private String element;
    
    public MutableHtmlTagImpl() {
        this.element = null;
        this.selfClosing = false;
        this.isOpen = false;
    }
    
    public MutableHtmlTagImpl(HtmlData data) {
        this.setData(data);
    }
    
    @Override
    public String getElement() {
        return this.element;
    }

    @Override
    public boolean isOpenTag() {
        return this.isOpen;
    }

    @Override
    public boolean isSelfClosing() {
        return this.selfClosing;
    }

    @Override
    public boolean equals(HtmlTag other) {
        return this.elementsEqual(other) && this.tagsEqual(other);
    }

    @Override
    public boolean matches(HtmlTag other) {
        return this.elementsEqual(other) && this.oppositeTag(other);
    }
    
    public void setData(HtmlData data) {
        this.setState(data.getElementData(), data.isOpenTag());
    }
    
    private void setState(String element, boolean isOpen) {
        this.setElement(element);
        this.setIsOpen(isOpen);
    }
    
    private void setElement(String element) {
        element = this.validateElement(element);
        this.element = element;
        this.selfClosing = selfClosingTags.contains(element);
    }
    
    private String validateElement(String element) {
        if(element == null || (element = element.trim()).length() <= 0)
            throw new IllegalArgumentException("Invalid element given!");
        return element;
    }
    
    private void setIsOpen(boolean isOpen) {
        this.isOpen = !this.selfClosing && isOpen;
    }
    
    private boolean elementsEqual(HtmlTag other) {
        return this.getElement() == other.getElement();
    }
    
    private boolean tagsEqual(HtmlTag other) {
        if(this.isSelfClosing())
            return other.isSelfClosing();
        return this.isOpenTag() == other.isOpenTag();
    }
    
    private boolean oppositeTag(HtmlTag other) {
        return this.isOpenTag() && !other.isOpenTag();
    }
    
}
