/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.formatters;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;
import com.mycompany.markupvalidator.errors.MarkupError;

public class TagErrorsFormatter extends TagLineFormatter {

    @Override
    public String format(FormatData<Tag> formatData) {
        setState(formatData);
        String result = formatErrors();
        clearState();
        return result;
    }

    protected String formatErrors() {
        writeErrors();
        return format();
    }


    protected void writeErrors() {
        String prev = this.indentation;
        this.indentation = "";
        writeErrorsWithIndentation();
        this.indentation = prev;
    }

    private void writeErrorsWithIndentation() {
        for (MarkupError err : this.tag.getErrorReporter().getErrors())
            writeLine(err);
    }

}
