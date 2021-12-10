package bsu.rfe.java.group10.lab3.Kosymbaev.varB12;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();

    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble =
                formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');

        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        String formattedDouble = formatter.format(value);

        label.setText(formattedDouble);
        System.out.println(value);
        double num = Double.valueOf(formattedDouble);
        int f = (int) num;
        boolean t = false;
        num = num - f;
        num*=1000;
        f=(int)num;
        num-=f;
        if (num==0) t =true;

        if ((col==1 || col==0) && needle!=null && needle.equals(formattedDouble)) {
            panel.setBackground(Color.RED);
        } else if(t == true && col == 1){
            panel.setBackground(Color.BLUE);
            t = false;
        }else panel.setBackground(Color.white);
        return panel;
    }
    public void setNeedle(String needle) {
        this.needle = needle;
    }
}
