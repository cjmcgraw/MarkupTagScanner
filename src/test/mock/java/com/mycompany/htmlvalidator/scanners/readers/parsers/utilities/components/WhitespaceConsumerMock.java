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
    
    private Integer parseHelper(PushbackAndPositionReader input) {
        PushbackAndPositionReaderMock mockInput = (PushbackAndPositionReaderMock) input;
        this.receivedData.add(mockInput.getRemainingData());
        return 0;
    }
    
}
