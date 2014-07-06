package com.mycompany.htmlvalidator.scanners.readers.parsers.errors;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class ParsingException extends RuntimeException implements HtmlData{
    private static final long serialVersionUID = 7976665383247960546L;
    private static final String errorMsg = "-----> PARSING ERROR  at [%s, %s] input=%s : %s at %s";
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

    @Override
    public String getName() {
        return this.getNameHelper();
    }
    
    private String getNameHelper() {
        return (this.validHtmlData()) ? this.htmlData.getName() : "";
    }

    @Override
    public List<HtmlAttribute> getAttributes() {
        return this.getDataHelper();
    }
    
    private List<HtmlAttribute> getDataHelper() {
        return (this.validHtmlData()) ? this.htmlData.getAttributes() : new ArrayList<HtmlAttribute>();
    }

    @Override
    public boolean isClosing() {
        return this.isClosingHelper();
    }
    
    @Override
    public boolean hasOpeningTag() {
        return this.htmlData.hasOpeningTag();
    }

    @Override
    public boolean hasClosingTag() {
        return this.htmlData.hasClosingTag();
    }
    
    private boolean isClosingHelper() {
        return this.validHtmlData() && this.htmlData.isClosing();
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

    @Override
    public boolean isSelfClosing() {
        return true;
    }
}
