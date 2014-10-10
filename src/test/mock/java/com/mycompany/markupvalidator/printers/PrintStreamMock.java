package com.mycompany.markupvalidator.printers;

import java.io.*;
import java.util.*;

public class PrintStreamMock extends PrintStream {
    private static OutputStream stream = new PipedOutputStream();
    private static Random rand = new Random();

    private int randomNum = rand.nextInt(100000000);
    private List<String> printData;

    public PrintStreamMock() {
        super(stream);
        this.printData = new ArrayList<>();
    }

    public void print(String s) {
        printData.add(s);
    }

    public Iterable<String> printData() {
        return printData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrintStreamMock)) return false;

        PrintStreamMock that = (PrintStreamMock) o;

        if (randomNum != that.randomNum) return false;
        if (printData != null ? !printData.equals(that.printData) : that.printData != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = randomNum;
        result = 31 * result + (printData != null ? printData.hashCode() : 0);
        return result;
    }
}
