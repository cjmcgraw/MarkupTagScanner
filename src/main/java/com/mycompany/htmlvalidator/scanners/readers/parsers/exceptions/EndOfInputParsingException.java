package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class EndOfInputParsingException extends FatalParsingException {
    private static final long serialVersionUID = 3805654602226525591L;
    private static final String defaultMsg = " END OF INPUT DETECTED. Cannot finish parsing tag";
    private static final char endOfInput = (char) -1;
    
    public EndOfInputParsingException(Point position, HtmlData result) {
        super(position, result, endOfInput, defaultMsg);
    }
    
}
