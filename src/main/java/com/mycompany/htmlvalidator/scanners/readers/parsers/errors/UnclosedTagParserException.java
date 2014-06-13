package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

public class UnclosedTagParserException extends ParsingException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8194616683282825723L;

    public UnclosedTagParserException(Point position, char data, String msg) {
        super(position, data, msg);
    }
}
