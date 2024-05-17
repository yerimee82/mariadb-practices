package example;

import java.sql.*;

public class DeleteEx02 {
    public static void main(String[] args) {
        boolean reuslt = delete(5L);
        System.out.println(reuslt ? "성공" : "실패");

    }
    private static boolean delete(long no) {
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
            String sql = "delete from dept where no = ?" ;
            pstmt = conn.prepareStatement(sql);

            // 4. SQL 실행
            pstmt.setLong(1, no);
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
