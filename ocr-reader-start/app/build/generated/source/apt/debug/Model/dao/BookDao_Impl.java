package Model.dao;

import Model.entity.Book;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class BookDao_Impl implements BookDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfBook;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfBook;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfBook;

  public BookDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBook = new EntityInsertionAdapter<Book>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Book`(`id_book`,`name`,`number_pages`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Book value) {
        stmt.bindLong(1, value.getIdBook());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getNumberPages());
      }
    };
    this.__deletionAdapterOfBook = new EntityDeletionOrUpdateAdapter<Book>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Book` WHERE `id_book` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Book value) {
        stmt.bindLong(1, value.getIdBook());
      }
    };
    this.__updateAdapterOfBook = new EntityDeletionOrUpdateAdapter<Book>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Book` SET `id_book` = ?,`name` = ?,`number_pages` = ? WHERE `id_book` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Book value) {
        stmt.bindLong(1, value.getIdBook());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getNumberPages());
        stmt.bindLong(4, value.getIdBook());
      }
    };
  }

  @Override
  public void insert(Book book) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfBook.insert(book);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Book book) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfBook.handle(book);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Book book) {
    __db.beginTransaction();
    try {
      __updateAdapterOfBook.handle(book);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Book> getWords() {
    final String _sql = "SELECT * FROM Book";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfNumberPages = _cursor.getColumnIndexOrThrow("number_pages");
      final List<Book> _result = new ArrayList<Book>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Book _item;
        final int _tmpIdBook;
        _tmpIdBook = _cursor.getInt(_cursorIndexOfIdBook);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final int _tmpNumberPages;
        _tmpNumberPages = _cursor.getInt(_cursorIndexOfNumberPages);
        _item = new Book(_tmpIdBook,_tmpName,_tmpNumberPages);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int existBook(final String bookName) {
    final String _sql = "SELECT count(*) FROM Book where name=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (bookName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, bookName);
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
  public Book getBook(final String bookName) {
    final String _sql = "SELECT * FROM Book where name=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (bookName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, bookName);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final int _cursorIndexOfNumberPages = _cursor.getColumnIndexOrThrow("number_pages");
      final Book _result;
      if(_cursor.moveToFirst()) {
        final int _tmpIdBook;
        _tmpIdBook = _cursor.getInt(_cursorIndexOfIdBook);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        final int _tmpNumberPages;
        _tmpNumberPages = _cursor.getInt(_cursorIndexOfNumberPages);
        _result = new Book(_tmpIdBook,_tmpName,_tmpNumberPages);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
