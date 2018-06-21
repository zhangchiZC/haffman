package GUI;

/**
 * Created by ZC on 2017/12/21.
 */
import compressFile.WriteFile;
import decompressFile.ReadFile;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class View {

    private JFrame frmZc;
    private JTextField compressPathField;
    private JTextField storePathField;
    JFileChooser chooser;
    JFileChooser chooser2;
   public  static String storePath;
    public static String findPath;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    View window = new View();
                    window.frmZc.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public View() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmZc = new JFrame();
        frmZc.setResizable(false);
        frmZc.getContentPane().setEnabled(false);
        frmZc.getContentPane().setLayout(null);

        JLabel label = new JLabel("\u9700\u8981\u538B\u7F29/\u89E3\u538B\u7684\u8DEF\u5F84");
        label.setBounds(14, 105, 170, 18);
        frmZc.getContentPane().add(label);

        JLabel label_1 = new JLabel("\u5B58\u50A8\u8DEF\u5F84");
        label_1.setBounds(90, 205, 72, 18);
        frmZc.getContentPane().add(label_1);

        compressPathField = new JTextField();
        compressPathField.setBounds(164, 102, 529, 24);
        frmZc.getContentPane().add(compressPathField);
        compressPathField.setColumns(10);

        storePathField = new JTextField();
        storePathField.setColumns(10);
        storePathField.setBounds(163, 202, 529, 24);
        frmZc.getContentPane().add(storePathField);

        JButton compressButton = new JButton("\u538B\u7F29");
        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WriteFile writeFile = new WriteFile();
                //writeFile.init(storePath);
                writeFile.writeFile(storePath);
            }
        });
        compressButton.setBounds(151, 332, 113, 27);
        frmZc.getContentPane().add(compressButton);

        JButton decompressButton = new JButton("\u89E3\u538B");
        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReadFile readFile = new ReadFile();
                readFile.readFile();
            }
        });
        decompressButton.setBounds(567, 332, 113, 27);
        frmZc.getContentPane().add(decompressButton);

        chooser = new JFileChooser();
        JButton findButton = new JButton("\u6D4F\u89C8\u6587\u4EF6");
       findButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               int result = chooser.showOpenDialog(null);
               findPath = chooser.getSelectedFile().getPath();
               if (result == JFileChooser.APPROVE_OPTION){
                   compressPathField.setText(chooser.getSelectedFile().getPath());
               }

           }
       });
        findButton.setBounds(720, 101, 113, 27);
        frmZc.getContentPane().add(findButton);
        chooser2 = new  JFileChooser();
        JButton storeButton = new JButton("\u6D4F\u89C8\u6587\u4EF6");
        storeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = chooser2.showOpenDialog(null);
                if (chooser.getSelectedFile().getPath().contains("txt")) {
                    storePath = chooser2.getSelectedFile().getPath().replaceAll("txt", "zc");
                }else if (chooser.getSelectedFile().getPath().contains("zc")){
                    storePath = chooser2.getSelectedFile().getPath().replaceAll("zc", "txt");
                }
                if (result == JFileChooser.APPROVE_OPTION){
                    storePathField.setText(storePath);
                }
            }
        });
        storeButton.setBounds(720, 201, 113, 27);


        JLabel lblNewLabel = new JLabel("\u7248\u6743\u6240\u6709 \u8FDD\u8005\u5FC5\u7A76");
        lblNewLabel.setBounds(374, 400, 161, 18);
       // frmZc.getContentPane().add(lblNewLabel);

        JLabel lblzc = new JLabel("\u00A9ZC");
        lblzc.setFont(new Font("宋体", Font.PLAIN, 17));
        lblzc.setBounds(417, 372, 72, 18);
      //  frmZc.getContentPane().add(lblzc);



        frmZc.getContentPane().add(storeButton);
        frmZc.setTitle("WinZC");
        frmZc.setBounds(100, 100, 898, 453);
        frmZc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
