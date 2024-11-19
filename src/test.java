import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class test implements ActionListener {
    private int cout = 0;
    private JLabel label;
    private JFrame frame;
    private JButton button;
    private JPanel panel;
    public test(){
        frame = new JFrame();
        button = new JButton("Click Me");
        button.addActionListener(this);
        label = new JLabel("Number o cliks: 0");

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 50, 40));
        panel.setLayout(new GridLayout(2, 2));
        panel.add(label);
        panel.add(button);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Test");
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new test();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        cout++;
        label.setText("Number of cliks: " + cout);

    }
}
