package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupCreateRoom extends JFrame {
    public String Name;
    public static final int PORT1 = 1010;
    public static final int PORT2 = 2020;
    private String[] randname = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "卢本伟", "孙笑川", "汤老师", "JAVA", "C语言", "高数", "高斯", "柯西", "sun", "oracle", "笑死", "聊天室", "大物", "Trump", "房猪", "学习仔"};


    public GroupCreateRoom() {
        JLabel userName = new JLabel("房主名字:");
        JLabel roomName = new JLabel("房间名字:");

        JTextField name = new JTextField(20);
        JTextField name1 = new JTextField(20);

        this.add(userName);
        this.add(roomName);
        this.add(name);
        this.add(name1);

        userName.setBounds(80, 60, 60, 20);
        name.setBounds(140, 60, 100, 20);
        roomName.setBounds(80, 90, 60, 20);
        name1.setBounds(140, 90, 100, 20);

        String[] number = {"3", "4", "5", "6"};
        JComboBox clientNumber = new JComboBox(number);
        this.add(clientNumber);
        clientNumber.setBounds(247,88,35,25);

        JButton output = new JButton("确定");
        this.add(output);
        output.setBounds(60, 130, 75, 40);
        JButton back = new JButton("返回");
        this.add(back);
        back.setBounds(210, 130, 75, 40);
        JButton randName = new JButton();
        ImageIcon imageIcon = new ImageIcon("src/chat/refresh.png");
        Image temp1 = imageIcon.getImage().getScaledInstance(20, 20, imageIcon.getImage().SCALE_DEFAULT);
        //新建图片，大小调制成和按钮大小一样大
        //getScaledInstance()方法返回的是一个图片，后面的参数在程序下有注解。
        imageIcon = new ImageIcon(temp1);
        randName.setIcon(imageIcon);
        this.add(randName);
        randName.setBounds(247, 58, 25, 25);

        randName.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Random random = new Random();
                name.setText(randname[random.nextInt(randname.length)]);
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


        name.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    Name = name.getText();
                    int ChineseAmount = 0;
                    int nameLength;
                    String regex = "[\u4E00-\u9FA5]+?";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(Name);
                    while (m.find()) {
                        ChineseAmount++;
                    }
                    regex = "[\\uFF01]|[\\uFF0C-\\uFF0E]|[\\uFF1A-\\uFF1B]|[\\uFF1F]|[\\uFF08-\\uFF09]|[\\u3001-\\u3002]|[\\u3010-\\u3011]|[\\u201C-\\u201D]|[\\u2013-\\u2014]|[\\u2018-\\u2019]|[\\u2026]|[\\u3008-\\u300F]|[\\u3014-\\u3015]";
                    p = Pattern.compile(regex);// 匹配中文标点
                    m = p.matcher(Name);
                    while (m.find()) {
                        ChineseAmount++;
                    }
                    nameLength = 13 * ChineseAmount + 7 * (Name.length() - ChineseAmount);
                    if (nameLength > 42) {
                        JOptionPane.showConfirmDialog(null, "名字过长，请重输", null, JOptionPane.CLOSED_OPTION);
                    } else if ("".equals(name1.getText()) && nameLength > 0 && nameLength < 42) {
                        JOptionPane.showConfirmDialog(null, "房间名字不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (nameLength == 0 && !("".equals(name1.getText()))) {
                        JOptionPane.showConfirmDialog(null, "名字不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (nameLength == 0 && "".equals(name1.getText())) {
                        JOptionPane.showConfirmDialog(null, "名字和房间名字都不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else {
                        try {
//                            System.out.println(name1.getText());
                            Server server = new Server(Name, "多人房间", InetAddress.getLocalHost(), name1.getText(), Integer.parseInt((String) clientNumber.getSelectedItem()));
                            new Thread(server).start();
                            Client client = new Client(Name);
                            new Thread(client).start();
                        } catch (IOException socketException) {
                            socketException.printStackTrace();
                        }
                        dispose();
                    }
                }
            }
        });

        name1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    Name = name.getText();
                    int ChineseAmount = 0;
                    int nameLength;
                    String regex = "[\u4E00-\u9FA5]+?";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(Name);
                    while (m.find()) {
                        ChineseAmount++;
                    }
                    regex = "[\\uFF01]|[\\uFF0C-\\uFF0E]|[\\uFF1A-\\uFF1B]|[\\uFF1F]|[\\uFF08-\\uFF09]|[\\u3001-\\u3002]|[\\u3010-\\u3011]|[\\u201C-\\u201D]|[\\u2013-\\u2014]|[\\u2018-\\u2019]|[\\u2026]|[\\u3008-\\u300F]|[\\u3014-\\u3015]";
                    p = Pattern.compile(regex);// 匹配中文标点
                    m = p.matcher(Name);
                    while (m.find()) {
                        ChineseAmount++;
                    }
                    nameLength = 13 * ChineseAmount + 7 * (Name.length() - ChineseAmount);
                    if (nameLength > 42) {
                        JOptionPane.showConfirmDialog(null, "名字过长，请重输", null, JOptionPane.CLOSED_OPTION);
                    } else if ("".equals(name1.getText()) && nameLength > 0 && nameLength < 42) {
                        JOptionPane.showConfirmDialog(null, "房间名字不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (nameLength == 0 && !("".equals(name1.getText()))) {
                        JOptionPane.showConfirmDialog(null, "名字不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (nameLength == 0 && "".equals(name1.getText())) {
                        JOptionPane.showConfirmDialog(null, "名字和房间名字都不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else {
                        try {
                            System.out.println(name1.getText());
                            Server server = new Server(Name, "多人房间", InetAddress.getLocalHost(), name1.getText(), Integer.parseInt((String) clientNumber.getSelectedItem()));
                            new Thread(server).start();
                            Client client = new Client(Name);
                            new Thread(client).start();
                        } catch (IOException socketException) {
                            socketException.printStackTrace();
                        }
                        dispose();
                    }
                }
            }
        });

        output.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Name = name.getText();
                int ChineseAmount = 0;
                int nameLength;
                String regex = "[\u4E00-\u9FA5]+?";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(Name);
                while (m.find()) {
                    ChineseAmount++;
                }
                regex = "[\\uFF01]|[\\uFF0C-\\uFF0E]|[\\uFF1A-\\uFF1B]|[\\uFF1F]|[\\uFF08-\\uFF09]|[\\u3001-\\u3002]|[\\u3010-\\u3011]|[\\u201C-\\u201D]|[\\u2013-\\u2014]|[\\u2018-\\u2019]|[\\u2026]|[\\u3008-\\u300F]|[\\u3014-\\u3015]";
                p = Pattern.compile(regex);// 匹配中文标点
                m = p.matcher(Name);
                while (m.find()) {
                    ChineseAmount++;
                }
                nameLength = 13 * ChineseAmount + 7 * (Name.length() - ChineseAmount);
                if (nameLength > 42) {
                    JOptionPane.showConfirmDialog(null, "名字过长，请重输", null, JOptionPane.CLOSED_OPTION);
                } else if ("".equals(name1.getText())) {
                    JOptionPane.showConfirmDialog(null, "房间名字不能为空", null, JOptionPane.CLOSED_OPTION);
                } else if (nameLength == 0) {
                    JOptionPane.showConfirmDialog(null, "名字不能为空", null, JOptionPane.CLOSED_OPTION);
                } else {
                    try {
                        System.out.println(name1.getText());
                        Server server = new Server(Name, "多人房间", InetAddress.getLocalHost(), name1.getText(), Integer.parseInt((String) clientNumber.getSelectedItem()));
                        new Thread(server).start();
                        Client client = new Client(Name);
                        new Thread(client).start();
                    } catch (IOException socketException) {
                        socketException.printStackTrace();
                    }
                    dispose();
                }
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
        this.setTitle("创建房间");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
