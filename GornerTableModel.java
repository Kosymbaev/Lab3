package bsu.rfe.java.group10.lab3.Kosymbaev.varB12;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {


    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step,
                            Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return Double.valueOf(Math.ceil((to - from) / step)).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
// Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ*НОМЕР_СТРОКИ
        double x = from + step*row;


        double result = 0;
        int i = coefficients.length - 1;
        result = coefficients[i--];
        while (i >= 0) {
            result = result * x + coefficients[i--];
        }
        // проверка на простое
        boolean f = false;
        int temp;
        int tempx;
        int prost = (int) result;
        int prostx = (int) x;


        if (col==0) {
            return x;
        } else if (col==1){
            return result;
        }else if(col == 2){
            for (int m=2; m<=prost/2 && m<=prostx/2; m++) {
                temp = prost % m;
                tempx=prostx%m;
                if (temp == 0 && tempx==0) {
                    f = false;
                    break;
                } else f = true;

            }
            if(f == false){
                return false;
            }else return true;
        }else return 0;

    }
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            default:
                return "Взаимно простое число?";
        }
    }
    public Class<?> getColumnClass(int col) {
        if(col == 1 || col == 0){
            return Double.class;
        }else return Boolean.class;
    }

}