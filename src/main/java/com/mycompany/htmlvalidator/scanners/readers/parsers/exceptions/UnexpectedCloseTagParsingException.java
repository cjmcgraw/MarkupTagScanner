package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import java.awt.Point;

import com.mycompany.htmlvalidator.scanners.enums.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class UnexpectedCloseTagParsingException extends NonFatalParsingException {
    private static final long serialVersionUID = 5703077562778043822L;
    private static final String defaultMsg = "UNEXPECTED CLOSE TAG! Close tag at invalid location!";

    public UnexpectedCloseTagParsingException(Point position, HtmlData result) {
        super(position, result, MarkupTag.CLOSING_TAG.toChar(), defaultMsg);
    }
}
