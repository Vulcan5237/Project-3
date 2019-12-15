import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class LoginUI {
    public JFrame view;

    public JButton btnLogin = new JButton("Login");
    public JButton btnProfile = new JButton("Edit Profile");

    public JTextField txtUsername = new JTextField(20);
    public JTextField txtPassword = new JPasswordField(20);

    Socket link;
    Scanner input;
    PrintWriter output;

    int accessToken;

    public LoginUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Login");
        view.setSize(600, 400);

        Container pane = view.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel();
        line.add(new JLabel("Username"));
        line.add(txtUsername);
        pane.add(line);

        line = new JPanel();
        line.add(new JLabel("Password"));
        line.add(txtPassword);
        pane.add(line);

        pane.add(btnLogin);
        pane.add(btnProfile);

        btnLogin.addActionListener(new LoginActionListener());

        btnProfile.addActionListener(new ProfileActionListener());

    }

    public void run() {

        LoginUI ui = new LoginUI();
        ui.view.setVisible(true);

        /*
        AdminUI ui0 = new AdminUI();
        ui0.view.setVisible(true);

        ManagerUI ui1 = new ManagerUI();
        ui1.view.setVisible(true);

        CashierUI ui2 = new CashierUI();
        System.out.println("MainUI = " + ui);
        ui2.view.setVisible(true);

        UserModel user = new UserModel();
        user.mFullname = "Grant";
        user.mUsername = "stuff";
        user.mUserType = 0;
        user.mPassword = "secret";
        CustomerUI ui3 = new CustomerUI(user);
        System.out.println("MainUI = " + ui);
        ui3.view.setVisible(true);

        */


    }

    private class ProfileActionListener implements ActionListener {
        //@Override
        public void actionPerformed(ActionEvent actionEvent) {
            //Reuse the stuff from Login Action Listener to validate a user
            UserModel user = new UserModel();


            user.mUsername = txtUsername.getText();
            user.mPassword = txtPassword.getText();

            if (txtUsername.getText().length() == 0 || txtPassword.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be null!");
                return;
            }

            //Load a user with that username
            UserModel usernameResult = StoreManager.getInstance().getDataAdapter().loadUser(txtUsername.getText());

            if (usernameResult == null) {
                JOptionPane.showMessageDialog(null, "User does not exist!");
                return;
            }
            else if (!(usernameResult.mPassword.equals(txtPassword.getText()))) {
                JOptionPane.showMessageDialog(null, "Password is incorrect!");
                return;
            }
            else {
                user = usernameResult;
            }

            //Not going to close the login window since they aren't really entering into the system, just editing their profile

            //Launch the edit profile window
            EditProfileUI ui = new EditProfileUI(user);
            ui.run();
        }
    }

    private class LoginActionListener implements ActionListener {
        //@Override
        public void actionPerformed(ActionEvent actionEvent) {


            UserModel user = new UserModel();


                user.mUsername = txtUsername.getText();
                user.mPassword = txtPassword.getText();

            if (txtUsername.getText().length() == 0 || txtPassword.getText().length() == 0) {
                JOptionPane.showMessageDialog(null, "Username or password cannot be null!");
                return;
            }

            //Load a user with that username
            UserModel usernameResult = StoreManager.getInstance().getDataAdapter().loadUser(txtUsername.getText());

            if (usernameResult == null) {
                JOptionPane.showMessageDialog(null, "User does not exist!");
                return;
            }
            else if (!(usernameResult.mPassword.equals(txtPassword.getText()))) {
                JOptionPane.showMessageDialog(null, "Password is incorrect!");
                return;
            }
            else {
                user = usernameResult;
                //"Close" login window. No longer needed
                view.setVisible(false);
            }

            //A valid user has been found. Now we can load a UI

            System.out.println("User = " + user);
            if (user.mUserType == UserModel.ADMIN) {
                AdminUI ui = new AdminUI();
                System.out.println("MainUI = " + ui);
                ui.view.setVisible(true);
            }
            else if (user.mUserType == UserModel.MANAGER) {
                ManagerUI ui = new ManagerUI();
                System.out.println("MainUI = " + ui);
                ui.view.setVisible(true);
            }
            else if (user.mUserType == UserModel.CASHIER) {
                CashierUI ui = new CashierUI();
                System.out.println("MainUI = " + ui);
                ui.view.setVisible(true);
            }
            else if (user.mUserType == UserModel.CUSTOMER) {
                CustomerUI ui = new CustomerUI(user);
                System.out.println("MainUI = " + ui);
                ui.view.setVisible(true);
            }
            else {
                JOptionPane.showMessageDialog(null, "Usertype NOT supported!");
                view.setVisible(true);
            }

            }

        }
    }
