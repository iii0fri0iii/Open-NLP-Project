import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Frame{
    private JFrame frame;
    private JTextField firstTextField;
    private JPanel panelSpoiler;
    private JPanel panelNeighbours;
    private  JPanel panelDisplayedResults;

    private JTextArea outputArea;

    private String searchBy;


    private List<List<List<String>>> src;
    private int numberOfNeighbours=2;
    private int numberOfDisplayedResults = 10;


    Frame(){
        frame=new JFrame("Test");
        frame.setSize(1000,600);
        firstTextField=new JTextField(60);

        firstTextField.setMaximumSize(new Dimension(200,50));


        Dimension size = new Dimension(95, 30);

        JButton loadButton = new JButton("load");
        loadButton.setMaximumSize(size);
        loadButton.addActionListener(new LoadButtonHandler());

        JButton searchButton = new JButton("search");
        searchButton.setMaximumSize(size);
        searchButton.addActionListener(new SearchButtonHandler());

        JRadioButton lemma = new JRadioButton("Lemma");
        JRadioButton pos = new JRadioButton("POS");
        JRadioButton word = new JRadioButton("Word");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(lemma);
        buttonGroup.add(pos);
        buttonGroup.add(word);

        lemma.addActionListener(new WordPosLemmaButtonHandler());
        pos.addActionListener(new WordPosLemmaButtonHandler());
        word.addActionListener(new WordPosLemmaButtonHandler());

        Box box1 = Box.createVerticalBox();
        box1.add(lemma);
        box1.add(pos);
        box1.add(word);

        JPanel panel1=new JPanel();
        BoxLayout aBoxLayout = new BoxLayout(panel1,BoxLayout.X_AXIS);
        panel1.setLayout(aBoxLayout);

        panel1.add(Box.createRigidArea(new Dimension(0,5)));
        panel1.add(loadButton);
        panel1.add(firstTextField);
        panel1.add(box1);
        panel1.add(Box.createHorizontalGlue());
        panel1.add(searchButton);
        //panel with spoiler panel
        JPanel panel2=new JPanel();
        BoxLayout bBoxLayout = new BoxLayout(panel2,BoxLayout.Y_AXIS);
        panel2.setLayout(bBoxLayout);

        panel2.add(Box.createRigidArea(new Dimension(0,5)));

        JButton spoilerButton = new JButton("Additional filters");
        spoilerButton.setMaximumSize(new Dimension(frame.getWidth(),30));
        spoilerButton.addActionListener(new SpoilerButtonHandler());
        spoilerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel2.add(spoilerButton);

        panel2.add(Box.createRigidArea(new Dimension(0,5)));
        //spoiler panel
        panelSpoiler=new JPanel();
        BoxLayout spoilerBoxLayout = new BoxLayout(panelSpoiler,BoxLayout.X_AXIS);
        panelSpoiler.setLayout(spoilerBoxLayout);

        panel2.add(panelSpoiler);

        JList<CheckboxListItem> posList = new JList<CheckboxListItem>(
                new CheckboxListItem[] { new CheckboxListItem("apple"),
                        new CheckboxListItem("orange"),
                        new CheckboxListItem("mango"),
                        new CheckboxListItem("paw paw"),
                        new CheckboxListItem("banana") });

        posList.setCellRenderer(new CheckboxListRenderer());
        posList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        posList.addMouseListener(new PosListListener());

        panelSpoiler.add(new JScrollPane(posList));

        panelNeighbours=new JPanel();
        BoxLayout neigboursBoxLayout = new BoxLayout(panelNeighbours,BoxLayout.Y_AXIS);
        panelNeighbours.setLayout(neigboursBoxLayout);
        panelSpoiler.add(panelNeighbours);

        JLabel neighbours= new JLabel("Neighbours");
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

        JLabel displayedResults= new JLabel("Amount of displayed results");
        displayedResults.setMaximumSize(new Dimension(300,30));

        JSlider slider = new JSlider(JSlider.HORIZONTAL,
                1, 50, numberOfDisplayedResults);
        slider.setMaximumSize(new Dimension(300,100));
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
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


        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panel3);
        //This is the common size of the buttons

        frame.addWindowListener(new MyWindowListener());
        frame.setVisible(true);


        // Create the JTextArea for displaying the output
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        frame.add(outputArea);


        Design.applyDesign(frame);


        Design.applyTextFieldStyle(firstTextField);
        Design.applyButtonStyle(loadButton);
        Design.applyButtonStyle(searchButton);
        Design.applyRadioButtonStyle(lemma);
        Design.applyRadioButtonStyle(pos);
        Design.applyRadioButtonStyle(word);


    }
    public class SpoilerButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (panelSpoiler.isVisible()){
                panelSpoiler.setVisible(false);

            } else {

                panelSpoiler.setVisible(true);
            }
        }
    }


    public class LoadButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

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

            CorpusBuilder corp= new CorpusBuilder(text);
            corp.getSentences();
            corp.getTokens();
            corp.getPosTags();
            corp.getLemmas();

            src = corp.getWordPosLemma();

            inputStream.close();
            }
        }
    }

    private class spinnerListener implements ChangeListener {
        public void stateChanged(ChangeEvent e) {
            JSpinner spinner= (JSpinner) e.getSource();
            SpinnerModel spinnerModel = (SpinnerModel) spinner.getModel();
            if (spinnerModel instanceof SpinnerDateModel) {
                numberOfNeighbours=(int)spinnerModel.getValue();
            }
        }
    }
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
            JList<CheckboxListItem> list =
                    (JList<CheckboxListItem>) e.getSource();

            // Get index of item clicked

            int index = list.locationToIndex(e.getPoint());
            CheckboxListItem item = (CheckboxListItem) list.getModel()
                    .getElementAt(index);

            // Toggle selected state

            item.setSelected(!item.isSelected());

            // Repaint cell

            list.repaint(list.getCellBounds(index, index));
        }
    }

    private class WordPosLemmaButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JRadioButton button = (JRadioButton) e.getSource();
            searchBy = button.getText();
        }
    }


    private class SearchButtonHandler implements ActionListener {
        String s = firstTextField.getText();
        public void actionPerformed(ActionEvent e) {
            if (searchBy.equals("Word")){
                for (int i=0;i<src.size();i++){   //iteration by sentences
                    for (int k=0;k<src.get(i).size();k++){   //iteration by words
                        if (src.get(i).get(k).get(0).equals(s)){
                            System.out.println(src.get(i)); //prints a list of tokens, lemmas and pos tags
                            //src.get(i).get(k).get(1) -- POS tag of the word
                            System.out.println(getContextWords(src.get(i), k, numberOfNeighbours)); //prints final sentences with neighbours
                        }
                    }
                }
            } else if (searchBy.equals("Lemma")) {


            } else if (searchBy.equals("POS")) {


            }
        }
    }


    /**
     * Helper method that gets a list from CorpusBuilder, index of the word and the number of neighbours
     * @param sentenceList
     * @param index
     * @param numNeighbours
     * @return a string with word and his neighbours
     */
    private static String getContextWords (List<List<String>> sentenceList, int index, int numNeighbours){
        ArrayList<String> contextWords = new ArrayList<>();
        ArrayList<String> sentenceArrayList = new ArrayList<>();

        for (List<String> list : sentenceList) {
            if (!list.isEmpty()) {
                sentenceArrayList.add(list.get(0));
            }
        }

        if (index >= numNeighbours) {
            contextWords.addAll(sentenceArrayList.subList((index - numNeighbours), index));
        } else {
            contextWords.addAll(sentenceArrayList.subList(0, index));
        }

        if (index < sentenceList.size() - numNeighbours - 1) {
            contextWords.addAll(sentenceArrayList.subList(index + 1, index + numNeighbours + 1));
        } else {
            contextWords.addAll(sentenceArrayList.subList(index + 1, sentenceList.size()));
        }

        return String.join(", ", contextWords);
    }

    class CheckboxListRenderer extends JCheckBox implements
            ListCellRenderer<CheckboxListItem> {

        @Override
        public Component getListCellRendererComponent(
                JList<? extends CheckboxListItem> list, CheckboxListItem value,
                int index, boolean isSelected, boolean cellHasFocus) {
            setEnabled(list.isEnabled());
            setSelected(value.isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
            return this;
        }
    }
    public class CheckboxListItem {

        private String label;
        private boolean isSelected = false;

        public CheckboxListItem(String label) {
            this.label = label;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean isSelected) {
            this.isSelected = isSelected;
        }

        public String toString() {
            return label;
        }

    }




    public static void main ( String[] args )
    {
        new Frame();
    }
}