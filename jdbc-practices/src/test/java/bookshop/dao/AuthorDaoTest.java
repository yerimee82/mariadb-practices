package bookshop.dao;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertTrue;

public class AuthorDaoTest {
    private static int count = 0;
    private static AuthorDao authorDao = new AuthorDao();

    @BeforeAll
    public static void setUp(){
        count = authorDao.findAll().size();
    }

    @Test
    public void testInsert() {
        assertTrue(1-1 == 0);
    }


    @AfterAll
    public static void cleanUp() {

    }
}
