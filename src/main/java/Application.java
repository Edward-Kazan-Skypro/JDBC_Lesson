import java.sql.Connection;
import java.sql.DriverManager;

public class Application {

    private String user = "postgres";
    private String pass = "admin";
    private String url = "jdbc:postgresql://localhost:5432/skypro";

    public static void main(String[] args) {
        try(final Connection connection = DriverManager.getConnection(url, user, pass)){

        }
    }
}
