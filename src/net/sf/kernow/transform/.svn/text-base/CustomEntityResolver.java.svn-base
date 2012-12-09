
package net.sf.kernow.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.transform.Source;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.tools.CatalogResolver;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

public class CustomEntityResolver implements EntityResolver {
    
    private ConcurrentHashMap<String, String> dtdCache;
    private Config config;
    
    private Catalog catalog;
    
    private static CustomEntityResolver customEntityResolver = new CustomEntityResolver();
    
    private CustomEntityResolver() {        
        config = Config.getConfig();
        dtdCache = new ConcurrentHashMap<String, String>();

        catalog = new CatalogResolver().getCatalog();
        
        // load all the files in the cache folder into the map
        loadFilesInCache();
    }
    
    public static CustomEntityResolver getInstance() {
        
        // The cache is reloaded each time to pick up any new or modified files in the dir,
        // or the cache dir location may have changed.  If we don't do this each time, the
        // user would have to restart Kernow after any changes.
        customEntityResolver.loadFilesInCache();
        return customEntityResolver;
    }
    
    /*
     * Loads the files from the cache dir into the map
     */
    private void loadFilesInCache() {

        String cacheDirPath = config.getLocalCacheDir();

        File cacheDir = new File(cacheDirPath);

        if (cacheDir.exists()) {
            if (cacheDir.isDirectory()) {
                for (File f : cacheDir.listFiles()) {
                    try {
                        String str = readFileToString(new BufferedReader(new InputStreamReader(new FileInputStream(f))));
                        dtdCache.put(f.getName(), str);
                    } catch (Exception e) {
                        Message.exception(e, false);
                    }
                }
            } else {
                Message.error("The path \"" + cacheDirPath + "\" specified in [options -> resolvers -> local cache dir] does not appear to be a directory and will be ignored.");
            }
        }
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) {
        synchronized(this) {
            
            String name = new File(systemId).getName();

            try {
                
                String pathFromCatalog = catalog.resolvePublic(publicId, systemId);
                
                if (pathFromCatalog != null) {

                    return new InputSource(pathFromCatalog);
                
                } else if (dtdCache.get(name) != null) {

                    if (!config.isSuppressURIandEntityMessages()) {
                        Message.info("Using cached copy of: " + name);
                    }

                    return (new InputSource(new StringReader(dtdCache.get(name))));

                } else {

                    BufferedReader in = new BufferedReader(new InputStreamReader(new URL(systemId).openStream()));  
                    
                    String str = readFileToString(in);

                    dtdCache.put(systemId, str);

                    if (!config.isSuppressURIandEntityMessages()) {
                        Message.info("The entity resolver has cached in memory: " + systemId + "\n");
                    }

                    return (new InputSource(new StringReader((str))));
                }
            } catch (Exception e) {
                Message.exception(e, true);
            }
        }
        
        return new InputSource(systemId);
    }

    private String readFileToString(BufferedReader in) throws IOException {
        StringBuilder buff = new StringBuilder();
        String line = in.readLine();
        while (line != null) {
            buff.append(line);
            line = in.readLine();
        }
        in.close();
        return buff.toString();
    }
}