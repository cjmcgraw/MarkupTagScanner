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
package com.mycompany.markuptagscanner.readers.utilities;

import java.awt.Point;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class PushbackAndPositionReaderMock implements PushbackAndPositionReader {
    private LinkedList<Character> data;
    private boolean loadExceptionState;
    private boolean isClosed;
    private Point position;
    
    public PushbackAndPositionReaderMock(LinkedList<Character> data) {
        this.data = data;
        this.loadExceptionState = false;
        this.isClosed = false;
        this.setPosition(new Point(0, 0));
    }
    
    public PushbackAndPositionReaderMock(String data) {
        this.data = this.parseStringIntoData(data);
        this.loadExceptionState = false;
        this.isClosed = false;
        this.setPosition(new Point(0, 0));
    }
    
    private LinkedList<Character> parseStringIntoData(String data) {
        LinkedList<Character> result = new LinkedList<>();
        
        for (int i = 0; i < data.length(); i++) {
            result.add(data.charAt(i));
        }
        
        return result;
    }
    
    @Override
    public int read(CharBuffer cb) throws IOException {
        this.checkValidState();
        int i = 0;
        
        for(; i < cb.limit(); i++)
            cb.append((char) this.read());
        
        return i;
    }
    
    @Override
    public void close() throws IOException {
        this.checkValidState();
        this.isClosed = true;
    }
    
    public boolean isClosed() {
        return this.isClosed;
    }
    
    @Override
    public int read() throws IOException {
        this.checkValidState();
        return (!this.data.isEmpty()) ? this.data.remove() : -1;
    }
    
    public String getRemainingData() {
        String result = "";
        
        for (char c : this.data)
            result += c;
        
        return result;
    }
    
    @Override
    public int read(char[] cbuf) throws IOException {
        this.checkValidState();
        return this.read(cbuf, 0, cbuf.length);
    }
    
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        this.checkValidState();
        
        int i = off;
        
        for(; i < len; i++)
            cbuf[i] = (char) this.read();
        
        return i;
    }
    
    @Override
    public void unread(int value) throws IOException {
        this.checkValidState();
        this.unread((char) value);
    }
    
    @Override
    public void unread(char c) throws IOException {
        this.checkValidState();
        this.data.addFirst(c);
    }
    
    @Override
    public Point getPosition() {
        return this.position;
    }
    
    public Queue<Character> getData() {
        return this.data;
    }
    
    public void setPosition(Point position) {
        this.position = position;
    }
    
    public void setExceptionState(boolean exceptionState) {
        this.loadExceptionState = exceptionState;
    }
    
    private void checkValidState() throws IOException {
        if (this.loadExceptionState)
            throw new IOException();
    }
    
}
