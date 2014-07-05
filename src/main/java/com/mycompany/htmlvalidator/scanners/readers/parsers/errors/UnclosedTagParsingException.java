package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.DataParser;

public class UnclosedTagParsingException extends ParsingException {
    private static final long serialVersionUID = -8194616683282825723L;
    private static final String defaultMsg = "UNEXPECTED OPEN TAG. Found open tag before current tag was closed";
    private static final char openTag = DataParser.OPEN_TAG_ENCLOSURE;
    
    public UnclosedTagParsingException(Point position, HtmlData result) {
        super(position, result, openTag, defaultMsg);
    }
}
