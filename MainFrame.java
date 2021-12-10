package bsu.rfe.java.group10.lab3.Kosymbaev.varB12;

import com.sun.jdi.DoubleValue;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.ActionListener;
import java.io.*;

public class MainFrame extends JFrame{
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;

    private Double[] coefficients;

    private JFileChooser fileChooser = null;

    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem aboutProgram;

    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;

    private Box hBoxResult;

    private GornerTableCellRenderer renderer = new
            GornerTableCellRenderer();

    private GornerTableModel data;

    protected void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);
            out.println("Результаты табулирования многочлена по схеме Горнера");
            out.print("Многочлен: ");
            for (int i = 0; i < coefficients.length; i++) {
                out.print(coefficients[i] + "*X^" +
                        (coefficients.length - i - 1));
                if (i != coefficients.length - 1)
                    out.print(" + ");
            }
            out.println("");
            out.println("Интервал от " + data.getFrom() + " до " +
                    data.getTo() + " с шагом " + data.getStep());
            out.println("====================================================");
            for (int i = 0; i < data.getRowCount(); i++) {
                out.println("Значение в точке " + data.getValueAt(i, 0)
                        + " равно " + data.getValueAt(i, 1));
            }
            out.close();
        } catch (FileNotFoundException e) {
        }
    }

        protected void saveToGraphicsFile(File selectedFile) {
        try {
            DataOutputStream out = new DataOutputStream(new
                    FileOutputStream(selectedFile));
            for (int i = 0; i<data.getRowCount(); i++) {
                out.writeDouble((Double)data.getValueAt(i,0));
                out.writeDouble((Double)data.getValueAt(i,1));
            }
            out.close();
        } catch (Exception e) {
        }
    }


    public MainFrame(Double[] coefficients) {
        super("Табулирование многочлена на отрезке по схеме Горнера");
        this.coefficients = coefficients;
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);
        JMenu referenceMenu = new JMenu("Справка");
        menuBar.add(referenceMenu);
        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            public void actionPerformed(ActionEvent ev){
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                    saveToTextFile(fileChooser.getSelectedFile());
                }
            }

        };
        saveToTextMenuItem = fileMenu.add(saveToTextAction);
        saveToTextMenuItem.setEnabled(false);
        Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(MainFrame.this) ==
                        JFileChooser.APPROVE_OPTION);

                saveToGraphicsFile(fileChooser.getSelectedFile());
            }
        };
        saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        saveToGraphicsMenuItem.setEnabled(false);
        Action searchValueAction = new AbstractAction("Найти значение многочлена") {
            public void actionPerformed(ActionEvent event) {
// Запросить пользователя ввести искомую строку
                String value =JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска",
                        "Поиск значения", JOptionPane.QUESTION_MESSAGE);
                renderer.setNeedle(value);
                getContentPane().repaint();
            }
        };
        searchValueMenuItem = tableMenu.add(searchValueAction);
        searchValueMenuItem.setEnabled(false);

        Action aboutProgramAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainFrame.this, "Косымбаев 10 группа");
            }
        };
        aboutProgram = referenceMenu.add(aboutProgramAction);
        aboutProgram.setEnabled(true);

    }

    public static void main(String[] args) {
        if (args.length==0) {
            System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
            System.exit(-1);
        }
        Double[] coefficients = new Double[args.length];
        int i = 0;
        try {
            for (String arg: args) {
                coefficients[i++] = Double.parseDouble(arg);
            }
        }
        catch (NumberFormatException ex) {
            System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double");
            System.exit(-2);
        }
        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
