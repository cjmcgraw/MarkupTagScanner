package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.IOException;
import java.util.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.enums.EnclosureTags;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors.ComponentError;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.*;

public class HtmlQuoteEnclosureParserMock extends HtmlComponentEnclosureParser {
    private List<String> receivedData;
    private ComponentError error;
    private String result;
    
    public HtmlQuoteEnclosureParserMock(String result) {
        this(result, null);
    }
    
    public HtmlQuoteEnclosureParserMock(String result, ComponentError error) {
        this.receivedData = new ArrayList<>();
        this.result = result;
        this.error = error;
    }
    
    @Override
    protected String getData() {
        return result;
    }
    
    @Override
    protected Collection<EnclosureTags> getValidEnclosures() {
        return null;
    }
    
    public List<String> getReceivedData() {
        return this.receivedData;
    }
    
    public ComponentError getError() {
        return this.error;
    }
    
    public void setError(ComponentError err) {
        this.error = err;
    }
    
    @Override
    public String parse(PushbackAndPositionReader input) throws IOException {
        if (this.error != null) {
            input.read();
            throw this.error;
        }
        return this.parseHelper(input);
    }
   
   private String parseHelper(PushbackAndPositionReader input) throws IOException {
        PushbackAndPositionReaderMock mockInput = (PushbackAndPositionReaderMock) input;
        this.receivedData.add(mockInput.getRemainingData());
        mockInput.read();
        return this.result;
    }
    
}
