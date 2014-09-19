package com.mycompany.htmlvalidator.scanners;

import java.io.Closeable;
import java.util.Iterator;

import com.mycompany.htmlvalidator.scanners.tokens.*;

public interface HtmlScanner extends Iterator<Tag>, Closeable {
}
