package by.bsu.library.entity;

import by.bsu.library.util.DateGenerator;

import java.sql.Date;

public class TakenBookInfo {

    private long id;
    private ItemInfo itemInfo;
    private Reader reader;
    private Date returnDate;

    public TakenBookInfo() {
        this.itemInfo = new ItemInfo();
        this.reader = new Reader();
        this.returnDate = DateGenerator.dateOfReturn();
    }

    public TakenBookInfo(ItemInfo itemInfo, Reader reader, Date returnDate) {
        this.itemInfo = itemInfo;
        this.reader = reader;
        this.returnDate = returnDate;
    }

    public TakenBookInfo(long id, ItemInfo itemInfo, Reader reader, Date returnDate) {
        this.id = id;
        this.itemInfo = itemInfo;
        this.reader = reader;
        this.returnDate = returnDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "TakenBookInfo{" +
                "id=" + id +
                ", itemInfo=" + itemInfo +
                ", reader=" + reader +
                ", returnDate=" + returnDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TakenBookInfo that = (TakenBookInfo) o;

        if (id != that.id) return false;
        if (itemInfo != null ? !itemInfo.equals(that.itemInfo) : that.itemInfo != null) return false;
        if (reader != null ? !reader.equals(that.reader) : that.reader != null) return false;
        return returnDate != null ? returnDate.equals(that.returnDate) : that.returnDate == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (itemInfo != null ? itemInfo.hashCode() : 0);
        result = 31 * result + (reader != null ? reader.hashCode() : 0);
        result = 31 * result + (returnDate != null ? returnDate.hashCode() : 0);
        return result;
    }
}
