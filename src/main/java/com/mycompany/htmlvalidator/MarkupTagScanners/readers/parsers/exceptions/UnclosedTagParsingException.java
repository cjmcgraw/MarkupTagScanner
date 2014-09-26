package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions;

import java.awt.Point;

import com.mycompany.htmlvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class UnclosedTagParsingException extends NonFatalParsingException {
    private static final long serialVersionUID = -8194616683282825723L;
    private static final String defaultMsg = "UNEXPECTED OPEN TAG. Found open tag before current tag was closed";
    
    public UnclosedTagParsingException(Point position, HtmlData result) {
        super(position, result, MarkupTag.OPENING_TAG.toChar(), defaultMsg);
        //logError(result.getErrorReporter());
    }
    
    public boolean equals(Object other) {
        return getClass() == other.getClass();
    }
}