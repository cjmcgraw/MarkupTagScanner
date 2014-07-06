package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class WhitespaceConsumer extends HtmlParser<Integer>{
    private int counter;
    
    public Integer parse(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        this.consumeAnyWhitespace();
        Integer result = this.counter;
        this.clearState();
        return result;
    }
    
    private void consumeAnyWhitespace() throws IOException {
        char c = this.read();
        
        while(this.isWhiteSpace(c)) {
            c = this.read();
            this.counter++;
        }
        
        this.input.unread(c);
    }
    
    private boolean isWhiteSpace(char ch) {
        return Character.isWhitespace(ch);
    }
    
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.counter = 0;
    }
    
    protected void clearState() {
        super.clearState();
        this.counter = 0;
    }
}
