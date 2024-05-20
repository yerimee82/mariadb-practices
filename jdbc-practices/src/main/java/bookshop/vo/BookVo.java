package bookshop.vo;

public class BookVo {
    private Long no;
    private String title;
    private String status;
    private Long authorNo;
    private String authorName;

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAuthorNo() {
        return authorNo;
    }

    public void setAuthorNo(Long authorNo) {
        this.authorNo = authorNo;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
