package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class UnclosedTagParsingError extends NonFatalParsingError {
    private static final long serialVersionUID = -8194616683282825723L;
    private static final String defaultMsg = "UNEXPECTED OPEN TAG. Found open tag before current tag was closed";
    
    public UnclosedTagParsingError(Point position, HtmlData result) {
        super(position, result, MarkupTag.OPENING_TAG.toChar(), defaultMsg);
        //logError(result.getErrorReporter());
    }
    
    public boolean equals(Object other) {
        return getClass() == other.getClass();
    }
}
