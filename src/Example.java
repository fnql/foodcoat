import javax.swing.*;
import java.awt.*;

public class Example extends JFrame {

    public Example() {
        initUI();
    }

    public final void initUI() {
        JPanel panel = new JPanel();
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("회원가입"));
        panel.setLayout(new GridLayout(7, 2, 5, 5));

        JComponent[] components = {
                new JLabel("ID"), new JTextField(), new JButton("중복확인"),
                new JLabel("PWD"), new JPasswordField(), new JLabel(),
                new JLabel("PWD확인"), new JPasswordField(), new JLabel(),
                new JLabel("이름"), new JTextField(), new JLabel(),
                new JLabel("전화번호"), new JTextField(), new JLabel(),
                new JLabel(), new JButton("확인"), new JLabel()
        };

        for (JComponent c : components) {
            panel.add(c);
        }

        add(BorderLayout.NORTH, titlePanel);
        add(BorderLayout.CENTER, panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    public static void main(String[] args) {
        Example ex = new Example();
        ex.setVisible(true);
        ex.setLocationRelativeTo(null);
    }
}