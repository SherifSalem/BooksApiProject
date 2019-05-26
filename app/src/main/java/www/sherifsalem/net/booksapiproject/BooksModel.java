package www.sherifsalem.net.booksapiproject;

public class BooksModel {
    private String bookTitle;
    private String[] autherName;

    public BooksModel(String bookTitle, String[] autherName) {
        this.bookTitle = bookTitle;
        this.autherName = autherName;
    }


    public String getBookTitle() {
        return bookTitle;
    }

    public String[] getAutherName() {
        return autherName;
    }
}
