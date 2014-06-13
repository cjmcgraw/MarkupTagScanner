package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlDataParser;

public class UnclosedTagParsingException extends ParsingException {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8194616683282825723L;

    public UnclosedTagParsingException(Point position, String msg) {
        super(position, HtmlDataParser.OPEN_TAG_ENCLOSURE, msg);
    }
}
