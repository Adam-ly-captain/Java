package Chat;

import java.io.*;
import java.net.*;

public class Server implements Runnable, Serializable {
    public static final int PORT = 18888;
    private static final int MAX_LEN = 1000000;
    private static int MAX_CLIENT_NUMBER;
    //    byte[] content = new byte[MAX_LEN];//接收数据
    byte[] serverContent = new byte[MAX_LEN];//接收数据
    int lineHeight = 0;
//    ChattingRoom chattingRoom;
    transient DatagramSocket server;
    transient DatagramPacket inPacket;
    transient DatagramPacket outPacket;
    InetAddress[] clientAddress;
    int[] clientPort;
    int clientNumber = 0;
    String chatContent;
    Client client;
    Client creatorClient;
    boolean clientFirstLoad;
    String creatorName;
    String userName;
    InetAddress creatorAddress;
    int tip;
    boolean isCreator;
    int agreement;
    String roomName;
    int isNameRepeat = 0;
    protected static String[] clientName;
//    HashSet<Client> clients;

    public Server(String name, String roomKind, InetAddress address, String roomName, int maxNumber) throws SocketException, UnknownHostException {
//        InetAddress address = InetAddress.getByName("www.lsh0602.xyz");
//        chattingRoom = new ChattingRoom(name);
        server = new DatagramSocket(PORT);
        if ("私人房间".equals(roomKind)) {
            MAX_CLIENT_NUMBER = 2;
        } else if ("多人房间".equals(roomKind)) {
            MAX_CLIENT_NUMBER = maxNumber;
        }
        clientName = new String[MAX_CLIENT_NUMBER];
        this.roomName = roomName;
//        System.out.println(MAX_CLIENT_NUMBER);
        clientAddress = new InetAddress[MAX_CLIENT_NUMBER];
        clientPort = new int[MAX_CLIENT_NUMBER];
        creatorName = name;//房主名字
        creatorAddress = address;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                inPacket = new DatagramPacket(serverContent, serverContent.length);
                server.receive(inPacket);
//                System.out.println(1);
                if (inPacket.getData() != null) {
                    byte[] chatContentByte = inPacket.getData();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(chatContentByte);
                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//                    chatContent = (String) objectInputStream.readObject();
                    Object object;
                    object = objectInputStream.readObject();
                    if (object instanceof Boolean) {
                        clientFirstLoad = (boolean)object;
                    } else if (object instanceof Integer) {
                        isNameRepeat = (int) object;
                    }
                    if (isNameRepeat == 0 && clientFirstLoad && clientNumber >= MAX_CLIENT_NUMBER) {
                        tip = 0;
                        byteArrayInputStream.close();
                        objectInputStream.close();
//                        byte[] content = inPacket.getData();
//                        ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(content);
//                        ObjectInputStream objectInputStream1 = new ObjectInputStream(byteArrayInputStream1);
//                    ChattingRoom chattingRoomLast = (ChattingRoom) objectInputStream.readObject();
//                    boolean clientFirstLoad = (boolean) objectInputStream.readObject();//这两行有待商榷
//                    String ip = (String) objectInputStream.readObject();
                        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(byteArrayOutputStream1);
                        objectOutputStream1.writeObject(tip);
                        objectOutputStream1.writeObject("房间已满");
                        byte[] sendContent = byteArrayOutputStream1.toByteArray();
                        InetAddress address = inPacket.getAddress();
                        int Port = inPacket.getPort();
                        outPacket = new DatagramPacket(sendContent, sendContent.length, address, Port);
                        server.send(outPacket);
//                        byteArrayInputStream1.close();
//                        objectInputStream1.close();
                        byteArrayOutputStream1.close();
                        objectOutputStream1.close();
                    } else if (isNameRepeat == 0 && clientNumber <= MAX_CLIENT_NUMBER) {
//                    int length = inPacket.getLength();
                        if (clientFirstLoad) {
                            tip = 1;
                            client = (Client) objectInputStream.readObject();
                            isCreator = (boolean) objectInputStream.readObject();
//                        clients.add(client);

                            byteArrayInputStream.close();
                            objectInputStream.close();
                            if (isCreator) {
                                clientAddress[clientNumber] = inPacket.getAddress();
                                clientPort[clientNumber] = inPacket.getPort();
                                clientName[clientNumber] = client.Name;
                                clientNumber++;
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                                objectOutputStream.writeObject(tip);
                                objectOutputStream.writeObject(roomName);
                                objectOutputStream.writeObject("加入成功");
                                objectOutputStream.writeObject(client.Name);//传输第一次连接提示
                                byte[] client = byteArrayOutputStream.toByteArray();
                                for (int i = 0; i < clientNumber; i++) {
                                    outPacket = new DatagramPacket(client, client.length, clientAddress[i], clientPort[i]);
                                    server.send(outPacket);
                                }
                                byteArrayOutputStream.close();
                                objectOutputStream.close();
                            } else {
//                                tip = -1;
                                Agreement agreement1 = new Agreement(client.Name);
                                while (true) {
                                    if (agreement1.agreement != -1) {
                                        agreement = agreement1.agreement;
                                        break;
                                    }
                                }
//                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//                                objectOutputStream.writeObject(tip);
//                                byte[] serverContent = byteArrayOutputStream.toByteArray();
//                                DatagramPacket outpacket1 = new DatagramPacket(serverContent, serverContent.length, creatorAddress, Server.PORT);
//                                server.send(outpacket1);
//                                while (true) {
//                                    try {
//                                        Thread.sleep(1000);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
//                                    byte[] serverContent1 = new byte[MAX_LEN];
//                                    DatagramPacket inpacket1 = new DatagramPacket(serverContent1, serverContent1.length);
//                                    server.receive(inpacket1);
//                                    byte[] serve = inpacket1.getData();
//                                    ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(serve);
//                                    ObjectInputStream objectInputStream1 = new ObjectInputStream(byteArrayInputStream1);
//                                    agreement = (boolean) objectInputStream1.readObject();
//                                    byteArrayOutputStream.close();
//                                    objectOutputStream.close();
//                                    byteArrayInputStream1.close();
//                                    objectInputStream1.close();
//                                    break;
//                                }
                                if (agreement == 1){
                                    clientAddress[clientNumber] = inPacket.getAddress();
                                    clientPort[clientNumber] = inPacket.getPort();
                                    clientName[clientNumber] = client.Name;
                                    clientNumber++;
                                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                                    ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(byteArrayOutputStream1);
                                    objectOutputStream1.writeObject(tip);
                                    objectOutputStream1.writeObject(roomName);
                                    objectOutputStream1.writeObject("加入成功");
                                    objectOutputStream1.writeObject(client.Name);//传输第一次连接提示
                                    byte[] client = byteArrayOutputStream1.toByteArray();
                                    for (int i = 0; i < clientNumber; i++) {
                                        outPacket = new DatagramPacket(client, client.length, clientAddress[i], clientPort[i]);
                                        server.send(outPacket);
                                    }
                                    byteArrayOutputStream1.close();
                                    objectOutputStream1.close();
                                } else {
                                    tip = 0;
                                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                                    ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(byteArrayOutputStream1);
                                    objectOutputStream1.writeObject(tip);
                                    objectOutputStream1.writeObject("你被房猪拒绝加入");
                                    byte[] sendContent = byteArrayOutputStream1.toByteArray();
                                    InetAddress address = inPacket.getAddress();
                                    int Port = inPacket.getPort();
                                    outPacket = new DatagramPacket(sendContent, sendContent.length, address, Port);
                                    server.send(outPacket);
//                        byteArrayInputStream1.close();
//                        objectInputStream1.close();
                                    byteArrayOutputStream1.close();
                                    objectOutputStream1.close();
                                }
                            }
                        } else {
                            tip = 2;
                            chatContent = (String) objectInputStream.readObject();
                            userName = (String) objectInputStream.readObject();
//                            System.out.println(userName);
                            byteArrayInputStream.close();
                            objectInputStream.close();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                            objectOutputStream.writeObject(tip);
                            objectOutputStream.writeObject(userName);
                            objectOutputStream.writeObject(chatContent);//传输聊天内容
                            byte[] sendContent = byteArrayOutputStream.toByteArray();
                            for (int i = 0; i < clientNumber; i++) {
                                if (inPacket.getPort() != clientPort[i] && inPacket.getAddress() != clientAddress[i]) {
                                    outPacket = new DatagramPacket(sendContent, sendContent.length, clientAddress[i], clientPort[i]);
                                    server.send(outPacket);
                                }
                            }
                            byteArrayOutputStream.close();
                            objectOutputStream.close();
                        }
//                    clientFirstLoad = (boolean) objectInputStream.readObject();
//                    destIp[clientNum] = (String) objectInputStream.readObject();
                    } else if (isNameRepeat == 1) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                        objectOutputStream.writeObject(clientName);
                        byte[] nameRepeat = byteArrayOutputStream.toByteArray();
                        DatagramPacket packet = new DatagramPacket(nameRepeat, nameRepeat.length, inPacket.getAddress(), inPacket.getPort());
                        server.send(packet);
                        byteArrayOutputStream.close();
                        objectOutputStream.close();
                        isNameRepeat = 0;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}