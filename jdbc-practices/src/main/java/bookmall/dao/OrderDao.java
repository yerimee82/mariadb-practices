package bookmall.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bookmall.DBConfig;
import bookmall.vo.CategoryVo;
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
                PreparedStatement pstmt1 = conn.prepareStatement("insert into orders(number, payment, shipping, status, user_no) values (?,?,?,?,?)");
                PreparedStatement pstmt2 = conn.prepareStatement("select last_insert_id() from dual");
        ) {
            pstmt1.setString(1, vo.getNumber());
            pstmt1.setInt(2, vo.getPayment());
            pstmt1.setString(3, vo.getShipping());
            pstmt1.setString(4, vo.getStatus());
            pstmt1.setLong(5, vo.getUserNo());
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
                PreparedStatement pstmt = conn.prepareStatement("insert into orders_book(order_no, book_no, quantity, price) values (?,?,?,?)");
        ) {
            pstmt.setLong(1, bookVo.getOrderNo());
            pstmt.setLong(2, bookVo.getBookNo());
            pstmt.setInt(3, bookVo.getQuantity());
            pstmt.setInt(4, bookVo.getPrice());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public OrderVo findByNoAndUserNo(Long no, Long userNo) {
        OrderVo result = null;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select a.no, a.number, a.payment, a.shipping, a.status, a.user_no " +
                        "from orders a " +
                        "where a.no = ? and a.user_no = ?");
        ) {
            pstmt.setLong(1, no);
            pstmt.setLong(2, userNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    result = new OrderVo();
                    result.setNo(rs.getLong(1));
                    result.setNumber(rs.getString(2));
                    result.setPayment(rs.getInt(3));
                    result.setShipping(rs.getString(4));
                    result.setStatus(rs.getString(5));
                    result.setUserNo(rs.getLong(6));
                }
            }

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public List<OrderBookVo> findBooksByNoAndUserNo(Long no, Long userNo) {
        List<OrderBookVo> result = new ArrayList<>();
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("select a.order_no, a.book_no, a.quantity, a.price, b.title " +
                        "from orders_book a " +
                        "join book b on a.book_no = b.no " +
                        "join orders c on a.order_no = c.no " +
                        "where a.order_no = ? and c.user_no = ?");
        ) {
            pstmt.setLong(1, no);
            pstmt.setLong(2, userNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Long orderNo = rs.getLong(1);
                    Long bookNo = rs.getLong(2);
                    int quantity = rs.getInt(3);
                    int price = rs.getInt(4);
                    String title = rs.getString(5);

                    OrderBookVo vo = new OrderBookVo();
                    vo.setOrderNo(orderNo);
                    vo.setBookNo(bookNo);
                    vo.setBookTitle(title);
                    vo.setQuantity(quantity);
                    vo.setPrice(price);

                    result.add(vo);
                }
            }
        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }

    public int deleteBooksByNo(Long no) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from orders_book where order_no = ?");
        ) {
            pstmt.setLong(1, no);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;

    }

    public int deleteByNo(Long no) {
        int result = 0;
        try (
                Connection conn = getConnection();
                PreparedStatement pstmt = conn.prepareStatement("delete from orders where no = ?");
        ) {
            pstmt.setLong(1, no);
            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("error:" + e);
        }
        return result;
    }
}
