package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.DataParser;

public class UnexpectedCloseTagParsingException extends ParsingException {
    private static final long serialVersionUID = 5703077562778043822L;
    private static final String defaultMsg = "UNEXPECTED CLOSE TAG! Close tag at invalid location!";
    private static final char closeTag = DataParser.CLOSE_TAG_ENCLOSURE;

    public UnexpectedCloseTagParsingException(Point position, HtmlData result) {
        super(position, result, closeTag, defaultMsg);
    }
    
}
