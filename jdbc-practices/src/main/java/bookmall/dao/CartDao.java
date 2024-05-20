package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.DBConfig;
import bookmall.vo.CartVo;
import bookshop.vo.AuthorVo;

public class CartDao {
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

    public int insert(CartVo vo) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("insert into cart(user_no, book_no, title, quantity) values (?,?,?,?)");
                PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
        ) {
            pstmt1.setLong(1, vo.getUserNo());
            pstmt1.setLong(2, vo.getBookNo());
            pstmt1.setString(3, vo.getBookTitle());
            pstmt1.setInt(4, vo.getQuantity());
            result = pstmt1.executeUpdate();

            ResultSet rs = pstmt2.executeQuery();
            vo.setUserNo(rs.next() ? rs.getLong(1) : null);
            rs.close();
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public List<CartVo> findByUserNo(Long no) {
        List<CartVo> result = new ArrayList<>();

        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select b.no, c.no, c.title, a.quantity from cart a, user b, book c where a.user_no = b.no and a.book_no = c.no");
                ResultSet rs = pstmt.executeQuery();
        ) {
            //6. 결과 처리
            while (rs.next()) {
                Long userNo = rs.getLong(1);
                Long bookNo = rs.getLong(2);
                String bookTitle = rs.getString(3);
                int quantity = rs.getInt(4);

                CartVo vo = new CartVo();
                vo.setUserNo(userNo);
                vo.setBookNo(bookNo);
                vo.setBookTitle(bookTitle);
                vo.setQuantity(quantity);

                result.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public int deleteByUserNoAndBookNo(Long userNo, Long bookNo) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from cart where user_no = ? and book_no = ?");
        ) {
            pstmt.setLong(1, userNo);
            pstmt.setLong(2, bookNo);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }
}
