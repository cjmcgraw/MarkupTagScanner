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
import java.io.Closeable;
import java.io.IOException;

public interface PushbackAndPositionReader extends Readable, Closeable{
    
    public int read() throws IOException;
    
    public int read(char[] cbuf) throws IOException;
    
    public int read(char[] cbuf, int off, int len) throws IOException;
    
    public void unread(int value) throws IOException;
    
    public void unread(char c) throws IOException;
    
    public Point getPosition();
}
