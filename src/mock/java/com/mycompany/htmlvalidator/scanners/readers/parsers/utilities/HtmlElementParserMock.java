package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.ParsingException;

public class HtmlElementParserMock extends HtmlTypeParserMock {
    private String element;
    
    public HtmlElementParserMock(ParsingException exception) {
        super(exception);
        this.element = "";
    }
    
    public String getElement() {
        return this.element;
    }
    
    public void setElement(String element) {
        this.element = element;
    }

    @Override
    protected void updateResult() {
        this.result.setName(this.element);
    }
}
