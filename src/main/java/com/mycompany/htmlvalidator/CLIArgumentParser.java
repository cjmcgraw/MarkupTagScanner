/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator;

import org.apache.commons.cli.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Function;

public class CLIArgumentParser implements ArgumentParser {
    public static String CLI_ARG_SYNTAX = "{-h} {-f <file> | -u <url>} [-v] [-t]";
    public static String CLI_MSG = "Required flags are both format (i,e. '-h' for HTML)," +
                                   "and input source for markup to be parsed (i,e. '-u www.google.com' " +
                                   "to parse the html at www.google.com)";

    private static HelpFormatter helpFormatter = new HelpFormatter();

    private Map<Option, Function<String, Object>> functionMap;
    private CommandLineParser parser;
    private Options options;


    public CLIArgumentParser() {
        this.functionMap = configureFunctionMap();
        this.parser = new GnuParser();
        this.options = configureOptions();
    }

    private void printHelp() {
        helpFormatter.printHelp(CLI_ARG_SYNTAX, CLI_MSG, options, "");
    }

    private Map<Option, Function<String, Object>> configureFunctionMap() {
        Map<Option, Function<String, Object>> result = new HashMap<>();
        result.put(Arguments.FILE.asOption(), this::fileArgValue);
        result.put(Arguments.URL.asOption(), this::urlArgValue);
        return result;
    }

    private Options configureOptions() {
        Options result = new Options();
        result.addOptionGroup(createTypeGroup());
        result.addOptionGroup(createInputGroup());
        result.addOption(Arguments.VERBOSE.asOption());
        result.addOption(Arguments.TIME.asOption());
        return result;
    }

    private OptionGroup createTypeGroup() {
        OptionGroup result = createGroup(Arguments.HTML.asOption());
        result.setRequired(true);
        return result;
    }

    private OptionGroup createInputGroup() {
        OptionGroup result = createGroup(Arguments.FILE.asOption(),
                Arguments.URL.asOption());
        result.setRequired(true);
        return result;
    }

    private OptionGroup createGroup(Option... options) {
        OptionGroup group = new OptionGroup();

        for (Option option : options)
            group.addOption(option);

        return group;
    }

    @Override
    public Map<Arguments, Object> parseArgs(String... args) {
        Map<Arguments, Object> result = new HashMap<>();

        Iterator<Option> itr = getOptions(args);

        while(itr.hasNext()) {
            Option option = itr.next();
            String arg = option.getValue();

            Arguments key = Arguments.fromOption(option);
            Object value = functionMap.containsKey(option) ? functionMap.get(option).apply(arg) : arg;

            result.put(key, value);
        }

        return result;
    }

    @SuppressWarnings("unchecked")
    private Iterator<Option> getOptions(String... args) {
        try {
            return parser.parse(options, args).iterator();
        } catch (ParseException e) {
            printHelp();
            throw new RuntimeException("ERROR: error parsing arguments. One or more arguments improperly formed. Please see usage");
        }
    }

    private File fileArgValue(String fileName) {
        return new File(fileName);
    }

    private InputStream urlArgValue(String urlName) {
        try {
            URL url = new URL(urlName);
            return new BufferedInputStream(url.openStream());
        } catch (Exception e) {
            throw new RuntimeException("ERROR: error parsing the given URL " + urlName);
        }
    }

}
