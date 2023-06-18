import javax.swing.*;
import java.awt.*;

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
        firstButton.setMaximumSize(size);
        firstButton.addActionListener(new loadButtonHandler());




        JPanel panel1=new JPanel();
        BoxLayout aBoxLayout = new BoxLayout(panel1,BoxLayout.X_AXIS);
        panel1.setLayout(aBoxLayout);

        panel1.add(Box.createRigidArea(new Dimension(0,5)));
        panel1.add(loadButton);
        panel1.add(firstTextField);

        JPanel panel2=new JPanel();
        BoxLayout bBoxLayout = new BoxLayout(panel2,BoxLayout.X_AXIS);
        panel2.setLayout(bBoxLayout);

        panel2.add(Box.createRigidArea(new Dimension(0,5)));

        JPanel panel3=new JPanel();
        BoxLayout cBoxLayout = new BoxLayout(panel3,BoxLayout.X_AXIS);
        panel3.setLayout(cBoxLayout);

        panel3.add(Box.createRigidArea(new Dimension(0,5)));

        JButton firstButton = new JButton("first");
        frame.getContentPane().add(firstButton);

        //This is the common size of the buttons
        Dimension size = new Dimension(95, 30);





    }
}