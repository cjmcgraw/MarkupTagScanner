package com.mycompany.markupvalidator.printers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.*;

import static org.junit.Assert.*;

public class OutputPrinterTest {
    private static final int DEFAULT_INITIAL_PRINTERS = 5;

    private List<PrintStreamMock> mockStream;
    private OutputPrinter printer;


    @Before
    public void setUp() {
        setUp(1);
    }

    public void setUp(int n) {
        this.mockStream = new ArrayList<>();
        List<PrintStream> data = new ArrayList<>();

        for (int i = 0; i < n; i++)
            this.mockStream.add(new PrintStreamMock());

        data.addAll(mockStream);
        this.printer = new OutputPrinter(data);
    }

    @After
    public void tearDown() {
        this.mockStream = null;
        this.printer = null;
    }

    @Test
    public void testAddPrinter_NoAdded_ExpectedEmpty() throws Exception {
        // Test
        testAddPrinter_DependsOnGetPrinters();
    }

    @Test
    public void testAddPrinter_SingleAdded_ExpectedContainsSingle() throws Exception {
        // Test
        testAddPrinter_DependsOnGetPrinters(new PrintStreamMock());
    }

    @Test
    public void testAddPrinter_DoubleAdded_ExpectedContainsTwo() throws Exception {
        // Test
        testAddPrinter_DependsOnGetPrinters(new PrintStreamMock(), new PrintStreamMock());
    }

