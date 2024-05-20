package bookshop.example;

public class Book {
	private int bookNo;
	private String title;
	private String author;
	private int stateCode;

	public Book(int bookNo, String title, String author) {
		this.bookNo = bookNo;
		this.title = title;
		this.author = author;
		this.stateCode = 1;
	}

	public void rent() {
		if (stateCode != 0) {
			stateCode = 0;
			System.out.println(title + " 이(가) 대여 됐습니다.");
		} else {
			System.out.println("다른 고객이 이미 대여중 입니다.");
		}
	}

	public void print(int stateCode) {
		if (stateCode == 1) {
			System.out.println("재고있음");
		} else {
			System.out.println("재고없음");
		}
	}

	public int getBookNo() {
		return bookNo;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}
	
	public int getStateCode() {
		return stateCode;
	}
}
