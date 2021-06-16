package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JoinRoom extends JFrame implements Serializable {
    public String Ip, Name;
    public static final int PORT1 = 1010;
    public static final int PORT2 = 2020;
    private static final int MAX_LEN = 1000000;
    private String[] randname = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "卢本伟", "孙笑川", "汤老师", "JAVA", "C语言", "高数", "高斯", "柯西", "sun", "oracle", "笑死", "聊天室", "大物", "Trump"};

    public JoinRoom() {

//        imageIcon.setImage(imageIcon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));

        JLabel user1 = new JLabel("ip:");
        JLabel userName = new JLabel("名字:");

        JTextField ip1 = new JTextField(20);
        JTextField name = new JTextField(20);

        this.add(user1);
        this.add(userName);
        this.add(ip1);
        this.add(name);

        user1.setBounds(20, 60, 30, 20);
        ip1.setBounds(50, 60, 100, 20);
        userName.setBounds(170, 60, 30, 20);
        name.setBounds(200, 60, 100, 20);

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
        randName.setBounds(307, 58, 25, 25);

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

        ip1.addKeyListener(new KeyListener() {
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
                    boolean isRepeatName = false;
                    for (int i = 0; i < Server.clientName.length; i++) {
                        if (Name == Server.clientName[i]) {
                            isRepeatName = true;
                        }
                    }
                    Ip = ip1.getText();
                    int round = 0;
                    int ChineseAmount = 0;
                    int nameLength;
                    Name = name.getText();
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
                    regex = "[.]";
                    p = Pattern.compile(regex);
                    m = p.matcher(Ip);
                    while (m.find()) {
                        round++;
                    }
                    nameLength = 13 * ChineseAmount + 7 * (Name.length() - ChineseAmount);

                    try {
                        InetAddress address = InetAddress.getByName(Ip);
                        DatagramSocket socket = new DatagramSocket();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(1);
                        byte[] nameRepeatOut = byteArrayOutputStream.toByteArray();
                        DatagramPacket packet = new DatagramPacket(nameRepeatOut, nameRepeatOut.length, address, Server.PORT);
                        socket.send(packet);
                        byte[] getNameRepeat = new byte[MAX_LEN];
                        DatagramPacket inPacket = new DatagramPacket(getNameRepeat, getNameRepeat.length);
                        socket.receive(inPacket);
                        getNameRepeat = inPacket.getData();
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getNameRepeat);
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        String[] nameRepeat = (String[]) objectInputStream.readObject();
                        for (int i = 0; i < nameRepeat.length; i++) {
                            if (Name.equals(nameRepeat[i])) {
                                isRepeatName = true;
                            }
                        }
                        byteArrayOutputStream.close();
                        objectOutputStream.close();
                        byteArrayInputStream.close();
                        objectInputStream.close();
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }

                    if (nameLength > 42) {
                        JOptionPane.showConfirmDialog(null, "名字过长，请重输", null, JOptionPane.CLOSED_OPTION);
                    } else if ("".equals(Ip) && nameLength > 0 && nameLength < 42) {
                        JOptionPane.showConfirmDialog(null, "IP不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (!("".equals(Ip)) && nameLength == 0) {
                        JOptionPane.showConfirmDialog(null, "名字不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if ("".equals(Ip) && nameLength == 0) {
                        JOptionPane.showConfirmDialog(null, "名字和IP都不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (round != 3) {
                        JOptionPane.showConfirmDialog(null, "请输入正确的IP地址", null, JOptionPane.CLOSED_OPTION);
                    } else if (isRepeatName) {
                        JOptionPane.showConfirmDialog(null, "名字重复，请重输", null, JOptionPane.CLOSED_OPTION);
                    } else {
                        Client client = null;
                        try {
                            client = new Client(Name, Ip);
                            new Thread(client).start();
                        } catch (SocketException socketException) {
                            socketException.printStackTrace();
                        } catch (IOException unknownHostException) {
                            unknownHostException.printStackTrace();
                        }
                        //                    new Thread(client).start();
                        dispose();
                    }
                }
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
                    boolean isRepeatName = false;
                    for (int i = 0; i < Server.clientName.length; i++) {
                        if (Name == Server.clientName[i]) {
                            isRepeatName = true;
                        }
                    }
                    Ip = ip1.getText();
                    int round = 0;
                    int ChineseAmount = 0;
                    int nameLength;
                    Name = name.getText();
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
                    regex = "[.]";
                    p = Pattern.compile(regex);
                    m = p.matcher(Ip);
                    while (m.find()) {
                        round++;
                    }
                    nameLength = 13 * ChineseAmount + 7 * (Name.length() - ChineseAmount);

                    try {
                        InetAddress address = InetAddress.getByName(Ip);
                        DatagramSocket socket = new DatagramSocket();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(1);
                        byte[] nameRepeatOut = byteArrayOutputStream.toByteArray();
                        DatagramPacket packet = new DatagramPacket(nameRepeatOut, nameRepeatOut.length, address, Server.PORT);
                        socket.send(packet);
                        byte[] getNameRepeat = new byte[MAX_LEN];
                        DatagramPacket inPacket = new DatagramPacket(getNameRepeat, getNameRepeat.length);
                        socket.receive(inPacket);
                        getNameRepeat = inPacket.getData();
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getNameRepeat);
                        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                        String[] nameRepeat = (String[]) objectInputStream.readObject();
                        for (int i = 0; i < nameRepeat.length; i++) {
                            if (Name.equals(nameRepeat[i])) {
                                isRepeatName = true;
                            }
                        }
                        byteArrayOutputStream.close();
                        objectOutputStream.close();
                        byteArrayInputStream.close();
                        objectInputStream.close();
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }

                    if (nameLength > 42) {
                        JOptionPane.showConfirmDialog(null, "名字过长，请重输", null, JOptionPane.CLOSED_OPTION);
                    } else if ("".equals(Ip) && nameLength > 0 && nameLength < 42) {
                        JOptionPane.showConfirmDialog(null, "IP不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (!("".equals(Ip)) && nameLength == 0) {
                        JOptionPane.showConfirmDialog(null, "名字不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if ("".equals(Ip) && nameLength == 0) {
                        JOptionPane.showConfirmDialog(null, "名字和IP都不能为空", null, JOptionPane.CLOSED_OPTION);
                    } else if (round != 3) {
                        JOptionPane.showConfirmDialog(null, "请输入正确的IP地址", null, JOptionPane.CLOSED_OPTION);
                    } else if (isRepeatName) {
                        JOptionPane.showConfirmDialog(null, "名字重复，请重输", null, JOptionPane.CLOSED_OPTION);
                    } else {
                        Client client = null;
                        try {
                            client = new Client(Name, Ip);
                            new Thread(client).start();
                        } catch (SocketException socketException) {
                            socketException.printStackTrace();
                        } catch (IOException unknownHostException) {
                            unknownHostException.printStackTrace();
                        }
                        //                    new Thread(client).start();
                        dispose();
                    }
                }
            }
        });

        output.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Name = name.getText();
                boolean isRepeatName = false;
                Ip = ip1.getText();
                int round = 0;
                int ChineseAmount = 0;
                int nameLength;
                Name = name.getText();
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
                regex = "[.]";
                Pattern p1 = Pattern.compile(regex);
                Matcher m1 = p1.matcher(Ip);
                while (m1.find()) {
                    round++;
                }
                nameLength = 13 * ChineseAmount + 7 * (Name.length() - ChineseAmount);

                try {
                    InetAddress address = InetAddress.getByName(Ip);
                    DatagramSocket socket = new DatagramSocket();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                    objectOutputStream.writeObject(1);
                    byte[] nameRepeatOut = byteArrayOutputStream.toByteArray();
                    DatagramPacket packet = new DatagramPacket(nameRepeatOut, nameRepeatOut.length, address, Server.PORT);
                    socket.send(packet);
                    byte[] getNameRepeat = new byte[MAX_LEN];
                    DatagramPacket inPacket = new DatagramPacket(getNameRepeat, getNameRepeat.length);
                    socket.receive(inPacket);
                    getNameRepeat = inPacket.getData();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(getNameRepeat);
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                    String[] nameRepeat = (String[]) objectInputStream.readObject();
                    for (int i = 0; i < nameRepeat.length; i++) {
                        if (Name.equals(nameRepeat[i])) {
                            isRepeatName = true;
                        }
                    }
                    byteArrayOutputStream.close();
                    objectOutputStream.close();
                    byteArrayInputStream.close();
                    objectInputStream.close();
                } catch (IOException | ClassNotFoundException ioException) {
                    ioException.printStackTrace();
                }

                if (nameLength > 42) {
                    JOptionPane.showConfirmDialog(null, "名字过长，请重输", null, JOptionPane.CLOSED_OPTION);
                } else if ("".equals(Ip) && nameLength > 0 && nameLength < 42) {
                    JOptionPane.showConfirmDialog(null, "IP不能为空", null, JOptionPane.CLOSED_OPTION);
                } else if (!("".equals(Ip)) && nameLength == 0) {
                    JOptionPane.showConfirmDialog(null, "名字不能为空", null, JOptionPane.CLOSED_OPTION);
                } else if ("".equals(Ip) && nameLength == 0) {
                    JOptionPane.showConfirmDialog(null, "名字和IP都不能为空", null, JOptionPane.CLOSED_OPTION);
                } else if (round != 3) {
                    JOptionPane.showConfirmDialog(null, "请输入正确的IP地址", null, JOptionPane.CLOSED_OPTION);
                } else if (isRepeatName) {
                    JOptionPane.showConfirmDialog(null, "名字重复，请重输", null, JOptionPane.CLOSED_OPTION);
                } else {
                    try {
                        Client client = null;
                        client = new Client(Name, Ip);
                        new Thread(client).start();
                    } catch (SocketException socketException) {
                        socketException.printStackTrace();
                    } catch (IOException unknownHostException) {
                        unknownHostException.printStackTrace();
                    }
                    //                    new Thread(client).start();
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
        this.setTitle("搜索房间");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
