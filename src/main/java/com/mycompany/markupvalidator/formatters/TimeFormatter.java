/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.markupvalidator.formatters;

public class TimeFormatter extends Formatter{
    public static final String TIME_FORMAT = "It took %01d minutes and %.4f seconds to execute!";
    public static final double NS_DENOMINATOR = Math.pow(10, 9);

    private double ns;

    protected void setState(double ns) {
        super.setState();
        this.ns = ns;
    }

    protected void clearState() {
        super.clearState();
        this.ns = 0.0;
    }

    public String format(Object obj) {
        setState((double) obj);
        String result = generateTimeFormat();
        clearState();
        return result;
    }

    private String generateTimeFormat() {
        createHeader();
        createBody();
        createFooter();
        return format();
    }

    private void createHeader() {
        writeln();
        writeln(BORDER_DASH);
        writeln();
    }

    private void createBody() {
        int mins = (int) getTotalMinutes();
        double secs = getTotalSeconds() % 60;
        writef(TIME_FORMAT, mins, secs);
        writeln();
    }

    private double getTotalMinutes() {
        return getTotalSeconds() / 60.0;
    }

    private double getTotalSeconds() {
        return ns / NS_DENOMINATOR;
    }

    private void createFooter() {
        writeln();
        writeln(BORDER_DASH);
        writeln();
    }

}
