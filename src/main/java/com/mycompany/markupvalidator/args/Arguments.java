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
