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
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.PanelUI;
import java.awt.*;

public class Design {
    private Color color1 = Color.decode("#0099F7");
    private Color color2 = Color.decode("#F11712");

    public Design(){
        super();
    }

    public static void applyDesign(JFrame frame) {
        frame.setSize(1000, 600);
        frame.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;

    }

    public static void applyTextFieldStyle(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 50));
    }

    public static void applyButtonStyle(JButton button) {

        Dimension size = new Dimension(95, 30);
        button.setMaximumSize(size);

        //button.setUI(new CustomButtonUI());


        //button.setContentAreaFilled(false); // Empty buttons without background
        //button.setForeground(Color.BLUE); // Text color is set to blue
    }

    public static void applyRadioButtonStyle(JRadioButton radioButton) {
        // Apply styling to the radio button
    }

    public static void applyPanelStyle(JPanel panel) {
        panel.setOpaque(false);
        panel.setUI(new CustomPanelUI());
    }

}


class CustomPanelUI extends PanelUI {

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        Graphics2D g2d = (Graphics2D) g.create();
        int width = c.getWidth();
        int height = c.getHeight();

        Paint p = new GradientPaint(0.0f, 0.0f, Color.RED,
                width, height, Color.YELLOW, true);

        g2d.setPaint(p);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
    }
}
/**class CustomButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g.create();
        int width = c.getWidth();
        int height = c.getHeight();

        // Call superclass paint method to ensure proper button rendering
        super.paint(g2d, c);

        // Set the foreground color for the button text
        c.setForeground(Color.BLACK);

        // Draw the gradient-filled rectangle
        Paint p = new GradientPaint(0.0f, 0.0f, Color.RED, width, height, Color.YELLOW, true);
        g2d.setPaint(p);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
    }
}*/

