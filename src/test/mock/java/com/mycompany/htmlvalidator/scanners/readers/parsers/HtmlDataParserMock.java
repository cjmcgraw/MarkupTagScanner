package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;
import java.util.List;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParserMock extends HtmlParser {
    private List<HtmlData> data;
    private int currIndex;
    private char openChar;
    private char closeChar;
    
    public HtmlDataParserMock(List<HtmlData> data) {
        this.data = data;
        this.currIndex = 0;
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
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
