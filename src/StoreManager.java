public class StoreManager {
    //public static String dbms = "Network";
    public static String path = "localhost:1000";

    IDataAdapter dataAdapter;
    private static StoreManager instance = null;

    public static StoreManager getInstance() {
        if (instance == null) {
            instance = new StoreManager(path);
        }
        return instance;
    }

    private StoreManager(String dbfile) {
         dataAdapter = new NetworkDataAdapter();
         dataAdapter.connect(dbfile);

    }

    public IDataAdapter getDataAdapter() {
        return dataAdapter;
    }

    public void run() {
        LoginUI ui = new LoginUI();
        ui.run();
    }

    public static void main(String[] args) {
        StoreManager.getInstance().run();
    }
}
