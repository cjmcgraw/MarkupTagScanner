package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.errors.ParsingError;

public class HtmlClosingSubParserMock extends HtmlTypeSubParserMock {
    private boolean data;
    
    public HtmlClosingSubParserMock(ParsingError exception) {
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
