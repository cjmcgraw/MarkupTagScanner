package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.htmlvalidator.scanners.Attribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.ParsingException;

public class HtmlAttributeParserMock extends HtmlTypeParserMock {
    private List<Attribute> data;
    
    public HtmlAttributeParserMock(ParsingException exception) {
        super(exception);
        this.data = new ArrayList<>();
    }
    
    public List<Attribute> getData() {
        return this.data;
    }
    
    public void setData(List<Attribute> data) {
        this.data = data;
    }

    @Override
    protected void updateResult() {
        this.result.setAttributes(this.data);
    }
    
}
