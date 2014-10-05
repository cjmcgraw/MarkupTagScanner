package com.mycompany.htmlvalidator.printers;

import java.io.PrintStream;
import java.util.*;

public class OutputPrinter implements Printer{
    public static String ERROR_MSG = "Invalid PrintStream given to OutputPrinter.%n" +
                                     "\tExpected non-null PrintStream, got %s instead";
    public static String NEWLINE = String.format("%n");

    private Collection<PrintStream> outputs;

    public OutputPrinter() {
        this(null);
    }

    public OutputPrinter(Iterable<PrintStream> printers) {
        this.outputs = new ArrayList<>();
        this.addAvailablePrinters(printers);
    }

    private void addAvailablePrinters(Iterable<PrintStream> printers) {
        if (printers != null)
            addPrinters(printers);
    }

    private void addPrinters(Iterable<PrintStream> printers) {
        for (PrintStream printer : printers) {
            validatePrinter(printer);
            outputs.add(printer);
        }
    }

    @Override
    public void addPrinter(PrintStream printer) {
        validatePrinter(printer);
        outputs.add(printer);
    }

    @Override
    public Iterable<PrintStream> getPrinters() {
        return outputs;
    }

    @Override
    public void removePrinter(PrintStream printer) {
        outputs.remove(printer);
    }

    @Override
    public void print(String s) {
        printToAll(s);
    }

    @Override
    public void print(Object obj) {
        print(obj.toString());
    }

    @Override
    public void println() {
        println("");
    }

    @Override
    public void println(String s) {
        printToAll(s + NEWLINE);
    }

    @Override
    public void println(Object obj) {
        println(obj.toString());
    }

    private void printToAll(String s) {
        for (PrintStream printer : outputs)
            printer.print(s);
    }

    private void validatePrinter(PrintStream printer) {
        if (printer == null) {
            String msg = String.format(ERROR_MSG, printer);
            throw new IllegalArgumentException(msg);
        }
    }
}