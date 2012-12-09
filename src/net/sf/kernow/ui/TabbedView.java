package net.sf.kernow.ui;

import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.text.DefaultCaret;
import net.sf.kernow.Config;
import net.sf.kernow.Message;
import net.sf.kernow.OpManager;
import net.sf.kernow.Status;
import net.sf.kernow.internal.CancelObserver;
import net.sf.kernow.internal.Cancellable;
import net.sf.kernow.internal.ObservableProgress;
import net.sf.kernow.internal.ProgressObserver;
import net.sf.kernow.internal.TimeObserver;
import net.sf.kernow.internal.TimedTransform;
import net.sf.kernow.internal.TransformController;
import net.sf.kernow.util.BrowserLauncher;
import net.sf.kernow.util.PropertyManager;


/**
 * The Kernow's main tabbed view.
 *
 * @author Florent Georges
 */
public class TabbedView extends JFrame implements TimeObserver, ProgressObserver, Cancellable {
    
    private TabbedViewComponentListener componentListener;
    private Switches switches;
    private TextAreaPrintStream textAreaPrintStream;
    private Options options;
    private ParamsDiag paramsDiag;
    private SchemaAwareDiag schemaAwareDiag;
    private TransformController transformController;
    private Config config;
    
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private SwingWorker worker;
    
    private ArrayList<CancelObserver> cancelObservers;
    private boolean cancelBtnClicked = false;    
    
    private int[] splitPaneDividerPositions = null;
    
    private ImageIcon successIcon;
    private ImageIcon failIcon;
    private ImageIcon workingIcon;
    
    protected List<Tab> tabs = new ArrayList<Tab>();

    static final int MIN_WIDTH = 400;
    static final int MIN_HEIGHT = 650;

    /**
     * Construct a new main frame.  It doesn't init its components (see {@link #init(TransformController)}).
     *
     * <p>Construct a new TabbedView object, doesn't initialise all of
     * its components.  In order to be used, {@link #init(TransformController)} must be
     * called.</p>
     */
    public TabbedView() {
        config = Config.getConfig();
        
        cancelObservers = new ArrayList<CancelObserver>();
        
        // Add the component listener to deal with resize events
        componentListener = new TabbedViewComponentListener(this);
        addComponentListener(componentListener);
        
        // Set the opening location of the window
        super.setLocation(50, 50);
        getRootPane().setDoubleBuffered(true);              
        
        // Create the switches dialog
        switches = new Switches();
        
        // Create the options and params dialogs
        options = new Options();
        paramsDiag = new ParamsDiag();
        schemaAwareDiag = new SchemaAwareDiag();
    }

