package bookmall.dao;

import bookmall.vo.BookVo;
import bookmall.vo.CategoryVo;
import org.junit.jupiter.api.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoTest {
    private static int count = 0;
    private static CategoryVo mockCategoryVo01 = null;
    private static CategoryDao categoryDao = new CategoryDao();
    private static BookVo bookBookVo = null;
    private static BookDao bookDao = new BookDao();

    @BeforeAll
    public static void setUp() {
        mockCategoryVo01 = new CategoryVo("인문");
        categoryDao.insert(mockCategoryVo01);

        count = bookDao.findAll().size();
    }
    @Test
    @Order(1)
    public void testInsert() {
        bookBookVo = new BookVo("과학혁명의 구조", 20000);
        bookBookVo.setCategoryNo(mockCategoryVo01.getNo());
        bookDao.insert(bookBookVo);

        assertNotNull(bookBookVo.getNo());
    }

    @Test
    @Order(2)
    public void testFindAll() {
        assertEquals(count+1, bookDao.findAll().size());
    }

    @AfterAll
    public static void cleanUp() {
        bookDao.deleteByNo(bookBookVo.getNo());
    }
}
