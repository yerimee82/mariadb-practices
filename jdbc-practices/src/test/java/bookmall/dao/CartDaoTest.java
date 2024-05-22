package bookmall.dao;

import bookmall.vo.BookVo;
import bookmall.vo.CartVo;
import bookmall.vo.CategoryVo;
import bookmall.vo.UserVo;
import org.junit.jupiter.api.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartDaoTest {
    private static int count = 0;
    private static CartVo mockCart = new CartVo();
    private static UserVo mockUserVo01 =  null;
    private static UserDao userDao = new UserDao();
    private static CategoryDao categoryDao = new CategoryDao();
    private static CategoryVo mockCategoryVo01 = null;
    private static BookVo mockBookVo01 = null;
    private static BookDao bookDao = new BookDao();
    private static CartDao cartDao = new CartDao();

    @BeforeAll
    public static void setUp() {
        mockUserVo01 = new UserVo("데스트유저01", "test01@test.com", "1234", "010-0000-0000");
        userDao.insert(mockUserVo01);

        mockCategoryVo01 = new CategoryVo("인문");
        categoryDao.insert(mockCategoryVo01);

        mockBookVo01 = new BookVo("과학혁명의 구조", 20000);
        mockBookVo01.setCategoryNo(mockCategoryVo01.getNo());
        bookDao.insert(mockBookVo01);

        count = cartDao.findByUserNo(mockUserVo01.getNo()).size();
    }
    @Test
    @Order(1)
    public void testInsert() {
        mockCart.setUserNo(mockUserVo01.getNo());
        mockCart.setBookNo(mockBookVo01.getNo());
        mockCart.setQuantity(1);
        cartDao.insert(mockCart);

        assertNotNull(mockCart.getUserNo());
    }

    @Test
    @Order(2)
    public void testFindByUserNo() {
        assertEquals(count + 1, cartDao.findByUserNo(mockUserVo01.getNo()).size());
    }

    @AfterAll
    public static void cleanUp() {
        cartDao.deleteByUserNoAndBookNo(mockUserVo01.getNo(), mockBookVo01.getNo());
        bookDao.deleteByNo(mockBookVo01.getNo());
        categoryDao.deleteByNo(mockCategoryVo01.getNo());
        userDao.deleteByNo(mockUserVo01.getNo());
    }
}
