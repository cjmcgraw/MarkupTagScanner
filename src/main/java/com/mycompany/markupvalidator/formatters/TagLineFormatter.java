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

import java.awt.*;

public abstract class TagLineFormatter extends Formatter<Tag> {
    protected String indentation;
    protected String location;
    protected Tag tag;

    protected void setState(FormatData<Tag> formatData) {
        super.setState();
        this.indentation = formatData.indentation();
        this.location = formatLocation(formatData.data().location());
        this.tag = formatData.data();
    }

    protected void clearState() {
        super.clearState();
        this.indentation = null;
        this.location = null;
        this.tag = null;
    }

    public String format(Point location, String msg) {
        super.setState();
        String loc = formatLocation(location);
        writeln(indent(loc, msg));
        String result = format();
        super.clearState();
        return result;
    }

    public String format(String msg) {
        super.setState();
        String loc = formatLocation();
        writeln(indent(loc, msg));
        String result = format();
        super.clearState();
        return result;
    }

    protected void writeLine(Object obj) {
        writeData(obj);
        write(NEWLINE);
    }

    private void writeData(Object obj) {
        String data = indent(location + indentation, obj);
        write(data);
    }
}
