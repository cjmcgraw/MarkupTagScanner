/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.formatters;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors.InvalidStateException;

public abstract class Formatter {
    public static final String BORDER_HASH      = "#############################################";
    public static final String BORDER_EQUALS    = "=============================================";
    public static final String BORDER_DASH      = "---------------------------------------------";

    public static final String LOCATION_FORMAT  = "[%-4d, %-4d]";

    public static final String NEWLINE = String.format("%n");

    public StringBuilder builder;

    protected void setState(Object s) {
        this.builder = new StringBuilder(s.toString());
    }

    protected void setState() {
        setState("");
    }

    protected void clearState() {
        this.builder = null;
    }

    public abstract String format(Object obj);

    protected void write(Object s) {
        getBuilder().append(s.toString());
    }

    protected void write() {
        write("");
    }

    protected void writef(String s, Object... objs) {
        write(String.format(s, objs));
    }

    protected void writeln(Object s) {
        write(s + NEWLINE);
    }

    protected void writeln() {
        writeln("");
    }

    protected String format() {
        return getBuilder().toString();
    }

    protected StringBuilder getBuilder() {
        validateState();
        return builder;
    }

    private void validateState() {
        if (builder == null)
            throw new InvalidStateException("Formatter", "format");
    }
}
