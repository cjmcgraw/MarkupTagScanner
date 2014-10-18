/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.errors;

import java.awt.Point;

import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;

public class ParsingError extends MarkupError{
    private static final long serialVersionUID = 7976665383247960546L;
    private static final String errorMsg = "Parsing Exception at [%s, %s] input = %s%n\t%s at %s";
    private static final int numOfMsgArgs = 5;
    
    private Point position;
    private char errorChar;
    
    private HtmlData htmlData;
    private String msg;
    
    public ParsingError(Point position, HtmlData htmlData, char errorChar, String msg) {
        super(String.format(errorMsg, position.x, position.y, htmlData, msg, errorChar));
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
        String errorMsg = ParsingError.errorMsg;
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
