package com.mycompany.htmlvalidator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * <code>HtmlValidator</code> stores a given list of <code>HtmlTag</code>s.
 * 
 *    Which allows for manipulation of the list of <code>HtmlTag</code>s, by
 *    adding, removing, and validating.
 * 
 *    The <code>validate</code> method is the special feature
 *    of <code>HtmlValidator</code>. Validate checks the given 
 *    <code>HtmlTag</code>s for inconsistencies in closing/opening
 *    <code>HtmlTag</code>s. This information is printed directly 
 *    to the <code>System.out</code>
 * <p>
 *    <code>validate</code> prints out each given <code>HtmlTag</code> 
 *    on its own separate line with indentation level increasing for 
 *    each opening <code>HtmlTag</code> and decreases indentation level 
 *    with each closing <code>HtmlTag</code>.if validate runs into any 
 *    given "strageness" on an <code>HtmlTag</code> then it prints an error message. 
 *    These error messages come in two varieties and are printed on 
 *    their occurence.
 * </p>
 *    1. unexpected tag - which is printed when a tag is closed before any 
 *    its children's open tags are closed.
 * <br>
 *    2. unclosed tag - which is printed when a tag was opened somewhere 
 *    in the HTML and never closed.
 * <br>
 * @author Carl McGraw
 */

public class HtmlValidatorImpl {
   /**
    * Represents indentation level to be added
    *    for each subsequent opened <code>HtmlTag</code>
    *    in the <code>validate</code> method.
    */
   public static final String INDENTATION_LEVEL = "    ";
   /**
    * Represents all tags stored in this <code>
    *    HtmlValidator</code>
    */
   private Queue<HtmlTagImpl> tags;
   
   /**
    * Generates a new <code>HtmlValidator</code> that stores
    *    <code>HtmlTag</code>'s.
    * <br>
    * This empty constructor generates an <code>HtmlValidator</code>
    *    that contains 0 <code>HtmlTag</code>s.
    */
   public HtmlValidatorImpl() {
      this(new LinkedList<HtmlTagImpl>());
   }
   
   /**
    * Generates a new <code>HtmlValidator</code> that stores
    *    <code>HtmlTag</code>s.
    * <br>
    * This constructor generates an <code>HtmlValidator</code>
    *    from the given <code>Queue&#60HtmlTag&#62</code> of 
    *    type <code>HtmlTag</code>.
    * <br>
    *    The given <code>Queue&#60HtmlTag&#62</code> is not modified 
    *    by this constructor.
    * 
    * @param tags represents the current list of HtmlTags to be stored and
    * evaluated by the <code>HtmlValidator</code>
    * @throws IllegalArgumentException if <code>tags</code> is null.
    */
   public HtmlValidatorImpl(Queue<HtmlTagImpl> tags) {
      checkValue(tags);
      this.tags = new LinkedList<HtmlTagImpl>();
      for(int i = tags.size(); i > 0; i--) {
         HtmlTagImpl curr = tags.remove();
         this.tags.add(curr);
         tags.add(curr);
      }
   }
   /**
    * Checks given <code>value</code> if null
    * 
    * @param value   represents any value that the 
    * <code>HtmlValidator</code> may check for null.
    * 
    * @throws IllegalArgumentException if given <code>value</code> is null.
    */
   private void checkValue(Object value) {
      if (value == null)
         throw new IllegalArgumentException();
   }
   /**
    * Adds the given <code>HtmlTag</code> to the current stored
    *    <code>HtmlTags</code>. The given <code>HtmlTag</code> 
    *    appended to the end of our stored tags.<br>With 
    *    each previous tag before it. ordering is always maintained.
    * 
    * @param tag represents the current <code>HtmlTag</code> to be stored.
    * 
    * @throws IllegalArgumentException if given <code>HtmlTag</code> is null
    */
   public void addTag(HtmlTagImpl tag) {
      checkValue(tag);
      tags.add(tag);
   }
   
