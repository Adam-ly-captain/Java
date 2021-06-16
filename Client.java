package Chat;

import javax.swing.*;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client implements Runnable, Serializable {
    ChattingRoom chattingRoom;
    String Name, chatText, currentName, roomName;
    byte[] content;
    transient DatagramSocket client;
    transient DatagramPacket inPacket;
    transient DatagramPacket outPacket;
    InetAddress address;
    boolean firstLoad = true;
    boolean isCreator;
    int times = 0;
    boolean flag = true;
    private static final int MAX_LEN = 4096;
    private static final int PORT1 = 8888;
    private static final int PORT2 = 7777;

    public Client(String name) throws IOException {
        this.Name = name;
        this.isCreator = true;
//        content = new byte[4096];
        client = new DatagramSocket();
        address = InetAddress.getLocalHost();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(firstLoad);
//        objectOutputStream.writeObject(this.chattingRoom.text);
        objectOutputStream.writeObject(this);
        objectOutputStream.writeObject(isCreator);
        content = byteArrayOutputStream.toByteArray();
        outPacket = new DatagramPacket(content, content.length, address, Server.PORT);
        client.send(outPacket);
//        System.out.println(chattingRoom);
        byteArrayOutputStream.close();
        objectOutputStream.close();
//        inPacket = new DatagramPacket(content, content.length);
//        outPacket = new DatagramPacket(content, content.length, inPacket.getSocketAddress());
    }

    public Client(String name, String ip) throws IOException {//server的IP
        this.Name = name;
        this.isCreator = false;
//        content = new byte[4096];
        client = new DatagramSocket();
        address = InetAddress.getByName(ip);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(firstLoad);
//        objectOutputStream.writeObject(this.chattingRoom.text);
        objectOutputStream.writeObject(this);
        objectOutputStream.writeObject(isCreator);
        content = byteArrayOutputStream.toByteArray();
        outPacket = new DatagramPacket(content, content.length, address, Server.PORT);
        client.send(outPacket);
        byteArrayOutputStream.close();
        objectOutputStream.close();
    }

    public void sendDataToServer() throws IOException {
//        firstLoad = false;
//        socket = new DatagramSocket(PORT1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(firstLoad);
        objectOutputStream.writeObject(this.chattingRoom.text);//顺序不可倒
        objectOutputStream.writeObject(Name);
//        System.out.println(Name);
//        objectOutputStream.writeObject(this);
//        System.out.println(111);
//        objectOutputStream.writeObject(InetAddress.getLocalHost());
        content = byteArrayOutputStream.toByteArray();
        outPacket = new DatagramPacket(content, content.length, address, Server.PORT);
        client.send(outPacket);
        byteArrayOutputStream.close();
        objectOutputStream.close();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            byte[] chatContent = new byte[MAX_LEN];
            inPacket = new DatagramPacket(chatContent, chatContent.length);
            try {
                client.receive(inPacket);
                chatContent = inPacket.getData();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(chatContent);
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                int tip = (int) objectInputStream.readObject();
                if (tip == 0) {
                    chatText = (String) objectInputStream.readObject();
                    JOptionPane.showConfirmDialog(null, chatText, null, JOptionPane.CLOSED_OPTION);
                    new JoinRoom();
                } else if (tip == 1) {
                    if (firstLoad) {
                        roomName = (String) objectInputStream.readObject();
                        chattingRoom = new ChattingRoom(Name, this, roomName);
                        firstLoad = false;
                    } else {
                        roomName = (String) objectInputStream.readObject();
                    }
                    chatText = (String) objectInputStream.readObject();
                    String newName = (String) objectInputStream.readObject();
                    chatText = newName + chatText;
                    this.chattingRoom.addText(chatText);
                } else if (tip == 2) {
                    String newName = (String) objectInputStream.readObject();
                    chatText = (String) objectInputStream.readObject();
                    this.chattingRoom.addText(chatText, newName);
                } else if (tip == -1) {
//                    new Agreement();
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//                    objectOutputStream.writeObject(Agreement.agreement);
//                    byte[] serve = byteArrayOutputStream.toByteArray();
//                    outPacket = new DatagramPacket(serve, serve.length, address, Server.PORT);
//                    client.send(outPacket);
//                    byteArrayOutputStream.close();
//                    objectOutputStream.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
//        while (true) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            byte[] chatContent = new byte[MAX_LEN];
//            inPacket = new DatagramPacket(chatContent, chatContent.length);
//            try {
//                client.receive(inPacket);
//                if (times == 2) {
//                    firstLoad = false;
//                }
//                if (inPacket != null) {
//                    chatContent = inPacket.getData();
//                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(chatContent);
//                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//                    chatText = (String) objectInputStream.readObject();
//                    if (chatText.equals("加入成功") && flag) {
//                        chattingRoom = new ChattingRoom(Name, this);
//                        String newName = (String) objectInputStream.readObject();
//                        chatText = newName + chatText;
//                        this.chattingRoom.addText(chatText);
//                        flag = false;
//                    } else if ("房间已满".equals(chatText)) {
//                        JOptionPane.showConfirmDialog(null, "房间已满", null, JOptionPane.CLOSED_OPTION);
//                    }else if (chatText.equals("加入成功") && !flag) {
//                        String newName = (String) objectInputStream.readObject();
//                        chatText = newName + chatText;
//                        this.chattingRoom.addText(chatText);
//                        times--;
//                    }
//                    if (!firstLoad) {
//                        this.currentName = (String) objectInputStream.readObject();
////                        System.out.println(currentName);
//                        this.chattingRoom.addText(chatText, this.currentName);
//                    } else {
//                        if (times != 1 && times != 0) {
//                            this.chattingRoom.addText(chatText, this.Name);
//                        }
//                    }
////                    chatText = chatContent.toString();
////                    chatText = new String(chatContent, 0, chatContent.length);第二种接受UDP数据报方法
//                    byteArrayInputStream.close();
//                    objectInputStream.close();
//                    times++;
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
