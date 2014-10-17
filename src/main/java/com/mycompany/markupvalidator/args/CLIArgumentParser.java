/*  This file is part of MarkupValidator.
 *
 *  MarkupValidator is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupValidator is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupValidator. If not, see <http://www.gnu.org/licenses/>.
 */

package com.mycompany.markupvalidator.args;

import org.apache.commons.cli.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.function.Function;

public class CLIArgumentParser implements ArgumentParser {
    public static String CLI_ARG_SYNTAX = "{-h} {-f <file> | -u <url>} [-v] [-t]";
    public static String CLI_MSG = "<HtmlValidator> -h {-f <file> | -u <url>} ...";

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
