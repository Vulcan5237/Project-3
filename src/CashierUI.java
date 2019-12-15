import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CashierUI {
    public JFrame view;

    public JButton btnManagePurchase = new JButton("Manage Purchases");
    public JButton btnManageCustomer = new JButton("Manage Customers");

    public CashierUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Cashier View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Cashier System View");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnManagePurchase);
        panelButtons.add(btnManageCustomer);

        view.getContentPane().add(panelButtons);


        btnManagePurchase.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ManagePurchaseUI mp = new ManagePurchaseUI();
                mp.run();
            }
        });

        btnManageCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                ManageCustomerUI mc = new ManageCustomerUI();
                mc.run();
            }
        });
    }
}