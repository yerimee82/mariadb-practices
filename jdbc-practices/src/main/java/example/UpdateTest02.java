package example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateTest02 {
    public static void main(String[] args) {
        DeptVo vo = new DeptVo();
        vo.setNo(1L);
        vo.setName("경영지원");

        boolean result = update(vo);
        System.out.println(result ? "성공" : "실패");
    }

    public static boolean update(DeptVo vo) {
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
            String sql = "update dept set name = '" + vo.getName() + "' where no = " + vo.getNo();
            int count = stmt.executeUpdate(sql);



            // 5. 결과처리
            result = count == 1;

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패 : " + e);
        } catch (SQLException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        } finally {
            try {
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
