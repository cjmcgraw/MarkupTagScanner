package com.mycompany.htmlvalidator.parsers.utilities.mock;

import java.util.Map;
import java.util.NoSuchElementException;

import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlTagDataParser;

public class HtmlTagDataParserMock implements HtmlTagDataParser {
    private Map<String, Boolean> parserData;
    private boolean outputException;
    
    public HtmlTagDataParserMock(Map<String, Boolean> parserData) {
        this.parserData = parserData;
    }
    
    public void setOutputException(boolean causesException) {
        this.outputException = causesException;
    }
    
    @Override
    public HtmlData parse(String data) throws IllegalHtmlTagException {
        if (this.outputException)
            return exceptionOutput();
        else
            return normalOutput(data);
    }
    
    public HtmlData normalOutput(String data) throws IllegalHtmlTagException {
        try {
            return new HtmlData(data, this.parserData.get(data));
        } catch (NoSuchElementException e) {
            throw new IllegalHtmlTagException();
        }
    }
    
    public HtmlData exceptionOutput() throws IllegalHtmlTagException{
        throw new IllegalHtmlTagException();
    }
    
}
