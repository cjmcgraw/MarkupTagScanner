package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.ComponentException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.*;

public class WhitespaceConsumerMock extends Consumer {
    private List<String> receivedData;
    private ComponentException error;
    
    public WhitespaceConsumerMock() {
        this(null);
    }
    
    public WhitespaceConsumerMock(ComponentException error) {
        this.receivedData = new ArrayList<String>();
        this.error = error;
    }
    
    public List<String> getReceivedData() {
        return this.receivedData;
    }
    
    public ComponentException getError() {
        return this.error;
    }
    
    public void setError(ComponentException err) {
        this.error = err;
    }
    
    @Override
    public Integer parse(PushbackAndPositionReader input) throws IOException {
        if(this.error != null)
            throw this.error;
        return this.parseHelper(input);
    }
    
    private Integer parseHelper(PushbackAndPositionReader input) throws IOException {
        PushbackAndPositionReaderMock mockInput = (PushbackAndPositionReaderMock) input;
        String data = mockInput.getRemainingData();
        this.receivedData.add(data);
        
        if(data.length() > 0) {
            this.removeWhitespace(input);
        }
        
        return 0;
    }
    
    private void removeWhitespace(PushbackAndPositionReader input) throws IOException {
        String data = this.receivedData.get(this.receivedData.size() - 1);
        
        int i = 0;
        char ch = data.charAt(i);
        
        while(this.isWhitespace(ch)) {
            input.read();
            i++;
            ch = data.charAt(i);
        }
    }
    
}
