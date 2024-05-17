package example;

import java.sql.*;

public class SelectEx01 {
    public static void main(String[] args) {
        search("pat");
    }

    private static boolean search(String keyword) {
        boolean result = false;
        Connection connection = null;
        Statement stmt = null;
        try {
            // 1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.64.3:3306/webdb?charset=utf8";
            connection = DriverManager.getConnection(url, "webdb", "webdb");

            // 3. Statement 생성하기
            stmt = connection.createStatement();

            // 4. SQL 실행
            String sql = "SELECT emp_no, first_name, last_name FROM employees WHERE first_name LIKE '%" + keyword + "%'";
            ResultSet rs = stmt.executeQuery(sql);

            // 5. 결과처리
            while (rs.next()) {
                Long no = rs.getLong(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);

                System.out.println("empNo = " + no + "first name = " + firstName + "lastName = " + lastName);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패 : " + e);
        } catch (SQLException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        } finally {
            try {

                if(stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

}
