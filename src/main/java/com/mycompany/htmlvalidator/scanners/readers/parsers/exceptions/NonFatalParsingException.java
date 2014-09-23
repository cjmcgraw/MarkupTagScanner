package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class NonFatalParsingException extends ParsingException {
    private static final long serialVersionUID = 249335334748494657L;

    public NonFatalParsingException(Point position, HtmlData htmlData, char errorChar, String msg) {
        super(position, htmlData, errorChar, msg);
    }
    
}
