package by.alexei.afanasyeu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresUtil {
    private static final String URL="jdbc:postgresql://localhost:5432/wg_forge_db";
    private static final String USER="wg_forge";
    private static final String PASSWORD="a42";

    private static PostgresUtil instance = new PostgresUtil();

    public static PostgresUtil getInstance() {
        return instance;
    }

    private PostgresUtil(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