    /**
     * Initialise all component of the view.
     *
     * <p>This method must be called once and only once before to use
     * the frame.  Among other things, it initialise the various tabs
     * in the tabbed pane.</p>
     */
    public void init(TransformController transformController) {
        this.transformController = transformController;
        
        initComponents();
        
        //set the title
        String trial = "";
        if (Config.getConfig().getStatus() == Status.LOCKED) {
            trial = " trial";
        }
        
        this.setTitle(config.getKernowVersion() + trial + " - " + config.getSaxonVersion());
                      
        // Redirect System.out and System.err to the textarea
        textAreaPrintStream = new TextAreaPrintStream(outputTextArea, System.out);
        System.setOut(textAreaPrintStream);
        System.setErr(textAreaPrintStream);
        config.setOutputStream(textAreaPrintStream);
        outputTextArea.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        // The various tabs
        if (config.isSingleFileTabEnabled()) {
            tabs.add(new TabSingleFile());    
        }
        
        if (config.isDirectoryTabEnabled()) {
            tabs.add(new TabDirectory());    
        }
        
        if (config.isStandaloneTabEnabled()) {
            tabs.add(new TabStandalone());    
        }
        
        if (config.isXsltSandboxTabEnabled()) {
            tabs.add(new TabXSLTSandbox());            
        }
         
        if (config.isXsdSandboxTabEnabled()) {
            tabs.add(new TabXMLSchemaSandbox());            
        }
        
        if (config.isXquerySandboxTabEnabled()) {
            tabs.add(new TabXQuerySandbox());            
        }
        
        if (config.isValidationTabEnabled()) {
            tabs.add(new TabValidation());            
        }
        
//        if (config.isSchematronTabEnabled()) {
//            tabs.add(new TabSchematron(this));
//        }
        
        if (config.isBatchTabEnabled()) {
            tabs.add(new TabAnt());
        }
        
        splitPaneDividerPositions = new int[tabs.size()];
        
        for ( int i = 0; i < tabs.size(); ++i ) {
            Tab t = tabs.get(i);
            splitPaneDividerPositions[i] = t.getSplitPanePos();

            t.init(this);
            t.insertInto(mainTabbedPane, i);            
        }
        
        // Populate the text input fields with whatever they were last time this app was run
        loadProperties();
        
        // add the listener to the divider to store its location after its moved
        addListenerToDivider();
        
        // add the mouse listener (for the popup menu) to the output window
        outputTextArea.addMouseListener(new CustomMouseAdapter(outputTextArea));

        // set the caret update policy so that the text area auto scrolls when text is appended
        DefaultCaret caret = (DefaultCaret)outputTextArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        // Sort out the options
        options.enableTextFields();
        options.loadOptionsFromConfig();
        
        // ...and scroll all text fields to the end
        setAllCaretPositions();
        
        // Sort out the progress bar
        UIManager.put("ProgressBar.repaintInterval", Integer.valueOf(4));
        UIManager.put("ProgressBar.cycleTime", Integer.valueOf(4000));
        //progressBar.setUI(new BasicProgressBarUI());
        setProgressBarStringPainted(true);
        
        // needs to use this classloader for java web start
        successIcon = new ImageIcon(this.getClass().getClassLoader().getResource("icons/successIcon.gif"));
        failIcon = new ImageIcon(this.getClass().getClassLoader().getResource("icons/failIcon.gif"));
        workingIcon = new ImageIcon(this.getClass().getClassLoader().getResource("icons/workingIcon.gif"));                

        // set the dialog icon
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage(this.getClass().getClassLoader().getResource("icons/Kernow-Icon.gif")));
                
        progressText.setIcon(workingIcon);
        progressText.setText("Kernow");

