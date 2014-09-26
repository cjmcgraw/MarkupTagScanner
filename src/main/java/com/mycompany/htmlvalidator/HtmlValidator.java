package com.mycompany.htmlvalidator;

import java.util.*;
import java.io.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;
import com.mycompany.htmlvalidator.printers.OutputPrinter;
import com.mycompany.htmlvalidator.printers.Printer;

public class HtmlValidator implements MarkupValidator {
    public static final PrintStream DEFAULT_OUTPUT = System.out;

    private Stack<Tag> openedTags;
    private Printer printer;

    public HtmlValidator(Printer printer) {
        this.openedTags = new Stack<>();
        this.printer = printer;
    }

    public HtmlValidator() {
        this(new OutputPrinter(Arrays.asList(DEFAULT_OUTPUT)));
    }

    @Override
    public void validate(Iterable<Tag> data) {

    }

}