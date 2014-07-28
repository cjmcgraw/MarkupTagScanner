package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.EnclosureTags;
import com.mycompany.htmlvalidator.scanners.readers.utilities.*;

public class HtmlQuoteEnclosureParserMock extends HtmlComponentEnclosureParser {
    private List<String> receivedData;
    private RuntimeException error;
    private String result;
    
    public HtmlQuoteEnclosureParserMock(String result) {
        this(result, null);
    }
    
    public HtmlQuoteEnclosureParserMock(String result, RuntimeException error) {
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
    
    public RuntimeException getError() {
        return this.error;
    }
    
    public void setError(RuntimeException err) {
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
