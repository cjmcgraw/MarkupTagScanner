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
