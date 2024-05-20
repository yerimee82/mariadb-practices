package emaillist.vo;

import emaillist.dao.EmailListDao;
import org.junit.jupiter.api.*;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmailListDaoTest {
    private static int count = 0;

    @BeforeAll
    public static void setUp() {
        new EmailListDao();
        List<EmailListVo> list = EmailListDao.findAll();
        count = list.size();
    }

    @Test
    @Order(1)
    public void testInsert() {
        EmailListVo vo = new EmailListVo();
        vo.setFirstName("둘");
        vo.setLastName("리");
        vo.setEmail("dooly@gmail.com");

        boolean result = new EmailListDao().insert(vo);
        assertTrue(result);

    }
    @Test
    @Order(2)
    public void testFindAll() {
        List<EmailListVo> list = new EmailListDao().findAll();
        assertEquals(count + 1, list.size());
    }

    @Test
    @Order(3)
    public void testDeleteByEmail() {
        boolean result = new EmailListDao().deleteByEmail("dooly@gmail.com");
        assertTrue(result);
    }



    @AfterAll
    public static void cleanUp() {

    }

}
