package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

public class CloseTagEncounteredParsingException extends ParsingException {
    private static final long serialVersionUID = 5703077562778043822L;

    public CloseTagEncounteredParsingException(Point position,
                                               String msg) {
        super(position, '>', msg);
    }
    
}
