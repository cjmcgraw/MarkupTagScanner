package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

public class EndOfInputParsingException extends ParsingException {

    /**
     * 
     */
    private static final long serialVersionUID = 3805654602226525591L;

    public EndOfInputParsingException(Point position, String msg) {
        super(position, ' ', msg);
    }
    
}
