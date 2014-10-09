package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.errors.ParsingError;

public class HtmlElementSubParserMock extends HtmlTypeSubParserMock {
    private String element;
    
    public HtmlElementSubParserMock(ParsingError exception) {
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
