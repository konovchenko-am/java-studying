import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Основной класс приложения Calculator, наследуется от JFrame для создания GUI
 */
public class Calculator extends JFrame {
    private final JFormattedTextField digitsFTF;   // поле для ввода и отображения цифр
    private double number1 = 0, number2 = 0;   // переменные для хранения операндов
    private int operationCode;                 // код матем. операции
    private static final Color backgroundColor = Color.DARK_GRAY;  // константа - цвет фона для всех элементов

    private boolean isResultDisplayed = false;  // флаг "на экране - результат"
                                                // (чтобы при вводе цифр начать вводить новое число)
    private String currOperation;               // строка - текущая операция
    private boolean isStartedOperation;         // флаг "операция начата" (введено первое число и знак операции -
                                                // чтобы не реагировать на ввод других операций до завершения текущей
    private final static int MAX_DIGITS_AMOUNT=12;  // количество символов на экране калькулятора

    /**
     * Конструктор
     * @throws IOException
     * @throws FontFormatException
     */
    Calculator() throws IOException, FontFormatException {
        String[] buttonsTitles = {"π", "CE", "C", "BS", "1/x", "x²", "²√x", "/", "7", "8",
                "9", "*", "4", "5", "6", "-", "1", "2", "3", "+", "+/-", "0", ".", "="};

        var container = getContentPane();
        var digitsPanel = new JPanel();             // панель для экрана для вывода цифр
        var buttonsPanel = new JPanel();            // панель для кнопок
        var emptyPanel = new JPanel();              // пустая панель для коррекции внешнего вида
        emptyPanel.add(new JLabel(" "));        // тоже для красоты
        emptyPanel.setBackground(backgroundColor);  // выставляем заданный цвет фона
        digitsPanel.setBackground(backgroundColor);

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS)); // в контейнере по вертикали разместим три панели
        digitsPanel.setLayout(new FlowLayout());    // в панели для вывода цифр свободное размещение одного текстового поля
        // в панели для вывода кнопок размещение таблицой: 6 рядов и 4 столбца
        buttonsPanel.setLayout(new GridLayout(6,4,5,5));
        buttonsPanel.setBackground(backgroundColor);   // выставляем заданный цвет фона

        Listener listener = new Listener();            // объект своего обработчика событий

        var menuBar = new JMenuBar();                  // строка меню
        JMenu fileMenu = new JMenu("Файл");         // пункт меню "Файл"
        JMenu refMenu = new JMenu("Справка");       // пункт меню "Справка
            // Определяем шрифт и назначаем его для пунктов меню
        var menuFont = new Font("arial",Font.PLAIN,12);
        fileMenu.setFont(menuFont);
        refMenu.setFont(menuFont);

        menuBar.setBackground(backgroundColor);
        fileMenu.setForeground(Color.LIGHT_GRAY);
        refMenu.setForeground(Color.LIGHT_GRAY);

        var aboutAppMenuItem = new JMenuItem("О программе");   // пункт меню "О программе
        aboutAppMenuItem.setFont(menuFont);
        aboutAppMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // при выборе пункта меню "О программе" выводим информацию о приложении
                JOptionPane.showMessageDialog(null, "Приложение \"Калькулятор 2.0\".\nАкадемия АЙТИ, г. Москва.\n" +
                        "Герасименко С.В., Коновченко А.М.");
            }
        });
        var exitMenuItem = new JMenuItem("Выход");  // пункт меню выход из приложения
        exitMenuItem.setFont(menuFont);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);                     // при выборе этого пункта выходим из программы
            }
        });
        fileMenu.add(exitMenuItem);         // к меню "Файл" добавляем пункт меню "Выход"
        refMenu.add(aboutAppMenuItem);      // к меню "Справка" добавляем пункт меню "О программе"
        menuBar.add(fileMenu);              // к строке меню добавляем меню "Файл"
        menuBar.add(refMenu);               // к строке меню добавляем меню "Справка"
        setJMenuBar(menuBar);               // к текущему объекту класса Calculator (потомок JFrame) добавляем меню

        // отрисовка поля ввода и вывода цифр с загрузкой соответсвующего шрифта
        digitsFTF = new JFormattedTextField(new NumberFormatter(new DecimalFormat("##0.###")));
        digitsFTF.setColumns(MAX_DIGITS_AMOUNT);
        digitsFTF.setHorizontalAlignment(JTextField.RIGHT);
        File fontFile = new File("./src/Digital7Monoitalic-8MDLJ.ttf");
        URL fontUrl = fontFile.toURI().toURL();
        Font digitFont = Font.createFont(Font.TRUETYPE_FONT, fontUrl.openStream());
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(digitFont);
        digitsFTF.setFont(new Font("Digital-7 MonoItalic", Font.ITALIC, 60));
        digitsFTF.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, backgroundColor));
        // digitsTField.setBounds(300, 10, 300, 35);       // на размер элемента не влияет
        digitsPanel.add(digitsFTF);

        JButton[] buttons = new JButton[24];                // массив из 24 кнопок
        var font = new Font("arial", Font.BOLD, 20);

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(font);//задаем шрифт текста в кнопке
            //buttons[i].setLocation(100, 50 + 30 * i);  // задаем отступы у кнопок - не влияет на размеры
            //buttons[i].setSize(150, 25);               // размер кнопки - не влияет на размеры
            buttons[i].setBackground(Color.BLACK);       // цвет фона кнопок
            buttons[i].setForeground(Color.LIGHT_GRAY);  // цвет текста кнопок
            buttons[i].setText(buttonsTitles[i]);        // выставляем наименования кнопок

            buttons[i].addActionListener(listener);      // добавляем слушателя события

            buttonsPanel.add(buttons[i]);                //размещаем каждую кнопку в панельке
        }
        digitsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);  // равняем панель с цифрами по центру
        container.add(digitsPanel);                        // добавляем панель с цифрами в контейнер
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // равняем панель с кнопками по центру
        container.add(buttonsPanel);                       // добавляем панель с кнопками в контейнер
        container.add(emptyPanel);                         // добавляем для красоты пустую панель в контейнер

        //container.setSize(600, 600);              // на размер по факту не влияет
        setTitle("Калькулятор v2.0");               // заголовок нашего окна

        setLocationRelativeTo(null);
        setResizable(false);                        //запрещаем менять размер окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //закрытие по крестику
        pack();
        // для красоты отрисовываем границу окна
        getRootPane().setBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, backgroundColor));
        setVisible(true);       // показываем что получилось
    }

    /**
     * Класс с обработкой нажатий на кнопки
     */
    class Listener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource(); //получили кнопку, на которую был клик
            String buttonText = button.getText();

            switch (buttonText){
                case "π":       // вывод числа Пи для последующих операций
                    digitsFTF.setText("3.1415926535");
                    break;
                case "CE":      // сброс
                    digitsFTF.setText("");//очищаем текстовое поле
                    number1 = 0;
                    number2 = 0;
                    isStartedOperation = false;
                    break;
                case "C":       // сброс
                    digitsFTF.setText("");//очищаем текстовое поле
                    break;
                case "BS":      // backspace - удаление последнего введенного симовла
                    String currDigitsFTF = digitsFTF.getText();
                    if(currDigitsFTF.length()>0)
                        digitsFTF.setText(currDigitsFTF.substring(0, currDigitsFTF.length() - 1));
                    break;

                case "=":       // по нажатию на = выполняем операцию
                    performOperation();
                    isStartedOperation = false;
                    break;
                // обработка операций с одним аргументом
                case "1/x":
                case "x²":
                case "²√x":
                    if(!isStartedOperation){   // если не начата другая операция
                        operationCode = getOperationCode(buttonText);  // получаем код операции по строке из названия кнопки
                        if (operationCode == -1) {
                            digitsFTF.setText("");
                            break;
                        }
                        performOneArgOperation();   // выполняем операцию с одним аргументом
                        isStartedOperation = false; // сбрасываем флаг начатой операции
                    }
                    break;
                // обработка операций с двумя аргументами
                case "+":
                case "-":
                case "*":
                case "/":
                    if(!isStartedOperation){
                        operationCode = getOperationCode(buttonText);
                        if (operationCode != -1)
                            digitsFTF.setText(currOperation);
                        else
                            digitsFTF.setText("");
                    }
                    break;
                case "+/-":
                    if(isNumeric(digitsFTF.getText()))
                        digitsFTF.setText(trimNumber(-Double.parseDouble(digitsFTF.getText())));
                    break;
                //case :
                  //  break;
                default: // остались необработанными только цифры
                    boolean isCurrDigits = isNumeric(digitsFTF.getText());   // узнаем содержимое экрана
                    if(isResultDisplayed){
                        digitsFTF.setText(buttonText);
                        isResultDisplayed = false;
                    } else if (isCurrDigits) {          // если на экране сейчас число
                        digitsFTF.setText(digitsFTF.getText() + buttonText); //клик на цифру
                    } else {
                        digitsFTF.setText(buttonText);
                    }
            }
        }
    }

    /**
     * Метод проверки является ли строка числом
     * @param str строка для проверки
     * @return true - если аргумент число, и false в обратном случае
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    /**
     * Метод приведения числа к формату, который можно разместить в окне вывода цифр
     * @param myDouble
     * @return String - строковое представление числа
     */
    public static String trimNumber(Double myDouble){
        String resultStrNum;
        Integer resultInt;
        // убераем лишний 0, если результат без дробной части
        if(myDouble % 1 == 0) {
            resultInt = myDouble.intValue();
            resultStrNum = resultInt.toString();
        } else // если есть дробная часть возвращаем как есть
            resultStrNum = myDouble.toString();

        if (resultStrNum.length() > MAX_DIGITS_AMOUNT) // если получилось слишком длинное число
            return resultStrNum.substring(0, MAX_DIGITS_AMOUNT);   // обрезаем и возвращаем обрезанную строку
        else
            return resultStrNum;    // иначе возвращаем как есть
    }

    /**
     * Метод получения кода операции
     * @param code строковое представление операции
     * @return int код операции
     */
    private int getOperationCode(String code){
        int returnValue;
        switch (code) {
            case "+":
                currOperation = "+";
                returnValue = 1;  // сложение это первая операция
                break;
            case "-":
                currOperation = "-";
                returnValue = 2;  // вычитание это вторая операция
                break;
            case "*":
                currOperation = "*";
                returnValue = 3;  // умножение это третья операция
                break;
            case "/":
                currOperation = "/";
                returnValue = 4;  // деление это четвертая операция
                break;
            case "1/x":
                returnValue = 5;  // деление это четвертая операция
                break;
            case "x²":
                returnValue = 6;  // деление это четвертая операция
                break;
            case "²√x":
                returnValue = 7;  // деление это четвертая операция
                break;
            default:
                currOperation = "";
                isStartedOperation = false;
                returnValue = -1;
        }
        try {
            number1 = Double.parseDouble(digitsFTF.getText());
            isStartedOperation = true;
        } catch (NumberFormatException numberFormatException) {
            isStartedOperation = false;
            return -1;
        }
        return returnValue;
    }

    /**
     * Метод выполнения операции с двумя аргументами
     */
    private void performOperation(){
        try {
            number2 = Double.parseDouble(digitsFTF.getText());
        } catch (NumberFormatException numberFormatException) {
            digitsFTF.setText("");
            return;
        }

        switch (operationCode) {
            case 1:
                digitsFTF.setText(trimNumber(number1 + number2));
                isResultDisplayed = true;
                break;
            case 2:
                digitsFTF.setText(trimNumber(number1 - number2));
                isResultDisplayed = true;
                break;
            case 3:
                digitsFTF.setText(trimNumber(number1 * number2));
                isResultDisplayed = true;
                break;
            case 4:
                if (number2 == 0) {
                    JOptionPane.showMessageDialog(null, "На 0 делить нельзя!");
                    digitsFTF.setText("");
                    isResultDisplayed = false;
                } else {
                    digitsFTF.setText(trimNumber(number1 / number2));
                    isResultDisplayed = true;
                }
                break;
            default:
                digitsFTF.setText("");
                isResultDisplayed = false;
        }

    }

    /**
     * Метод выполнения операций с одним аргументом (есть отличия!)
     */
    private void performOneArgOperation(){
        try {
            number2 = Double.parseDouble(digitsFTF.getText());
        } catch (NumberFormatException numberFormatException) {
            digitsFTF.setText("");
            return;
        }

        switch (operationCode) {
            case 5:         // операция 1/х
                if (number1 == 0) {
                    JOptionPane.showMessageDialog(null, "На 0 делить нельзя!");
                    digitsFTF.setText("");
                    isResultDisplayed = false;
                } else {
                    digitsFTF.setText(trimNumber( 1 / number1));
                    isResultDisplayed = true;
                }
                break;
            case 6:         // x^2
                digitsFTF.setText(trimNumber(Math.pow(number1, 2)));
                isResultDisplayed = true;
                break;
            case 7:         // корень квадратный
                digitsFTF.setText(trimNumber(Math.pow(number1, 0.5)));
                isResultDisplayed = true;
                break;

            default:        // если неизвестная операция - результат пустая строка
                digitsFTF.setText("");
                isResultDisplayed = false;
        }

    }

}
