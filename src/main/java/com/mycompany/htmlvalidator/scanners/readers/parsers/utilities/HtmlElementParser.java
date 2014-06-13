package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnclosedTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlElementParser extends HtmlParser {
    public static final char elementTerminator = ' ';
    
    private PushbackAndPositionReader input;
    private MutableHtmlData result;
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.parseData();
        HtmlData dataResult = this.result;
        this.clearState();
        return dataResult;
        
    }
    
    private void parseData() throws IOException {
        this.parseElementName();
    }
    
    private boolean checkChar(char c) throws IOException {
        if(HtmlParser.isCloseTagEnclosure(c)) {
            this.input.unread(c);
            throw new UnexpectedCloseTagParsingException(this.input.getPosition(), this.result);
        }else if (HtmlParser.isOpenTagEnclosure(c)) {
            this.input.unread(c);
            throw new UnclosedTagParsingException(this.input.getPosition(), this.result);
        }
        
        return c != elementTerminator;
    }
    
    private boolean checkValue(int value) {
        if (value == -1) {
            throw new EndOfInputParsingException(this.input.getPosition(), this.result);
        }
        return true;
    }
    
    private void parseElementName() throws IOException {
        int value = this.input.read();
        char c = (char) value;
        
        while(this.checkValue(value) && this.checkChar(c)) {
            this.result.updateName(c);
            
            value = this.input.read();
            c = (char) value;
        }
    }
    
    private void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        this.input = input;
        this.result = result;
    }
    
    private void clearState() {
        this.setState(null, null);
    }
    
}
