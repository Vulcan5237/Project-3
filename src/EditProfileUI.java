import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditProfileUI {

    //Basically nerf the admin level manage user class
    //Only allowed to edit password and name
    public JFrame view;
    UserModel user;


    public JButton btnSave = new JButton("Save Changes");

    public JLabel username = new JLabel();
    public JTextField txtPassword = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    //public JTextField txtUserType = new JTextField(20);
    public JLabel userType = new JLabel();
    public JLabel userID = new JLabel();



    public EditProfileUI(UserModel currentUser) {
        this.view = new JFrame();
        user = currentUser;
        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Edit Profile for Username: " + user.mUsername);
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Username "));
        username.setText(user.mUsername);
        line1.add(username);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Password "));
        txtPassword.setText(user.mPassword);
        line2.add(txtPassword);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Full Name "));
        txtName.setText(user.mFullname);
        line3.add(txtName);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("User Type "));

        if (user.mUserType == 0)
        {
            userType.setText("Customer");
        }
        else if (user.mUserType == 1) {
            userType.setText("Cashier");
        }
        else if (user.mUserType == 2) {
            userType.setText("Manager");
        }
        else if (user.mUserType == 3) {
            userType.setText("System Administrator");
        }
        else {
            userType.setText("Unknown User Type");
        }

        line4.add(userType);
        view.getContentPane().add(line4);

        JPanel line5 = new JPanel(new FlowLayout());
        line5.add(new JLabel("UserID "));
        userID.setText(Integer.toString(user.mUserID));
        line5.add(userID);
        view.getContentPane().add(line5);

        btnSave.addActionListener(new SaveButtonListener());

    }

    public void run() {
        view.setVisible(true);
    }

    class SaveButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            //UserModel user = new UserModel();
            Gson gson = new Gson();

            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "User's Full Name cannot be empty!");
                return;
            }

            user.mFullname = name;

            String pWord = txtPassword.getText();
            if (pWord.length() == 0) {
                JOptionPane.showMessageDialog(null, "User password cannot be empty!");
                return;
            }
            user.mPassword = pWord;

            int  res = StoreManager.getInstance().getDataAdapter().saveUser(user);

            if (res == IDataAdapter.USER_SAVE_FAILED)
                JOptionPane.showMessageDialog(null, "User is NOT saved successfully!");
            else
                JOptionPane.showMessageDialog(null, "User is SAVED successfully!");

        }
    }
}