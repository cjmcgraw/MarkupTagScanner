/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;
import com.mycompany.markupvalidator.printers.Printer;
import com.mycompany.markupvalidator.utilities.TagStackFactory;

public class VerboseHtmlValidator extends HtmlValidator {

    public VerboseHtmlValidator() {
        super();
    }

    public VerboseHtmlValidator(Printer printer) {
        super(printer);
    }

    public VerboseHtmlValidator(Printer printer, TagStackFactory tagStackFactory) {
        super(printer, tagStackFactory);
    }

    @Override
    protected void printTagData(Tag tag) {
        String indentation = getIndentation(validClosingTag(tag) ? 1 : 0);
        printer.println(indentation + tag);
        super.printTagData(tag);
    }

    private String getIndentation() {
        return tagStack.indentation();
    }

    private String getIndentation(int truncValue) {
        String indentation =  getIndentation();
        return (indentation.length() > truncValue) ? indentation.substring(truncValue) : "";
    }

    protected boolean validClosingTag(Tag tag) {
        return !tagStack.empty() && isOppositeTag(tagStack.peek(), tag);
    }
}
