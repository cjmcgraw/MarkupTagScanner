package com.mycompany.htmlvalidator;

import java.util.*;
import java.io.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;

public class HtmlValidator implements MarkupValidator {
    private Set<PrintStream> printers;
    private Stack<Tag> openedTags;

    public HtmlValidator(Iterable<PrintStream> outputs) {
        openedTags = new Stack<>();
        printers = new HashSet<>();
        setUpOutputs(outputs);
    }

    private void setUpOutputs(Iterable<PrintStream> outputs) {
        for (PrintStream printer : outputs)
            printers.add(printer);
    }

    public HtmlValidator() {
        this(null);
    }

    public void addOutput(PrintStream output) {
        printers.add(output);
    }

    public void removeOutput(PrintStream output) {
        printers.remove(output);
    }

    @Override
    public void validate(Iterable<Tag> data) {

    }

}