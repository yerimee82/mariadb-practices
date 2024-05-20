package bookshop.dao;

import bookshop.vo.AuthorVo;
import bookshop.vo.BookVo;
import org.junit.jupiter.api.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookDaoTest {
    private static int count = 0;
    private static AuthorDao authorDao = new AuthorDao();
    private static AuthorVo mockAuthorVo = new AuthorVo();
    private static BookDao bookDao = new BookDao();
    @BeforeAll
    public static void setUp() {
        mockAuthorVo.setName("칼세이건");
        authorDao.insert(mockAuthorVo);

        count = bookDao.findAll().size();
    }
    @Test
    public void testInsert() {
        BookVo vo = new BookVo();
        vo.setTitle("코스모스");
        vo.setAuthorNo(mockAuthorVo.getNo());
        bookDao.insert(vo);

        assertNotNull(vo.getNo());
    }

    public void testFindAll() {
        assertEquals(count+1, bookDao.findAll().size());
    }

    @AfterAll
    public static void cleanUp() {
        authorDao.deleteByNo(mockAuthorVo.getNo());
    }


}
