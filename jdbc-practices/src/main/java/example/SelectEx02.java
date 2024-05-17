package example;

import java.sql.*;

public class SelectEx02 {
    public static void main(String[] args) {
        search("pat");
    }

    public static void search(String keyword) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            //2. 연결하기
            String url = "jdbc:mariadb://192.168.0.203:3306/employees?charset=utf8";
            conn = DriverManager.getConnection(url, "hr", "hr");

            //3. Statement 준비
            String sql =
                    "select emp_no, first_name, last_name" +
                            "  from employees" +
                            " where first_name like ?" +
                            "   and last_name like ?";
            pstmt = conn.prepareStatement(sql);

            //4. binding
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");

            //5. SQL 실행
            rs = pstmt.executeQuery();

            //6. 결과 처리
            while(rs.next()) {
                Long empNo = rs.getLong(1);
                String firstName = rs.getString(2);
                String lastName = rs.getString(3);

                System.out.println(empNo + ":" + firstName + ":" + lastName);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        } catch (SQLException e) {
            System.out.println("error:" + e);
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }

                if(pstmt != null) {
                    pstmt.close();
                }

                if(conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
