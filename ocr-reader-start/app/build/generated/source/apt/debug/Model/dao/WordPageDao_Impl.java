package Model.dao;

import Model.entity.Page;
import Model.entity.Word;
import Model.entity.WordPage;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class WordPageDao_Impl implements WordPageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfWordPage;

  public WordPageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWordPage = new EntityInsertionAdapter<WordPage>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `WordPage`(`id_word`,`id_page`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, WordPage value) {
        stmt.bindLong(1, value.id_word);
        stmt.bindLong(2, value.id_page);
      }
    };
  }

  @Override
  public void insert(WordPage wordPage) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfWordPage.insert(wordPage);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Word> getWordForPage(final int idPage) {
    final String _sql = "SELECT * FROM Word INNER JOIN WordPage ON Word.id_word = WordPage.id_word WHERE WordPage.id_page=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idPage);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdWord = _cursor.getColumnIndexOrThrow("id_word");
      final int _cursorIndexOfWord = _cursor.getColumnIndexOrThrow("word");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
      final int _cursorIndexOfIdWord_1 = _cursor.getColumnIndexOrThrow("id_word");
      final List<Word> _result = new ArrayList<Word>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Word _item;
        final String _tmpWord;
        _tmpWord = _cursor.getString(_cursorIndexOfWord);
        final int _tmpAmount;
        _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
        final int _tmpId_book;
        _tmpId_book = _cursor.getInt(_cursorIndexOfIdBook);
        _item = new Word(_tmpWord,_tmpAmount,_tmpId_book);
        final int _tmpIdWord;
        _tmpIdWord = _cursor.getInt(_cursorIndexOfIdWord);
        _item.setIdWord(_tmpIdWord);
        final int _tmpIdWord_1;
        _tmpIdWord_1 = _cursor.getInt(_cursorIndexOfIdWord_1);
        _item.setIdWord(_tmpIdWord_1);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Page> getPagesForWord(final int idWord) {
    final String _sql = "SELECT * FROM Page INNER JOIN WordPage ON Page.id_page = WordPage.id_page WHERE WordPage.id_word=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, idWord);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdPage = _cursor.getColumnIndexOrThrow("id_page");
      final int _cursorIndexOfPageNumber = _cursor.getColumnIndexOrThrow("page_number");
      final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
      final int _cursorIndexOfIdPage_1 = _cursor.getColumnIndexOrThrow("id_page");
      final List<Page> _result = new ArrayList<Page>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Page _item;
        final int _tmpIdPage;
        _tmpIdPage = _cursor.getInt(_cursorIndexOfIdPage);
        final int _tmpPageNumber;
        _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
        final int _tmpId_book;
        _tmpId_book = _cursor.getInt(_cursorIndexOfIdBook);
        final int _tmpIdPage_1;
        _tmpIdPage_1 = _cursor.getInt(_cursorIndexOfIdPage_1);
        _item = new Page(_tmpIdPage,_tmpPageNumber,_tmpId_book);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
