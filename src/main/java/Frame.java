import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class Frame{
    private JFrame frame;
    private JTextField firstTextField;
    private JPanel panelSpoiler;
    private JPanel panelNeighbours;
    private  JPanel panelDisplayedResults;

    private JTextPane outputArea;

    private String searchBy = "POS"; //variable that we pass to the method of the search button, default - pos tag

    private String s; //users input in the text field, word or lemma

    private DefaultListModel model = new DefaultListModel();
    private JList posList = new JList(model);
    List<Object> posListStringInitial = null;
    List<Object> posListString = null;
    List<String> posListSelected = new ArrayList<>();

    private List<List<List<String>>> src;  //result of CorpusBuilder method
    public static List<List<List<String>>> finalresults = new ArrayList<>();
    private int numberOfNeighbours=2;
    private int numberOfDisplayedResults = 10;
    private boolean hasFile = false; //if user uploaded the file true, otherwise false
    private boolean isCleared = true; //start of the program or if user has used the clear all button - true, otherwise - false
    private JButton lemma = null;
    private JButton word = null;
    private ButtonGroup buttonGroup = new ButtonGroup();


    Frame(){
        frame=new JFrame("Test");

        frame.setSize(1000,600);

        firstTextField=new JTextField(60);

        firstTextField.setMaximumSize(new Dimension(150,40));


        Dimension size = new Dimension(95, 30);
        String folderEmoji = "\uD83D\uDCC2";
        JButton loadButton = new JButton(" LOAD "+folderEmoji+" ");
        loadButton.setMaximumSize(size);
        loadButton.addActionListener(new LoadButtonHandler());
        String XMLEmoji = "\uD83D\uDCE5";

        JButton saveButton = new JButton(" SAVE TO XML "+XMLEmoji+" ");
        saveButton.setMaximumSize(size);
        saveButton.addActionListener(new SaveButtonHandler());
        String magnifyingGlass = "\uD83D\uDD0D";
        String clear="\uD83D\uDD03";


        JButton searchButton = new JButton(" SEARCH "+magnifyingGlass+" ");
        searchButton.setMaximumSize(size);
        searchButton.addActionListener(new SearchButtonHandler());

        JButton clearAllButton = new JButton(" CLEAR ALL "+clear+" ");
        clearAllButton.setMaximumSize(size);
        clearAllButton.addActionListener(new ClearAllButtonHandler());

        JRadioButton lemma = new JRadioButton("LEMMA");
        JRadioButton word = new JRadioButton("WORD");
        buttonGroup.add(lemma);
        buttonGroup.add(word);
        lemma.addActionListener(new WordPosLemmaButtonHandler());
        word.addActionListener(new WordPosLemmaButtonHandler());
        Box box1 = Box.createVerticalBox();
        box1.add(lemma);
        box1.add(word);

        JPanel panel1=new JPanel();
        BoxLayout aBoxLayout = new BoxLayout(panel1,BoxLayout.X_AXIS);
        panel1.setLayout(aBoxLayout);

        panel1.add(Box.createRigidArea(new Dimension(0,5)));
        panel1.add(loadButton);
        panel1.add(firstTextField);
        panel1.add(box1);
        panel1.add(Box.createHorizontalGlue());
        panel1.add(clearAllButton);
        panel1.add(Box.createRigidArea(new Dimension(10,0)));
        panel1.add(saveButton);
        panel1.add(Box.createRigidArea(new Dimension(10,0)));
        panel1.add(searchButton);

        //panel with spoiler panel
        JPanel panel2=new JPanel();
        BoxLayout bBoxLayout = new BoxLayout(panel2,BoxLayout.Y_AXIS);
        panel2.setLayout(bBoxLayout);

        panel2.add(Box.createRigidArea(new Dimension(0,5)));

        JButton spoilerButton = new JButton("ADDITIONAL FILTERS");
        spoilerButton.setMaximumSize(new Dimension(frame.getWidth(),100));
        spoilerButton.addActionListener(new SpoilerButtonHandler());
        spoilerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel2.add(spoilerButton);

        panel2.add(Box.createRigidArea(new Dimension(0,5)));
        //spoiler panel
        panelSpoiler=new JPanel();
        BoxLayout spoilerBoxLayout = new BoxLayout(panelSpoiler,BoxLayout.X_AXIS);
        panelSpoiler.setLayout(spoilerBoxLayout);
        Design.applyPanelStyle(panelSpoiler);

        panel2.add(panelSpoiler);

        model.addElement("CC Coordinating conjunction");
        model.addElement("CD Cardinal number");
        model.addElement("DT Determiner");
        model.addElement("EX Existential there");
        model.addElement("FW Foreign word");
        model.addElement("IN Preposition or subordinating conjunction");
        model.addElement("JJ Adjective");
        model.addElement("JJR Adjective; comparative");
        model.addElement("JJS Adjective; superlative");
        model.addElement("LS List item marker");
        model.addElement("MD Modal");
        model.addElement("NN Noun; singular or mass");
        model.addElement("NNS Noun; plural");
        model.addElement("NNP Proper noun; singular");
        model.addElement("NNPS Proper noun; plural");
        model.addElement("PDT Predeterminer");
        model.addElement("POS Possessive ending");
        model.addElement("PRP Personal pronoun");
        model.addElement("PRP$ Possessive pronoun");
        model.addElement("RB Adverb");
        model.addElement("RBR Adverb; comparative");
        model.addElement("RBS Adverb; superlative");
        model.addElement("RP Particle");
        model.addElement("SYM Symbol");
        model.addElement("TO to");
        model.addElement("UH Interjection");
        model.addElement("VB Verb, base form");
        model.addElement("VBD Verb, past tense");
        model.addElement("VBG Verb, gerund or present participle");
        model.addElement("VBN Verb, past participle");
        model.addElement("VBP Verb, non3rd person singular present");
        model.addElement("VBZ Verb, 3rd person singular present");
        model.addElement("WDT Whdeterminer");
        model.addElement("WP Whpronoun");
        model.addElement("WP$ Possessive whpronoun");
        model.addElement("WRB Whadverb");
        posListString= Arrays.asList(model.toArray());
        posListStringInitial=posListString;
                posList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        posList.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        posList.addMouseListener(new PosListListener());

        panelSpoiler.add(new JScrollPane(posList));

        panelNeighbours=new JPanel();
        BoxLayout neigboursBoxLayout = new BoxLayout(panelNeighbours,BoxLayout.Y_AXIS);
        panelNeighbours.setLayout(neigboursBoxLayout);
        Design.applyPanelStyle(panelNeighbours);
        panelSpoiler.add(panelNeighbours);

        JLabel neighbours= new JLabel("NEIGHBORS");
        neighbours.setMaximumSize(size);

        Integer[] neighboursStrings = {0, 1,2,3,4,5,6,7,8,9,10};
        SpinnerListModel neighboursModel = new SpinnerListModel(neighboursStrings);
        JSpinner spinner = new JSpinner(neighboursModel);
        spinner.setMaximumSize(size);
        spinner.setValue(numberOfNeighbours);
        spinner.addChangeListener(new spinnerListener());

        panelNeighbours.add(neighbours);
        panelNeighbours.add(spinner);
        neighbours.setAlignmentX(Component.CENTER_ALIGNMENT);
        spinner.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelSpoiler.add(Box.createRigidArea(new Dimension(5,0)));

        panelDisplayedResults=new JPanel();
        BoxLayout displayedResultsLayout = new BoxLayout(panelDisplayedResults,BoxLayout.Y_AXIS);
        panelDisplayedResults.setLayout(displayedResultsLayout);
        panelSpoiler.add(panelDisplayedResults);
        Design.applyPanelStyle(panelDisplayedResults);

        JLabel displayedResults= new JLabel( "AMOUNT OF DISPLAYED RESULS");
        displayedResults.setMaximumSize(new Dimension(300,30));

        JSlider slider = new JSlider(JSlider.HORIZONTAL,
                0, 50, numberOfDisplayedResults);
        slider.setMaximumSize(new Dimension(300,100));
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(0);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.addChangeListener(new sliderListener());

        panelDisplayedResults.add(displayedResults);
        panelDisplayedResults.add(slider);
        displayedResults.setAlignmentX(Component.CENTER_ALIGNMENT);
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel panel3=new JPanel();
        BoxLayout cBoxLayout = new BoxLayout(panel3,BoxLayout.X_AXIS);
        panel3.setLayout(cBoxLayout);

        panel3.add(Box.createRigidArea(new Dimension(0,5)));

        //JPanel for the bottom text
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.decode("#F6F6F2"));
        JLabel creatorsLabel = new JLabel("Created by Ramil Fatkiev, Ekaterina Nikitina, Oleksii Ariasov, Ekaterina Akhmetshina");
        creatorsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomPanel.add(creatorsLabel);

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);

        frame.addWindowListener(new MyWindowListener());
        frame.setVisible(true);

        // JTextPane for displaying the output
        outputArea = new JTextPane();
        outputArea.setEditable(false);

        frame.add(outputArea);
        outputArea.setVisible(true);

        frame.getContentPane().add(bottomPanel);

        Design.applyTextFieldStyle(firstTextField);
        Design.applyPanelStyle(panel1);
        Design.applyPanelStyle(panel2);
        Design.applyButtonStyle(loadButton);
        Design.applyButtonStyle(saveButton);
        Design.applyButtonStyle(searchButton);
        Design.applyButtonStyle(clearAllButton);
        Design.spoilerButtonStyler(spoilerButton);
        Design.applyRadioButtonStyle(lemma);
        Design.applyRadioButtonStyle(word);
        Design.applyDesign(frame);

        showInstructionMessage();
    }
    public class SpoilerButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            panelSpoiler.setVisible(!panelSpoiler.isVisible()); //user can show or hide additional filters
        }
    }


    /**
     * Clear All Button handler, that resets all variables to default value in order to perform a new search
     */
    public class ClearAllButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e){
            isCleared = true;
            firstTextField.setText("");
            firstTextField.setEditable(true);
            outputArea.setText("");
            //resetting pos tags
            model.clear();
            model.addElement("CC Coordinating conjunction");
            model.addElement("CD Cardinal number");
            model.addElement("DT Determiner");
            model.addElement("EX Existential there");
            model.addElement("FW Foreign word");
            model.addElement("IN Preposition or subordinating conjunction");
            model.addElement("JJ Adjective");
            model.addElement("JJR Adjective; comparative");
            model.addElement("JJS Adjective; superlative");
            model.addElement("LS List item marker");
            model.addElement("MD Modal");
            model.addElement("NN Noun; singular or mass");
            model.addElement("NNS Noun; plural");
            model.addElement("NNP Proper noun; singular");
            model.addElement("NNPS Proper noun; plural");
            model.addElement("PDT Predeterminer");
            model.addElement("POS Possessive ending");
            model.addElement("PRP Personal pronoun");
            model.addElement("PRP$ Possessive pronoun");
            model.addElement("RB Adverb");
            model.addElement("RBR Adverb; comparative");
            model.addElement("RBS Adverb; superlative");
            model.addElement("RP Particle");
            model.addElement("SYM Symbol");
            model.addElement("TO to");
            model.addElement("UH Interjection");
            model.addElement("VB Verb, base form");
            model.addElement("VBD Verb, past tense");
            model.addElement("VBG Verb, gerund or present participle");
            model.addElement("VBN Verb, past participle");
            model.addElement("VBP Verb, non3rd person singular present");
            model.addElement("VBZ Verb, 3rd person singular present");
            model.addElement("WDT Whdeterminer");
            model.addElement("WP Whpronoun");
            model.addElement("WP$ Possessive whpronoun");
            model.addElement("WRB Whadverb");
            posList=new JList(model);
            posListString= Arrays.asList(model.toArray());
            posListStringInitial=posListString;
            posListSelected=new ArrayList<>();
            searchBy = "POS";
            try{lemma.setEnabled(true);}
            catch(Exception ex){
            }
            try{word.setEnabled(true);}
            catch(Exception ez){}
            buttonGroup.clearSelection();
            finalresults.clear();
        }
    }

    public class LoadButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String[] options = { "Load File", "Load Link" };


            int choice = JOptionPane.showOptionDialog(frame, "Choose an option", "Load", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            if (choice == 0) {
                //Load File
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    hasFile = true; //file has been selected

                    //reading the file
                    String text="";
                    Scanner inputStream;
                    try{
                        inputStream=new Scanner(selectedFile);

                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(frame,"File can not be found");
                        throw new RuntimeException(ex);
                    }
                    while (inputStream.hasNextLine()){
                        text=text+inputStream.nextLine();
                    }

                    //running open nlp on the file
                    CorpusBuilder corp= new CorpusBuilder(text);
                    corp.getSentences();
                    corp.getTokens();
                    corp.getPosTags();
                    corp.getLemmas();

                    //result of CorpusBuilder, list of lists of lists of strings
                    src = corp.getWordPosLemma();

                    inputStream.close();
                }

            }else if (choice == 1) {
                //Load Link
                String link = JOptionPane.showInputDialog(frame, "Enter the link:");

                if (link != null && !link.isEmpty()) {
                    hasFile = true; //file has been selected
                    try {
                        // Fetch the web page content using Jsoup
                        Document doc = Jsoup.connect(link).get();
                        //String htmlContent = doc.html();
                        Elements divsDescendant = doc.select("div");
                        String webContent=divsDescendant.text();

                        // Process the web page content as needed
                        //running open nlp on the file
                        CorpusBuilder corp = new CorpusBuilder(webContent);
                        corp.getSentences();
                        corp.getTokens();
                        corp.getPosTags();
                        corp.getLemmas();

                        //result of CorpusBuilder, list of lists of lists of strings
                        src = corp.getWordPosLemma();


                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(frame, "Error occurred while fetching the web page");
                        ex.printStackTrace();
                    }

                }
            }
        }

    }


    /**
     * Set number of neighbours
     */
    private class spinnerListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner= (JSpinner) e.getSource();
            SpinnerModel spinnerModel = (SpinnerModel) spinner.getModel();
                numberOfNeighbours=(int)spinnerModel.getValue();
        }
    }

    /**
     * Set number of displayed results
     */
    private class sliderListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {

        JSlider source = (JSlider) e.getSource();
        if(!source.getValueIsAdjusting())
        {
             numberOfDisplayedResults= source.getValue();
        }
    }
    }

    private class QuitButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    private class MyWindowListener extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    private class PosListListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {

            JList list = (JList) e.getSource();
            posListSelected=new ArrayList<>();
            int[] indecies= list.getSelectedIndices();
            for (int i:indecies){
                String item= (String) model.getElementAt(i);
                posListSelected.add(item.substring(0,item.indexOf(" ")));
            }
        }

    }

    private class WordPosLemmaButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JRadioButton button = (JRadioButton) e.getSource();
            searchBy = button.getText();
            try{lemma.setEnabled(false);}
            catch(Exception ex){}
            try{word.setEnabled(false);}
            catch(Exception ez){}
        }
    }

    private class SaveButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try {
                XMLSaver.wholeFileSaver(finalresults);
                JOptionPane.showMessageDialog(frame, "Results have been saved to output.xml file in the working directory.");
            }
            catch (Exception ex){
                ex.getMessage();
            }
        }
    }

    private class SearchButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            List<String> recreatedPosList=new ArrayList<>();
            if (isCleared){
                s = firstTextField.getText();
                isCleared = false;
                firstTextField.setEditable(false);
                lemma.setEnabled(false);
                word.setEnabled(false);
            }
            else{
                finalresults.clear();
            }
            outputArea.setText("");
            ArrayList<ArrayList> results = new ArrayList<>();
            if (hasFile) {
                if (searchBy.equals("WORD")) {
                    for (int i = 0; i < src.size(); i++) {   //iteration by sentences
                        for (int k = 0; k < src.get(i).size(); k++) {   //iteration by words
                            if (src.get(i).get(k).get(0).equalsIgnoreCase(s)) {
                                String pos = src.get(i).get(k).get(1);
                                if (!recreatedPosList.contains(pos)){
                                    recreatedPosList.add(pos);
                                }
                                if (!posListSelected.isEmpty()) {
                                    if (containsPos(pos)) {
                                        ArrayList<String> outputArrayList = getContextWords(src.get(i), k, numberOfNeighbours);
                                        results.add(outputArrayList);
                                    }
                                }else {
                                    ArrayList<String> outputArrayList = getContextWords(src.get(i), k, numberOfNeighbours);
                                    results.add(outputArrayList);
                                }
                            }
                        }
                    }
                    recreatePosList(recreatedPosList);

                } else if (searchBy.equals("LEMMA")) {
                    for (int i = 0; i < src.size(); i++) {   //iteration by sentences
                        for (int k = 0; k < src.get(i).size(); k++) {   //iteration by words
                            if (src.get(i).get(k).get(2).equalsIgnoreCase(s)) {
                                String pos = src.get(i).get(k).get(1);
                                if (!recreatedPosList.contains(pos)) {
                                    recreatedPosList.add(pos);
                                }
                                if (!posListSelected.isEmpty()) {
                                    if (containsPos(pos)) {
                                        ArrayList<String> outputArrayList = getContextWords(src.get(i), k, numberOfNeighbours);
                                        results.add(outputArrayList);
                                    }
                                }else {
                                    ArrayList<String> outputArrayList = getContextWords(src.get(i), k, numberOfNeighbours);
                                    results.add(outputArrayList);
                                }
                            }
                        }
                    }
                    recreatePosList(recreatedPosList);

                } else if (searchBy.equals("POS")) {
                    for (int i = 0; i < src.size(); i++) {   //iteration by sentences
                        for (int k = 0; k < src.get(i).size(); k++) {   //iteration by words
                            if (containsPos(src.get(i).get(k).get(1))) {
                                ArrayList<String> outputArrayList = getContextWords(src.get(i), k, numberOfNeighbours);
                                results.add(outputArrayList);
                            }
                        }
                    }
                }

                //displaying the correct amount of results
                int i = 0;
                while ((i < numberOfDisplayedResults) && (i < results.size())) {
                    ArrayList<String> outputArrayList = results.get(i);
                    Design.textOutputStyle(outputArrayList.get(0), outputArrayList.get(1), outputArrayList.get(2), outputArea);
                    i++;
                }
                if(numberOfDisplayedResults<finalresults.size())
                finalresults=finalresults.subList(0, numberOfDisplayedResults); //output results for saving to the xml file
            }
            else{
                JOptionPane.showMessageDialog(frame, "Please select the file first.");
            }
        }
    }

    public boolean containsPos(String pos){
        for (Object item: posListSelected
        ) {
            String el = (String) item;
            if (pos.equalsIgnoreCase(el)){
                return true;
            }
        }
        return false;
    }

        public void recreatePosList(List<String> recreatedPosList){
        model.clear();
        for (String element: recreatedPosList
        ) {
            for (Object item: posListStringInitial
            ) {
                String el = (String) item;
                if (el.substring(0, el.indexOf(" ")).equalsIgnoreCase(element)){
                    model.addElement(item);
                }
            }
        }
        posList=new JList(model);
    }
    /**
     * Helper method that gets a list from CorpusBuilder, index of the word and the number of neighbours
     * @param sentenceList a sentence from CorpusBuilder, src.get(i)
     * @param index index of a searched word in src.get(i)
     * @param numNeighbours
     * @return an ArrayList of three strings: neighbours to the left, searched word, neighbours to the right
     */
    private static ArrayList<String> getContextWords (List<List<String>> sentenceList, int index, int numNeighbours){
        ArrayList<String> sentenceArrayList = new ArrayList<>();
        String string1;
        String string2;
        String string3;


        for (List<String> list : sentenceList) {
            if (!list.isEmpty()) {
                sentenceArrayList.add(list.get(0));
            }
        }

        //variables for xml saving
        List<List<String>> templist = new ArrayList<>();
        List<String> specialIndex= new ArrayList<>();

        //left neighbours
        if (index >= numNeighbours) {
            string1 = String.join(" ", sentenceArrayList.subList((index - numNeighbours), index));
            templist.addAll(sentenceList.subList((index - numNeighbours), index));
        } else {
            string1 = String.join(" ", sentenceArrayList.subList(0, index));
            templist.addAll(sentenceList.subList(0, index));
        }
        specialIndex=new ArrayList<>(sentenceList.get(index));
        specialIndex.add("new");
        templist.add(specialIndex);

        //right neighbours
        if (index < sentenceList.size() - numNeighbours - 1) {
            string3 = String.join(" ", sentenceArrayList.subList(index + 1, index + numNeighbours + 1));
            templist.addAll(sentenceList.subList(index + 1, index + numNeighbours + 1));
        } else {
            string3 = String.join(" ", sentenceArrayList.subList(index + 1, sentenceList.size()));
            templist.addAll(sentenceList.subList(index + 1, sentenceList.size()));
        }

        //the word itself
        string2 = sentenceArrayList.get(index);
        finalresults.add(templist);
        return new ArrayList<String>(Arrays.asList(string1, string2, string3));
    }
    private void showInstructionMessage() {
        String message = "Here is the instruction: \n " +
                "1. Load text options:\n" +
                "- From file\n" +
                "- Using URL link\n" +
                "2. Search options:\n" +
                "- Input text in Text Field\n" +
                "-- then select to search by Lemma\n" +
                "-- or by Word\n" +
                "- or leave Text Field empty to search by POS\n" +
                "3. Clear All to Search again\n" +
                "4. Save to XML\n";
        JOptionPane.showMessageDialog(frame, message, "Welcome", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main ( String[] args )
    {
        new Frame();

    }
}