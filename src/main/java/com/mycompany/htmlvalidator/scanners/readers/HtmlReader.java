package com.mycompany.htmlvalidator.scanners.readers;

import java.io.Closeable;
import java.util.Iterator;

import com.mycompany.htmlvalidator.scanners.tokens.Tag;

public interface HtmlReader extends Iterator<Tag>, Closeable{
}
