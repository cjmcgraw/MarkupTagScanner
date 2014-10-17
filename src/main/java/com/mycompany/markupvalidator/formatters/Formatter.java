/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.formatters;

import com.mycompany.markupvalidator.errors.InvalidStateException;

import java.awt.*;

public abstract class Formatter<T> {
    public static final String BORDER_HASH      = "#############################################";
    public static final String BORDER_EQUALS    = "=============================================";
    public static final String BORDER_DASH      = "---------------------------------------------";

    public static final String LOCATION_FORMAT  = "[%-4d, %-4d] :\t";

    public static final String NEWLINE = String.format("%n");
    public static final String SIMPLE_INDENTATION = ".";
    public static final String INDENTATION = "\t";

    public static String formatLocation(Point location) {
        return String.format(LOCATION_FORMAT, location.x, location.y);
    }

    public static String formatLocation() {
        return String.format(LOCATION_FORMAT, "", "");
    }

    public static String indent(String indentation, String msg) {
        return indentation + msg.replace(NEWLINE, NEWLINE + indentation);
    }

    public static String indent(String indentation, Object obj) {
        return indentation + obj.toString().replace(NEWLINE, NEWLINE + indentation);
    }

    private StringBuilder builder;

    protected void setState(Object s) {
        this.builder = new StringBuilder(s.toString());
    }

    protected void setState() {
        setState("");
    }

    protected void clearState() {
        this.builder = null;
    }

    public abstract String format(FormatData<T> formatData);

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
            throw new InvalidStateException("Formatter", "builder");
    }
}
