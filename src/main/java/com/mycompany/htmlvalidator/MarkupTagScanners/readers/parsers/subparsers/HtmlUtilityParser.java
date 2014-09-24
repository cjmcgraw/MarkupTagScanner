package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlUtilityParser extends DataParser{

    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        HtmlData result = new HtmlData();
        return this.parse(input, result);
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException;
    
}
