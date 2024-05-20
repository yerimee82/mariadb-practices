package bookmall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import bookmall.dao.BookDao;
import bookmall.dao.CartDao;
import bookmall.dao.CategoryDao;
import bookmall.dao.OrderDao;
import bookmall.dao.UserDao;
import bookmall.vo.BookVo;
import bookmall.vo.CartVo;
import bookmall.vo.CategoryVo;
import bookmall.vo.OrderBookVo;
import bookmall.vo.OrderVo;
import bookmall.vo.UserVo;

/**
 *
 *  [경고!!] 수정하면 빵(0)점 입니다.
 *
 */
public class BookMallTest {

	private static UserVo mockUserVo01 = new UserVo("데스트유저01", "test01@test.com", "1234", "010-0000-0000");
	private static UserVo mockUserVo02 = new UserVo("데스트유저02", "test02@test.com", "1234", "010-1111-1111");

	private static CategoryVo mockCategoryVo01 = new CategoryVo("인문");
	private static CategoryVo mockCategoryVo02 = new CategoryVo("컴퓨터/IT");
	private static CategoryVo mockCategoryVo03 = new CategoryVo("예술");

	private static BookVo mockBookVo01 = new BookVo("과학혁명의 구조", 20000);
	private static BookVo mockBookVo02 = new BookVo("J2EE Development Without EJB", 32000);
	private static BookVo mockBookVo03 = new BookVo("서양미술사", 50000);

	private static CartVo mockCartVo01 = new CartVo();
	private static CartVo mockCartVo02 = new CartVo();

	private static OrderVo mockOrderVo = new OrderVo();

	private static OrderBookVo mockOrderBookVo01 = new OrderBookVo();
	private static OrderBookVo mockOrderBookVo02 = new OrderBookVo();

	private static UserDao userDao = new UserDao();
	private static CategoryDao categoryDao = new CategoryDao();
	private static BookDao bookDao = new BookDao();
	private static CartDao cartDao = new CartDao();
	private static OrderDao orderDao = new OrderDao();

	@BeforeAll
	public static void setUp() {
		// 사용자 추가(2명)
		userDao.insert(mockUserVo01);
		userDao.insert(mockUserVo02);

		// 카테고리 등록(3개)
		categoryDao.insert(mockCategoryVo01);
		categoryDao.insert(mockCategoryVo02);
		categoryDao.insert(mockCategoryVo03);

		// 서적 등록(3개)
		mockBookVo01.setCategoryNo(mockCategoryVo01.getNo());
		bookDao.insert(mockBookVo01);

		mockBookVo02.setCategoryNo(mockCategoryVo02.getNo());
		bookDao.insert(mockBookVo02);

		mockBookVo03.setCategoryNo(mockCategoryVo03.getNo());
		bookDao.insert(mockBookVo03);

		// 카트 담기(2개)
		mockCartVo01.setUserNo(mockUserVo01.getNo());
		mockCartVo01.setBookNo(mockBookVo01.getNo());
		mockCartVo01.setQuantity(1);
		cartDao.insert(mockCartVo01);

		mockCartVo02.setUserNo(mockUserVo01.getNo());
		mockCartVo02.setBookNo(mockBookVo02.getNo());
		mockCartVo02.setQuantity(2);
		cartDao.insert(mockCartVo02);

		// 주문하기(1개)
		mockOrderVo.setUserNo(mockUserVo01.getNo());
		mockOrderVo.setNumber("20240520-000012");
		mockOrderVo.setPayment(82400);
		mockOrderVo.setShipping("서울시 은평구 진관3로 77 구파발 래미안 926-801");
		mockOrderVo.setStatus("배송준비");
		orderDao.insert(mockOrderVo);

		// 주문책(2개)
		mockOrderBookVo01.setOrderNo(mockOrderVo.getNo());
		mockOrderBookVo01.setBookNo(mockBookVo01.getNo());
		mockOrderBookVo01.setQuantity(1);
		mockOrderBookVo01.setPrice(20000);
		orderDao.insertBook(mockOrderBookVo01);

		mockOrderBookVo02.setOrderNo(mockOrderVo.getNo());
		mockOrderBookVo02.setBookNo(mockBookVo02.getNo());
		mockOrderBookVo02.setQuantity(2);
		mockOrderBookVo02.setPrice(64000);
		orderDao.insertBook(mockOrderBookVo02);
	}

