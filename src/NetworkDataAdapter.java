import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NetworkDataAdapter implements IDataAdapter {

    String host = "localhost";
    int port = 1000;

    Gson gson = new Gson();
    SocketNetworkAdapter adapter = new SocketNetworkAdapter();
    MessageModel msg = new MessageModel();


    public int connect(String dbfile) {
        int pos = dbfile.indexOf(":");
        host = dbfile.substring(0, pos);
        port = Integer.parseInt(dbfile.substring(pos+1, dbfile.length()));
        return 0;
    }


    public int disconnect() {
        return 0;
    }


    public ProductModel loadProduct(int id) {
        msg.code = MessageModel.GET_PRODUCT;
        msg.data = Integer.toString(id);
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, ProductModel.class);
        }
    }


    public int saveProduct(ProductModel model) {
        msg.code = MessageModel.PUT_PRODUCT;
        msg.data = gson.toJson(model);

        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.PRODUCT_SAVE_FAILED;
        else {
            return IDataAdapter.PRODUCT_SAVE_OK;
        }
    }


    public CustomerModel loadCustomer(int id) {
        msg.code = MessageModel.GET_CUSTOMER;
        msg.data = Integer.toString(id);
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, CustomerModel.class);
        }
    }

    public int saveCustomer (CustomerModel customer) {
        msg.code = MessageModel.PUT_CUSTOMER;
        msg.data = gson.toJson(customer);

        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.CUSTOMER_SAVE_FAILED;
        else {
            return IDataAdapter.CUSTOMER_SAVE_OK;
        }
    }

    public PurchaseModel loadPurchase(int id) {
        msg.code = MessageModel.GET_PURCHASE;
        msg.data = Integer.toString(id);
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, PurchaseModel.class);
        }

    }

    public int savePurchase(PurchaseModel model) {
        msg.code = MessageModel.PUT_PURCHASE;
        msg.data = gson.toJson(model);

        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.PURCHASE_SAVE_FAILED;
        else {
            return IDataAdapter.PRODUCT_SAVE_OK;
        }
    }

    public UserModel loadUser(String uName) {
        msg.code = MessageModel.GET_USER;
        msg.data = uName;
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, UserModel.class);
        }
    }

    public int saveUser(UserModel user){
        msg.code = MessageModel.PUT_USER;
        msg.data = gson.toJson(user);

        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return IDataAdapter.USER_SAVE_FAILED;
        else {
            return IDataAdapter.USER_SAVE_OK;
        }
    }

    public CustomerModel loadCustomerByUser(int uID) {
        msg.code = MessageModel.GET_CUSTOMER_BY_USER;
        msg.data = Integer.toString(uID);
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, CustomerModel.class);
        }
    }

    public ArrayList<PurchaseModel> loadPurchaseHistory(){
        Type listOfPurchaseModel = new TypeToken<ArrayList<PurchaseModel>>() {}.getType();
        msg.code = MessageModel.GET_PURCHASE_HISTORY;
        msg.data = "placeholder";
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            ArrayList<PurchaseModel> retVal= gson.fromJson(msg.data, listOfPurchaseModel);
            System.out.println(retVal);
            return retVal;
        }
    }

    public ArrayList<ProductModel> searchProductByPrice(double price){
        Type listOfProductModel = new TypeToken<ArrayList<ProductModel>>() {}.getType();
        msg.code = MessageModel.SEARCH_PRODUCT_BY_PRICE;
        msg.data = Double.toString(price);
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, listOfProductModel);
        }
    }

    public ArrayList<ProductModel> searchProductByName(String name) {
        Type listOfProductModel = new TypeToken<ArrayList<ProductModel>>() {}.getType();
        msg.code = MessageModel.SEARCH_PRODUCT_BY_NAME;
        msg.data = name;
        try {
            msg = adapter.send(msg, host, port);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (msg.code == MessageModel.OPERATION_FAILED)
            return null;
        else {
            return gson.fromJson(msg.data, listOfProductModel);
        }
    }


}