    @Test
    public void testAddPrinter_TripleAdded_ExpectedContainsThree() throws Exception {
        // Test
        testAddPrinter_DependsOnGetPrinters(new PrintStreamMock(), new PrintStreamMock(), new PrintStreamMock());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddPrinter_InvalidPrinter_NullAdded() throws Exception {
        printer.addPrinter(null);
    }

    private void testAddPrinter_DependsOnGetPrinters(PrintStream... printers) {
        // Set up
        Collection<PrintStream> exp = new ArrayList<>();

        OutputPrinter printer = new OutputPrinter();

        for (PrintStream stream : printers) {
            exp.add(stream);
            printer.addPrinter(stream);
        }

        // Test
        testPrintersMatch_DependsOnGetPrinters(exp, printer);
    }

    @Test
    public void testGetPrinters_EmptyPrinters_ExpectedEmpty() throws Exception{
        // Test
        testGetPrinters();
    }

    @Test
    public void testGetPrinters_SinglePrinter_ExpectedSingle() throws Exception{
        // Test
        testGetPrinters(new PrintStreamMock());
    }

    @Test
    public void testGetPrinters_DoublePrinter_ExpectedTwo() throws Exception {
        // Test
        testGetPrinters(new PrintStreamMock(), new PrintStreamMock());
    }

    @Test
    public void testGetPrinters_TriplePrinter_ExpectedThree() throws Exception {
        // Test
        testGetPrinters(new PrintStreamMock(), new PrintStreamMock(), new PrintStreamMock());
    }

    private void testGetPrinters(PrintStream... printers) throws Exception {
        // Set up
        Collection<PrintStream> exp = new ArrayList<>();
        exp.addAll(Arrays.asList(printers));

        // Test
        testPrintersMatch_DependsOnGetPrinters(exp, new OutputPrinter(exp));
    }

    @Test
    public void testRemovePrinter_SinglePrinterRemoved_FirstPrinter() throws Exception {
        // Test
        testRemovePrinter(0);
    }

    @Test
    public void testRemovePrinter_SinglePrinterRemoved_MiddlePrinter() throws Exception {
        //Test
        testRemovePrinter(DEFAULT_INITIAL_PRINTERS / 2);
    }

    @Test
    public void testRemovePrinter_SinglePrinterRemoved_LastPrinter() throws Exception {
        // Test
        testRemovePrinter(DEFAULT_INITIAL_PRINTERS - 1);
    }

    @Test
    public void testRemovePrinter_TwoPrintersRemoved_FirstTwo() throws Exception {
        // Set up
        int start = 0;

        // Test
        testRemovePrinter(start, start);
    }

    @Test
    public void testRemovePrinter_TwoPrintersRemoved_MiddleTwo() throws Exception {
        // Set up
        int mid = DEFAULT_INITIAL_PRINTERS / 2;

        // Test
        testRemovePrinter(mid, mid);
    }

    @Test
    public void testRemovePrinter_TwoPrintersRemoved_LastTwo() throws Exception {
        // Set up
        int end = DEFAULT_INITIAL_PRINTERS - 1;

        // Test
        testRemovePrinter(end, end - 1);
    }

    @Test
    public void testRemovePrinter_ThreePrintersRemoved_FirstThree() throws Exception {
        // Set up
        int start = 0;

        // Test
        testRemovePrinter(start, start, start + 1);
    }

    @Test
    public void testRemovePrinter_ThreePrintersRemoved_MiddleThree() throws Exception {
        // Set up
        int mid = DEFAULT_INITIAL_PRINTERS / 2;

        // Test
        testRemovePrinter(mid - 1, mid, mid);
    }

    @Test
    public void testRemovePrinter_ThreePrintersRemoved_LastThree() throws Exception {
        // Set up
        int end = DEFAULT_INITIAL_PRINTERS - 1;

        // Test
        testRemovePrinter(end, end - 1, end - 2);
    }

    private void testRemovePrinter(int... indicesToRemove) throws Exception {
        // Arrange
        List<PrintStream> exp = new ArrayList<>();

        for (int i = 0; i < DEFAULT_INITIAL_PRINTERS; i++)
            exp.add(new PrintStreamMock());

        OutputPrinter printer = new OutputPrinter(exp);

        // Apply
        for (int index : indicesToRemove) {
            PrintStream toBeRemoved = exp.get(index);
            exp.remove(toBeRemoved);
            printer.removePrinter(toBeRemoved);
        }

        // Test
        testPrintersMatch_DependsOnGetPrinters(exp, printer);
    }

    private void testPrintersMatch_DependsOnGetPrinters(Iterable<PrintStream> exp, OutputPrinter printer) {
        // Arrange
        Iterable<PrintStream> actual;

        // Apply
        actual = printer.getPrinters();

        // Assert
        assertEquals(exp, actual);
    }

    @Test
    public void testPrint_SinglePrinter_SinglePrint() throws Exception {
        // Test
        testPrint("the");
    }

    @Test
    public void testPrint_SinglePrinter_TwoPrints() throws Exception {
        // Test
        testPrint("the", "quick");
    }

    @Test
    public void testPrint_SinglePrinter_ThreePrints() throws Exception {
        // Test
        testPrint("the", "quick", "brown");
    }

    @Test
    public void testPrint_SinglePrinter_MultiplePrints() throws Exception {
        // Test
        testPrint("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    @Test
    public void testPrint_DoublePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrint("the");
    }

    @Test
    public void testPrint_DoublePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrint("the", "quick");
    }

    @Test
    public void testPrint_DoublePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrint("the", "quick", "brown");
    }

    @Test
    public void testPrint_DoublePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrint("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    @Test
    public void testPrint_TriplePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrint("the");
    }

