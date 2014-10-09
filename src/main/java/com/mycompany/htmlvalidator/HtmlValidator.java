package com.mycompany.htmlvalidator;

import java.util.*;
import java.io.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.enums.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.*;
import com.mycompany.htmlvalidator.exceptions.*;
import com.mycompany.htmlvalidator.printers.*;
import com.mycompany.htmlvalidator.utilities.*;

public class HtmlValidator implements MarkupValidator {
    public static final String DEFAULT_ERR_MSG = MarkupError.MSG;

    public static final String NON_MATCHING_ERROR_MSG = String.format(DEFAULT_ERR_MSG, "Closing Tag doesn't match opening tag [ %s ]");
    public static final String UNEXPECTED_CLOSE_TAG = String.format(DEFAULT_ERR_MSG, "Unexpected closing tag. No available opening tags to compare to!");
    public static final String UNCLOSED_TAG_MSG = String.format(DEFAULT_ERR_MSG, "UNCLOSED TAG: %s");

    public static final PrintStream DEFAULT_OUTPUT = System.out;
    public static final String DEFAULT_TAG_STACK_NAME = "HTML";


    private TagStackFactory tagStackFactory;

    private TagStack tagStack;
    private Printer printer;

    public HtmlValidator() {
        this(new OutputPrinter(Arrays.asList(DEFAULT_OUTPUT)));
    }

    public HtmlValidator(Printer printer) {
        this(printer, new TagStackFactory());
    }

    public HtmlValidator(Printer printer, TagStackFactory tagStackFactory) {
        this.printer = printer;
        this.tagStackFactory = tagStackFactory;
    }

    private void setState() {
        this.tagStack = tagStackFactory.create(DEFAULT_TAG_STACK_NAME);
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

    private void validateTags(Iterator<Tag> tags) {
        while (tags.hasNext()) {
            Tag tag = tags.next();
            printTagData(tag);
            validateTagStack(tag);
        }

        printRemainingTags();
    }

    private void printTagData(Tag tag) {
        String indentation = getIndentation(validClosingTag(tag) ? 1 : 0);
        printer.println(indentation + tag);
        printTagErrorData(tag);
    }

    private void printTagErrorData(Tag tag) {
        ErrorReporter reporter = tag.getErrorReporter();

        if (reporter.hasErrors())
            printErrorData(tag.getErrorReporter());
    }

    private void printErrorData(ErrorReporter reporter) {
        for (MarkupError err : reporter.getErrors())
            printer.println(err);
        printer.println();
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
        else
            printNonMatchingError(previousTag);
    }

    private void printRemainingTags() {
        while(!tagStack.empty()) {
            printUnclosedTag(tagStack.pop());
        }
    }

    private void printUnclosedTag(Tag tag) {
        printUnclosedTagError(tag);
        printTagErrorData(tag);
    }

    private String getIndentation() {
        return tagStack.indentation();
    }

    private String getIndentation(int truncValue) {
        String indentation =  getIndentation();
        return (indentation.length() > truncValue) ? indentation.substring(truncValue) : "";
    }

    private void pushOntoTagStack(Tag tag) {
        tagStack.push(tag);
    }

    private void printDefaultError(String msg) {
        printer.println(String.format(DEFAULT_ERR_MSG, msg));
    }

    private void printNonMatchingError(String msg) {
        printer.println(String.format(NON_MATCHING_ERROR_MSG, msg));
    }

    private void printNonMatchingError(Tag tag) {
        printNonMatchingError(tag.toString());
    }

    private void printUnclosedTagError(String msg) {
        printer.println(String.format(UNCLOSED_TAG_MSG, msg));
    }

    private void printUnclosedTagError(Tag tag) {
        printUnclosedTagError(tag.toString());
    }

    private void printUnexpectedCloseTagError() {
        printer.println(UNEXPECTED_CLOSE_TAG);
    }

    private boolean validClosingTag(Tag tag) {
        return !tagStack.empty() && isOppositeTag(tagStack.peek(), tag);
    }

    private boolean isOppositeTag(Tag tag1, Tag tag2) {
        return tag1.getName().equals(tag2.getName()) &&
               !tag1.isClosing() && tag2.isClosing();
    }
}