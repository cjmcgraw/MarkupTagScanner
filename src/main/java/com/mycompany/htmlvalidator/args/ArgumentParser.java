/*
 * @author: Carl McGraw
 * @email:  cjmcgraw@u.washington.edu
 * @version: 1.0
 */

package com.mycompany.htmlvalidator.args;

import java.util.Map;

public interface ArgumentParser {

    public Map<Arguments, Object> parseArgs(String... args);
}
