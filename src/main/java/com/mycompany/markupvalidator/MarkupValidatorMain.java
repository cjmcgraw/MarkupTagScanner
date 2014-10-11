/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator;

import com.mycompany.markupvalidator.MarkupTagScanners.*;
import com.mycompany.markupvalidator.args.*;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class MarkupValidatorMain {
    private static final int NS_DENOMINATOR = 1000000000;
    public static final String TIME_BORDERS = String.format("%n==============================================%n");
    public static final String TIME_FRMT = TIME_BORDERS + "TIME TO EXECUTE: %01d Minutes and %.4f seconds" + TIME_BORDERS;

    public static void main(String... args) throws Exception {
        ArgumentParser parser = new CLIArgumentParser();
        Map<Arguments, Object> params = parser.parseArgs(args);

        BiConsumer<MarkupValidator, MarkupScanner> proxy = configureValidateProxy(params);

        MarkupValidator validator = getValidator(params);
        MarkupScanner scanner = getScanner(params);
        proxy.accept(validator, scanner);
    }

    private static MarkupScanner getScanner(Map<Arguments, Object> args) throws IOException {
        if (args.containsKey(Arguments.FILE))
            return fileScanner((File) args.get(Arguments.FILE));
        else if (args.containsKey(Arguments.URL))
            return inputScanner((InputStream) args.get(Arguments.URL));
        return null;
    }

    private static BiConsumer<MarkupValidator, MarkupScanner> configureValidateProxy(Map<Arguments, Object> args) {
        if (args.containsKey(Arguments.TIME))
            return MarkupValidatorMain::timeValidator;
        return (x, y) -> x.validate(y);
    }

    private static void timeValidator(MarkupValidator validator, MarkupScanner scanner) {
        double t1 = System.nanoTime();
        validator.validate(scanner);
        double t2 = System.nanoTime();
        displayTimeDiff(t2 - t1);
    }

    private static void displayTimeDiff(double ns) {
        double totalSec = nsToSec(ns);
        int mins = ((int) totalSec) / 60;
        double secs = totalSec % 60;
        System.out.format(TIME_FRMT, mins, secs);
    }

    private static double nsToSec(double ns) {
        return ns / NS_DENOMINATOR;
    }

    private static MarkupScanner fileScanner(File file) throws IOException {
        return new HtmlScanner(file);
    }

    private static MarkupScanner inputScanner(InputStream stream) throws IOException {
        return new HtmlScanner(stream);
    }

    private static MarkupValidator getValidator(Map<Arguments, Object> args) {
        if (args.containsKey(Arguments.HTML))
            return getHtmlValidator(args.containsKey(Arguments.VERBOSE));
        return null;
    }

    private static MarkupValidator getHtmlValidator(boolean verbose) {
        if (verbose)
            return new VerboseHtmlValidator();
        return new HtmlValidator();
    }
}
