package com.mycompany.htmlvalidator.scanners;

import java.io.Closeable;
import java.util.Iterator;

import com.mycompany.htmlvalidator.scanners.tokens.HtmlTag;

public interface HtmlScanner extends Iterator<HtmlTag>, Closeable {
}
