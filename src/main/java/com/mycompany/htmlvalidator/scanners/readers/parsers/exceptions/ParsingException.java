package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import java.awt.Point;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class ParsingException extends RuntimeException {
    private static final long serialVersionUID = 7976665383247960546L;
    private static final String errorMsg = "-----> PARSING ERROR  at [%s, %s] tinput = %s%n\t%s at %s";
    private static final int numOfMsgArgs = 5;
    
    private Point position;
    private char errorChar;
    
    private HtmlData htmlData;
    private String msg;
    
    public ParsingException(Point position, HtmlData htmlData, char errorChar, String msg) {
        super();
        this.position = position;
        this.htmlData = htmlData;
        this.errorChar = errorChar;
        this.msg = msg;
    }
    
    public Point getPosition() {
        return this.position;
    }
    
    public void setHtmlData(HtmlData htmlData) {
        this.htmlData = htmlData;
    }
    
    public HtmlData getHtmlData() {
        return this.htmlData;
    }
    
    public char getErrorCharacter() {
        return this.errorChar;
    }
    
    public boolean validHtmlData() {
        return this.htmlData != null;
    }
    
    public String toString() {
        String errorMsg = ParsingException.errorMsg;
        Object[] msgArgs = this.parseMsgArgs();
        return String.format(errorMsg, msgArgs);
    }
    
    private Object[] parseMsgArgs() {
        Object[] result = new Object[numOfMsgArgs];
        
        result[0] = this.position.x;
        result[1] = this.position.y;
        result[2] = this.getHtmlDataToString();
        result[3] = this.msg;
        result[4] = this.errorChar;
        
        return result;
    }
    
    private String getHtmlDataToString() {
        String missingMsg = "**MISSING**";
        return (this.validHtmlData()) ? this.htmlData.toString() : missingMsg;
    }
}
