package by.bsu.library.entity;

public class ItemInfo {

    private long itemID;
    private Book book;

    public ItemInfo() {
        this.book = new Book();
    }

    public ItemInfo(long itemID, Book book) {
        this.itemID = itemID;
        this.book = book;
    }

    public ItemInfo (long itemID, long bookID, String author, String title) {
        this.itemID = itemID;
        this.book = new Book(bookID, author, title);
    }

    public long getItemID() {
        return itemID;
    }

    public void setItemID(long itemID) {
        this.itemID = itemID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "itemID=" + itemID +
                ", book=" + book +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemInfo itemInfo = (ItemInfo) o;

        if (itemID != itemInfo.itemID) return false;
        return book != null ? book.equals(itemInfo.book) : itemInfo.book == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (itemID ^ (itemID >>> 32));
        result = 31 * result + (book != null ? book.hashCode() : 0);
        return result;
    }
}
