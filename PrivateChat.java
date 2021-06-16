package Chat;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class PrivateChat extends JFrame implements Serializable {
    public PrivateChat() {
        JButton createRoom = new JButton("创建房间");
        JButton joinRoom = new JButton("加入房间");
        JButton back = new JButton("返回");

        this.add(createRoom);
        this.add(joinRoom);
        this.add(back);

        createRoom.setBounds(50, 50, 100, 50);
        joinRoom.setBounds(200, 50, 100, 50);
        back.setBounds(220, 130, 80, 50);

        createRoom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new PrivateCreateRoom();
                new JoinRoom();
//                new JoinRoom();
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

        joinRoom.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new JoinRoom();
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

        back.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChatSelection();
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
        this.setTitle("私人聊天");
        this.setSize(360, 240);
        this.setLocation(550, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
