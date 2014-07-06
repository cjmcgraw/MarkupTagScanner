package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class UnclosedTagParsingException extends ParsingException {
    private static final long serialVersionUID = -8194616683282825723L;
    private static final String defaultMsg = "UNEXPECTED OPEN TAG. Found open tag before current tag was closed";
    
    public UnclosedTagParsingException(Point position, HtmlData result) {
        super(position, result, MarkupTag.OPENING_TAG.toChar(), defaultMsg);
    }
}
