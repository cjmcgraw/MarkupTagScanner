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
package com.mycompany.markupvalidator.MarkupTagScanners.readers;

import java.io.*;
import java.util.*;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class HtmlReaderMock implements MarkupReader {
    private Queue<HtmlData> data;
    private boolean exceptionState;
    private boolean closed;
    
    public HtmlReaderMock(Collection<HtmlData> data, boolean exceptionState) {
        this.data = new LinkedList<>(data);
        this.exceptionState = exceptionState;
        this.closed = false;
    }
    
    public boolean getClosed() {
        return this.closed;
    }

    @Override
    public boolean hasNext() {
        return !this.data.isEmpty();
    }

    @Override
    public HtmlData next(){
        if (this.exceptionState)
            throw new NoSuchElementException();
        return data.remove();
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }
}
