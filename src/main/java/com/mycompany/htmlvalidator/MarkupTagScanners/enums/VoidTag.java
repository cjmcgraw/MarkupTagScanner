/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.MarkupTagScanners.enums;

import java.util.*;

public enum VoidTag {
    AREA        ("area"),
    BASE        ("base"),
    BR          ("br"),
    COL         ("col"),
    COMMAND     ("command"),
    EMBED       ("embed"),
    HR          ("hr"),
    IMG         ("img"),
    INPUT       ("input"),
    KEYGEN      ("keygen"),
    LINK        ("link"),
    META        ("meta"),
    PARAM       ("param"),
    SOURCE      ("source"),
    TRACK       ("track"),
    WBR         ("wbr");

    private static Map<String, VoidTag> reverseLookup;
    private String name;

    private VoidTag(String s) {
        this.name = s;
    }

    public String getName() {
        return name;
    }

    private static void populateReverseLookup() {
        reverseLookup = new HashMap<>();

        for (VoidTag tag : VoidTag.values())
            reverseLookup.put(tag.name, tag);

    }

    public static boolean contains(String s) {
        if(reverseLookup == null || reverseLookup.isEmpty())
            populateReverseLookup();
        return reverseLookup.containsKey(s);
    }
}
