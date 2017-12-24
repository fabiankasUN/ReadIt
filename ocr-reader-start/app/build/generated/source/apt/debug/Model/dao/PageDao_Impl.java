package Model.dao;

import Model.entity.Page;
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

public class PageDao_Impl implements PageDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPage;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPage;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPage;

  public PageDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPage = new EntityInsertionAdapter<Page>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Page`(`id_page`,`page_number`,`id_book`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Page value) {
        stmt.bindLong(1, value.getIdPage());
        stmt.bindLong(2, value.getPageNumber());
        stmt.bindLong(3, value.getId_book());
      }
    };
    this.__deletionAdapterOfPage = new EntityDeletionOrUpdateAdapter<Page>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Page` WHERE `id_page` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Page value) {
        stmt.bindLong(1, value.getIdPage());
      }
    };
    this.__updateAdapterOfPage = new EntityDeletionOrUpdateAdapter<Page>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `Page` SET `id_page` = ?,`page_number` = ?,`id_book` = ? WHERE `id_page` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Page value) {
        stmt.bindLong(1, value.getIdPage());
        stmt.bindLong(2, value.getPageNumber());
        stmt.bindLong(3, value.getId_book());
        stmt.bindLong(4, value.getIdPage());
      }
    };
  }

  @Override
  public void insert(Page word) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPage.insert(word);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Page word) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPage.handle(word);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(Page word) {
    __db.beginTransaction();
    try {
      __updateAdapterOfPage.handle(word);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Page> getWords() {
    final String _sql = "SELECT * FROM Page";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfIdPage = _cursor.getColumnIndexOrThrow("id_page");
      final int _cursorIndexOfPageNumber = _cursor.getColumnIndexOrThrow("page_number");
      final int _cursorIndexOfIdBook = _cursor.getColumnIndexOrThrow("id_book");
      final List<Page> _result = new ArrayList<Page>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Page _item;
        final int _tmpIdPage;
        _tmpIdPage = _cursor.getInt(_cursorIndexOfIdPage);
        final int _tmpPageNumber;
        _tmpPageNumber = _cursor.getInt(_cursorIndexOfPageNumber);
        final int _tmpId_book;
        _tmpId_book = _cursor.getInt(_cursorIndexOfIdBook);
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
