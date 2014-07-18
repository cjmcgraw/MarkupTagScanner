package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.ParsingException;

public class HtmlAttributeParserMock extends HtmlTypeParserMock {
    private List<HtmlAttribute> data;
    
    public HtmlAttributeParserMock(ParsingException exception) {
        super(exception);
        this.data = new ArrayList<>();
    }
    
    public List<HtmlAttribute> getData() {
        return this.data;
    }
    
    public void setData(List<HtmlAttribute> data) {
        this.data = data;
    }

    @Override
    protected void updateResult() {
        this.result.setAttributes(this.data);
    }
    
}
