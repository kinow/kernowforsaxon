package net.sf.kernow.xquery;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Record namespace bindings, that is prefix/URI pairs.
 *
 * Neither of prefixes or URIs can be null.
 *
 * @author Florent Georges
 */
public class NamespaceBindings {
    
    private Map<String, String> nsBindings;
    
    public NamespaceBindings() {
        nsBindings = new HashMap<String, String>();
    }
    
    /**
     * Add a binding.  This is an error if the prefix is already bound.
     *
     * @throws NullPointerException
     *             If either the prefix or the URI is null.
     *
     * @throws IllegalArgumentException
     *             If either the prefix is already bound.
     */
    public void addBinding(String prefix, String uri) throws NullPointerException, IllegalArgumentException {
        if ( prefix == null ) {
            throw new NullPointerException("Prefix cannot be null");
        }
        if ( uri == null ) {
            throw new NullPointerException("URI cannot be null");
        }
        String old_uri = nsBindings.get(prefix);
        if ( old_uri != null ) {
            throw new IllegalArgumentException("Prefix is already bound: " + prefix + "->" + old_uri);
        }
        nsBindings.put(prefix, uri);
    }
    
    /** 
     * Removes a prefix from the map
     * @param prefix The prefix of the binding that should be removed
     */
    public void removeBinding(String prefix) throws NullPointerException {
        if ( prefix == null ) {
            throw new NullPointerException("Prefix cannot be null");
        }
        
        nsBindings.remove(prefix);
    }
    
    /**
     * Removes all mappings
     */
    public void removeAllBindings() {
        nsBindings.clear();
    }
    
    /**
     * Get the URI bound to the prefix.
     *
     * @return The URI bound to the prefix or null if the prefix is not bound.
     *
     * @throws NullPointerException
     *             If the prefix is null.
     */
    public String getBinding(String prefix) throws NullPointerException {
        if ( prefix == null ) {
            throw new NullPointerException("Prefix cannot be null");
        }
        return nsBindings.get(prefix);
    }
    
    /**
     * Get the set of bound prefixes.
     * @return The namespace bindings.
     */
    public Set<String> getPrefixes() {
        return nsBindings.keySet();
    }
    
    public String toString() {
        return nsBindings.toString();
    }
}
