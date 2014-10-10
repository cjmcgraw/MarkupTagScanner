/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.MarkupTagScanners;

import com.mycompany.markupvalidator.MarkupTagScanners.enums.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.*;
import com.mycompany.markupvalidator.errors.*;

import java.io.*;

public class HtmlScanner implements MarkupScanner {
    public static final String INVALID_SELF_CLOSING_ERROR_MSG = "[ %s ] contains a self closing, but isn't an expected void tag";

    private MarkupReader reader;

    public HtmlScanner(File file) throws IOException {
        this(new HtmlReader(file));
    }

    public HtmlScanner(InputStream input) throws IOException {
        this(new HtmlReader(input));
    }

    public HtmlScanner(MarkupReader reader) {
        this.reader = reader;
    }

    @Override
    public int currentLineNumber() {
        return reader.currentLineNumber();
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public boolean hasNext() {
        return reader.hasNext();
    }

    @Override
    public Tag next() {
        Tag tag = reader.next();
        try {
            validateSelfClosing(tag);
        } catch (MarkupError err) {
            tag.getErrorReporter().addError(err);
        }
        return tag;
    }

    private void validateSelfClosing(Tag tag) {
        if(invalidSelfClosingTag(tag))
            throw new InvalidMarkupError(String.format(INVALID_SELF_CLOSING_ERROR_MSG, tag));
    }

    private boolean invalidSelfClosingTag(Tag tag) {
        return tag.isSelfClosing() &&
               !VoidTag.contains(tag.getName());
    }
}