    @Test
    public void testPrint_TriplePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrint("the", "quick");
    }

    @Test
    public void testPrint_TriplePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrint("the", "quick", "brown");
    }

    @Test
    public void testPrint_TriplePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrint("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    @Test
    public void testPrint_MultiplePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrint("the");
    }

    @Test
    public void testPrint_MultiplePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrint("the", "quick");
    }

    @Test
    public void testPrint_MultiplePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrint("the", "quick", "brown");
    }

    @Test
    public void testPrint_MultiplePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrint("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    private void testPrint(String... strs) {
        // Arrange + Apply
        List<String> exp = new ArrayList<>();

        for (String s : strs) {
            exp.add(s);
            printer.print(s);
        }

        // Test
        testPrintedDataMatches(exp);
    }

    @Test
    public void testPrintln_SinglePrinter_SinglePrint() throws Exception {
        // Test
        testPrintln("the");
    }

    @Test
    public void testPrintln_SinglePrinter_TwoPrints() throws Exception {
        // Test
        testPrintln("the", "quick");
    }

    @Test
    public void testPrintln_SinglePrinter_ThreePrints() throws Exception {
        // Test
        testPrintln("the", "quick", "brown");
    }

    @Test
    public void testPrintln_SinglePrinter_MultiplePrints() throws Exception {
        // Test
        testPrintln("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    @Test
    public void testPrintln_DoublePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrintln("the");
    }

    @Test
    public void testPrintln_DoublePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrintln("the", "quick");
    }

    @Test
    public void testPrintln_DoublePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrintln("the", "quick", "brown");
    }

    @Test
    public void testPrintln_DoublePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testPrintln("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    @Test
    public void testPrintln_TriplePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrintln("the");
    }

    @Test
    public void testPrintln_TriplePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrintln("the", "quick");
    }

    @Test
    public void testPrintln_TriplePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrintln("the", "quick", "brown");
    }

    @Test
    public void testPrintln_TriplePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testPrintln("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    @Test
    public void testPrintln_MultiplePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrintln("the");
    }

    @Test
    public void testPrintln_MultiplePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrintln("the", "quick");
    }

    @Test
    public void testPrintln_MultiplePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrintln("the", "quick", "brown");
    }

    @Test
    public void testPrintln_MultiplePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testPrintln("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy", "dog");
    }

    private void testPrintln(String... strs) {
        // Arrange + Apply
        List<String> exp = new ArrayList<>();

        for (String s : strs) {
            exp.add(s + String.format("%n"));
            printer.println(s);
        }

        // Test
        testPrintedDataMatches(exp);
    }

    @Test
    public void testEmptyPrintln_SinglePrinter_SinglePrint() throws Exception {
        // Test
        testEmptyPrintln(1);
    }

    @Test
    public void testEmptyPrintln_SinglePrinter_TwoPrints() throws Exception {
        // Test
        testEmptyPrintln(2);
    }

    @Test
    public void testEmptyPrintln_SinglePrinter_ThreePrints() throws Exception {
        // Test
        testEmptyPrintln(3);
    }

    @Test
    public void testEmptyPrintln_SinglePrinter_MultiplePrints() throws Exception {
        // Test
        testEmptyPrintln(10);
    }

    @Test
    public void testEmptyPrintln_DoublePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(2);

        // Test
        testEmptyPrintln(1);
    }

    @Test
    public void testEmptyPrintln_DoublePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testEmptyPrintln(2);
    }

    @Test
    public void testEmptyPrintln_DoublePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testEmptyPrintln(2);
    }

    @Test
    public void testEmptyPrintln_DoublePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(2);

        // Test
        testEmptyPrintln(10);
    }

    @Test
    public void testEmptyPrintln_TriplePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(3);

        // Test
        testEmptyPrintln(1);
    }

    @Test
    public void testEmptyPrintln_TriplePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testEmptyPrintln(2);
    }

    @Test
    public void testEmptyPrintln_TriplePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testEmptyPrintln(3);
    }

    @Test
    public void testEmptyPrintln_TriplePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(3);

        // Test
        testEmptyPrintln(10);
    }

    @Test
    public void testEmptyPrintln_MultiplePrinter_SinglePrint() throws Exception {
        // Set up
        setUp(10);

        // Test
        testEmptyPrintln(1);
    }

    @Test
    public void testEmptyPrintln_MultiplePrinter_TwoPrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testEmptyPrintln(2);
    }

    @Test
    public void testEmptyPrintln_MultiplePrinter_ThreePrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testEmptyPrintln(3);
    }

    @Test
    public void testEmptyPrintln_MultiplePrinter_MultiplePrints() throws Exception {
        // Set up
        setUp(10);

        // Test
        testEmptyPrintln(10);
    }

    private void testEmptyPrintln(int n) {
        // Arrange + Apply
        List<String> exp = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            exp.add(String.format("%n"));
            printer.println();
        }

        // Test
        testPrintedDataMatches(exp);
    }

    private void testPrintedDataMatches(List<String> exp) {
        for (PrintStreamMock printer : mockStream)
            assertEquals(exp, printer.printData());
    }
}