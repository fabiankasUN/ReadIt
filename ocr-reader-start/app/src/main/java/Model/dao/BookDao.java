package Model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Model.entity.Book;


@Dao
public interface BookDao {

    @Insert
    void insert( Book book );

    @Update
    void update( Book book);

    @Delete
    void delete( Book book);

    @Query("SELECT * FROM Book")
    List<Book> getWords();

    @Query("SELECT count(*) FROM Book where name=:bookName")
    int existBook(final String bookName);

    @Query("SELECT * FROM Book where name=:bookName")
    Book getBook(final String bookName);

}
