package com.mycompany.htmlvalidator;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher.*;

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
    
    public HtmlTagImpl(String tagData){
        // TODO set up constructor
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
        return this.elementsMatch(other) && this.tagsMatch(other);
    }

    @Override
    public boolean matches(HtmlTag other) {
        return false;
    }
    
    private boolean elementsMatch(HtmlTag other) {
        return this.element == other.getElement();
    }
    
    private boolean tagsMatch(HtmlTag other) {
        if (this.isSelfClosing()) {
            return this.isSelfClosing() == other.isSelfClosing();
        }
        return this.isOpenTag() && !other.isOpenTag();
    }
    
}
