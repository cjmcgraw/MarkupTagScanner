package com.mycompany.htmlvalidator.scanners.readers;

import java.io.Closeable;
import java.util.Iterator;

import com.mycompany.htmlvalidator.scanners.Tag;

public interface MarkupReader extends Iterator<Tag>, Closeable{
}
