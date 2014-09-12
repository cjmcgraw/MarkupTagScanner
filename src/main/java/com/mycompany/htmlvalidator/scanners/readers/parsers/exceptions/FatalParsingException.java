package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class FatalParsingException extends ParsingException {
    private static final long serialVersionUID = -661240324028796574L;

    public FatalParsingException(Point position, HtmlData htmlData,
            char errorChar, String msg) {
        super(position, htmlData, errorChar, msg);
    }
    
}
