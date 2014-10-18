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
package com.mycompany.markuptagscanner.enums;

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
