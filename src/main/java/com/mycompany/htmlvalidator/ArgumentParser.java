/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator;

import java.util.Map;

public interface ArgumentParser {

    public Map<Arguments, Object> parseArgs(String... args);
}
