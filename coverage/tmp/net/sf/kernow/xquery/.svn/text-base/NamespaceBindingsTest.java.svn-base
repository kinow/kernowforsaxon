package net.sf.kernow.xquery;

import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;

/**
 * Unit tests about the NamespaceBindings class.
 *
 * @author Florent Georges
 * @date   2007-06-29
 */
public class NamespaceBindingsTest
        extends TestCase {
    /**
     * Test addBinding throws NPE on null prefix.
     */
    public void testAddBindingNullPrefix() {
        System.out.println("testAddBindingNullPrefix");
        
        NamespaceBindings b = new NamespaceBindings();
        try {
            b.addBinding(null, "uri");
        } catch ( NullPointerException ex ) {
            // Succeed
            return;
        }
        fail("addBinding() should have threw an NPE.");
    }
    
    /**
     * Test addBinding throws NPE on null uri.
     */
    public void testAddBindingNullURI() {
        System.out.println("testAddBindingNullURI");
        
        NamespaceBindings b = new NamespaceBindings();
        try {
            b.addBinding("prefix", null);
        } catch ( NullPointerException ex ) {
            // Succeed
            return;
        }
        fail("addBinding() should have threw an NPE.");
    }
    
    /**
     * Test addBinding throws IllegalArgumentException on already bound prefix.
     */
    public void testAddBindingPrefixBound() {
        System.out.println("testAddBindingOk");
        
        NamespaceBindings b = new NamespaceBindings();
        b.addBinding("prefix", "uri");
        try {
            b.addBinding("prefix", "uri");
        } catch ( IllegalArgumentException ex ) {
            // Succeed
            return;
        }
        fail("addBinding() should have threw an IllegalArgumentException.");
    }
    
    /**
     * Test addBinding on successful completion.
     *
     * When the prefix is not already bound, prefix is not null and URI is not null,
     * then getBinding(prefix) == uri.
     */
    public void testAddBindingOk() {
        System.out.println("testAddBindingOk");
        
        NamespaceBindings b = new NamespaceBindings();
        b.addBinding("prefix", "uri");
        assertEquals("The returned URI must be 'uri'.", "uri", b.getBinding("prefix"));
    }
    
    /**
     * Test setBinding throws NPE on null prefix.
     */
    public void testSetBindingNullPrefix() {
        System.out.println("testSetBindingNullPrefix");
        
        NamespaceBindings b = new NamespaceBindings();
        try {
            b.addBinding(null, "uri");
        } catch ( NullPointerException ex ) {
            // Succeed
            return;
        }
        fail("setBinding() should have threw an NPE.");
    }
    
    /**
     * Test setBinding throws NPE on null uri.
     */
    public void testSetBindingNullURI() {
        System.out.println("testSetBindingNullURI");
        
        NamespaceBindings b = new NamespaceBindings();
        try {
            b.addBinding("prefix", null);
        } catch ( NullPointerException ex ) {
            // Succeed
            return;
        }
        fail("setBinding() should have threw an NPE.");
    }
    
    /**
     * Test adding a binding using a prefix thats already bound.
     * Should throw an exception
     */
    public void testAddPrefixThatsAlreadyBound() {
        System.out.println("testAddPrefixThatsAlreadyBound");
        
        NamespaceBindings b = new NamespaceBindings();
        
        b.addBinding("prefix", "uri");
        assertEquals("The returned URI must be 'uri'.", "uri", b.getBinding("prefix"));
        try {                       
            b.addBinding("prefix", "uri2");
        } catch (IllegalArgumentException ex) {
            // pass
        } catch (NullPointerException ex) {
            fail("Expected IllegalArgumentException");
        }        
    }
    
    /**
     * Test getBinding throws NPE on null prefix.
     */
    public void testGetBindingNullPrefix() {
        System.out.println("testGetBindingNullPrefix");
        
        NamespaceBindings b = new NamespaceBindings();
        try {
            b.getBinding(null);
        } catch ( NullPointerException ex ) {
            // Succeed!
            return;
        }
        fail("getBinding() should have threw an NPE.");
    }
    
    /**
     * Test getBinding on successful completion.
     */
    public void testGetBindingOk() {
        System.out.println("testGetBindingOk");
        
        NamespaceBindings b = new NamespaceBindings();
        b.addBinding("p1", "uri1");
        b.addBinding("p2", "uri2");
        
        assertEquals("The returned URI must be 'uri1'.", "uri1", b.getBinding("p1"));
        assertEquals("The returned URI must be 'uri2'.", "uri2", b.getBinding("p2"));
        assertEquals("The returned URI must be null.",   null,   b.getBinding("p3"));
    }
    
    /**
     * Test getPrefixes on successful completion.
     */
    public void testGetPrefixes() {
        System.out.println("testGetPrefixes");
        
        NamespaceBindings b = new NamespaceBindings();
        b.addBinding("p1", "uri1");
        b.addBinding("p2", "uri2");
        
        Set<String> expected = new HashSet<String>();
        Set<String> result   = b.getPrefixes();
        expected.add("p1");
        expected.add("p2");
        
        assertEquals(expected, result);
    }
    
    /**
     * Test getPrefixes on successful completion.
     */
    public void testRemovedBinding() {
        System.out.println("testRemovedBinding");
        
        NamespaceBindings b = new NamespaceBindings();
        b.addBinding("foo", "bar");
        b.removeBinding("foo");
        
        assertEquals(b.getPrefixes().size(), 0);
    }
    
}
