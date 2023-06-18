import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Frame{
    private JFrame frame;
    private JTextField firstTextField;
    private JPanel panelSpoiler;

    private String searchBy;
    private List<List<List<String>>> src;

    Frame(){
        frame=new JFrame("Test");
        frame.setSize(1000,600);
        firstTextField=new JTextField(60);

        firstTextField.setMaximumSize(new Dimension(200,50));

        Dimension size = new Dimension(95, 30);

        JButton loadButton = new JButton("load");
        loadButton.setMaximumSize(size);
        loadButton.addActionListener(new LoadButtonHandler());

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

        JPanel panel2=new JPanel();
        BoxLayout bBoxLayout = new BoxLayout(panel2,BoxLayout.X_AXIS);
        panel2.setLayout(bBoxLayout);

        panel2.add(Box.createRigidArea(new Dimension(0,5)));

        JButton spoilerButton = new JButton("Additional filters");
        spoilerButton.setMaximumSize(size);
        spoilerButton.addActionListener(new SpoilerButtonHandler());

        panelSpoiler=new JPanel();
        BoxLayout spoilerBoxLayout = new BoxLayout(panelSpoiler,BoxLayout.X_AXIS);
        panelSpoiler.setLayout(spoilerBoxLayout);

        panel2.add(spoilerButton);
        panel2.add(panelSpoiler);

        panelSpoiler.add(firstTextField);


        JPanel panel3=new JPanel();
        BoxLayout cBoxLayout = new BoxLayout(panel3,BoxLayout.X_AXIS);
        panel3.setLayout(cBoxLayout);

        panel3.add(Box.createRigidArea(new Dimension(0,5)));

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.getContentPane().add(panel1);
        frame.getContentPane().add(panel2);
        frame.getContentPane().add(panelSpoiler);
        frame.getContentPane().add(panel3);
        //This is the common size of the buttons

        frame.addWindowListener(new MyWindowListener());
        frame.setVisible(true);

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

            Scanner inputStream;
            String text=null;
            try{
                inputStream=new Scanner(selectedFile);

            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(frame,"File can not be found");
                throw new RuntimeException(ex);
            }
            while (inputStream.hasNext()){
                text=text+inputStream.next();
            }
            CorpusBuilder corp= new CorpusBuilder(text);
            corp.getSentences();
            //List<List<List<String>>> src=new ArrayList<>();

            /*
            for(int i=0; i<corp.getSentences().length; i++){
                src = corp.getWordPosLemma();
            }
                   */
            System.out.println(corp.getSentences());

            System.out.println(corp.getTokens());

            inputStream.close();
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

    private class WordPosLemmaButtonHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JRadioButton button = (JRadioButton) e.getSource();
            searchBy = button.getText();
            //System.out.println(searchBy);
        }
    }


    private class SearchButtonHandler implements ActionListener {
        String s = firstTextField.getText();
        public void actionPerformed(ActionEvent e) {
            if (searchBy.equals("Word")){

            } else if (searchBy.equals("Lemma")) {


            } else if (searchBy.equals("POS")) {


            }
        }
    }

    public static void main ( String[] args )
    {
        new Frame();
    }
}