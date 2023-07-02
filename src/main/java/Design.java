import javax.swing.*;
import java.awt.*;

public class Design {
    public static void applyDesign(JFrame frame) {
        frame.setSize(1000, 600);
    }

    public static void applyTextFieldStyle(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 50));
    }

    public static void applyButtonStyle(JButton button) {
        Dimension size = new Dimension(95, 30);
        button.setMaximumSize(size);
        button.setBackground(Color.PINK);
    }

    public static void applyRadioButtonStyle(JRadioButton radioButton) {
        // Apply styling to the radio button
    }



    public static void applyPanelStyle(JPanel panel) {


    }

}
