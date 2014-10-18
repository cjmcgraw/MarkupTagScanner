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
package com.mycompany.markuptagscanner.errors;

public class EndOfInputComponentError extends ComponentError {
    private static final long serialVersionUID = 5802533142904660152L;
    private static final String MSG = "End of input detected after after [%s]";
    
    private String data;
    
    public EndOfInputComponentError(String data) {
        super(String.format(DEFAULT_ERROR_MSG, String.format(MSG, data)));
        this.data = data;
    }
    
    @Override
    public String getErrorMessage() {
        return String.format(MSG, data);
    }
    
    @Override
    public String getData() {
        return data;
    }
    
}
