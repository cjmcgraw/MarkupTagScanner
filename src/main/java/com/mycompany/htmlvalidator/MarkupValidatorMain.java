/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator;

import com.mycompany.htmlvalidator.MarkupTagScanners.*;

import java.io.*;
import java.util.*;

public class MarkupValidatorMain {

    public static void main(String... args) throws Exception {
        ArgumentParser parser = new CLIArgumentParser();
        Map<Arguments, Object> params = parser.parseArgs(args);

        MarkupValidator validator = getValidator(params);
        MarkupScanner scanner = getScanner(params);
        validator.validate(scanner);
    }

    private static MarkupScanner getScanner(Map<Arguments, Object> args) throws IOException {
        if (args.containsKey(Arguments.FILE))
            return fileScanner((File) args.get(Arguments.FILE));
        else if (args.containsKey(Arguments.URL))
            return inputScanner((InputStream) args.get(Arguments.URL));
        return null;
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
