package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class EndOfInputParsingError extends FatalParsingError {
    private static final long serialVersionUID = 3805654602226525591L;
    private static final String defaultMsg = " END OF INPUT DETECTED. Cannot finish parsing tag";
    private static final char endOfInput = (char) -1;
    
    public EndOfInputParsingError(Point position, HtmlData result) {
        super(position, result, endOfInput, defaultMsg);
    }
}
