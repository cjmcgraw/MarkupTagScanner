package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class NonFatalParsingError extends ParsingError {
    private static final long serialVersionUID = 249335334748494657L;

    public NonFatalParsingError(Point position, HtmlData htmlData, char errorChar, String msg) {
        super(position, htmlData, errorChar, msg);
    }
    
}
