package Model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Fabian on 15/12/2017.
 */
@Entity(tableName = "Page"
        ,foreignKeys = @ForeignKey(entity = Book.class,parentColumns = "id_book",
                                              childColumns = "id_book" ))
public class Page {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_page")
    private int idPage;

    @ColumnInfo(name = "page_number")
    private int pageNumber;

    @ColumnInfo(name = "id_book")
    private int id_book;

    public Page(int idPage, int pageNumber, int id_book) {
        this.idPage = idPage;
        this.pageNumber = pageNumber;
        this.id_book = id_book;
    }

    public int getIdPage() {
        return idPage;
    }

    public void setIdPage(int idPage) {
        this.idPage = idPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }
}
