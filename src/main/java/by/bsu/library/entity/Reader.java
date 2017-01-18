package by.bsu.library.entity;


public class Reader {

    private long readerID;
    private String name;
    private String password;

    public Reader() {

    }

    public Reader(long readerID, String name, String password) {
        this.readerID = readerID;
        this.name = name;
        this.password = password;
    }

    public long getReaderID() {
        return readerID;
    }

    public void setReaderID(long readerID) {
        this.readerID = readerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Reader{" +
                "readerID=" + readerID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        if (readerID != reader.readerID) return false;
        if (name != null ? !name.equals(reader.name) : reader.name != null) return false;
        return password != null ? password.equals(reader.password) : reader.password == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (readerID ^ (readerID >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
