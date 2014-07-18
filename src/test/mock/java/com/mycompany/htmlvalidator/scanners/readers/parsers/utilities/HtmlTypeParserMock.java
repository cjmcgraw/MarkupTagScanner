package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.ParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlTypeParserMock extends HtmlUtilityParser {
    protected ParsingException exception;
    protected MutableHtmlData result;
    
    protected HtmlTypeParserMock(ParsingException exception) {
        this.exception = exception;
    }
    
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setResult(result);
        this.updateResult();
        this.throwApplicableException();
        return this.result;
    }
    
    private void setResult(MutableHtmlData result) {
        this.result = result;
    }
    
    protected abstract void updateResult();
    
    protected void throwApplicableException() {
        if(this.exception != null) {
            exception.setHtmlData(this.result);
            throw this.exception;
        }
    }
}
