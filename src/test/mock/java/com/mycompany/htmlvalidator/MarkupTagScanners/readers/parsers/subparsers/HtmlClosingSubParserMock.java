package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions.ParsingException;

public class HtmlClosingSubParserMock extends HtmlTypeParserMock {
    private boolean data;
    
    public HtmlClosingSubParserMock(ParsingException exception) {
        super(exception);
    }
    
    public boolean getData() {
        return this.data;
    }
    
    public void setData(boolean data) {
        this.data = data;
    }

    @Override
    protected void updateResult() {
        this.result.setIsClosing(data);
    }
}
