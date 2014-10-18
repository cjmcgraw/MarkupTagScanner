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
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.*;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.InputParser;
import com.mycompany.markupvalidator.MarkupTagScanners.errors.EndOfInputComponentError;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class Consumer extends InputParser<Integer>{
    private int counter;
    
    @Override
    public abstract Integer parse(PushbackAndPositionReader input) throws IOException;
    
    protected Integer getCounter() {
        return this.counter;
    }
    
    @Override
    protected char read() throws IOException{
        try {
            this.counter++;
            return super.read();
        } catch (EOFException e) {
            throw new EndOfInputComponentError("");
        }
    }
    
    @Override
    protected void unread(char c) throws IOException {
        this.counter--;
        super.unread(c);
    }
    
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.counter = 0;
    }
    
    protected void clearState() {
        super.clearState();
        this.counter = 0;
    }
}
