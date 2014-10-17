/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.formatters;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;

public class TagVerboseFormatter extends TagErrorsFormatter {

    @Override
    public String format(FormatData<Tag> formatData) {
        setState(formatData);
        String result = generateTagLineFormat();
        clearState();
        return result;
    }

    protected String generateTagLineFormat() {
        writeTag();
        writeErrors();
        return format();
    }

    protected void writeTag() {
        writeLine(tag);
    }
}
