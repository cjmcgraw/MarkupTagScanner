package com.mycompany.htmlvalidator;

import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.Tag;


public interface MarkupValidator {

    public void validate(Iterator<Tag> data);

    public void validate(Iterable<Tag> data);
}
