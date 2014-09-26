package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.htmlvalidator.MarkupTagScanners.Attribute;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions.ParsingException;

public class HtmlAttributeSubParserMock extends HtmlTypeParserMock {
    private List<Attribute> data;
    
    public HtmlAttributeSubParserMock(ParsingException exception) {
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
