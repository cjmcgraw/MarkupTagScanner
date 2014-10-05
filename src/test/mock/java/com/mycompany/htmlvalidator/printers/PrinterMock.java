/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.printers;

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
