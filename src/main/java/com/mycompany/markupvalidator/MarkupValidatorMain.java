/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator;

import com.mycompany.markupvalidator.MarkupTagScanners.*;
import com.mycompany.markupvalidator.args.*;
import com.mycompany.markupvalidator.formatters.*;
import com.mycompany.markupvalidator.formatters.Formatter;

import java.io.*;
import java.util.*;
import java.util.function.*;

public class MarkupValidatorMain {

    public static void main(String... args) throws Exception {
        Map<Arguments, Object> arguments = parseArguments(args);
        BiConsumer<MarkupValidator, MarkupScanner> proxy = configureValidateProxy(arguments);

        MarkupValidator validator = getValidator(arguments);
        MarkupScanner scanner = getScanner(arguments);

        proxy.accept(validator, scanner);
    }

    private static Map<Arguments, Object> parseArguments(String... args) {
        ArgumentParser parser = new CLIArgumentParser();
        return parser.parseArgs(args);
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
        return (MarkupValidator x, MarkupScanner y) -> x.validate(y);
    }

    private static void timeValidator(MarkupValidator validator, MarkupScanner scanner) {
        double t1 = System.nanoTime();
        validator.validate(scanner);
        double t2 = System.nanoTime();
        displayTimeDiff(t2 - t1);
    }

    private static void displayTimeDiff(Double ns) {
        Formatter<Double> formatter = new TimeFormatter();
        FormatData<Double> data = new FormatData<>(ns);
        System.out.println(formatter.format(data));
    }

    private static MarkupScanner fileScanner(File file) throws IOException {
        return new HtmlScanner(file);
    }

    private static MarkupScanner inputScanner(InputStream stream) throws IOException {
        return new HtmlScanner(stream);
    }

    private static MarkupValidator getValidator(Map<Arguments, Object> args) {
        ValidatorBuilder bldr = null;

        if (args.containsKey(Arguments.HTML))
            bldr = new ValidatorBuilder(ValidatorBuilder.HTML_TYPE);

        bldr.isVerbose(args.containsKey(Arguments.VERBOSE));
        return bldr.create();
    }
}
