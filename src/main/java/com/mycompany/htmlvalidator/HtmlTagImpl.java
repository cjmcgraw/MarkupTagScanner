package com.mycompany.htmlvalidator;

import com.mycompany.htmlvalidator.interfaces.HtmlTag;

public class HtmlTagImpl implements HtmlTag{

    @Override
    public String getElement() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isOpenTag() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSelfClosing() {
        return false;
    }

    @Override
    public boolean equals(HtmlTag other) {
        return false;
    }

    @Override
    public boolean matches(HtmlTag other) {
        return false;
    }
    
}
