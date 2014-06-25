package com.mycompany.htmlvalidator.scanners.readers;

import java.io.Closeable;
import java.util.Iterator;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public interface HtmlReader extends Iterator<HtmlData>, Closeable{
}
