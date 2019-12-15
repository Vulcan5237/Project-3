import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerUI {

    public CustomerModel customer = null;

    public JFrame view;

    public JButton btnMakePurchase = new JButton("Make a Purchase");
    //public JButton btnCancelPurchase = new JButton("Cancel a Purchase");
    public JButton btnViewPurchases = new JButton("View Purchase History");
    public JButton btnSeachProduct = new JButton("Search Product");

    public CustomerUI(final UserModel user) {

        //customer = StoreManager.getInstance().getDataAdapter().loadCustomerByUser(user.mUserID);

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System - Customer View");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Customer Store View");

        title.setFont (title.getFont ().deriveFont (24.0f));
        view.getContentPane().add(title);

        JPanel panelUser = new JPanel(new FlowLayout());
        panelUser.add(new JLabel("Name: " + user.mFullname));
        //panelUser.add(new JLabel("CustomerID: ADD ID BACK"));

        view.getContentPane().add(panelUser);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnMakePurchase);
        panelButtons.add(btnViewPurchases);
        //panelButtons.add(btnCancelPurchase);
        panelButtons.add(btnSeachProduct);

        view.getContentPane().add(panelButtons);


        btnViewPurchases.addActionListener(new ActionListener() {
           // @Override
            public void actionPerformed(ActionEvent actionEvent) {
                PurchaseHistoryUI ui = new PurchaseHistoryUI();
                ui.run();
            }
        });

        btnMakePurchase.addActionListener(new ActionListener() {
           /// @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddPurchaseUI ui = new AddPurchaseUI();
                ui.run();
            }
        });

        btnSeachProduct.addActionListener(new ActionListener() {
          //  @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ProductSearchUI ui = new ProductSearchUI();
                ui.view.setVisible(true);
            }
        } );

    }
}