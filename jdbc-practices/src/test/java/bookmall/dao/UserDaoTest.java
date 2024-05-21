package bookmall.dao;

import bookmall.vo.UserVo;
import org.junit.jupiter.api.*;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTest {
    private static int count = 0;
    private static UserVo mockUserVo01 = null;
    private static UserDao userDao = new UserDao();

    @BeforeAll
    public static void setUp() {
        mockUserVo01 = new UserVo("데스트유저01", "test01@test.com", "1234", "010-0000-0000");
        count = userDao.findAll().size();
    }
    @Test
    @Order(1)
    public void testInsert() {
        userDao.insert(mockUserVo01);
        assertNotNull(mockUserVo01.getNo());
    }

    @Test
    @Order(2)
    public void testFindAll() {
        assertEquals(count + 1, userDao.findAll().size());
    }

    @AfterAll
    public static void cleanUp() {
        userDao.deleteByNo(mockUserVo01.getNo());
    }
}
