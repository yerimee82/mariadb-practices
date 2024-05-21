package bookmall.dao;

import bookmall.vo.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderDaoTest {
    private static int count = 0;
    private static UserVo userVo = null;
    private static UserDao userDao = new UserDao();
    private static OrderVo mockOrder = new OrderVo();
    private static OrderDao orderDao = new OrderDao();
    private static OrderBookVo orderBookVo = new OrderBookVo();
    private static BookVo bookVo = null;
    private static BookDao bookDao = new BookDao();

    private static CategoryVo categoryVo = null;
    private static CategoryDao categoryDao = new CategoryDao();
    @BeforeAll
    public static void setUp() {
        userVo = new UserVo("데스트유저01", "test01@test.com", "1234", "010-0000-0000");
        userDao.insert(userVo);

        categoryVo = new CategoryVo("과학");
        categoryDao.insert(categoryVo);

        bookVo = new BookVo("코스모스", 20000);
        bookVo.setCategoryNo(categoryVo.getNo());
        bookDao.insert(bookVo);

        mockOrder.setNo(1L);
        mockOrder.setNumber("20240520-000012");
        mockOrder.setPayment(82400);
        mockOrder.setShipping("서울시 은평구 진관3로 77 구파발 래미안 926-801");
        mockOrder.setStatus("배송준비");
        mockOrder.setUserNo(userVo.getNo());

    }

    @Test
    @Order(1)
    public void testInsert() {
        orderDao.insert(mockOrder);
        assertNotNull(mockOrder.getNo());
    }

    @Test
    @Order(2)
    public void testFindByNoAndUserNo() {
        OrderVo mockVo = orderDao.findByNoAndUserNo(mockOrder.getNo(), userVo.getNo());
        assertNotNull(mockVo);
    }

    @Test
    @Order(3)
    public void testInsertBook() {
        orderBookVo.setOrderNo(mockOrder.getNo());
        orderBookVo.setBookNo(bookVo.getNo());
        orderBookVo.setQuantity(1);
        orderBookVo.setPrice(20000);

        orderDao.insertBook(orderBookVo);
        assertNotNull(orderBookVo.getOrderNo());
    }

    @Test
    @Order(4)
    public void testFindBooksByNoAndUserNo() {
        List<OrderBookVo> orderBook = orderDao.findBooksByNoAndUserNo(orderBookVo.getOrderNo(), mockOrder.getUserNo());
        assertEquals(count+1, orderBook.size());
    }



    @AfterAll
    public static void cleanUp() {
        userDao.deleteByNo(userVo.getNo());
        categoryDao.deleteByNo(categoryVo.getNo());
        bookDao.deleteByNo(bookVo.getNo());
        orderDao.deleteByNo(mockOrder.getNo());
        orderDao.deleteBooksByNo(mockOrder.getNo());
    }
}
