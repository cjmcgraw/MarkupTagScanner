package com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities;

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
