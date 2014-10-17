/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator;

import com.mycompany.markupvalidator.formatters.*;
import com.mycompany.markupvalidator.printers.*;
import com.mycompany.markupvalidator.utilities.TagStackFactory;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.function.Supplier;

public class ValidatorBuilder {
    public static final PrintStream DEFAULT_OUTPUT = System.out;

    public static final String HTML_TYPE = "html";

    private Supplier<MarkupValidator> constructor;
    private TagLineFormatter formatter;
    private TagStackFactory tagFactory;
    private Printer printer;

    public ValidatorBuilder(String type) {
        this.constructor = configureConstructor(type);
        this.formatter = new TagErrorsFormatter();
        this.tagFactory = new TagStackFactory();
        this.printer = new OutputPrinter(Arrays.asList(DEFAULT_OUTPUT));
    }

    private Supplier<MarkupValidator> configureConstructor(String type) {
        if (type.equalsIgnoreCase(HTML_TYPE))
            return () -> new HtmlValidator(printer, tagFactory, formatter);
        return null;
    }

    public void isVerbose(boolean verbose) {
        if (verbose) this.formatter = new TagVerboseFormatter();
    }

    public MarkupValidator create() {
        return constructor.get();
    }
}
