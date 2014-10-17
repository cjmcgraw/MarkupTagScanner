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

package com.mycompany.markupvalidator.formatters;

public class TimeFormatter extends Formatter<Double> {
    public static final String TIME_FORMAT = "It took %01d minutes and %.4f seconds to execute!";
    public static final double NS_DENOMINATOR = Math.pow(10, 9);

    private double ns;

    protected void setState(FormatData<Double> formatData) {
        super.setState();
        this.ns = formatData.data();
    }

    protected void clearState() {
        super.clearState();
        this.ns = 0.0;
    }

    public String format(FormatData<Double> formatData) {
        this.setState(formatData);
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
