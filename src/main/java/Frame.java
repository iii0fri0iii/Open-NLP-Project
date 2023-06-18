import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Frame{
    private JFrame frame;
    private JTextField firstTextField;

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

        JPanel panel3=new JPanel();
        BoxLayout cBoxLayout = new BoxLayout(panel3,BoxLayout.X_AXIS);
        panel3.setLayout(cBoxLayout);

        panel3.add(Box.createRigidArea(new Dimension(0,5)));


        //This is the common size of the buttons
        frame.setVisible(true);
    }


    public class LoadButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String fileName=JOptionPane.showInputDialog(frame,"Enter the file name: ");
            Scanner inputStream;
            String text=null;
            try{
                inputStream=new Scanner(new File(fileName));


            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(frame,"File can not be found");
                throw new RuntimeException(ex);
            }
            while (inputStream.hasNext()){
                text=text+inputStream.next();
            }
            CorpusBuilder corp= new CorpusBuilder(text);
            corp.getSentences();
            List<List<List<String>>> src=new ArrayList<>();


            for(int i=0; i<corp.getSentences().length; i++){
                src=corp.getWordPosLemma();
            }


            inputStream.close();
        }
    }

    public static void main ( String[] args )
    {
        new Frame();
    }
}