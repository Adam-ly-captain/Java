package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ChatSelection extends JFrame implements Serializable {
    public ChatSelection() {

        JButton privateChat = new JButton("私人聊天");
        JButton groupChat = new JButton("多人聊天");
        JButton myIp = new JButton("查看本机IP");
        this.add(myIp);
        this.add(privateChat);
        this.add(groupChat);

        JLabel selection = new JLabel("请选择:");
        selection.setFont(new Font("楷体", Font.BOLD, 16));
        this.add(selection);

        privateChat.setBounds(110, 40, 120, 50);
        groupChat.setBounds(110, 110, 120, 50);
        selection.setBounds(10, 0, 80, 50);
        myIp.setBounds(240, 160, 100, 40);

        myIp.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showIp();
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

        privateChat.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new PrivateChat();
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

        groupChat.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new GroupChat();
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
        this.setTitle("选择界面");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void showIp() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();//获取本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        JOptionPane.showConfirmDialog(null, address.getHostAddress(), null, JOptionPane.CLOSED_OPTION);
    }
}
