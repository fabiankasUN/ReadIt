package Model.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import Model.entity.Word;

@Dao
public interface WordDao {

    @Insert
    void insert( Word word );

    @Update
    void update( Word word);

    @Delete
    void delete( Word word);

    @Query("DELETE FROM Word")
    void deleteAll();

    @Query("SELECT * FROM Word")
    List<Word> getWords();

    @Query("SELECT count(*) FROM Word where word =:word")
    int existWord(final String word);

    @Query("SELECT count(*) FROM Word")
    int countWords();

    @Query("SELECT * FROM Word where word=:word")
    Word getWordByValue(final String word);
}
