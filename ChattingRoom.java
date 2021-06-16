package Chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("serial")
public class ChattingRoom extends JFrame implements Serializable {
    int lineHeight = 0;
    JPanel background = new JPanel();
    Client client;
    String text, Name;
    JTextArea input1;
    JLabel userName;
    String currentName;
//    boolean firstLoad = true;

    @SuppressWarnings("deprecation")
    public ChattingRoom(String Name, Client client, String roomName) {
        this.Name = Name;
        this.client = client;
        String name = Name;// 从SighIn获取
        JLabel title = new JLabel(roomName);
        title.setFont(new Font("黑体", Font.BOLD, 18));
        this.add(title);
        title.setBounds(0, -10, 350, 50);
// 用户输入的内容
        input1 = new JTextArea();
        input1.setLineWrap(true);
        input1.setFont(new Font("楷体", Font.BOLD, 16));
        JScrollPane scr = new JScrollPane(input1);
        scr.setBounds(10, 380, 250, 50);
        this.add(scr);
// 发送按钮
        JButton sendMessage = new JButton("发送");
        sendMessage.setBounds(267, 390, 60, 30);
        this.add(sendMessage);
// 内容窗格透明  不设置的话JPanel不显示(因为JPanel被设置在最底层)
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
//               聊天内容显示框
        background.setBackground(Color.WHITE);
        this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE));
        background.setPreferredSize(new Dimension(300, 330));
        background.setLayout(null);
// 聊天内容显示框的滚动条
        JScrollPane chatContent = new JScrollPane(background);
