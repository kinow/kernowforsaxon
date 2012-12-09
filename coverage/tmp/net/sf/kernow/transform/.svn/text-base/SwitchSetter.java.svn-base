/*
 * SwitchSetter.java
 *
 * Created on 26 January 2006, 19:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package net.sf.kernow.transform;

import net.sf.kernow.Config;
import net.sf.saxon.Configuration;
import net.sf.saxon.om.TreeModel;
import net.sf.saxon.lib.Validation;
import net.sf.saxon.value.Whitespace;

/**
 *
 * @author ajwelch
 */
public class SwitchSetter {    
    
    public static void setSwitches(Configuration configuration) {
                
        Config config = Config.getConfig();
                
        // Standard tree, Tiny tree
        if (config.isUseStandardTree()) {
            configuration.setTreeModel(TreeModel.LINKED_TREE.getSymbolicValue());
        } else {
            configuration.setTreeModel(TreeModel.TINY_TREE.getSymbolicValue());
        }
        
        // Retain line numbers
        configuration.setLineNumbering(Boolean.valueOf(config.isRetainLineNumbers()));
        
        //whitespace stripping
        if (config.isStripNoWhitespace()) {
            configuration.setStripsWhiteSpace(Whitespace.NONE);
        } else if (config.isStripAllWhitespace()) {
            configuration.setStripsAllWhiteSpace(true);
        } else if (config.isStripIgnorableWhitespace()) {
            configuration.setStripsWhiteSpace(Whitespace.IGNORABLE);
        }
        
        // Show timing
        configuration.setTiming(config.isShowTiming());
        
        // explain 
        configuration.setOptimizerTracing(config.isExplain());
        
        // Trace java
        configuration.setTraceExternalFunctions(Boolean.valueOf(config.isTraceCalls()));
        
        // Recovery
        if (config.isRecoverablesAreSilent()) {
            configuration.setRecoveryPolicy(Configuration.RECOVER_SILENTLY);
        } else if (config.isRecoverablesAreReported()) {
            configuration.setRecoveryPolicy(Configuration.RECOVER_WITH_WARNINGS);
        } else if (config.isRecoverablesAreFatal()) {
            configuration.setRecoveryPolicy(Configuration.DO_NOT_RECOVER);
        }
        
        // No Version Warning
        configuration.setVersionWarning(Boolean.valueOf(!config.isNovw()));
        
        // XML 1.1
        if (config.isAllowXML11()) {
            configuration.setXMLVersion(Configuration.XML11);
        } else {
            configuration.setXMLVersion(Configuration.XML10);
        }
        
        // Schema Aware
        if (config.isSchemaAwareEnabled() && config.isUseSchemaAware()) {
            if (config.isSkipValidation()) {
                configuration.setSchemaValidationMode(Validation.SKIP);
            } else if (config.isStrictValidation()) {
                configuration.setSchemaValidationMode(Validation.STRICT);
            } else if (config.isLaxValidation()) {
                configuration.setSchemaValidationMode(Validation.LAX);
            }
            
            
            if (config.isTreatValidationErrorsAsWarnings()) {
                configuration.setValidationWarnings(true);
            } else {
                configuration.setValidationWarnings(false);
            }
        }
        
    }
}