        if (config.isSchemaAwareEnabled()) {
            configureSchemaAware.setEnabled(true);
        } else {
            configureSchemaAware.setEnabled(false);
        }
    }

    /**
     * Return the {@link Config} used by this frame.
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Return the {@link ParamsDiag} used by this frame.
     */
    public ParamsDiag getParamsDiag() {
        return paramsDiag;
    }

    /**
     * Return the {@link TransformController} used by this frame.
     */
    public TransformController getTransformController() {
        return transformController;
    }

    /**
     * Execute a task in a separate thread so the GUI remains responsive.
     *
     * @param worker The service to execute.     
     */
    public void executeService(SwingWorker worker) {

        if (OpManager.hasOpsLeft()) {
            // Save a handle to the future so it can be cancelled if the user clicks the cancel button
            this.worker = worker;

            executorService.submit(worker); 

            OpManager.opPerformed();

        } else {
            outputTextArea.setText("Sorry, your free trial of Kernow has expired, please use the Help -> Purchase menu to continue.");
        }
    }

    public void startIndeterminant(String text) {
        setProgressBarIndeterminate();
        startProcess(text);
    }
    
    public void endIndeterminant(boolean success) {
        setProgressBarDeterminate();
        endProcess(success);
        if (!success) {
            setProgressText(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView").getString("Error!"));
        }
    }
    
    public void startDeterminant(final String text) {
        setProgressBarDeterminate();
        setProgressBarStringPainted(true);
        startProcess(text);
    }
    
    public void endDeterminant(final boolean success) {
        endProcess(success);
    }
    
    /**
     * Set the text in the progress below the progress bar.
     */
    public void setProgressText(String text) {
        progressText.setText(text);
    }
    
    /**
     * Clear the text area (the middle low in the Kernow GUI).
     */
    public void clearOutputWindow() {
        outputTextArea.setText("");
    }
    
    /**
     * Update the text below the progress bar with the time {@code transform} took.
     */
    @Override
    public void updateTimeTaken(TimedTransform transform) {
        progressText.setText(transform.getTimeTakenInWords());
    }
    
    /**
     * Add {@code cancelObserver} as a {@link CancelObserver}.
     */
    public void addCancelObserver(CancelObserver cancelObserver) {
        cancelObservers.add(cancelObserver);
    }
    
    /**
     * Return true iff the current action was canceled.
     *
     * <p>An action can be canceled by clicking the cancel button in
     * the GUI.  This method returns true if and only if this button
     * was clicked while the current action was running.</p>
     */
    @Override
    public boolean isCancelButtonClicked() {
        return cancelBtnClicked;
    }
    
    /**
     * See {@link ProgressObserver#updateProgress(ObservableProgress)}.
     */
    @Override
    public void updateProgress(ObservableProgress task) {
        setProgressText(task.getProgressText());
        setProgressBarValue(task.getPercentComplete());
    }
    
    /**
     * Call setCaretPosition() for every combobox.
     */
    protected void setAllCaretPositions() {
        try {
            for ( Tab t : tabs ) {
                t.setAllCaretPositions();
            }
        } catch (Exception e) {
            // do nothing
        }
    }

    protected JTabbedPane getTabbedPane() {
        return mainTabbedPane;
    }

    private void addListenerToDivider() {
        BasicSplitPaneDivider divider = ((BasicSplitPaneUI)jSplitPane1.getUI()).getDivider();
        divider.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {
                // If not already instanciated, silently ignore.
                if ( splitPaneDividerPositions == null ) {
                    return;
                }
                int index = mainTabbedPane.getSelectedIndex();
                // If "out of bounds", silently ignore.
                if ( index >= splitPaneDividerPositions.length || index < 0 ) {
                    return;
                }
                int location = jSplitPane1.getDividerLocation();
                splitPaneDividerPositions[index] = location;
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
        });        
    }

    /**
     * Enable or disable the run buttons.
     *
     * <p>The run buttons are the buttons that can launch an action.
     * When an action is executed, they are temprorarily disabled.
     * When the action stops or is canceled, they are enabled
     * again.</p>
     *
     * <p>This method just delegates to the various tabs to enable (or
     * disable) the various buttons.</p>
     */
    protected void setRunButtonsEnabled(boolean enable) {
        for ( Tab t : tabs ) {
            t.setRunButtonsEnabled(enable);
        }
        mainTabbedPane.setEnabled(enable);
    }
    
    protected void loadProperties() {
        Properties properties = PropertyManager.getProperties();
        try {
            // Window size and location
            Double winX = Double.parseDouble(properties.getProperty("windowLeft", "50"));
            Double winY = Double.parseDouble(properties.getProperty("windowTop", "50"));
            Double winWidth = Double.parseDouble(properties.getProperty("windowWidth", "400"));
            Double winHeight = Double.parseDouble(properties.getProperty("windowHeight", "650"));

            if (winX < 0 || winX > 1024) {
                winX = 50.0;
            }

            if (winY < 0 || winY > 900) {
                winY = 50.0;
            }

            super.setBounds(winX.intValue(), winY.intValue(), winWidth.intValue(), winHeight.intValue());

        } catch (NumberFormatException ex) {
            super.setBounds(50, 50, 400, 650);
        }
        
        String positions = properties.getProperty("splitPaneDividerPositions");
        try {
            String[] pos = positions.split(" ");
            // take each value in the properties
            for (int i = 0; i < pos.length; i++) {
                splitPaneDividerPositions[i] = Integer.parseInt(pos[i]);
            }
        } catch (Exception e) {
            // if there's a problem, just use the default position for all tabs
            for (int i = 0; i < tabs.size(); i++) {
                splitPaneDividerPositions[i] = tabs.get(i).getSplitPanePos();
            }
            // e.printStackTrace();
        }

        // All tabs...
        for ( Tab t : tabs ) {
            t.loadProperties(properties);
        }
        
        // current tab
        setCurrentTab(Integer.parseInt(properties.getProperty("currentTab", "1")));
        switches.loadSettings();
    }
    
    protected void saveProperties() {
        Properties properties = PropertyManager.getProperties();
        
        // All tabs...
        for ( Tab t : tabs ) {
            t.saveProperties(properties);
        }
        
        // window position and size
        properties.setProperty("windowLeft", Double.toString(super.getLocationOnScreen().getX()));
        properties.setProperty("windowTop", Double.toString(super.getLocationOnScreen().getY()));
        properties.setProperty("windowWidth", Integer.toString(super.getWidth()));
        properties.setProperty("windowHeight", Integer.toString(super.getHeight()));
        StringBuilder buffer = new StringBuilder();
        for ( int pos : splitPaneDividerPositions ) {
            buffer.append(pos).append(' ');
        }
        properties.setProperty("splitPaneDividerPositions", buffer.toString());
        
        // current tab
        properties.setProperty("currentTab", String.valueOf(mainTabbedPane.getSelectedIndex()));
        
        PropertyManager.saveProperties(config);
    }
    
    private void moveScrollPaneDivider(final int from, final int to) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    if (from < to) {
                        for (int i = from; i <= to; i+=1) {
                            jSplitPane1.setDividerLocation(i);
                            Thread.sleep(1);
                        }
                    } else if (to < from) {
                        for (int i = from; i >= to; i-=1) {
                            jSplitPane1.setDividerLocation(i);
                            Thread.sleep(1);
                        }                    
                    }                                        
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }                                        
            }
        };
        t.setPriority(9);
        t.start();    
    }
    
    private void startProcess(final String text) {
        prepSuccessIcon();
        setCancelButtonEnabled(true);
        setRunButtonsEnabled(false);
        setProgressText(text);
        clearOutputWindow();
    }
    
    private void endProcess(final boolean success) {
        setSuccessIcon(success);
        setRunButtonsEnabled(true);
        setCancelButtonEnabled(false);
    }

    private void setCancelButtonEnabled(boolean enabled) {
        cancelBtn.setEnabled(enabled);
    }
    
    private void setProgressBarValue(int value) {
        progressBar.setValue(value);
    }
    
    private void setProgressBarDeterminate() {
        progressBar.setIndeterminate(false);
        progressBar.setValue(0);
        setProgressBarStringPainted(false);
    }
    
    private void setProgressBarIndeterminate() {
        progressBar.setIndeterminate(true);
        setProgressBarStringPainted(false);
    }
    
    private void setProgressBarStringPainted(boolean painted) {
        progressBar.setStringPainted(painted);
    }
    
    private void prepSuccessIcon() {
        progressText.setIcon(workingIcon);
    }
    
    private void setSuccessIcon(boolean success) {
        if (success) {
            progressText.setIcon(successIcon);
        } else {
            progressText.setIcon(failIcon);
        }
    }
    
    private void notifyCancelObservers() {
        for (CancelObserver c : cancelObservers) {
            c.updateCancelStatus(this);
        }
    }
    
    private void setCurrentTab(int index) {
        if ( index < -1 || index >= mainTabbedPane.getTabCount() ) {
            mainTabbedPane.setSelectedIndex(0);
        } else {
            mainTabbedPane.setSelectedIndex(index);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        mainTabbedPane = new javax.swing.JTabbedPane();
        ProgressBoxPanel = new javax.swing.JPanel();
        progressBar = new javax.swing.JProgressBar();
        cancelBtn = new javax.swing.JButton();
        progressText = new javax.swing.JLabel();
        outputScrollPane = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        clearOutputWinBtn = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        configurationMenu = new javax.swing.JMenu();
        optionsMenuItem = new javax.swing.JMenuItem();
        setSaxonSwitchesMenuItem = new javax.swing.JMenuItem();
        configureSchemaAware = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        purchaseMenuItem = new javax.swing.JMenuItem();
        showHelp = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                onWindowClosing(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(220);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setContinuousLayout(true);

        mainTabbedPane.setFocusable(false);
        mainTabbedPane.setMinimumSize(new java.awt.Dimension(142, 70));
        mainTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mainTabbedPaneStateChanged(evt);
            }
        });
        jSplitPane1.setTopComponent(mainTabbedPane);

        ProgressBoxPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        progressBar.setDoubleBuffered(true);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView"); // NOI18N
        cancelBtn.setText(bundle.getString("Cancel")); // NOI18N
        cancelBtn.setToolTipText(bundle.getString("Cancel_the_current_running_transformation")); // NOI18N
        cancelBtn.setEnabled(false);
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });

        progressText.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/workingIcon.gif"))); // NOI18N
        progressText.setText(bundle.getString("Kernow")); // NOI18N

        outputScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        outputScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        outputScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        outputTextArea.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        outputScrollPane.setViewportView(outputTextArea);

        clearOutputWinBtn.setText(bundle.getString("Clear")); // NOI18N
        clearOutputWinBtn.setToolTipText(bundle.getString("Cancel_the_current_running_transformation")); // NOI18N
        clearOutputWinBtn.setMaximumSize(new java.awt.Dimension(65, 23));
        clearOutputWinBtn.setMinimumSize(new java.awt.Dimension(65, 23));
        clearOutputWinBtn.setPreferredSize(new java.awt.Dimension(65, 23));
        clearOutputWinBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearOutputWinBtnActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout ProgressBoxPanelLayout = new org.jdesktop.layout.GroupLayout(ProgressBoxPanel);
        ProgressBoxPanel.setLayout(ProgressBoxPanelLayout);
        ProgressBoxPanelLayout.setHorizontalGroup(
            ProgressBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(ProgressBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(ProgressBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, outputScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, ProgressBoxPanelLayout.createSequentialGroup()
                        .add(ProgressBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(progressText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
                            .add(progressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE))
                        .add(8, 8, 8)
                        .add(ProgressBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(clearOutputWinBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(cancelBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        ProgressBoxPanelLayout.setVerticalGroup(
            ProgressBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(ProgressBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(ProgressBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(cancelBtn, 0, 0, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, progressBar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(ProgressBoxPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(clearOutputWinBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(progressText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(outputScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                .addContainerGap())
        );

        jSplitPane1.setRightComponent(ProgressBoxPanel);

        fileMenu.setText(bundle.getString("File")); // NOI18N

        exitMenuItem.setText(bundle.getString("Exit")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        configurationMenu.setText(bundle.getString("Configuration")); // NOI18N
        configurationMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configurationMenuActionPerformed(evt);
            }
        });

        optionsMenuItem.setText(bundle.getString("Options")); // NOI18N
        optionsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsMenuItemActionPerformed(evt);
            }
        });
        configurationMenu.add(optionsMenuItem);

        setSaxonSwitchesMenuItem.setText(bundle.getString("Set_Saxon_Switches")); // NOI18N
        setSaxonSwitchesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setSaxonSwitchesMenuItemActionPerformed(evt);
            }
        });
        configurationMenu.add(setSaxonSwitchesMenuItem);

        configureSchemaAware.setText(bundle.getString("Configure_Schema_Aware")); // NOI18N
        configureSchemaAware.setEnabled(false);
        configureSchemaAware.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                configureSchemaAwareActionPerformed(evt);
            }
        });
        configurationMenu.add(configureSchemaAware);

        jMenuBar1.add(configurationMenu);

        helpMenu.setText(bundle.getString("Help")); // NOI18N

        purchaseMenuItem.setText(bundle.getString("TabbedView.purchaseMenuItem.text")); // NOI18N
        purchaseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purchaseMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(purchaseMenuItem);

        showHelp.setText(bundle.getString("Show_Help")); // NOI18N
        showHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHelpActionPerformed(evt);
            }
        });
        helpMenu.add(showHelp);

        aboutMenuItem.setText(bundle.getString("About_Kernow")); // NOI18N
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        jMenuBar1.add(helpMenu);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 614, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mainTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mainTabbedPaneStateChanged
        // If not already instanciated, silently ignore.
        if ( splitPaneDividerPositions == null ) {
            return;
        }
        int index = mainTabbedPane.getSelectedIndex();
        // If "out of bounds", silently ignore.
        if ( index >= splitPaneDividerPositions.length || index < 0 ) {
            return;
        }
        int location = jSplitPane1.getDividerLocation();
        moveScrollPaneDivider(location, splitPaneDividerPositions[index]);
    }//GEN-LAST:event_mainTabbedPaneStateChanged
        
    private void configureSchemaAwareActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configureSchemaAwareActionPerformed
        schemaAwareDiag.setVisible(true);
    }//GEN-LAST:event_configureSchemaAwareActionPerformed
    
    private void configurationMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_configurationMenuActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_configurationMenuActionPerformed
                                                
    private void clearOutputWinBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearOutputWinBtnActionPerformed
        clearOutputWindow();
    }//GEN-LAST:event_clearOutputWinBtnActionPerformed
        
    private void showHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHelpActionPerformed
        BrowserLauncher.openURL("http://kernowforsaxon.sourceforge.net");
    }//GEN-LAST:event_showHelpActionPerformed
    
    private void optionsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsMenuItemActionPerformed
        options.setVisible(true);
    }//GEN-LAST:event_optionsMenuItemActionPerformed
            
    private void setSaxonSwitchesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setSaxonSwitchesMenuItemActionPerformed
        switches.setVisible(true);
    }//GEN-LAST:event_setSaxonSwitchesMenuItemActionPerformed
    
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        componentListener.componentResized(evt);
    }//GEN-LAST:event_formComponentResized
    
    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        
        cancelBtnClicked = true;

        cancelBtn.setEnabled(false);

        setRunButtonsEnabled(true);

        notifyCancelObservers();


        // Cancel the worker thread if it exists and hasn't already completed
        if (worker != null && !worker.isCancelled()) {
            worker.cancel(true);
        }

        textAreaPrintStream.flush();
        
        // The user has clicked the cancel button
        Message.info("\n" + java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView").getString("Operation_cancelled_by_user.") + "\n");

        // Reset the progress bar and text
        setProgressText(java.util.ResourceBundle.getBundle("net/sf/kernow/i18n/TabbedView").getString("Cancelled."));

        //tidy up the progress bar if its indeterminate
        if (progressBar.isIndeterminate()) {
            setProgressBarValue(0);
            setProgressBarDeterminate();
            setProgressBarStringPainted(false);
        }
        
    }//GEN-LAST:event_cancelBtnActionPerformed
    
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        About about = new About();
        // center it on the screen
        about.setLocationRelativeTo(null);
        // sort out closing
        about.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        about.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    private void onWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_onWindowClosing
        saveProperties();
    }//GEN-LAST:event_onWindowClosing
    
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        saveProperties();
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void purchaseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purchaseMenuItemActionPerformed
        Purchase purchase = new Purchase(this);
        // center it on the screen
        purchase.setLocationRelativeTo(null);
        // sort out closing
        purchase.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        purchase.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_purchaseMenuItemActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ProgressBoxPanel;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton clearOutputWinBtn;
    private javax.swing.JMenu configurationMenu;
    private javax.swing.JMenuItem configureSchemaAware;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JMenuItem optionsMenuItem;
    private javax.swing.JScrollPane outputScrollPane;
    private javax.swing.JTextArea outputTextArea;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel progressText;
    private javax.swing.JMenuItem purchaseMenuItem;
    private javax.swing.JMenuItem setSaxonSwitchesMenuItem;
    private javax.swing.JMenuItem showHelp;
    // End of variables declaration//GEN-END:variables
}
