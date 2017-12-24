package Model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.ArrayList;
import java.util.List;

import Model.entity.Page;


@Dao
public interface PageDao {


    @Insert
    void insert( Page word );

    @Update
    void update( Page word);

    @Delete
    void delete( Page word);

    @Query("SELECT * FROM Page")
    List<Page> getWords();
}
