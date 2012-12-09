package net.sf.kernow.util;

import java.io.File;
import java.util.ArrayList;
import net.sf.kernow.Message;
import net.sf.kernow.internal.ProgressObserver;

/**
 * A class that creates a list of files for a given directory by (optionally) 
 * recursing the directory.  The files added to the list must have a suffix that
 * matches one of the suffixes supplied using the allowedInputSuffix String.
 *
 * @author Andrew Welch
 */
public class FileListBuilder {
        
    /**
     * Returns an ArrayList of Files contained in the supplied directory by comparing
     * the suffix of each file with the list of comma separated suffixes
     * @param dir The directory to build the file list for
     * @param allowedInputSuffixes A comma separated list of allowed input suffixes
     * @param recurseSubdirectories <code>true</code> to recusively process subdirectories
     * @return The list of the files with matching suffixes for the directory
     */
    public static ArrayList<File> buildFileList(File dir, String allowedInputSuffixes, 
            boolean recurseSubdirectories) {
        
        ArrayList<String> allowedInputSuffixesList = getAllowedInputSuffixes(allowedInputSuffixes);
        
        ArrayList<File> fileList = new ArrayList<File>();
        
        recursivelyBuildFileList(dir, fileList, allowedInputSuffixesList, recurseSubdirectories);
        
        return fileList;
    }
    
    static ArrayList<String> getAllowedInputSuffixes(String allowedInputSuffixes) {
        
        ArrayList<String> allowedInputSuffixesList = new ArrayList<String>(5);
        
        if (allowedInputSuffixes == null || allowedInputSuffixes.equals("")) {
            Message.info("There are no allowed input suffixes specified in the Options, defaulting to \".xml\"");
            allowedInputSuffixes = ".xml";
        }     
        
        for (String suffix : allowedInputSuffixes.split(",")) {
            allowedInputSuffixesList.add(suffix.trim());
        }        
        
        return allowedInputSuffixesList;
    }
    
    /* Recursively process a directory to build a list of Files */
    static void recursivelyBuildFileList(File dir, ArrayList<File> fileList, 
            ArrayList<String> allowedInputSuffixes, boolean recurse) {        
        
        String[] files = dir.list();
        
        for (String filename : files) {
                 
            File f = new File(dir, filename);
            if (f.isDirectory()) {
                if (recurse) {
                    recursivelyBuildFileList(f, fileList, allowedInputSuffixes, recurse);
                }                           
            } else {
                int index = filename.lastIndexOf(".");
                if (index != -1) {
                    String filesuffix = filename.substring(index);
                    if (allowedInputSuffixes.contains(filesuffix)) {
                        fileList.add(f);
                    } else {
                        Message.info("Ignored input file: " + filename);
                    } 
                } else {
                    Message.info("Ignored input file: " + filename);
                }
            }                                                   
        }
    }     
}
