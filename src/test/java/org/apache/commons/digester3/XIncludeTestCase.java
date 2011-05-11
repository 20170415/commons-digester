/* $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.commons.digester3;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester3.Digester;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;


/**
 * <p>Tests for XInclude aware parsing.</p>
 *
 */
public class XIncludeTestCase {


    // ----------------------------------------------------- Instance Variables


    /**
     * The digester instance we will be processing.
     */
    protected Digester digester = null;


    // --------------------------------------------------- Overall Test Methods


    /**
     * Set up instance variables required by this test case.
     */
    @Before
    public void setUp() {

        digester = new Digester();

    }


    /**
     * Tear down instance variables required by this test case.
     */
    @After
    public void tearDown() {

        digester = null;

    }



    // ------------------------------------------------ Individual Test Methods


    /**
     * Test XInclude.
     */
    @Test
    public void testXInclude() throws SAXException, IOException {

        // Turn on XInclude processing
        digester.setNamespaceAware(true);
        digester.setXIncludeAware(true);

        // Configure the digester as required
        digester.addObjectCreate("employee", Employee.class);
        digester.addCallMethod("employee/firstName", "setFirstName", 0);
        digester.addCallMethod("employee/lastName", "setLastName", 0);

        digester.addObjectCreate("employee/address", Address.class);
        digester.addCallMethod("employee/address/type", "setType", 0);
        digester.addCallMethod("employee/address/city", "setCity", 0);
        digester.addCallMethod("employee/address/state", "setState", 0);
        digester.addSetNext("employee/address", "addAddress");

        // Parse our test input
        Employee employee = digester.parse(getInputStream("Test12.xml"));
        assertNotNull("failed to parsed an employee", employee);

        // Test basics
        assertEquals("First Name", employee.getFirstName());
        assertEquals("Last Name", employee.getLastName());

        // Test includes have been processed
        Address ha = employee.getAddress("home");
        assertNotNull(ha);
        assertEquals("Home City", ha.getCity());
        assertEquals("HS", ha.getState());
        Address oa = employee.getAddress("office");
        assertNotNull(oa);
        assertEquals("Office City", oa.getCity());
        assertEquals("OS", oa.getState());

    }

    // ------------------------------------------------ Utility Support Methods


    /**
     * Return an appropriate InputStream for the specified test file (which
     * must be inside our current package.
     *
     * @param name Name of the test file we want
     *
     * @exception IOException if an input/output error occurs
     */
    protected InputStream getInputStream(String name) throws IOException {

        return (this.getClass().getResourceAsStream
                ("/org/apache/commons/digester3/" + name));

    }


}

