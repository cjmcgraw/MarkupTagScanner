package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;

public class ParsingException extends RuntimeException {
    private static final long serialVersionUID = 7976665383247960546L;
    
    private Point position;
    private char data;
    
    public ParsingException(Point position, char data, String msg) {
        super(msg);
        this.position = position;
        this.data = data;
    }
    
    public Point getPosition() {
        return this.position;
    }
    
    public char getData() {
        return this.data;
    }
}
