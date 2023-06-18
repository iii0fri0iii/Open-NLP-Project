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

        JPanel panel1=new JPanel();
        BoxLayout aBoxLayout = new BoxLayout(panel1,BoxLayout.Y_AXIS);
        panel1.setLayout(aBoxLayout);

        panel1.add(Box.createRigidArea(new Dimension(0,5)));
        panel1.add(firstTextField);

        //This is the common size of the buttons
        Dimension size = new Dimension(95, 30);



    }
}