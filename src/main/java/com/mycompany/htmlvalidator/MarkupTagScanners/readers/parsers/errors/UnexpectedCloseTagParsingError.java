package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.errors;

import java.awt.Point;

import com.mycompany.htmlvalidator.MarkupTagScanners.enums.MarkupTag;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class UnexpectedCloseTagParsingError extends NonFatalParsingError {
    private static final long serialVersionUID = 5703077562778043822L;
    private static final String defaultMsg = "UNEXPECTED CLOSE TAG! Close tag at invalid location!";

    public UnexpectedCloseTagParsingError(Point position, HtmlData result) {
        super(position, result, MarkupTag.CLOSING_TAG.toChar(), defaultMsg);
    }
}
