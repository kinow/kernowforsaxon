
package net.sf.kernow.xquery;

import java.util.TimerTask;
import net.sf.kernow.ui.TabXQuerySandbox;
import net.sf.saxon.s9api.SaxonApiException;

/**
 * A helper class that allows an XQuery to be run after a scheduled amount of time. 
 *
 * @author Andrew Welch
 */
public class ScheduledXQuery extends TimerTask {
    

    private StandaloneXQuery standaloneXQuery;
    private TabXQuerySandbox xquerySandbox;
    
    public ScheduledXQuery(TabXQuerySandbox xquerySandbox) {
        this.standaloneXQuery = new StandaloneXQuery();
        this.xquerySandbox = xquerySandbox;
    }
    
    @Override
    public void run() {
        String query = xquerySandbox.getXQuery();
        try {
            standaloneXQuery.checkQuery(query);
        } catch (SaxonApiException sae) {
            xquerySandbox.processError(sae);
        }           
    }
    
    
}
