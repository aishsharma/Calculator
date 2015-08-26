/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Aishwarya Sharma
 */
public class CalcWindow extends JFrame {

    //Form Components
    private JPanel panel;
    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JPanel numberButtonPanel;
    private JPanel symbolButtonPanel;
    private JLabel input;
    private JLabel symbol;
    private JSplitPane split;
    private JButton[] numberButtons;
    private JButton pButton;
    private JButton sButton;
    private JButton mButton;
    private JButton dButton;
    private JButton clearButton;
    private JButton pmButton;

    //Calculator variables
    private double fnum;
    private double snum;
    private double res;
    private String op;

    public CalcWindow() {
        super("Calculator");
        this.setSize(300, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initializeVariables();
        paint();
        addActionListeners();
        setDisplay();

        this.setVisible(true);
    }

    //For convenience.
    private static void print(String str) {
        System.out.println(str);
    }

    private void setDisplay() {
        input.setText(((Double) fnum).toString());
        symbol.setText(op);
    }

    private void paint() {
        panel = new JPanel();
        inputPanel = new JPanel();
        buttonPanel = new JPanel();
        numberButtonPanel = new JPanel();
        symbolButtonPanel = new JPanel();

        input = new JLabel(); //To show user input and calculation result
        input.setBackground(Color.green);
        input.setSize(10, 5);

        symbol = new JLabel("");   //To show current mathematical operation
        split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        //Making buttons
        numberButtons = new JButton[12];
        pButton = new JButton("+");
        sButton = new JButton("-");
        mButton = new JButton("*");
        dButton = new JButton("÷");
        clearButton = new JButton("Clear");
        pmButton = new JButton("+/-");

        //Setting button text
        for (int i = 0; i < 9; i++) {
            numberButtons[i] = new JButton(Integer.toString(i + 1));
        }
        numberButtons[9] = new JButton("0");
        numberButtons[10] = new JButton(".");
        numberButtons[11] = new JButton("=");

        //For number button panel
        GridLayout numberButtonLayout = new GridLayout(0, 3);
        numberButtonPanel.setLayout(numberButtonLayout);
        for (int i = 0; i < 12; i++) {
            numberButtonPanel.add(numberButtons[i]);
        }
        //For symbol button panel
        symbolButtonPanel.setLayout(new GridLayout(0, 1));
        symbolButtonPanel.add(pButton);
        symbolButtonPanel.add(sButton);
        symbolButtonPanel.add(mButton);
        symbolButtonPanel.add(dButton);

        //Adding stuff to button panel
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(numberButtonPanel, BorderLayout.WEST);
        buttonPanel.add(symbolButtonPanel, BorderLayout.CENTER);

        //Adding stuff to input display panel
        inputPanel.setLayout(new GridLayout(0, 2));
        inputPanel.add(input);
        inputPanel.add(symbol);
        inputPanel.add(clearButton);
        inputPanel.add(pmButton);

        //Setting up Split panel
        split.setTopComponent(inputPanel);
        split.setBottomComponent(buttonPanel);
        split.setDividerSize(0);

        //Setting up main panel.
        panel.add(split);

        //Adding content panels to window
        this.getContentPane().add(panel);
    }

    private void addActionListeners() {
        //Creating action listeners
        ActionListener numberListener = (ActionEvent e) -> {    //Listener for numbers
            String cmd = e.getActionCommand();
            String value = input.getText();

            if (value.equals("0") || value.equals("0.0")) {
                input.setText(cmd);
            } else {
                if (op.trim().equals("")) {
                    input.setText(value + cmd);
                } else {
                    input.setText(cmd);
                }
            }
        };
        
        ActionListener pmListener = (ActionEvent e) -> {    //Listener for numbers
            String cmd = e.getActionCommand();
            String value = input.getText();
            if (!value.equals("0.0")) {
                char ch = value.charAt(0);
                if (ch == '-') {
                    input.setText(value.replace("-", ""));
                } else {
                input.setText("-" + value);
                }
            }
        };

        ActionListener symbolListener = (ActionEvent e) -> {    //Listener for symbols
            String cmd = e.getActionCommand();
            String value = input.getText();
            symbol.setText(cmd);
            if (cmd.equalsIgnoreCase("=")) {
                if (!op.trim().equals("")) {
                    snum = Double.parseDouble(value);
                    doMath();
                    fnum = res;
                    snum = 0;
                    op = "";
                    setDisplay();
                }
            } else {
                if (op.trim().equals("")) {
                    fnum = Double.parseDouble(value);
                    op = cmd;
                    setDisplay();
                } else if (snum > 0) {
                    op = cmd;
                } else {
                    snum = Double.parseDouble(value);
                    doMath();
                    fnum = res;
                    snum = 0;
                    setDisplay();
                }
            }
        };

        ActionListener clearListener = (ActionEvent e) -> {    //Listener for clear button
            initializeVariables();
            setDisplay();
        };

        //Assigning listeners to buttons
        for (int i = 0; i < 11; i++) {
            numberButtons[i].addActionListener(numberListener);
        }

        numberButtons[11].addActionListener(symbolListener);
        pButton.addActionListener(symbolListener);
        sButton.addActionListener(symbolListener);
        mButton.addActionListener(symbolListener);
        dButton.addActionListener(symbolListener);
        clearButton.addActionListener(clearListener);
        pmButton.addActionListener(pmListener);

    }

    private void doMath() {
        switch (op) {

            case "+": {
                res = fnum + snum;
                break;
            }
            case "-": {
                res = fnum - snum;
                break;
            }
            case "*": {
                res = fnum * snum;
                break;
            }
            case "÷": {
                res = fnum / snum;
                break;
            }
            default: {
                JOptionPane.showMessageDialog(null, "Matematical Operation not recognized.");
            }
        }
    }

    private void initializeVariables() {
        fnum = 0;
        snum = 0;
        res = 0;
        op = "";
    }

}
