package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;
import java.util.List;

import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.ParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParserMock extends DataParser {
    private List<HtmlData> data;
    private int currIndex;
    private char openChar;
    private char closeChar;
    
    private ParsingException err;
    
    public HtmlDataParserMock(List<HtmlData> data) {
        this.data = data;
        this.currIndex = 0;
        this.err = null;
    }
    
    public void setException(ParsingException err) {
        this.err = err;
    }
    
    public ParsingException getException() {
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
