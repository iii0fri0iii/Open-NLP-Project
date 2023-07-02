import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

public class Design {
    private Color color1 = Color.decode("#0099F7");
    private Color color2 = Color.decode("#F11712");

    public Design(){
        super();
    }

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
        //button.setContentAreaFilled(false); // Empty buttons without background
        //button.setForeground(Color.BLUE); // Text color is set to blue
    }

    public static void applyRadioButtonStyle(JRadioButton radioButton) {
        // Apply styling to the radio button
    }

    public static void applyPanelStyle(JPanel panel) {
        panel.setOpaque(false);
    }
    private void paintComponent(Graphics g){

        Graphics2D g2d=(Graphics2D) g;
        int width=10;
        int heigth=15;
        Color color1=new Color(52,143,80);
        Color color2=new Color(86,180,211);
        GradientPaint gp=new GradientPaint(0,0,color1,180,15,color2);
        g2d.setPaint(gp);
        g2d.fillRect(0,0,10,15);
    }


}
