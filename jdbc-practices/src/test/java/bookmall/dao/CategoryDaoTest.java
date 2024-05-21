package bookmall.dao;

import bookmall.vo.CategoryVo;
import org.junit.jupiter.api.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryDaoTest {
    private static int count = 0;
    private static CategoryVo mockCategoryVo01 = null;
    private static CategoryDao categoryDao = new CategoryDao();

    @BeforeAll
    public static void setUp() {
        mockCategoryVo01 = new CategoryVo("인문");
        count = categoryDao.findAll().size();
    }
    @Test
    @Order(1)
    public void testInsert() {
        categoryDao.insert(mockCategoryVo01);
        assertNotNull(mockCategoryVo01.getNo());
    }

    @Test
    @Order(2)
    public void testFindAll() {
        assertEquals(count + 1, categoryDao.findAll().size());
    }

    @AfterAll
    public static void cleanUp() {
        categoryDao.deleteByNo(mockCategoryVo01.getNo());
    }
}
