package example;

import java.sql.*;

public class InsertEx02 {
    public static void main(String[] args) {
        System.out.println(insert("기획1팀"));
        System.out.println(insert("기획2팀"));
    }
    public static boolean insert(String deptName) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // 1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mariadb://192.168.64.3:3306/webdb?charset=utf8";
            conn = DriverManager.getConnection(url, "webdb", "webdb");

            // 3. Statement 생성하기
            String sql = "insert into dept values(null, ?)";
            pstmt = conn.prepareStatement(sql);

            // 4. SQL 실행
            pstmt.setString(1, sql);

            int count = pstmt.executeUpdate(sql);

            // 5. 결과처리
            result = count == 1;

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패 : " + e);
        } catch (SQLException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }
}
