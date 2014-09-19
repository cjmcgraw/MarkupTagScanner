package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.ParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlTypeParserMock extends HtmlUtilityParser {
    protected ParsingException exception;
    protected HtmlData result;
    
    protected HtmlTypeParserMock(ParsingException exception) {
        this.exception = exception;
    }
    
    public HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException {
        this.setResult(result);
        this.updateResult();
        this.throwApplicableException();
        return this.result;
    }
    
    private void setResult(HtmlData result) {
        this.result = result;
    }
    
    protected abstract void updateResult();
    
    protected void throwApplicableException() {
        beforeException();
        if(this.exception != null) {
            exception.setHtmlData(this.result);
            throw this.exception;
        }
    }
    
    protected void beforeException() {}
}
