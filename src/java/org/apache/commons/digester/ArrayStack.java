/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001-2004 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.commons.digester;

import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * <p>Imported copy of the <code>ArrayStack</code> class from
 * Commons Collections, which was the only direct dependency from Digester.</p>
 *
 * <p><strong>WARNNG</strong> - This class is public solely to allow it to be
 * used from the <code>org.apache.commons.digester.xmlrules</code> package.
 * It should not be considered part of the public API of Commons Digester.
 * If you want to use such a class yourself, you should use the one from
 * Commons Collections directly.</p>
 *
 * <p>An implementation of the {@link java.util.Stack} API that is based on an
 * <code>ArrayList</code> instead of a <code>Vector</code>, so it is not
 * synchronized to protect against multi-threaded access.  The implementation
 * is therefore operates faster in environments where you do not need to
 * worry about multiple thread contention.</p>
 *
 * <p>Unlike <code>Stack</code>, <code>ArrayStack</code> accepts null entries.
 * </p>
 *
 * @see java.util.Stack
 * @since Commons Collections 1.0
 * @version $Revision: 1.1 $ $Date: 2004/02/15 01:02:13 $
 * 
 * @author Craig R. McClanahan
 * @author Paul Jack
 * @author Stephen Colebourne
 */
public class ArrayStack extends ArrayList {

    /** Ensure serialization compatibility */    
    private static final long serialVersionUID = 2130079159931574599L;

    /**
     * Constructs a new empty <code>ArrayStack</code>. The initial size
     * is controlled by <code>ArrayList</code> and is currently 10.
     */
    public ArrayStack() {
        super();
    }

    /**
     * Constructs a new empty <code>ArrayStack</code> with an initial size.
     * 
     * @param initialSize  the initial size to use
     * @throws IllegalArgumentException  if the specified initial size
     *  is negative
     */
    public ArrayStack(int initialSize) {
        super(initialSize);
    }

    /**
     * Return <code>true</code> if this stack is currently empty.
     * <p>
     * This method exists for compatibility with <code>java.util.Stack</code>.
     * New users of this class should use <code>isEmpty</code> instead.
     * 
     * @return true if the stack is currently empty
     */
    public boolean empty() {
        return isEmpty();
    }

    /**
     * Returns the top item off of this stack without removing it.
     *
     * @return the top item on the stack
     * @throws EmptyStackException  if the stack is empty
     */
    public Object peek() throws EmptyStackException {
        int n = size();
        if (n <= 0) {
            throw new EmptyStackException();
        } else {
            return get(n - 1);
        }
    }

    /**
     * Returns the n'th item down (zero-relative) from the top of this
     * stack without removing it.
     *
     * @param n  the number of items down to go
     * @return the n'th item on the stack, zero relative
     * @throws EmptyStackException  if there are not enough items on the
     *  stack to satisfy this request
     */
    public Object peek(int n) throws EmptyStackException {
        int m = (size() - n) - 1;
        if (m < 0) {
            throw new EmptyStackException();
        } else {
            return get(m);
        }
    }

    /**
     * Pops the top item off of this stack and return it.
     *
     * @return the top item on the stack
     * @throws EmptyStackException  if the stack is empty
     */
    public Object pop() throws EmptyStackException {
        int n = size();
        if (n <= 0) {
            throw new EmptyStackException();
        } else {
            return remove(n - 1);
        }
    }

    /**
     * Pushes a new item onto the top of this stack. The pushed item is also
     * returned. This is equivalent to calling <code>add</code>.
     *
     * @param item  the item to be added
     * @return the item just pushed
     */
    public Object push(Object item) {
        add(item);
        return item;
    }


    /**
     * Returns the one-based position of the distance from the top that the
     * specified object exists on this stack, where the top-most element is
     * considered to be at distance <code>1</code>.  If the object is not
     * present on the stack, return <code>-1</code> instead.  The
     * <code>equals()</code> method is used to compare to the items
     * in this stack.
     *
     * @param object  the object to be searched for
     * @return the 1-based depth into the stack of the object, or -1 if not found
     */
    public int search(Object object) {
        int i = size() - 1;        // Current index
        int n = 1;                 // Current distance
        while (i >= 0) {
            Object current = get(i);
            if ((object == null && current == null) ||
                (object != null && object.equals(current))) {
                return n;
            }
            i--;
            n++;
        }
        return -1;
    }


}
