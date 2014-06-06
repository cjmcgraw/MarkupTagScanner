package com.mycompany.htmlvalidator.parsers.utilities.mock;

import java.util.Map;

import com.mycompany.htmlvalidator.exceptions.IllegalHtmlTagException;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlData;
import com.mycompany.htmlvalidator.parsers.utilities.HtmlTagDataParser;

public class HtmlTagDataParserMock implements HtmlTagDataParser {
    private Map<String, Boolean> parserData;
    
    public HtmlTagDataParserMock(Map<String, Boolean> parserData) {
        this.parserData = parserData;
    }
    
    @Override
    public HtmlData parse(String data) throws IllegalHtmlTagException {
        return new HtmlData(data, this.parserData.get(data));
    }
    
}
