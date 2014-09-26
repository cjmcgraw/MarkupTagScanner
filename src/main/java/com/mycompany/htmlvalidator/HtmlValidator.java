package com.mycompany.htmlvalidator;

import java.util.*;
import java.io.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;

public class HtmlValidator implements MarkupValidator {
    private Stack<Tag> openedTags;


    @Override
    public void setOutput(PrintStream output) {

    }

    @Override
    public void validate(Iterable<Tag> data) {

    }
    
}