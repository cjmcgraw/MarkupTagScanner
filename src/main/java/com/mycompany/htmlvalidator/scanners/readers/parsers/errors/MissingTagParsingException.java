package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;

public class MissingTagParsingException extends ParsingException {
    private static final long serialVersionUID = -8566122934275099978L;
    private static final String defaultMsg = "MISSING EXPECTED TAG! Tag improperly formed, missing opening or closing brackets.";
    private static final char errorChar = HtmlParser.OPEN_TAG_ENCLOSURE;
    
    public MissingTagParsingException(Point position, HtmlData result) {
        super(position, result, errorChar, defaultMsg);
    }
    
}
