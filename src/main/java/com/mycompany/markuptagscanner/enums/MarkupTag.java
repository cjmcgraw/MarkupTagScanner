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

public enum MarkupTag {
    OPENING_TAG                     ('<'),
    CLOSING_TAG                     ('>'),
    CLOSING_ATTRIBUTE               ('/'),
    DOUBLE_QUOTE                    ('"'),
    SINGLE_QUOTE                    ('\''),
    ATTRIBUTE_VALUE_SEPARATOR       ('=');
    
    private char c;
    MarkupTag(char c) {
        this.c = c;
    }
    
    public boolean equals(char c) {
        return this.c == c;
    }
    
    public boolean equals(String s) {
        return this.toString().equals(s);
    }
    
    public String toString() {
        return this.c + "";
    }
    
    public char toChar() {
        return c;
    }
    
    public static MarkupTag getTag(char c) {
        for (MarkupTag tag : MarkupTag.values()) {
            if (tag.equals(c))
                return tag;
        }
        return null;
    }
    
    public static boolean isTagEnclosure(char c) {
        return MarkupTag.OPENING_TAG.equals(c) || MarkupTag.CLOSING_TAG.equals(c);
    }
    
    public static boolean isQuoteEnclosure(char c) {
        return MarkupTag.SINGLE_QUOTE.equals(c) || MarkupTag.DOUBLE_QUOTE.equals(c);
    }
}
