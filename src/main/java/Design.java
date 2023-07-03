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
import javax.swing.plaf.InternalFrameUI;
import javax.swing.plaf.PanelUI;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;

public class Design {
    private Color color1 = Color.decode("#0099F7");
    private Color color2 = Color.decode("#F11712");

    public Design() {
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

    public static void textOutputStyle(String string1, String string2, String string3, JTextPane outputArea) {
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        StyledDocument doc = outputArea.getStyledDocument();

        outputArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set default font size and style
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style blackStyle = doc.addStyle("black", defaultStyle);
        StyleConstants.setForeground(blackStyle, Color.BLACK);
        Style redStyle = doc.addStyle("pink", defaultStyle);
        StyleConstants.setForeground(redStyle, Color.RED);
        StyleConstants.setFontSize(redStyle, 15);
        StyleConstants.setItalic(redStyle, true);
        StyleConstants.setBold(redStyle, true);

        try {
            doc.insertString(doc.getLength(), string1 + " ", blackStyle);
            doc.insertString(doc.getLength(), string2 + " ", redStyle);
            doc.insertString(doc.getLength(), string3 + "\n", blackStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

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

class CustomFrameUI extends InternalFrameUI {
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
}
