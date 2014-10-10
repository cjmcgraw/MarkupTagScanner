package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers;

import java.io.IOException;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlSubParser extends DataParser{

    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        HtmlData result = new HtmlData();
        return this.parse(input, result);
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException;
    
}
