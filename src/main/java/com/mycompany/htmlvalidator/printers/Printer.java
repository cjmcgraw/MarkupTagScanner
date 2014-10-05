package com.mycompany.htmlvalidator.printers;

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
