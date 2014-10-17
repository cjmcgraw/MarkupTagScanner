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
import java.util.*;

public class PrinterMock implements Printer{
    public static final String NEWLINE = String.format("%n");
    private List<String> printerData;


    public PrinterMock() {
        this.printerData = new ArrayList<>();
    }

    @Override
    public void addPrinter(PrintStream printer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterable<PrintStream> getPrinters() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePrinter(PrintStream printer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void print(String s) {
        printerData.add(s);
    }

    @Override
    public void print(Object obj) {
        print(obj.toString());
    }

    @Override
    public void println() {
        printerData.add(NEWLINE);
    }

    @Override
    public void println(String s) {
        printerData.add(s + NEWLINE);
    }

    @Override
    public void println(Object obj) {
        println(obj.toString());
    }

    public List<String> getPrintData() {
        return printerData;
    }
}
