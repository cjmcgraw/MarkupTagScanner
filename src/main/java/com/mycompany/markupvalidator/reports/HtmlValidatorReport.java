/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.reports;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;
import com.mycompany.markupvalidator.formatters.*;
import com.mycompany.markupvalidator.formatters.Formatter;

import java.util.*;

public class HtmlValidatorReport implements ReportGenerator {
    private Formatter<Report> reportFormatter = new ReportFormatter();

    private Queue<Tag> validationErrors;
    private Queue<Tag> parsingErrors;
    private Tag cause;

    public HtmlValidatorReport() {
        this.validationErrors = new LinkedList<>();
        this.parsingErrors = new LinkedList<>();
        this.cause = null;
    }

    @Override
    public Tag presumedCause() {
        return cause;
    }

    @Override
    public void addParsingError(Tag tag) {
        addTagTo(tag, parsingErrors);
    }

    @Override
    public Collection<Tag> parsingErrors() {
        return parsingErrors;
    }

    @Override
    public void addValidationError(Tag tag) {
        addTagTo(tag, validationErrors);
    }

    private void addTagTo(Tag tag, Queue<Tag> data) {
        if (!hasCause())
            setCause(tag);
        data.add(tag);
    }

    private void setCause(Tag tag) {
        this.cause = tag;
    }

    @Override
    public Collection<Tag> validationErrors() {
        return validationErrors;
    }

    @Override
    public boolean isValid() {
        return !hasCause() &&
               validationErrors.isEmpty() &&
               parsingErrors.isEmpty();
    }

    private boolean hasCause() {
        return cause != null;
    }

    @Override
    public String toString() {
        FormatData<Report> formatData = new FormatData<>(this);
        return reportFormatter.format(formatData);
    }
}
