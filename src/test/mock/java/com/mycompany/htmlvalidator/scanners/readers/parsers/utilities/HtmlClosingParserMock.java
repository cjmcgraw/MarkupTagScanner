package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.ParsingException;

public class HtmlClosingParserMock extends HtmlTypeParserMock {    
    private boolean data;
    
    public HtmlClosingParserMock(ParsingException exception) {
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
