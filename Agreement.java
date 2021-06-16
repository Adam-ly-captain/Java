package Chat;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Agreement extends JFrame {
    public int agreement = -1;
    private String name;
    public Agreement(String name) {
        this.name = name;
        JButton agree = new JButton("同意");
        JButton disagree = new JButton("不同意");
        this.add(agree);
        agree.setBounds(30, 35, 75, 40);
        this.add(disagree);
        disagree.setBounds(145,     35, 75, 40);

        agree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                agreement = 1;
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        disagree.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                agreement = 0;
                dispose();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


        ImageIcon icon = new ImageIcon("src/Chat/comment.png");
        this.setIconImage(icon.getImage());
        this.setLayout(null);
        this.setSize(260, 140);
        this.setLocation(550, 300);
        this.setTitle(this.name + "请求加入");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