//        chatContent.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(chatContent);
        chatContent.setBounds(0, 35, 330, 330);

        input1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    text = input1.getText();// 用户输入内容的获取
                    if (!("".equals(text) || "\n".equals(text))) {
                        userName = new JLabel(Name);
                        background.add(userName);
                        userName.setBounds(0, lineHeight, 40, 30);
//                    firstLoad = false;
                        String chatContent = input1.getText();
                        int contentLength = 0, ChineseAmount = 0, line = 0;
                        String regex = "[\u4E00-\u9FA5]+?";
                        Pattern p = Pattern.compile(regex);
                        Matcher m = p.matcher(chatContent);
                        while (m.find()) {
                            ChineseAmount++;
                        }
                        regex = "[\\uFF01]|[\\uFF0C-\\uFF0E]|[\\uFF1A-\\uFF1B]|[\\uFF1F]|[\\uFF08-\\uFF09]|[\\u3001-\\u3002]|[\\u3010-\\u3011]|[\\u201C-\\u201D]|[\\u2013-\\u2014]|[\\u2018-\\u2019]|[\\u2026]|[\\u3008-\\u300F]|[\\u3014-\\u3015]";
                        p = Pattern.compile(regex);// 匹配中文标点
                        m = p.matcher(chatContent);
                        while (m.find()) {
                            ChineseAmount++;
                        }
                        contentLength = 13 * ChineseAmount + 7 * (chatContent.length() - ChineseAmount);// 计算输入内容总共的占位大小来计算所需要的行数
                        if (contentLength % 260 != 0) {
                            line = contentLength / 260 + 1;
                        } else {
                            line = contentLength;
                        } // 计算用户输入内容的行数

                        JTextArea content = new JTextArea();
                        content.setText(chatContent);
                        content.setLineWrap(true);
                        content.setEditable(false);
                        background.add(content);
                        content.setBounds(50, lineHeight, 260, line * 17);
                        lineHeight += line * 17 + 20;
                        if (lineHeight >= 320) {
                            background.setPreferredSize(new Dimension(300, lineHeight));
                        }
                        try {
                            client.sendDataToServer();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        // Dint contentWidth, contentHeight;
                        // JLabel content = new JLabel(text);
                        // content.setFont(new Font("楷体", Font.BOLD, 16));
                        // background.add(content);//必须先add再setBounds
                        // contentWidth = text.length();
                        // System.out.println(contentWidth);
                        // content.setBounds(50, 0, 330, 30);
                    }
                    input1.setText("");
                }
            }
        });

        sendMessage.addMouseListener(new MouseListener() {
            //a is 7    1 is 7  啊 是 13    ， 是 13   一行是17
            @Override
            public void mouseClicked(MouseEvent arg0) {
//                firstLoad[0] = false;
                text = input1.getText();// 用户输入内容的获取
                if (!("".equals(text) || "\n".equals(text))) {
                    userName = new JLabel(Name);
                    background.add(userName);
                    userName.setBounds(0, lineHeight, 40, 30);

                    String chatContent = input1.getText();
                    int contentLength = 0, ChineseAmount = 0, line = 0;
                    String regex = "[\u4E00-\u9FA5]+?";
                    Pattern p = Pattern.compile(regex);
                    Matcher m = p.matcher(chatContent);
                    while (m.find()) {
                        ChineseAmount++;
                    }
                    regex = "[\\uFF01]|[\\uFF0C-\\uFF0E]|[\\uFF1A-\\uFF1B]|[\\uFF1F]|[\\uFF08-\\uFF09]|[\\u3001-\\u3002]|[\\u3010-\\u3011]|[\\u201C-\\u201D]|[\\u2013-\\u2014]|[\\u2018-\\u2019]|[\\u2026]|[\\u3008-\\u300F]|[\\u3014-\\u3015]";
                    p = Pattern.compile(regex);// 匹配中文标点
                    m = p.matcher(chatContent);
                    while (m.find()) {
                        ChineseAmount++;
                    }
                    contentLength = 13 * ChineseAmount + 7 * (chatContent.length() - ChineseAmount);// 计算输入内容总共的占位大小来计算所需要的行数
                    if (contentLength % 260 != 0) {
                        line = contentLength / 260 + 1;
                    } else {
                        line = contentLength;
                    } // 计算用户输入内容的行数

                    JTextArea content = new JTextArea();
                    content.setText(chatContent);
                    content.setLineWrap(true);
                    content.setEditable(false);
                    background.add(content);
                    content.setBounds(50, lineHeight, 260, line * 17);
                    lineHeight += line * 17 + 20;
                    if (lineHeight >= 320) {
                        background.setPreferredSize(new Dimension(300, lineHeight));
                    }
                    try {
                        client.sendDataToServer();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                input1.setText("");
            }

            @Override
            public void mouseEntered(MouseEvent arg0) {
            }

            @Override
            public void mouseExited(MouseEvent arg0) {
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
            }

            @Override
            public void mouseReleased(MouseEvent arg0) {
            }

        });
// 设置窗口
        ImageIcon icon = new ImageIcon("src/Chat/comment.png");
        this.setIconImage(icon.getImage());
        this.setLayout(null);
        this.setTitle("聊天室");
        this.setLocation(500, 300);
        this.setSize(350, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void addText(String chatText, String name) {
        currentName = name;
        userName = new JLabel(currentName);
//        System.out.println(currentName);
        background.add(userName);
        userName.setBounds(0, lineHeight, 40, 30);

        String chatContent = chatText;
        int contentLength = 0, ChineseAmount = 0, line = 0;
        String regex = "[\u4E00-\u9FA5]+?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(chatContent);
        while (m.find()) {
            ChineseAmount++;
        }
        regex = "[\\uFF01]|[\\uFF0C-\\uFF0E]|[\\uFF1A-\\uFF1B]|[\\uFF1F]|[\\uFF08-\\uFF09]|[\\u3001-\\u3002]|[\\u3010-\\u3011]|[\\u201C-\\u201D]|[\\u2013-\\u2014]|[\\u2018-\\u2019]|[\\u2026]|[\\u3008-\\u300F]|[\\u3014-\\u3015]";
        p = Pattern.compile(regex);// 匹配中文标点
        m = p.matcher(chatContent);
        while (m.find()) {
            ChineseAmount++;
        }
        contentLength = 13 * ChineseAmount + 7 * (chatContent.length() - ChineseAmount);// 计算输入内容总共的占位大小来计算所需要的行数
        if (contentLength % 260 != 0) {
            line = contentLength / 260 + 1;
        } else {
            line = contentLength;
        } // 计算用户输入内容的行数

        JTextArea content = new JTextArea();
        content.setText(chatContent);
        content.setLineWrap(true);
        content.setEditable(false);
        background.add(content);
        content.setBounds(50, lineHeight, 260, line * 17);
        lineHeight += line * 17 + 20;
        if (lineHeight >= 320) {
            background.setPreferredSize(new Dimension(300, lineHeight));
        }
        input1.setText("");
    }

    public void addText(String chatText) {
//        userName = new JLabel(currentName);
////        System.out.println(currentName);
//        background.add(userName);
//        userName.setBounds(0, lineHeight, 40, 30);

        String chatContent = chatText;
        int contentLength = 0, ChineseAmount = 0, line = 0;
        String regex = "[\u4E00-\u9FA5]+?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(chatContent);
        while (m.find()) {
            ChineseAmount++;
        }
        regex = "[\\uFF01]|[\\uFF0C-\\uFF0E]|[\\uFF1A-\\uFF1B]|[\\uFF1F]|[\\uFF08-\\uFF09]|[\\u3001-\\u3002]|[\\u3010-\\u3011]|[\\u201C-\\u201D]|[\\u2013-\\u2014]|[\\u2018-\\u2019]|[\\u2026]|[\\u3008-\\u300F]|[\\u3014-\\u3015]";
        p = Pattern.compile(regex);// 匹配中文标点
        m = p.matcher(chatContent);
        while (m.find()) {
            ChineseAmount++;
        }
        contentLength = 13 * ChineseAmount + 7 * (chatContent.length() - ChineseAmount);// 计算输入内容总共的占位大小来计算所需要的行数
        if (contentLength % 260 != 0) {
            line = contentLength / 260 + 1;
        } else {
            line = contentLength;
        } // 计算用户输入内容的行数

        JTextArea content = new JTextArea();
        content.setText(chatContent);
        content.setLineWrap(true);
        content.setEditable(false);
        background.add(content);
        content.setBounds(50, lineHeight, 260, line * 17);
        lineHeight += line * 17 + 20;
        if (lineHeight >= 320) {
            background.setPreferredSize(new Dimension(300, lineHeight));
        }
        input1.setText("");
    }
}
