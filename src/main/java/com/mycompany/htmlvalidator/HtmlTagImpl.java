package com.mycompany.htmlvalidator;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

import com.mycompany.htmlvalidator.interfaces.HtmlTag;

public class HtmlTagImpl implements HtmlTag{
    public final static Set<String> selfClosingTags = new HashSet<String>(
            Arrays.asList("area","base", "br", "col", "command", "hr", "img",
                          "input", "keygen","link", "meta", "param", "source", 
                          "track", "wbr")
            );
    private String element;
    private boolean isClosing;
    private boolean selfClosing;
    
    public HtmlTagImpl(String element, boolean isClosing){
        this.element = element;
        this.selfClosing = selfClosingTags.contains(element);
        this.isClosing = selfClosing || isClosing;
    }

    @Override
    public String getElement() {
        return this.element;
    }

    @Override
    public boolean isOpenTag() {
        return !this.isClosing;
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
