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
package com.mycompany.markuptagscanner.enums;

public enum MarkupTagNames {   
    COMMENT_TAG     ("!--", "--"),
    ENTITY_TAG      ("!");
    
    private static final String EMPTY_NAME = "";
    
    private String beginName;
    private String endName;
    
    MarkupTagNames(String beginName, String endName) {
        this.beginName = beginName;
        this.endName = endName; 
    }
    
    MarkupTagNames(String beginName) {
        this.beginName = beginName;
        this.endName = EMPTY_NAME;
    }
    
    public boolean equals(String name) {
        return this.isOpening(name);
    }
    
    public boolean isOpening(String name) {
        return this.beginName.equals(name);
    }
    
    public boolean isClosing(String name) {
        return this.endName.equals(name);
    }
    
    public String getBeginName() {
        return this.beginName;
    }
    
    public String getEndName() {
        return this.endName;
    }
    
    public boolean hasEndName() {
        return !this.endName.equals(EMPTY_NAME);
    }

    public static boolean contains(String s) {
        return s.equals(COMMENT_TAG.getBeginName()) ||
               s.startsWith(ENTITY_TAG.getBeginName());
    }
}
