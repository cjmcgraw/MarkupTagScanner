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
package com.mycompany.markuptagscanner.readers.parsers.subparsers.components;

import java.io.*;
import java.util.Collection;

import com.mycompany.markuptagscanner.enums.*;
import com.mycompany.markuptagscanner.errors.*;
import com.mycompany.markuptagscanner.readers.parsers.MarkupParser;

public abstract class HtmlComponentEnclosureParser extends MarkupParser<String> {
    private final static String CLASS_NAME = "HtmlComponentEnclosureParser";
    private final static String FIELD_NAME = "enclosure";
    
    protected EnclosureTags enclosure;
    
    protected abstract String getData();
    
    protected abstract Collection<EnclosureTags> getValidEnclosures();
    
    protected boolean setAndValidateOpening(char c) throws IOException {
        MarkupTag tag = MarkupTag.getTag(c);
        this.findAndSetEnclosure(tag);
        return this.validateOpenEnclosure(c);
    }
    
    @Override
    public char read() throws IOException {
        try {
            return super.read();
        } catch (EOFException e) {
            throw new EndOfInputComponentError(this.getData());
        }
    }
    
    private void findAndSetEnclosure(MarkupTag tag) {
        this.enclosure = this.findMatchingEnclosure(tag);
    }
    
    private EnclosureTags findMatchingEnclosure(MarkupTag tag) {
        for (EnclosureTags enclosureTags : this.getValidEnclosures())
            if (enclosureTags.isOpening(tag))
                return enclosureTags;
        return null;
    }
    
    private boolean validateOpenEnclosure(char c) throws IOException {
        if(this.isMissingState()) {
            char ch = getValidOpeningEnclosure();
            String data = getData();
            this.unread(c);
            throw new MissingCharacterComponentError(ch, this.currentPosition(), data);
        }
        return true;
    }
    
    private char getValidOpeningEnclosure() {
        EnclosureTags enclosure = getValidEnclosures().iterator().next();
        MarkupTag tag = enclosure.getOpening();
        return tag.toChar();
    }
    
    protected boolean validateClosing(char c) throws IOException {
        MarkupTag tag = MarkupTag.getTag(c);
        MarkupTag expectedTag = this.getCurrentEnclosureClosing();
        
        if (tag == null || expectedTag != tag) {
            String data = getData();
            this.unread(c);
            throw new MissingCharacterComponentError(c, this.currentPosition(), data);
        }
        return expectedTag == tag;
    }
    
    protected boolean isClosing(char c) {
        MarkupTag tag = MarkupTag.getTag(c);
        return this.matchesCurrentEnclosureClosing(tag);
    }
    
    protected boolean isOpening(char c) {
        MarkupTag tag = MarkupTag.getTag(c);
        return this.findMatchingEnclosure(tag) != null;
    }
    
    private MarkupTag getCurrentEnclosureClosing() {
        this.validateState();
        return this.getEnclosure().getClosing();
    }
    
    private boolean matchesCurrentEnclosureClosing(MarkupTag tag) {
        this.validateState();
        return this.getEnclosure().isClosing(tag);
    }
    
    protected EnclosureTags getEnclosure() {
        this.validateState();
        return this.enclosure;
    }
    
    private void validateState() {
        if(this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return this.enclosure == null;
    }
}
