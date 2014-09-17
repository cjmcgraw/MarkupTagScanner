package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class WhitespaceConsumer extends Consumer{
    
    public Integer parse(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        this.consumeAnyWhitespace();
        int counter = getCounter();
        this.clearState();
        return counter;
    }
    
    private void consumeAnyWhitespace() throws IOException {
        char c = this.read();
        
        while(this.isWhiteSpace(c))
            c = this.read();
        
        this.unread(c);
    }
    
    private boolean isWhiteSpace(char ch) {
        return Character.isWhitespace(ch);
    }
}
