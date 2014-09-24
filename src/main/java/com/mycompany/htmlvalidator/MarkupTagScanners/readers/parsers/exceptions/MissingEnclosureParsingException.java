package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions;

import java.awt.Point;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class MissingEnclosureParsingException extends NonFatalParsingException {
    private static final long serialVersionUID = -8566122934275099978L;
    private static final String defaultMsg = "MISSING EXPECTED ENCLOSURE! Enclosure improperly formed, " +
                                             "expected [ %s ] but that value was missing!";
    
    public MissingEnclosureParsingException(Point position, char expChar, char errorChar, HtmlData result) {
        super(position, result, errorChar, String.format(defaultMsg, expChar));
    }
    
    public boolean equals(Object other) {
        return getClass() == other.getClass();
    }
}
