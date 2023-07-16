import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

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

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                JButton button = (JButton) c;
                if (button.getModel().isPressed()) {
                    g2d.setColor(Color.decode("#F11712"));
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                }

                g2d.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);

                g2d.setColor(Color.black);
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(button.getText(), g2d);

                int x = (c.getWidth() - (int) r.getWidth()) / 2;
                int y = (c.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();

                g2d.drawString(button.getText(), x, y);
                g2d.dispose();
            }
        });
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

class CustomButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
    private static final int ARC_WIDTH = 15;
    private static final int ARC_HEIGHT = 15;

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(false);
        b.setBorder(new RoundedCornerBorder());
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g.create();
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
        int width = button.getWidth();
        int height = button.getHeight();

        // Draw rounded rectangle background
        g2d.setColor(button.getBackground());
        g2d.fillRoundRect(0, 0, width, height, ARC_WIDTH, ARC_HEIGHT);

        // Draw text
        g2d.setColor(button.getForeground());
        g2d.setFont(button.getFont());
        FontMetrics fm = g2d.getFontMetrics();
        int x = (width - fm.stringWidth(button.getText())) / 2;
        int y = (height + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(button.getText(), x, y);




        g2d.dispose();
    }
}

class RoundedCornerBorder extends AbstractBorder {
    private static final int ARC_WIDTH = 15;
    private static final int ARC_HEIGHT = 15;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(c.getForeground());
        g2d.drawRoundRect(x, y, width - 1, height - 1, ARC_WIDTH, ARC_HEIGHT);

        g2d.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(4, 8, 4, 8);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.set(4, 8, 4, 8);
        return insets;
    }
}

class CustomPanelUI extends PanelUI {
    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        Graphics2D g2d = (Graphics2D) g.create();
        int width = c.getWidth();
        int height = c.getHeight();

        Paint p = new GradientPaint(0.0f, 0.0f, Color.WHITE,
                width, height, Color.BLACK, true);

        g2d.setPaint(p);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
    }
}

class ImagePanel extends JPanel {
    private Image backgroundImage;

    public ImagePanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this));
    }
}

/**class CustomFrameUI extends InternalFrameUI {
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



