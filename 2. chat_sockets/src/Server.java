import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Коновченко Александр
 * Промежуточная аттестация. Модуль 1
 * Программа должна считывать с консоли сообщения неограниченного числа пользователей и
 * отображать его всем пользователям, которые есть в чате. При первом входе в чат, новый
 * пользователь должен увидеть всю историю сообщений.
 */

/**
 * Класс представления серверной части чата
 */
public class Server {

    public static final int PORT = 1234;                // номер порта сервера
    public static final int MAX_STORED_MSGS_AMOUNT = 5; // максимальное количество сохраненных сообщений (для передачи
                                                        // вновь подключившимся клиентам

    public static int storedMessagesAmount=0;   // текущее количество сохраненных сообщений
    // очередь для хранения сообщений
    private static final ConcurrentLinkedQueue<String> messagesQueue = new ConcurrentLinkedQueue<>();
    // список для хранения объектов-потоков работы с клиентами
    private static final ArrayList<ServerPerClientThread> serverThreadsList = new ArrayList<>(); // список всех нитей

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
                // открыли Socket
        System.out.println(My.getCurrentDateTime() + ": chat server has started.");
        new ShowWorkingThread();
        try {
            while (true) {
                Socket socket = server.accept(); // встаем в режим приема запросов на подключение
                serverThreadsList.add(new ServerPerClientThread(socket)); // добавляем новое соединение в список
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            server.close();
        }
    }

    /**
     * Геттер для объекта с очередью сохраненных сообщений
     * @return
     */
    public static ConcurrentLinkedQueue<String> getMessagesQueue() {
        return messagesQueue;
    }

    /**
     * Геттер для получения текущего количества сохраненных сообщений
     * @return
     */
    public static int getStoredMessagesAmount() {
        return storedMessagesAmount;
    }

    /**
     * Метод сохранения сообщений в очереди (для передачи вновь подключившимся клиентам)
     * @param message
     */
    public static void saveMessage(String message){
        if(storedMessagesAmount == MAX_STORED_MSGS_AMOUNT) {
            messagesQueue.remove();
        } else
            storedMessagesAmount++;
        messagesQueue.add(message);
        System.out.print("\r" + message);
        sendToAllClients(message);
    }

    /**
     * Метод отправки сообщений всем подключенным в настоящий момент клиентам
     * @param msg
     */
    public static void sendToAllClients(String msg){
        for (ServerPerClientThread serverPerClientThread : serverThreadsList) {
            serverPerClientThread.sendMessage(msg);
        }
    }
}

/**
 * Класс представления потока демонстрации работы сервера
 */
class ShowWorkingThread extends Thread{

    protected ShowWorkingThread(){
        start();   // запускаем поток в конструкторе объекта
    }

    @Override
    public void run() {
        char[] workChars = {'|','/','-','\\'};
        int i = 0;
        while(true){
            try {
                sleep(333);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            System.out.print("\r" + workChars[i]);
            i = i==3 ? 0: i+1;
        }
    }
}

/**
 * Класс представления потока работы с клиентом чата
 */
class ServerPerClientThread extends Thread {

    private String loginName;     // имя пользователя
    private final Socket socket;  // сокет для подключения к клиенту
    private final BufferedReader receiveSocketReader; // поток получения сообщений от клиента
    private final BufferedWriter sendSocketWriter; // поток передачи сообщений клиенту

    /**
     * Конструктор
     * @param socket
     * @throws IOException
     */
    public ServerPerClientThread(Socket socket) throws IOException {
        this.socket = socket;
        // создаем объект для получения сообщений от клиента
        receiveSocketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        // создаем объект для передачи сообщений клиенту
        sendSocketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start(); // вызываем run()
    }
    @Override
    public void run() {
        String message, savedMessage;
        try {
            loginName = receiveSocketReader.readLine();
            savedMessage = My.getCurrentTime() + ": client has connected " + loginName + " from address "
                    + My.getSocketInfo(socket) + "\n";
            sendMsgsHistory(Server.getMessagesQueue());
            Server.saveMessage(savedMessage);
            while (true) {
                message = receiveSocketReader.readLine();   // получаем сообщение от клиента
                if(message == null) {
                    savedMessage =  My.getCurrentTime() + ": client " + loginName +
                            " has abnormally closed connection." + "\n";
                    Server.saveMessage(savedMessage);       // сохраняем и отправляем сообщение
                    break;
                } else {
                    savedMessage = loginName + " " + My.getCurrentTime() + ": " + message + "\n";
                    Server.saveMessage(savedMessage);       // сохраняем и отправляем сообщение
                }
                if (message.equalsIgnoreCase("stop")) {
                    savedMessage = My.getCurrentTime() + ": client " + loginName +
                            " has left the chat." + "\n";
                    Server.saveMessage(savedMessage);       // сохраняем и отправляем сообщение
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                // закрываем все потоки ввода-вывода
                receiveSocketReader.close();
                sendSocketWriter.close();
                socket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Метод отправки одного сообщения одному клиенту
     * @param msg
     */

    protected void sendMessage(String msg) {
        try {
            sendSocketWriter.write(msg);
            sendSocketWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод отправки истории сообщений одному клиенту
     * @param messagesQueue
     */
    protected void sendMsgsHistory(ConcurrentLinkedQueue<String> messagesQueue){
        try {
            sendSocketWriter.write("The previous last " + Server.getStoredMessagesAmount() + " messages:\n");
            for (String message : messagesQueue) {
                sendSocketWriter.write(message);    // отправляем все сообщения - элементы очереди сообщений
            }
            sendSocketWriter.write("\n");
            sendSocketWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
