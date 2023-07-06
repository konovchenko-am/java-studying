import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Класс My с полезными статическими методами
 */
public class My {
    /**
     * Метод возвращает текущие дату и время в формате dd.MM.yyyy HH:mm:ss
     * @return
     */
    static String getCurrentDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(new GregorianCalendar().getTime());
    }

    /**
     * Метод возвращает текущее время в формате HH:mm:ss
     * @return
     */
    static String getCurrentTime(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new GregorianCalendar().getTime());
    }

    /**
     * Метод возвращает IP адрес и порт, сопоставленные заданному сокету
     * @param socket
     * @return
     */
    static String getSocketInfo(Socket socket){
        return socket.getInetAddress() + ":" + socket.getPort();
    }
}
