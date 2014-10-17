/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.formatters;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

import java.awt.*;

public abstract class TagLineFormatter extends Formatter<Tag> {
    protected String indentation;
    protected String location;
    protected Tag tag;

    protected void setState(FormatData<Tag> formatData) {
        super.setState();
        this.indentation = formatData.indentation();
        this.location = formatLocation(formatData.data().location());
        this.tag = formatData.data();
    }

    protected void clearState() {
        super.clearState();
        this.indentation = null;
        this.location = null;
        this.tag = null;
    }

    public String format(Point location, String msg) {
        super.setState();
        String loc = formatLocation(location);
        writeln(indent(loc, msg));
        String result = format();
        super.clearState();
        return result;
    }

    public String format(String msg) {
        super.setState();
        String loc = formatLocation();
        writeln(indent(loc, msg));
        String result = format();
        super.clearState();
        return result;
    }

    protected void writeLine(Object obj) {
        writeData(obj);
        write(NEWLINE);
    }

    private void writeData(Object obj) {
        String data = indent(location + indentation, obj);
        write(data);
    }
}
