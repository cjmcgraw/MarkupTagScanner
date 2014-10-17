package com.mycompany.markupvalidator;

import java.awt.*;
import java.util.*;

import com.mycompany.markupvalidator.MarkupTagScanners.*;
import com.mycompany.markupvalidator.MarkupTagScanners.enums.*;
import com.mycompany.markupvalidator.errors.*;
import com.mycompany.markupvalidator.formatters.*;
import com.mycompany.markupvalidator.printers.*;
import com.mycompany.markupvalidator.reports.*;
import com.mycompany.markupvalidator.utilities.*;

public class HtmlValidator implements MarkupValidator {
    public static final String DEFAULT_ERR_MSG = MarkupError.MSG;
    public static final String NON_MATCHING_ERROR_MSG =
            String.format(DEFAULT_ERR_MSG, "Closing Tag doesn't match opening tag [ %s ]");
    public static final String UNEXPECTED_CLOSE_TAG =
            String.format(DEFAULT_ERR_MSG, "Unexpected closing tag. No available opening tags to compare to!");
    public static final String UNCLOSED_TAG_MSG = String.format(DEFAULT_ERR_MSG, "UNCLOSED TAG: %s");

    private TagStackFactory tagStackFactory;
    private TagLineFormatter formatter;
    private ReportGenerator report;
    private TagStack tagStack;
    private Printer printer;

    public HtmlValidator(Printer printer, TagStackFactory tagStackFactory, TagLineFormatter formatter) {
        this.tagStackFactory = tagStackFactory;
        this.formatter = formatter;
        this.printer = printer;
    }

    private void setState() {
        this.tagStack = tagStackFactory.create("html");
        this.report = new HtmlValidatorReport();
    }

    private void clearState() {
        this.tagStack = null;
    }

    @Override
    public void validate(Iterator<Tag> data) {
        setState();
        validateTags(data);
        clearState();
    }

    @Override
    public void validate(Iterable<Tag> data) {
        validate(data.iterator());
    }

    @Override
    public Report getReport() {
        return report;
    }

    private void validateTags(Iterator<Tag> tags) {
        while (tags.hasNext()) {
            Tag tag = tags.next();
            interpretTagData(tag);
            validateTagStack(tag);
        }

        printRemainingTags();
        printReport();
    }

    protected void interpretTagData(Tag tag) {
        updateReport(tag);
        printf(tag);
    }

    private void updateReport(Tag tag) {
        if (tag.getErrorReporter().hasErrors())
            updateReportWithParsingErrorTag(tag);
    }

    private void updateReportWithParsingErrorTag(Tag tag) {
        report.addParsingError(tag);
    }

    private void validateTagStack(Tag tag) {
        if (!isSelfClosing(tag))
            updateTagStack(tag);
    }

    private boolean isSelfClosing(Tag tag) {
        return tag.isSelfClosing() ||
               MarkupTagNames.contains(tag.getName());
    }

    private void updateTagStack(Tag tag) {
        if (tag.isClosing())
            popFromTagStackAndValidate(tag);
        else if (tagIsOpening(tag))
            pushOntoTagStack(tag);
    }

    private boolean tagIsOpening(Tag tag) {
        return !VoidTag.contains(tag.getName());
    }

    private void popFromTagStackAndValidate(Tag tag) {
        try {
            checkPreviousTagAndPopFromStack(tag);
        } catch (EmptyStackException e) {
            printUnexpectedCloseTagError();
        }
    }

    private void checkPreviousTagAndPopFromStack(Tag tag) {
        Tag previousTag = tagStack.peek();

        if (isOppositeTag(previousTag, tag))
            tagStack.pop();
        else {
            printNonMatchingError(previousTag);
            updateReportWithValidationErrorTag(tag);
        }
    }

    private void printRemainingTags() {
        while(!tagStack.empty()) {
            Tag tag = tagStack.pop();
            updateReportWithValidationErrorTag(tag);
            printUnclosedTag(tag);
        }
    }

    private void updateReportWithValidationErrorTag(Tag tag) {
        report.addValidationError(tag);
    }

    private void printUnclosedTag(Tag tag) {
        printUnclosedTagError(tag);
        printf(tag);
    }

    private void printReport() {
        print(report);
    }

    private void pushOntoTagStack(Tag tag) {
        tagStack.push(tag);
    }

    private void printNonMatchingError(Tag tag) {
        String msg = String.format(NON_MATCHING_ERROR_MSG, tag);
        printf(tag.location(), msg);
    }

    private void printUnclosedTagError(Tag tag) {
        String msg = String.format(UNCLOSED_TAG_MSG, tag);
        printf(tag.location(), msg);
    }

    private void printUnexpectedCloseTagError() {
        println(UNEXPECTED_CLOSE_TAG);
    }

    protected String format(Point location, String msg) {
        return formatter.format(location, msg);
    }

    protected String format(FormatData<Tag> data) {
        return formatter.format(data);
    }

    protected void printf(Point location, String msg) {
        print(this.format(location, msg));
    }

    protected void printf(FormatData<Tag> data) {
        print(this.format(data));
    }

    protected void printf(Tag tag) {
        FormatData<Tag> data = new FormatData<>(tagStack.indentation(), tag);
        printf(data);
    }

    protected void print(Object obj) {
        printer.print(obj);
    }

    protected void println(Object obj) {
        printer.println(obj);
    }

    protected boolean isOppositeTag(Tag tag1, Tag tag2) {
        return tag1.getName().equals(tag2.getName()) &&
               !tag1.isClosing() && tag2.isClosing();
    }
}