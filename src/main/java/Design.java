import javax.swing.*;
import javax.swing.plaf.PanelUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Design {
    public static Color color1 = new Color(115, 201, 177);

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


        button.setUI(new CustomButtonUI());

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                JButton button = (JButton) c;
                if (button.getModel().isPressed()) {
                    g2d.setColor(Color.decode("#388087"));
                } else {
                    g2d.setColor(Color.decode("#6FB3B8"));
                }

                g2d.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);

                g2d.setColor(Color.BLACK);
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(button.getText(), g2d);

                int x = (c.getWidth() - (int) r.getWidth()) / 2;
                int y = (c.getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();

                g2d.drawString(button.getText(), x, y);
                g2d.dispose();
            }
        });
    }
    public static void spoilerButtonStyler(JButton button){



        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                JButton button = (JButton) c;
                if (button.getModel().isPressed()) {
                    g2d.setColor(Color.decode("#388087"));
                } else {
                    g2d.setColor(Color.decode("#6FB3B8"));
                }

                g2d.fillRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 20, 20);

                g2d.setColor(Color.BLACK);
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
        radioButton.setUI(new CustomRadioButtonUI());
        radioButton.setForeground(Color.BLACK);
    }

    public static void applyPanelStyle(JPanel panel) {
        panel.setOpaque(false);
        panel.setUI(new CustomPanelUI());

        // Iterate over the components of the panel
        for (Component component : panel.getComponents()) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                label.setForeground(Color.WHITE);
            }
        }
    }

    public static void textOutputStyle(String string1, String string2, String string3, JTextPane outputArea) {
        outputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        StyledDocument doc = outputArea.getStyledDocument();

        outputArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set default font size and style
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style blackStyle = doc.addStyle("black", defaultStyle);
        StyleConstants.setForeground(blackStyle, Color.BLACK);
        Style redStyle = doc.addStyle("pink", defaultStyle);
        StyleConstants.setForeground(redStyle, color1);
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
    private static final int BUTTON_WIDTH = 150;

    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(false);
        b.setBorder(BorderFactory.createEmptyBorder()); // Set an empty border
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g.create();
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
        int width = BUTTON_WIDTH; // Use the custom button width

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

class CustomRadioButtonUI extends javax.swing.plaf.basic.BasicButtonUI {
    @Override
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setOpaque(false); // Set the button to be non-opaque
        Color transparentWhite = new Color(255, 255, 255, 128);
        b.setBackground(transparentWhite);

        b.setBorder(BorderFactory.createEmptyBorder()); // Set an empty border

        // Set the custom radio button icon
        b.setIcon(new CustomRadioButtonIcon());
    }
}
class CustomRadioButtonIcon implements Icon {
    private static final int RADIUS = 8;
    private static final int BORDER_THICKNESS = 2;

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();

        // border of the icon
        g.setColor(Color.decode("#6BB5DE"));
        g.drawOval(x, y, RADIUS * 2, RADIUS * 2);


        if (model.isSelected()) {
            g.setColor(Color.decode("#A4E0F2"));
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillOval(x + BORDER_THICKNESS, y + BORDER_THICKNESS, (RADIUS - BORDER_THICKNESS) * 2, (RADIUS - BORDER_THICKNESS) * 2);
    }

    @Override
    public int getIconWidth() {
        return (RADIUS + BORDER_THICKNESS) * 2;
    }

    @Override
    public int getIconHeight() {
        return (RADIUS + BORDER_THICKNESS) * 2;
    }
}

class CustomPanelUI extends PanelUI {
    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);

        Graphics2D g2d = (Graphics2D) g.create();
        int width = c.getWidth();
        int height = c.getHeight();

        Paint p = new GradientPaint(0.0f, 0.0f, Color.decode("#BADFE7"),
                width, height, Color.decode("#BADFE7"), true);

        g2d.setPaint(p);
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
    }
}


