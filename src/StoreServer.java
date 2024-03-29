import com.google.gson.Gson;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StoreServer {
    static String dbfile = "C:\\Users\\grant\\Documents\\Databases\\Project3.db";


    public static void main(String[] args) {

        int port = 1000;

        if (args.length > 0) {
            System.out.println("Running arguments: ");
            for (String arg : args)
                System.out.println(arg);
            port = Integer.parseInt(args[0]);
            dbfile = args[1];
        }

        try {
            SQLiteDataAdapter adapter = new SQLiteDataAdapter();
            Gson gson = new Gson();
            adapter.connect(dbfile);

            ServerSocket server = new ServerSocket(port);

            System.out.println("Server is listening at port = " + port);

            while (true) {
                Socket pipe = server.accept();
                PrintWriter out = new PrintWriter(pipe.getOutputStream(), true);
                Scanner in = new Scanner(pipe.getInputStream());

                MessageModel msg = gson.fromJson(in.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.GET_PRODUCT) {
                    System.out.println("GET product with id = " + msg.data);
                    ProductModel p = adapter.loadProduct(Integer.parseInt(msg.data));
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_PRODUCT) {
                    ProductModel p = gson.fromJson(msg.data, ProductModel.class);
                    System.out.println("PUT command with Product = " + p);
                    int res = adapter.saveProduct(p);
                    if (res == IDataAdapter.PRODUCT_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_CUSTOMER) {
                    System.out.println("GET customer with id = " + msg.data);
                    CustomerModel c = adapter.loadCustomer(Integer.parseInt(msg.data));
                    if (c == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(c);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_CUSTOMER) {
                    //Step 1: build customer model from message
                    CustomerModel c = gson.fromJson(msg.data, CustomerModel.class);
                    System.out.println("PUT command with Customer = " + c);

                    //Use the sql adapter to actually edit database
                    int res = adapter.saveCustomer(c);

                    //test if it worked and return the result
                    if (res == IDataAdapter.CUSTOMER_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    //Tell the UI the result
                    out.println(gson.toJson(msg));

                }

                if (msg.code == MessageModel.GET_PURCHASE) {
                    PurchaseModel p = adapter.loadPurchase(Integer.parseInt(msg.data));
                    if (p == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(p);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_PURCHASE) {
                    //Step 1: build customer model from message
                    PurchaseModel p = gson.fromJson(msg.data, PurchaseModel.class);
                    System.out.println("PUT command with Purchase = " + p);

                    //Use the sql adapter to actually edit database
                    int res = adapter.savePurchase(p);

                    //test if it worked and return the result
                    if (res == IDataAdapter.PURCHASE_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    //Tell the UI the result
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_CUSTOMER_BY_USER) {
                    System.out.println("GET customer with user id = " + msg.data);
                    CustomerModel c = adapter.loadCustomerByUser(Integer.parseInt(msg.data));
                    if (c == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(c);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_USER) {
                    System.out.println("GET user with username = " + msg.data);
                    UserModel u = adapter.loadUser(msg.data);
                    if (u == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(u);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.PUT_USER) {
                    //Step 1: build customer model from message
                    UserModel u = gson.fromJson(msg.data, UserModel.class);
                    System.out.println("PUT command with User = " + u);

                    //Use the sql adapter to actually edit database
                    int res = adapter.saveUser(u);

                    //test if it worked and return the result
                    if (res == IDataAdapter.USER_SAVE_OK) {
                        msg.code = MessageModel.OPERATION_OK;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    //Tell the UI the result
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.GET_PURCHASE_HISTORY) {
                    System.out.println("GET Purchase history");

                    ArrayList<PurchaseModel> pMList = adapter.loadPurchaseHistory();
                    if (pMList == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(pMList);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.SEARCH_PRODUCT_BY_PRICE) {
                    System.out.println("SEARCH Product Database for price <= " + msg.data);
                    ArrayList<ProductModel> pMList = adapter.searchProductByPrice(Double.parseDouble(msg.data));
                    if (pMList == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(pMList);
                    }
                    out.println(gson.toJson(msg));
                }

                if (msg.code == MessageModel.SEARCH_PRODUCT_BY_NAME) {
                    System.out.println("SEARCH Product Database for name = " + msg.data);
                    ArrayList<ProductModel> pMList = adapter.searchProductByName(msg.data);
                    if (pMList == null) {
                        msg.code = MessageModel.OPERATION_FAILED;
                    }
                    else {
                        msg.code = MessageModel.OPERATION_OK; // load successfully!!!
                        msg.data = gson.toJson(pMList);
                    }
                    out.println(gson.toJson(msg));
                }



            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}