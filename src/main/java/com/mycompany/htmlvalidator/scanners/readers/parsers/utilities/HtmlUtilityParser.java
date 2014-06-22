package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlUtilityParser extends HtmlParser{

    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        MutableHtmlData result = new MutableHtmlData();
        this.setState(input, result);
        return this.parse(input, result);
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException;
    
}
