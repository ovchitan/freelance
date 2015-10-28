import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author rezenter
 */
public class a extends JFrame {

    public static void main(String[] args) {
        new a();
    }

    public static final String FRAME_TITLE = "boring task";

    public a() {
        super(FRAME_TITLE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        final JTextArea textArea = new JTextArea();
        panel.add(textArea);

        JPanel upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(2, 1));
        panel.add(upPanel);

        final JTextArea task = new JTextArea();
        upPanel.add(task);

        JPanel downPanel = new JPanel();
        downPanel.setLayout(new GridLayout(1, 2));
        upPanel.add(downPanel);

        for (int i = 0; i < 2; i++) {
            final JButton button = new JButton();
            downPanel.add(button);

            button.setText((i + 1) + "");
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = button.getText();

                    textArea.append(text);
                }
            });
        }

        setContentPane(panel);
        int w = 0;
        int h = 0;
        setSize(w, h);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
}