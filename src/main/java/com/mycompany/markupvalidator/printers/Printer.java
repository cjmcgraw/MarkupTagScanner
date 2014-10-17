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
package com.mycompany.markupvalidator.printers;

import java.io.PrintStream;

public interface Printer {

    public void addPrinter(PrintStream printer);

    public Iterable<PrintStream> getPrinters();

    public void removePrinter(PrintStream printer);

    public void print(String s);

    public void print(Object obj);

    public void println();

    public void println(String s);

    public void println(Object obj);
}
