package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionTest {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            // 1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.64.3:3306/webdb?charset=utf8";
            connection = DriverManager.getConnection(url, "webdb", "webdb");

            System.out.println("success!!");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패 : " + e);
        } catch (SQLException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        } finally {
            try {
                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
