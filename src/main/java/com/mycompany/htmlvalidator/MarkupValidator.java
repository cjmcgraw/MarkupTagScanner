package com.mycompany.htmlvalidator;

import java.io.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;


public interface MarkupValidator {

    public void addOutput(PrintStream output);

    public void validate(Iterable<Tag> data);
}
