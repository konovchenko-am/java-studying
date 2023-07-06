import java.io.*;
import java.net.Socket;


/**
 * Класс, описывающий представление клиента чата
 */
public class Client {
    static final int PORT = 1234;
    static final String SERVER_IP = "127.0.0.1";

    static String login = null;
    private Socket socket; //сокет для обмена информацией с сервером
    private BufferedReader consoleReader; // объект для чтения с консоли
    private BufferedReader receiveReader; // объект для чтения сообщений от сервера
    private BufferedWriter sendWriter; // объект для передачи сообщений на сервер
    private ReceiveMsgThread receiveMsgThread; // объект - поток получения сообщений от сервера
    private SendMsgThread sendMsgThread;    // объект - поток передачи сообщений на сервер

    /**
     * Конструктор
     */
    public Client() {
        try {
            socket = new Socket(SERVER_IP, PORT); // запрашиваем у сервера доступ на соединение
            // объект для получения сообщений с консоли
            consoleReader = new BufferedReader(new InputStreamReader(System.in));
            // объект для чтения сообщений с сервера
            receiveReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // объект для передачи сообщений на сервер
            sendWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            System.out.println("Успешно подключились к серверу: " + My.getSocketInfo(socket));
            System.out.println("Введите логин: ");
            login = consoleReader.readLine()+'\n';
            sendWriter.write(login);
            sendWriter.flush();

            sendMsgThread = new SendMsgThread();            // создаем поток для передачи сообщений
            receiveMsgThread = new ReceiveMsgThread();      // создаем поток для получения сообщений
            // запускаем созданные потоки
            sendMsgThread.start();
            receiveMsgThread.start();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Client(); // создаем объект класса Client, всё необходимое делается в конструкторе
    }

    /**
     * Класс представления потока чтения сообщений с сервера
     */
    public class ReceiveMsgThread extends Thread {
        @Override
        public void run() {

            String str;
            try {
                while (true) {
                    str = receiveReader.readLine(); // ждем сообщения с сервера
                    System.out.println("\rSERVER>> " + str); // получив сообщение, выводим на экран
                    System.out.print("LOCAL>> "); // выводим приглашение
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                ShutClient();
            }
        }
    }

    /**
     * Метод закрытия приложения - клиента
     */
    private void ShutClient(){
        System.out.println("Клиент был закрыт.");
        try {
            // закрываем все ресурсы ввода-вывода
            socket.close();
            receiveReader.close();
            sendWriter.close();
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Класс представления потока отправки сообщений на сервер
     */
    public class SendMsgThread extends Thread {

        @Override
        public void run() {
            while (true) {
                String userWord;
                System.out.print("LOCAL>> ");
                try {
                    userWord = consoleReader.readLine(); // получаем сообщение с консоли
                    if (userWord.equalsIgnoreCase("stop")) {
                        sendWriter.write("stop" + "\n");
                        break; // выходим из цикла если пришло "stop"
                    } else {
                        sendWriter.write(userWord + "\n"); // отправляем сообщение  на сервер
                    }
                    sendWriter.flush(); // форсируем передачу данных через сокет
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
            ShutClient();
        }
    }
}




