package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public interface HtmlParser<T> {
    
    public T parse(PushbackAndPositionReader input) throws IOException;
    
}
