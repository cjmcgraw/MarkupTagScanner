/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator;

import org.apache.commons.cli.*;

import java.util.*;

public enum Arguments {
    HTML    ("h", "html", "Configures the validator to parse and validate HTML", false),
    FILE    ("f", "file", "Inputs the given file as the markup data to be validated", true),
    URL     ("u", "URL", "Inputs the given URL as the markup data to be obtained and validated", true),
    VERBOSE ("v", "verbose", "Prints out each tag as it is considered, including its relative indentation level", false),
    TIME    ("t", "time", "Displays the run time of the validator as the last line", false);

    private final Option option;

    Arguments(String flag, String name, String msg, boolean requiresArg) {
        OptionBuilder.withLongOpt(name).withDescription(msg);
        if (requiresArg)
            OptionBuilder.hasArg();
        this.option = OptionBuilder.create(flag);
    }

    public Option asOption() {
        return this.option;
    }

    public String toString() {
        return this.asOption().toString();
    }

    public static Arguments fromOption(Option option) {
        for (Arguments arg : Arguments.values()) {
            if (option.equals(arg.asOption()))
                return arg;
        }
        return null;
    }
}