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
package com.mycompany.markuptagscanner.readers.parsers;

import com.mycompany.markuptagscanner.enums.MarkupTag;

public abstract class MarkupParser<T> extends InputParser<T> {
    
    protected boolean isClosingTag(char c) {
        return MarkupTag.CLOSING_TAG.equals(c);
    }
    
    protected boolean isClosingTag() {
        return this.isClosingTag(this.getCurrChar());
    }
    
    protected boolean isOpeningTag(char c) {
        return MarkupTag.OPENING_TAG.equals(c);
    }
    
    protected boolean isOpeningTag() {
        return this.isOpeningTag(this.getCurrChar());
    }
    
    protected boolean isClosingAttribute(char c) {
        return MarkupTag.CLOSING_ATTRIBUTE.equals(c);
    }
    
    protected boolean isClosingAttribute() {
        return this.isClosingAttribute(this.getCurrChar());
    }
    
    protected boolean isQuoteEnclosure(char c) {
        return MarkupTag.isQuoteEnclosure(c);
    }
    
    protected boolean isQuoteEnclosure() {
        return this.isQuoteEnclosure(this.getCurrChar());
    }
    
    protected boolean isTagEnclosure(char c) {
        return MarkupTag.isTagEnclosure(c);
    }
    
    protected boolean isTagEnclosure() {
        return this.isTagEnclosure(this.getCurrChar());
    }
}
