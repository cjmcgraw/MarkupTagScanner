package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers;

import java.util.ArrayList;
import java.util.List;

import com.mycompany.markupvalidator.MarkupTagScanners.Attribute;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors.ParsingError;

public class HtmlAttributeSubParserMock extends HtmlTypeSubParserMock {
    private List<Attribute> data;
    
    public HtmlAttributeSubParserMock(ParsingError exception) {
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