/*  This file is part of MarkupValidator.
 *
 *  MarkupValidator is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupValidator is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupValidator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers;

import java.io.IOException;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.MarkupTagNames;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlElementSubParser extends HtmlSubParser {
    private static final String COMMENT_NAME = MarkupTagNames.COMMENT_TAG.getBeginName();
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException {
        this.setState(input, result);
        this.parseData();
        this.clearState();
        return result;
    }
    
    private void parseData() throws IOException {
        this.parseElementName();
    }
    
    private void parseElementName() throws IOException {
        char c;

        while(!this.isComment() && this.validateChar(c = read()))
            this.getResult().updateName(c);
        /*
        if (this.isComment())
            this.unread();
            */
    }
    
    protected boolean validateChar(char c) throws IOException {
        return super.validateChar(c) && 
                this.NonClosingAttribute() &&
                !this.isWhitespace(c) && 
                !this.isComment();
    }
    
    private boolean isComment() throws IOException {
        String name = this.getResult().getName();
        
        return name.length() == COMMENT_NAME.length() && 
               name.startsWith(COMMENT_NAME);
    }
    
    protected boolean NonClosingAttribute() throws IOException {
        if (super.isClosingAttribute()) {
            this.unread();
            return false;
        }
        return true;
    }
}
