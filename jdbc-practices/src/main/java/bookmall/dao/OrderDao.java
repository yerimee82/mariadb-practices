package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.DBConfig;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;

public class OrderDao {
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
    public int insert(OrderVo vo) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("insert into orders(no, number, payment, shipping, status, user_no) values (?,?,?,?,?,?)");
                PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
        ) {
            pstmt1.setLong(1, vo.getUserNo());
            pstmt1.setString(2, vo.getNumber());
            pstmt1.setInt(3, vo.getPayment());
            pstmt1.setString(4, vo.getShipping());
            pstmt1.setString(5, vo.getStatus());
            pstmt1.setLong(6, vo.getUserNo());
            result = pstmt1.executeUpdate();

            ResultSet rs = pstmt2.executeQuery();
            vo.setNo(rs.next() ? rs.getLong(1) : null);
            rs.close();
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public int insertBook(OrderBookVo bookVo) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt1 = conn.prepareStatement("insert into orders_book(order_no, book_no, quantity, price) values (?,?,?,?)");
                PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
        ) {
            pstmt1.setLong(1, bookVo.getOrderNo());
            pstmt1.setLong(2, bookVo.getBookNo());
            pstmt1.setInt(3, bookVo.getQuantity());
            pstmt1.setInt(4, bookVo.getPrice());

            result = pstmt1.executeUpdate();

            ResultSet rs = pstmt2.executeQuery();
            bookVo.setNo(rs.next() ? rs.getLong(1) : null);
            rs.close();
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public OrderVo findByNoAndUserNo(long l, Long no) {
    }

    public List<OrderBookVo> findBooksByNoAndUserNo(Long no, Long no1) {
    }

    public void deleteBooksByNo(Long no) {
    }

    public void deleteByNo(Long no) {
    }
}
