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

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;
import com.mycompany.markupvalidator.errors.MarkupError;
import com.mycompany.markupvalidator.reports.Report;

public class ReportFormatter extends Formatter<Report> {
    public static final String BODY_FORMAT_SECTION_1 = "Total Errors:%n%n" +
                                                       "\t%-20s:  %-4d%n" +
                                                       "\t%-20s:  %-4d%n";
    public static final String SECTION_1_TITLE_1 = "PARSING ERRORS";
    public static final String SECTION_1_TITLE_2 = "VALIDATION ERRORS";

    public static final String BODY_SECTION_1_VALID_MSG =
            String.format("%nNO ERRORS TO REPORT!!");

    public static final String BODY_SECTION_1_INVALID_MSG =
            String.format("%nAll Errors here may have an initial cause%n" +
                          "from an improperly parsed tag.%n%n" +
                          "Take the presumed cause into account when%n" +
                          "assessing the total number of errors.%n%n" +
                          "All errors could have potentially been%n" +
                          "caused by an initial error in the presumed%n" +
                          "cause tag.%n%n");

    public static final String BODY_FORMAT_SECTION_2 = "Presumed Cause:%n%n" +
                                                       "\tLocation:%s%n%n" +
                                                       "\t%s%n";

    public static final String CAUSE_INDENTATION = "\t";

    private Report report;

    protected void setState(Report report) {
        super.setState();
        this.report = report;
    }

    protected void clearState() {
        super.clearState();
        this.report = null;
    }

    @Override
    public String format(FormatData<Report> formatData) {
        setState(formatData.data());
        String result = formatReport();
        clearState();
        return result;
    }

    private String formatReport() {
        createHeader();
        createBody();
        createFooter();
        return super.format();
    }

    private void createHeader() {
        writeln();
        writeln();
        writeln(BORDER_HASH);
        writeln();
    }

    private void createBody() {
        if (report.isValid())
            createValidBody();
        else
            createInvalidBody();
    }

    private void createValidBody() {
        writeSectionOneTotals();
        writeValidMessage();
    }

    private void createInvalidBody() {
        createSectionOne();
        createSectionTwo();
    }

    private void createSectionOne() {
        writeSectionOneTotals();
        writeSectionOneInvalidMessage();
    }

    private void writeSectionOneTotals() {
        writef(BODY_FORMAT_SECTION_1,
                SECTION_1_TITLE_1, report.parsingErrors().size(),
                SECTION_1_TITLE_2, report.validationErrors().size());
    }

    private void writeValidMessage() {
        writef(BODY_SECTION_1_VALID_MSG);
    }

    private void writeSectionOneInvalidMessage() {
        writef(BODY_SECTION_1_INVALID_MSG);
    }

    private void createSectionTwo() {
        Tag cause = report.presumedCause();
        String locStr = formatLocation(cause.location());
        String tagStr = formatTag(cause);
        writef(BODY_FORMAT_SECTION_2, locStr, tagStr);
    }

    private String formatTag(Tag tag) {
        StringBuilder result = new StringBuilder();
        result.append(tag.toString());
        result.append(NEWLINE);

        for (MarkupError err : tag.getErrorReporter().getErrors()) {
            String errStr = err.toString().replace(NEWLINE, NEWLINE + CAUSE_INDENTATION + '\t');
            result.append(CAUSE_INDENTATION + '\t');
            result.append(errStr);
        }

        return result.toString();
    }

    private void createFooter() {
        writeln();
        writeln(BORDER_HASH);
        writeln();
    }
}
