package Model.dao;

import Model.entity.Word;
import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class WordDao_Impl implements WordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfWord;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfWord;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfWord;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public WordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWord = new EntityInsertionAdapter<Word>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Word`(`id_word`,`word`,`amount`,`id_book`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Word value) {
        stmt.bindLong(1, value.getIdWord());
        if (value.getWord() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getWord());
        }
        stmt.bindLong(3, value.getAmount());
        stmt.bindLong(4, value.getId_book());
      }
    };
    this.__deletionAdapterOfWord = new EntityDeletionOrUpdateAdapter<Word>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Word` WHERE `id_word` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Word value) {
        stmt.bindLong(1, value.getIdWord());
      }
    };
    this.__updateAdapterOfWord = new EntityDeletionOrUpdateAdapter<Word>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Word` SET `id_word` = ?,`word` = ?,`amount` = ?,`id_book` = ? WHERE `id_word` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Word value) {
        stmt.bindLong(1, value.getIdWord());
        if (value.getWord() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getWord());
        }
        stmt.bindLong(3, value.getAmount());
        stmt.bindLong(4, value.getId_book());
        stmt.bindLong(5, value.getIdWord());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM Word";
        return _query;
      }
    };
  }

  @Override
  public void insert(Word word) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfWord.insert(word);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Word word) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfWord.handle(word);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Word word) {
    __db.beginTransaction();
    try {
      __updateAdapterOfWord.handle(word);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<Word> getWords() {
    final String _sql = "SELECT * FROM Word";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdWord = _cursor.getColumnIndexOrThrow("id_word");
      final int _cursorIndexOfWord = _cursor.getColumnIndexOrThrow("word");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
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
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int existWord(final String word) {
    final String _sql = "SELECT count(*) FROM Word where word =?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (word == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, word);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countWords() {
    final String _sql = "SELECT count(*) FROM Word";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Word getWordByValue(final String word) {
    final String _sql = "SELECT * FROM Word where word=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (word == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, word);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdWord = _cursor.getColumnIndexOrThrow("id_word");
      final int _cursorIndexOfWord = _cursor.getColumnIndexOrThrow("word");
      final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
      final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
      final Word _result;
      if(_cursor.moveToFirst()) {
        final String _tmpWord;
        _tmpWord = _cursor.getString(_cursorIndexOfWord);
        final int _tmpAmount;
        _tmpAmount = _cursor.getInt(_cursorIndexOfAmount);
        final int _tmpId_book;
        _tmpId_book = _cursor.getInt(_cursorIndexOfIdBook);
        _result = new Word(_tmpWord,_tmpAmount,_tmpId_book);
        final int _tmpIdWord;
        _tmpIdWord = _cursor.getInt(_cursorIndexOfIdWord);
        _result.setIdWord(_tmpIdWord);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<Word>> getWordsLiveData() {
    final String _sql = "SELECT * FROM Word";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Word>>() {
      private Observer _observer;

      @Override
      protected List<Word> compute() {
        if (_observer == null) {
          _observer = new Observer("Word") {
            @Override
            public void onInvalidated() {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfIdWord = _cursor.getColumnIndexOrThrow("id_word");
          final int _cursorIndexOfWord = _cursor.getColumnIndexOrThrow("word");
          final int _cursorIndexOfAmount = _cursor.getColumnIndexOrThrow("amount");
          final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
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
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}
