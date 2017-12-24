package Model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Book")
public class Book {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_book")
    private int idBook;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "number_pages")
    private int numberPages;


    public Book(int idBook, String name, int numberPages) {
        this.idBook = idBook;
        this.name = name;
        this.numberPages = numberPages;
    }

    public int getIdBook() {
        return idBook;
    }

    public void setIdBook(int idBook) {
        this.idBook = idBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberPages() {
        return numberPages;
    }

    public void setNumberPages(int numberPages) {
        this.numberPages = numberPages;
    }
}
