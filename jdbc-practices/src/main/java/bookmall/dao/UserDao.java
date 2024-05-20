package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bookmall.DBConfig;
import bookmall.vo.UserVo;
import bookshop.vo.AuthorVo;

public class UserDao {
    private Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            //1. JDBC Driver 로딩
            Class.forName("org.mariadb.jdbc.Driver");

            //2. 연결하기
            String url = DBConfig.getUrl();
            String userName = DBConfig.getUsername();
            String password = DBConfig.getPassword();
            conn = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패:" + e);
        }
        return conn;
    }

    public int insert(UserVo vo) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("insert into user(name, email, password, phone) values (?,?,?,?)");
                PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
        ) {
            pstmt1.setString(1, vo.getName());
            pstmt1.setString(2, vo.getEmail());
            pstmt1.setString(3, vo.getPassword());
            pstmt1.setString(4, vo.getPhone());
            result = pstmt1.executeUpdate();

            ResultSet rs = pstmt2.executeQuery();
            vo.setNo(rs.next() ? rs.getLong(1) : null);
            rs.close();
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public List<UserVo> findAll() {
        List<UserVo> result = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select no, name, email, password, phone from user");
                ResultSet rs = pstmt.executeQuery();
        ) {
            //6. 결과 처리
            while (rs.next()) {
                Long no = rs.getLong(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(4);
                String phone = rs.getString(5);

                UserVo vo = new UserVo();
                vo.setNo(no);
                vo.setName(name);
                vo.setEmail(email);
                vo.setPassword(password);
                vo.setPhone(phone);

                result.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public int deleteByNo(Long no) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from user where no = ?");
        ) {
            pstmt.setLong(1, no);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }
}
