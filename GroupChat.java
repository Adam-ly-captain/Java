package Chat;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class GroupChat extends JFrame implements Serializable {
    public GroupChat() {

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
                new GroupCreateRoom();
                new JoinRoom();
                new JoinRoom();
                new JoinRoom();
                new JoinRoom();
                new JoinRoom();
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
        this.setSize(360, 240);
        this.setLocation(550, 300);
        this.setTitle("多人聊天");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

//        JLabel user1 = new JLabel("IP1:");
//        JLabel user2 = new JLabel("IP2:");
//        JLabel user3 = new JLabel("IP3:");
//        JLabel user4 = new JLabel("IP4:");
//        JLabel user5 = new JLabel("IP5:");
//        JLabel user6 = new JLabel("IP6:");
//
//        JTextField ip1 = new JTextField(20);
//        JTextField ip2 = new JTextField(20);
//        JTextField ip3 = new JTextField(20);
//        JTextField ip4 = new JTextField(20);
//        JTextField ip5 = new JTextField(20);
//        JTextField ip6 = new JTextField(20);
//
//        this.add(user1);
//        this.add(user2);
//        this.add(user3);
//        this.add(user4);
//        this.add(user5);
//        this.add(user6);
//        this.add(ip1);
//        this.add(ip2);
//        this.add(ip3);
//        this.add(ip4);
//        this.add(ip5);
//        this.add(ip6);
//
//        user1.setBounds(20, 15, 30, 20);
//        ip1.setBounds(50, 15, 100, 20);
//        user2.setBounds(170, 15, 30, 20);
//        ip2.setBounds(200, 15, 100, 20);
//        user3.setBounds(20, 50, 30, 20);
//        ip3.setBounds(50, 50, 100, 20);
//        user4.setBounds(170, 50, 30, 20);
//        ip4.setBounds(200, 50, 100, 20);
//        user5.setBounds(20, 85, 30, 20);
//        ip5.setBounds(50, 85, 100, 20);
//        user6.setBounds(170, 85, 30, 20);
//        ip6.setBounds(200, 85, 100, 20);
//
//        JButton output = new JButton("确定");
//        this.add(output);
//        output.setBounds(60, 130, 75, 40);
//
//        JButton back = new JButton("返回");
//        this.add(back);
//        back.setBounds(210, 130, 75, 40);
//
//        output.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//        });
//
//        back.addMouseListener(new MouseListener() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                new ChatSelection();
//                dispose();
//            }
//
//            @Override
//            public void mousePressed(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseReleased(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseEntered(MouseEvent e) {
//
//            }
//
//            @Override
//            public void mouseExited(MouseEvent e) {
//
//            }
//
//        });

    }
}