   /**
    * returns the current stored <code>HtmlTag</code>s.
    * The returned <code>Queue&#60HtmlTag&#62</code> 
    * is also a reference to the stored <code>HtmlTag</code>s.
    * <br>
    *    Any changes to the <code>Queue&#60HtmlTag&#62</code>
    *  returned by this method will be reflected by the 
    *  stored <code>HtmlTag</code>s in this 
    *  <code>HtmlValidator</code>
    * 
    * @return  Queue&#60HtmlTag&#62 that is a representation of the storage
    *  of this <code>HtmlValidator</code>
    */
   public Queue<HtmlTagImpl> getTags() {
      return tags;
   }
   
   /**
    * removes any occurrence of the given value 
    * from the storage of <code>HtmlTag</code>s.
    * <br>
    *  Matches are determined with reference to
    *  the <code>String</code> value stored by 
    *  the <code>HtmlTag</code><br>therefore - 
    *  "p" matches both &#60p&#62, and &#60/p&#62
    *  , "title" matches both &#60;title&#62; and
    *   &#60/title&#62.
    * 
    * @param element represents the "element" of 
    * <code>HtmlTag</code> to be removed.<br>
    * Where the "element" is just the contents of 
    * the tag, ignoring the &#60, &#62, and /
    * 
    * @throws IllegalArgumentException if the given <code>String</code> is null
    */
   public void removeAll(String element) {
      checkValue(element);
      for(int i = tags.size(); i > 0; i--) {
         HtmlTagImpl curr = tags.remove();
         if(!element.equals(curr.getElement()))
            tags.add(curr);
      }
   }
   /**
    * Helper method that determines the current 
    * indentation level based on the size 
    * passed in.
    * <br>
    * Here size is meant to be the size of
    * a stack.
    * 
    * @param size int value representing the 
    * current indentation level.
    * 
    * @return  <code>String</code> with the 
    * indentation level as first element.
    */
   private String getTabs(int size) {
      String temp = "";
      for(int i = size; i > 0; i--) {
         temp += INDENTATION_LEVEL;
      }
      return temp;
   }
   
   /**
    * Prints out <code>HtmlTag</code>s stored
    *    by this object. Each <code>HtmlTag</code>
    *    is printed at a specific indentation level.
    * <br>
    *    if the <code>HtmlTag</code> is an opening
    *    tag than we follow standard Html format and
    *    it creates a new level of indentation for
    * <br>
    *    all of its "children" - when the "parent"
    *    is closed the indentation level drops back
    *    down again.
    * <br>
    *    This method may also print two possible <code>
    *    ERROR</code> messages as well.
    * <p>
    *    1. <code>unexpected tag</code> - which prints 
    *    when a parent was closed before a child or
    *    if a normal "self closing" tag was attempted
    *    to be closed
    * </p>
    * <p>
    *    2. <code>unclosed tag</code> - which prints
    *    when a non "self closing" tag was opened
    *    and subsequently never closed and the end of
    *    all <code>HtmlTags</code> was reached.
    * </p>
    *    All prints made by the <code>Validate</code>
    *    method are made to the <code>System.out</code>
    */
   public void validate() {
      Stack<HtmlTagImpl> s = new Stack<HtmlTagImpl>();
      for(int i = tags.size(); i > 0; i--) {
         String temp = getTabs(s.size());
         HtmlTagImpl curr = tags.remove();
         tags.add(curr);
         if(!curr.isOpenTag()) {
            if(s.size() == 0 || !s.peek().matches(curr)) 
               temp = "ERROR unexpected tag: ";
            else {
               s.pop();
               temp = getTabs(s.size());
            }
         } else if(!curr.isSelfClosing())
            s.push(curr);
         temp += curr.toString();
         System.out.println(temp);
      }
      
      while(!s.isEmpty())
         System.out.println("ERROR unclosed tag: " + s.pop().toString());
   }
   
}