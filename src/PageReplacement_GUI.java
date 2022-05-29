import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;


public class PageReplacement_GUI extends JFrame {
    private JTextField ReferenceString_textField;
    private JTextField Frame_textField;
    private chart chart;
    private CircleChart CircleChart;
    private DefaultTableModel OutputModel;
    public PageReplacement_GUI(PageReplacement PageReplacement) {
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        String Policy[] = {"FIFO","Optimal","LRU","Second Chance","LRU Pool"};
        JComboBox comboBox = new JComboBox(Policy);
        comboBox.setBounds(12, 10, 235, 47);
        getContentPane().add(comboBox);

        chart = new chart();
        chart.setPreferredSize(new Dimension(600,400));
        JScrollPane scroll = new JScrollPane(chart, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);  // 스크롤패널을 선언
        scroll.setBounds(12,60,570,400);

        getContentPane().add(scroll);

        ReferenceString_textField = new JTextField();
        ReferenceString_textField.setBounds(259, 36, 240, 21);
        getContentPane().add(ReferenceString_textField);
        ReferenceString_textField.setColumns(10);

        Frame_textField = new JTextField();
        Frame_textField.setBounds(511, 36, 68, 21);
        getContentPane().add(Frame_textField);
        Frame_textField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Reference String");
        lblNewLabel.setBounds(333, 10, 100, 16);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Frame");
        lblNewLabel_1.setBounds(511, 10, 68, 15);
        getContentPane().add(lblNewLabel_1);

        String[] OutputColumn = {"Char", "Hit or Fault"};
        OutputModel = new DefaultTableModel(OutputColumn, 0);
        JTable Output_table = new JTable(OutputModel);
        Output_table.getTableHeader().setReorderingAllowed(false);
        Output_table.getTableHeader().setResizingAllowed(false);
        Output_table.setEnabled(false);
        JScrollPane outputTableScrollPane = new JScrollPane(Output_table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outputTableScrollPane.setBounds(591, 60, 180, 150);
        getContentPane().add(outputTableScrollPane);

        CircleChart = new CircleChart();
        CircleChart.setBounds(591, 220, 180, 200);
        getContentPane().add(CircleChart);

        JButton Random_button = new JButton("Random");
        Random_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int leftLimit = 65;
                int rightLimit = 90;

                int targetStringLength = (int)((Math.random()*10000)%25)+10;
                Random random = new Random();
                StringBuilder buffer = new StringBuilder(targetStringLength);
                for (int i = 0; i < targetStringLength; i++) {
                    int randomLimitedInt = leftLimit + (int)
                            (random.nextFloat() * (rightLimit - leftLimit + 1));
                    buffer.append((char) randomLimitedInt);
                }
                ReferenceString_textField.setText(buffer.toString());
            }
        });
        Random_button.setBounds(591, 10, 90, 47);
        getContentPane().add(Random_button);

        JButton Run_button = new JButton("Run");
        Run_button.setBounds(692, 10, 80, 47);
        Run_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String combo_String = comboBox.getSelectedItem().toString();
                String frame = Frame_textField.getText();
                String reference_string = ReferenceString_textField.getText();
                ArrayList<PageResult> result = null;
                switch (combo_String) {
                    case "FIFO":
                        result = PageReplacement.FIFO(reference_string,Integer.parseInt(frame));
                        break;
                    case "Optimal":
                        result = PageReplacement.Optimal(reference_string,Integer.parseInt(frame));
                        break;
                    case "LRU":
                        result = PageReplacement.LRU(reference_string,Integer.parseInt(frame));
                        break;
                    case "Second Chance":
                        result = PageReplacement.Second_Chance(reference_string,Integer.parseInt(frame));
                        break;
                    case "LRU Pool":
                        result = PageReplacement.LRUPool(reference_string,Integer.parseInt(frame));
                        break;
                    default:
                        break;
                }

                int pagefault_num=0;
                for(PageResult PageResult : result){
                    if(PageResult.isPageFault()){
                        OutputModel.addRow(new Object[]{PageResult.getReference_Char(),"Fault"});
                        pagefault_num++;
                    }
                    else{
                        OutputModel.addRow(new Object[]{PageResult.getReference_Char(),"Hit"});
                    }
                }
                CircleChart.setpage(result.size(),pagefault_num,result.size()-pagefault_num);
                chart.setResult(result);
                chart.repaint();
                CircleChart.repaint();;
                repaint();
            }
        });
        getContentPane().add(Run_button);
    }

    public static void main(String[] args) {

        PageReplacement_GUI frame = new PageReplacement_GUI(new PageReplacement());
        frame.setVisible(true);

    }
}

class chart extends JPanel{
    private ArrayList<PageResult> result;
    public void paint(Graphics g){
        super.paint(g);
        int x = 0;
        int y = 0;
        int jump = 30;
        int xjump = jump + 3;
        int yjump = jump + 1;
        int maxy = 0;
        if(result!=null){
            for(PageResult PageResult : result){
                g.drawRect(x,y,30,30);
                g.drawString(Character.toString(PageResult.getReference_Char()),x+13,y+17);
                y += jump*2;
                for(char c : PageResult.getResult()){
                    if(c==PageResult.getReference_Char()){
                        if(PageResult.isPageFault()){
                            g.setColor(Color.RED);
                            g.drawRect(x,y,30,30);
                            g.drawString(Character.toString(c),x+13,y+17);
                            y += yjump;
                            g.setColor(Color.BLACK);
                        }
                        else{
                            g.setColor(Color.blue);
                            g.drawRect(x,y,30,30);
                            g.drawString(Character.toString(c),x+13,y+17);
                            y += yjump;
                            g.setColor(Color.BLACK);
                        }
                    }
                    else{
                        g.drawRect(x,y,30,30);
                        g.drawString(Character.toString(c),x+13,y+17);
                        y += yjump;
                    }
                }
                if(maxy<y){
                    maxy = y;
                }
                x+=xjump;
                y = 0;
            }
            setPreferredSize(new Dimension(x,maxy));
            updateUI();
        }
    }

    public void setResult(ArrayList<PageResult> result) {
        this.result = result;
    }
}

class CircleChart extends JPanel{
    private int whole=0;
    private int pagefault_num;
    private int pagehit_num;

    public void paint(Graphics g){
        super.paint(g);
        if(whole!=0){
            int fault_angle = (int) (3.6*((double) pagefault_num / (double) whole) * 100);
            int hit_angle = (int) (3.6*((double)  pagehit_num/ (double) whole) * 100);
            g.setColor(Color.red);
            g.fillArc(15, 0, 150, 150, 0, fault_angle);
            g.drawString("Fault : "+pagefault_num,15,165);
            g.setColor(Color.blue);
            g.fillArc(15, 0, 150, 150,fault_angle, hit_angle);
            g.drawString("Hit : "+pagehit_num,80,165);
            String Fault_rate = Double.toString(Math.round((double)pagefault_num/(double)whole*100)/100.0*100);
            g.setColor(Color.black);
            g.drawString("Page Fault Rate (%) = "+Fault_rate,15,185);
        }
    }

    public void setpage(int whole,int pagefault_num,int pagehit_num) {
        this.whole = whole;
        this.pagefault_num = pagefault_num;
        this.pagehit_num = pagehit_num;
    }
}
