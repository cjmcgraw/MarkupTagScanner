package com.mycompany.markupvalidator.MarkupTagScanners.readers;

import java.io.Closeable;
import java.util.Iterator;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

public interface MarkupReader extends Iterator<Tag>, Closeable{}
