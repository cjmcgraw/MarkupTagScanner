package com.mycompany.htmlvalidator.exceptions;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.*;

import org.junit.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.exceptions.*;

public class HtmlErrorReporterTest {
    private static final MarkupError FIRST_ERROR = new MissingCharacterComponentException(' ', new Point(0, 0), "some data");
    private static final MarkupError SECOND_ERROR = new UnexpectedCharacterComponentException(' ', new Point(0, 0), "some data");
    
    private static final List<MarkupError> ERRORS = generateErrorList();
    
    private static List<MarkupError> generateErrorList() {
        List<MarkupError> result = new ArrayList<>();
        result.add(FIRST_ERROR);
        result.add(SECOND_ERROR);
        return result;
    }
    
    private MarkupErrorReporter reporter;
    private List<MarkupError> data;
    
    @Test
    public void testConstructor_AddDirectlyToData_ResultFromGetMatches() {
        // Arrange
        MarkupError expData = FIRST_ERROR;
        MarkupError data;
        
        this.setState();
        
        // Apply
        this.data.add(FIRST_ERROR);
        data = this.reporter.errorIterator().next();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testConstructor_LoadDataThroughConstructor_IteratedDataMatches() {
        // Arrange
        List<MarkupError> expData = ERRORS;
        List<MarkupError> data = new ArrayList<>();
        
        this.setState(expData);
        
        // Apply
        for (MarkupError err : this.reporter.getErrors())
            data.add(err);
        
        // Assert
        assertEquals(expData, data);
    }

    @Test
    public void testHasErrors_EmptyErrorData_ResultIsFalse() {
        // Set up
        setState();

        // Test
        assertFalse(reporter.hasErrors());
    }

    @Test
    public void testHasErrors_NonEmptyErrorData_ResultIsTrue() {
        // Set up
        setState();
        this.reporter.addError(FIRST_ERROR);

        // Assert
        assertTrue(reporter.hasErrors());
    }
    
    @Test
    public void testAddError_DefaultEmptyList_AddError_ResultInDataMatches() {
        // Arrange
        MarkupError expData = FIRST_ERROR;
        MarkupError data;
        
        this.setState();
        
        // Apply
        this.reporter.addError(expData);
        data = this.data.get(0);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testAddError_DefaultEmptyList_MultipleAdds_ResultInDataMatchesAllAdds() {
        // Arrange
        List<MarkupError> expData = ERRORS;
        List<MarkupError> data;
        
        this.setState();
        
        // Apply
        this.reporter.addError(FIRST_ERROR);
        this.reporter.addError(SECOND_ERROR);
        
        data = this.data;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testAddError_ListContainsSingleError_AddError_ReusltInDataMatches() {
        // Arrange
        List<MarkupError> expData = ERRORS;
        List<MarkupError> data;
        
        this.setState(FIRST_ERROR);
        
        // Apply
        this.reporter.addError(SECOND_ERROR);
        data = this.data;
        
        // Assert
        assertEquals(expData, data);
        
    }
    
    @Test
    public void testAddErrors_DefaultEmptyList_AddListOfErrors_ResultInDataMatches() {
        // Arrange
        List<MarkupError> expData = ERRORS;
        List<MarkupError> data;
        
        this.setState();
        
        // Apply
        this.reporter.addErrors(expData);
        data = this.data;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testAddErrors_DefaultEmptyList_MultipleAddListOfErrors_ResultInDataMatches() {
        // Arrange
        List<MarkupError> expData = new ArrayList<>(ERRORS);
        expData.addAll(ERRORS);
        List<MarkupError> data;
        
        this.setState();
        
        // Apply
        this.reporter.addErrors(ERRORS);
        this.reporter.addErrors(ERRORS);
        data = this.data;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testAddErrors_ListContainsTwoErrors_AddListOfErrors_ResultInDataMatches() {
        // Arrange
        List<MarkupError> expData = Arrays.asList(FIRST_ERROR, FIRST_ERROR, SECOND_ERROR);
        List<MarkupError> data;
        
        this.setState(FIRST_ERROR);
        
        // Apply
        this.reporter.addErrors(ERRORS);
        data = this.data;
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testErrorIterator_DefaultEmptyList_ResultIsEmpty() {
        // Arrange
        List<MarkupError> expData = new ArrayList<>();
        List<MarkupError> data;
        
        this.setState();
        
        // Apply
        data = this.getData_errorIterator();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testErrorIterator_SingleErrorInList_ResultMatches() {
        // Arrange
        List<MarkupError> expData = Arrays.asList(FIRST_ERROR);
        List<MarkupError> data;
        
        this.setState(FIRST_ERROR);
        
        // Apply
        data = this.getData_errorIterator();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testErrorIterator_MultipleErrorInList_ResultMatches() {
        // Arrange
        List<MarkupError> expData = ERRORS;
        List<MarkupError> data;
        
        this.setState(ERRORS);
        
        // Apply
        data = this.getData_errorIterator();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetErrors_DefaultEmptyList_ResultIsEmpty() {
        // Arrange
        List<MarkupError> expData = new ArrayList<>();
        List<MarkupError> data;
        
        this.setState();
        
        // Apply
        data = this.getData_getErrors();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetErrors_SingleErrorInList_ResultMatches() {
        // Arrange
        List<MarkupError> expData = Arrays.asList(FIRST_ERROR);
        List<MarkupError> data;
        
        this.setState(FIRST_ERROR);
        
        // Apply
        data = this.getData_getErrors();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testGetErrors_MultipleErrorInList_ResultMatches() {
        // Arrange
        List<MarkupError> expData = ERRORS;
        List<MarkupError> data;
        
        this.setState(ERRORS);
        
        // Apply
        data = this.getData_getErrors();
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsObject_SameObjects_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.setState(ERRORS);
        
        MarkupErrorReporter other = this.reporter;
        
        // Apply
        data = this.symmetricEquals_Positive(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsObject_DiffObjects_SameFields_ResultIsTrue() {
        // Arrange
        boolean expData = true;
        boolean data;
        
        this.setState(ERRORS);
        
        MarkupErrorReporter other = new MarkupErrorReporter(ERRORS);
        
        // Apply
        data =  this.symmetricEquals_Positive(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsObject_DiffObjects_DiffErrors_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.setState(FIRST_ERROR);
        
        MarkupErrorReporter other = new MarkupErrorReporter();
        other.addError(SECOND_ERROR);
        
        // Apply
        data = this.symmetricEquals_Negative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsObject_DiffObjects_PrimaryIncludesAllErrorsInSecondaryAndMore_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.setState(ERRORS);
        
        MarkupErrorReporter other = new MarkupErrorReporter();
        other.addError(SECOND_ERROR);
        
        // Apply
        data = this.symmetricEquals_Negative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    @Test
    public void testEqualsObject_DiffObjects_SecondaryIncludesAllErrorsInPrimaryAndMore_ResultIsFalse() {
        // Arrange
        boolean expData = false;
        boolean data;
        
        this.setState(FIRST_ERROR);
        
        MarkupErrorReporter other = new MarkupErrorReporter(ERRORS);
        
        // Apply
        data = this.symmetricEquals_Negative(other);
        
        // Assert
        assertEquals(expData, data);
    }
    
    private boolean symmetricEquals_Positive(MarkupErrorReporter other) {
        return this.reporter.equals(other) &&
               other.equals(this.reporter) &&
               this.reporter.equals((Object) other) &&
               other.equals((Object) this.reporter);
    }
    
    private boolean symmetricEquals_Negative(MarkupErrorReporter other) {
        return this.reporter.equals(other) ||
               other.equals(this.reporter) ||
               this.reporter.equals((Object) other) ||
               other.equals((Object) this.reporter);
    }
    
    private void setState(MarkupError... data) {
        List<MarkupError> result = new ArrayList<>();
        
        for (MarkupError err : data)
            result.add(err);
        
        this.setState(result);
    }
    
    private List<MarkupError> getData_errorIterator() {
        List<MarkupError> result = new ArrayList<>();
        Iterator<MarkupError> itr = this.reporter.errorIterator();
        
        while (itr.hasNext())
            result.add(itr.next());
        
        return result;
    }
    
    private List<MarkupError> getData_getErrors() {
        List<MarkupError> result = new ArrayList<>();
        
        for (MarkupError err : this.reporter.getErrors())
            result.add(err);
        
        return result;
    }
    
    private void setState() {
        this.setState(new ArrayList<MarkupError>());
    }
    
    private void setState(List<MarkupError> data) {
        this.reporter = new MarkupErrorReporter(data);
        this.data = data;
    }
    
    @After
    public void clearState() {
        this.reporter = null;
        this.data = null;
    }
}