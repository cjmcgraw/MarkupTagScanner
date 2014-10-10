package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class FatalParsingError extends ParsingError {
    private static final long serialVersionUID = -661240324028796574L;

    public FatalParsingError(Point position, HtmlData htmlData,
                             char errorChar, String msg) {
        super(position, htmlData, errorChar, msg);
    }
    
}
