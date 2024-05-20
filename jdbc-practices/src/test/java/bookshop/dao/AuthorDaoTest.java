package bookshop.dao;

import bookshop.vo.AuthorVo;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;

import static junit.framework.TestCase.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthorDaoTest {
    private static int count = 0;
    private static AuthorDao authorDao = new AuthorDao();
    private static AuthorVo mockAuthorWo = new AuthorVo();

    @BeforeAll
    public static void setUp(){
        count = authorDao.findAll().size();
    }

    @Test
    @Order(1)
    public void testInsert() {
        mockAuthorWo.setName("칼세이건");
        authorDao.insert(mockAuthorWo);

        assertNotNull(mockAuthorWo.getNo());
    }
    @Test
    @Order(2)
    public void testFindAll() {
        assertEquals(count + 1, authorDao.findAll().size());
    }

    @AfterAll
    public static void cleanUp() {
        authorDao.deleteByNo(mockAuthorWo.getNo());
    }
}
