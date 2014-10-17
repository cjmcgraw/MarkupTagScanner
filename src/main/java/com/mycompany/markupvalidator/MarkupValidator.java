package com.mycompany.markupvalidator;

import java.util.*;

import com.mycompany.markupvalidator.MarkupTagScanners.Tag;
import com.mycompany.markupvalidator.reports.Report;


public interface MarkupValidator {

    public void validate(Iterator<Tag> data);

    public void validate(Iterable<Tag> data);

    public Report getReport();
}
