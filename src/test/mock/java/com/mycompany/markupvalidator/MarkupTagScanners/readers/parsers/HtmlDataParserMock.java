package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers;

import java.io.IOException;
import java.util.List;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors.ParsingError;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParserMock extends DataParser {
    private List<HtmlData> data;
    private int currIndex;
    private char openChar;
    private char closeChar;
    
    private ParsingError err;
    
    public HtmlDataParserMock(List<HtmlData> data) {
        this.data = data;
        this.currIndex = 0;
        this.err = null;
    }
    
    public void setException(ParsingError err) {
        this.err = err;
    }
    
    public ParsingError getException() {
        return err;
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        if (err != null)
            throw err;
        
        this.openChar = (char) input.read();
        this.closeChar = (char) input.read();
        HtmlData result = data.get(this.currIndex);
        this.currIndex++;
        return result;
        
    }
    
    public char getOpenChar() {
        return this.openChar;
    }
    
    public char getCloseChar() {
        return this.closeChar;
    }
}
