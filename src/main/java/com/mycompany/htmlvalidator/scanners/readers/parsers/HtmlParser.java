package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public interface HtmlParser {
    
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException;
}