	@Test
	public void testUser() {
		assertEquals(2, userDao.findAll().size());
	}

	@Test
	public void testCategory() {
		assertEquals(3, categoryDao.findAll().size());
	}

	@Test
	public void testCart() {
		List<CartVo> list = cartDao.findByUserNo(mockUserVo01.getNo());

		assertEquals(2, list.size());

		assertEquals(mockBookVo01.getNo(), list.get(0).getBookNo());
		assertEquals(mockBookVo01.getTitle(), list.get(0).getBookTitle());
		assertEquals(mockCartVo01.getQuantity(), list.get(0).getQuantity());

		assertEquals(mockBookVo02.getNo(), list.get(1).getBookNo());
		assertEquals(mockBookVo02.getTitle(), list.get(1).getBookTitle());
		assertEquals(mockCartVo02.getQuantity(), list.get(1).getQuantity());
	}

	@Test
	public void testOrder() {
		OrderVo vo = null;

		vo = orderDao.findByNoAndUserNo(1234567L, mockUserVo01.getNo());
		assertNull(vo);

		vo = orderDao.findByNoAndUserNo(mockOrderVo.getNo(), mockUserVo01.getNo());
		assertNotNull(vo);
		assertEquals(mockOrderVo.getNumber(), vo.getNumber());
		assertEquals(mockOrderVo.getPayment(), vo.getPayment());
		assertEquals(mockOrderVo.getStatus(), vo.getStatus());
		assertEquals(mockOrderVo.getShipping(), vo.getShipping());
	}

	@Test
	public void testOrderBooks() {
		List<OrderBookVo> list = orderDao.findBooksByNoAndUserNo(mockOrderVo.getNo(), mockUserVo01.getNo());

		assertEquals(2, list.size());

		assertEquals(mockOrderBookVo01.getOrderNo(), list.get(0).getOrderNo());
		assertEquals(mockOrderBookVo01.getQuantity(), list.get(0).getQuantity());
		assertEquals(mockOrderBookVo01.getPrice(), list.get(0).getPrice());
		assertEquals(mockOrderBookVo01.getBookNo(), list.get(0).getBookNo());
		assertEquals(mockBookVo01.getTitle(), list.get(0).getBookTitle());

		assertEquals(mockOrderBookVo02.getOrderNo(), list.get(1).getOrderNo());
		assertEquals(mockOrderBookVo02.getQuantity(), list.get(1).getQuantity());
		assertEquals(mockOrderBookVo02.getPrice(), list.get(1).getPrice());
		assertEquals(mockOrderBookVo02.getBookNo(), list.get(1).getBookNo());
		assertEquals(mockBookVo02.getTitle(), list.get(1).getBookTitle());
	}

	@AfterAll
	public static void cleanUp() {
		//주문책
		orderDao.deleteBooksByNo(mockOrderVo.getNo());

		// 주문
		orderDao.deleteByNo(mockOrderVo.getNo());

		// 카트
		cartDao.deleteByUserNoAndBookNo(mockCartVo01.getUserNo(), mockBookVo01.getNo());
		cartDao.deleteByUserNoAndBookNo(mockCartVo02.getUserNo(), mockBookVo02.getNo());

		// 서적
		bookDao.deleteByNo(mockBookVo01.getNo());
		bookDao.deleteByNo(mockBookVo02.getNo());
		bookDao.deleteByNo(mockBookVo03.getNo());

		// 카테고리
		categoryDao.deleteByNo(mockCategoryVo01.getNo());
		categoryDao.deleteByNo(mockCategoryVo02.getNo());
		categoryDao.deleteByNo(mockCategoryVo03.getNo());

		// 사용자
		userDao.deleteByNo(mockUserVo01.getNo());
		userDao.deleteByNo(mockUserVo02.getNo());
	}
}
