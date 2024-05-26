package bookshop.dao;

import bookshop.vo.AuthorVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthorDao {
    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            //1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            //2. 연결하기
            String url = "jdbc:mariadb://192.168.64.3:3306/webdb?charset=utf8";
            conn = DriverManager.getConnection(url, "webdb", "webdb");
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }

        return conn;
    }

    public List<AuthorVo> findAll() {
        List<AuthorVo> result = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select no, name from author");
                ResultSet rs = pstmt.executeQuery();
        ) {
            //6. 결과 처리
            while (rs.next()) {
                Long no = rs.getLong(1);
                String name = rs.getString(2);

                AuthorVo vo = new AuthorVo();
                vo.setNo(no);
                vo.setName(name);

                result.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public int insert(AuthorVo vo) {
        int result = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("insert into author(name) values (?)");
                PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
        ) {
            pstmt1.setString(1, vo.getName());
            result = pstmt1.executeUpdate();

            ResultSet rs = pstmt2.executeQuery();
            vo.setNo(rs.next() ? rs.getLong(1) : null);
            rs.close();
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public int deleteByNo(Long no) {
        int result = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from author where no = ?");
        ) {
            pstmt.setLong(1, no);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }
    public int deleteAll() {
        int result = 0;

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from author");
        ) {
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("error:" + ex);
        }

        return result;
    }
}
