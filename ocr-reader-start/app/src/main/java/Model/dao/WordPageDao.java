package Model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import Model.entity.Page;
import Model.entity.Word;
import Model.entity.WordPage;

/**
 * Created by Fabian on 16/12/2017.
 */
@Dao
public interface WordPageDao {

    @Insert
    void insert(WordPage wordPage);

    @Query("SELECT * FROM Word INNER JOIN WordPage ON Word.id_word = WordPage.id_word WHERE WordPage.id_page=:idPage")
    List<Word> getWordForPage(final int idPage);

    @Query("SELECT * FROM Page INNER JOIN WordPage ON Page.id_page = WordPage.id_page WHERE WordPage.id_word=:idWord")
    List<Page> getPagesForWord(final int idWord);

}
