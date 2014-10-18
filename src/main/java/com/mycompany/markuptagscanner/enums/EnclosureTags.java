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
package com.mycompany.markuptagscanner.enums;

public enum EnclosureTags {
    HTML_ENCLOSURE          (MarkupTag.OPENING_TAG, MarkupTag.CLOSING_TAG),
    SINGLE_QUOTE_ENCLOSURE  (MarkupTag.SINGLE_QUOTE),
    DOUBLE_QUOTE_ENCLOSURE  (MarkupTag.DOUBLE_QUOTE);
    
    private MarkupTag open;
    private MarkupTag close;
    
    private EnclosureTags(MarkupTag open, MarkupTag close) {
        this.open = open;
        this.close = close;
    }
    
    private EnclosureTags(MarkupTag enclosure) {
        this.open = enclosure;
        this.close = enclosure;
    }
    
    public MarkupTag getOpening() {
        return this.open;
    }
    
    public MarkupTag getClosing() {
        return this.close;
    }
    
    public boolean isOpening(MarkupTag tag) {
        return this.open == tag;
    }
    
    public boolean isClosing(MarkupTag tag) {
        return this.close == tag;
    }
}
