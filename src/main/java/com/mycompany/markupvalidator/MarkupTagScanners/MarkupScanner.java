package com.mycompany.markupvalidator.MarkupTagScanners;

import java.io.Closeable;
import java.util.Iterator;

public interface MarkupScanner extends Iterator<Tag>, Closeable {

    public int currentLineNumber();
}
