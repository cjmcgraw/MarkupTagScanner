package com.mycompany.htmlvalidator.MarkupTagScanners.readers;

import java.io.Closeable;
import java.util.Iterator;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;

public interface MarkupReader extends Iterator<Tag>, Closeable{
}
