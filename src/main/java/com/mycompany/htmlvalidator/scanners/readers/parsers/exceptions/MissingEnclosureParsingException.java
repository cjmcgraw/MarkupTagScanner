package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class MissingEnclosureParsingException extends ParsingException {
    private static final long serialVersionUID = -8566122934275099978L;
    private static final String defaultMsg = "MISSING EXPECTED ENCLOSURE! Enclosure improperly formed, missing opening or closing enclosure value (either tag or quote).";
    
    public MissingEnclosureParsingException(Point position, char errorChar, HtmlData result) {
        super(position, result, errorChar, defaultMsg);
    }
    
}
