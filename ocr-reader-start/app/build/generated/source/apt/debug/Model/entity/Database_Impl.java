package Model.entity;

import Model.dao.BookDao;
import Model.dao.BookDao_Impl;
import Model.dao.PageDao;
import Model.dao.PageDao_Impl;
import Model.dao.WordDao;
import Model.dao.WordDao_Impl;
import Model.dao.WordPageDao;
import Model.dao.WordPageDao_Impl;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Database_Impl extends Database {
  private volatile BookDao _bookDao;

  private volatile PageDao _pageDao;

  private volatile WordDao _wordDao;

  private volatile WordPageDao _wordPageDao;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate() {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Book` (`id_book` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `number_pages` INTEGER)");
        _db.execSQL("CREATE  INDEX `index_Book_id_book`\n"
                + "ON `Book` (`id_book`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Page` (`id_page` INTEGER PRIMARY KEY AUTOINCREMENT, `page_number` INTEGER, `id_book` INTEGER, FOREIGN KEY(`id_book`) REFERENCES `Book`(`id_book`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE  INDEX `index_Page_id_page`\n"
                + "ON `Page` (`id_page`)");
        _db.execSQL("CREATE  INDEX `index_Page_id_book`\n"
                + "ON `Page` (`id_book`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Word` (`id_word` INTEGER PRIMARY KEY AUTOINCREMENT, `word` TEXT, `amount` INTEGER, `id_book` INTEGER, FOREIGN KEY(`id_book`) REFERENCES `Book`(`id_book`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE UNIQUE INDEX `index_Word_id_word_id_book`\n"
                + "ON `Word` (`id_word`, `id_book`)");
        _db.execSQL("CREATE  INDEX `index_Word_id_word`\n"
                + "ON `Word` (`id_word`)");
        _db.execSQL("CREATE  INDEX `index_Word_id_book`\n"
                + "ON `Word` (`id_book`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `WordPage` (`id_word` INTEGER, `id_page` INTEGER, PRIMARY KEY(`id_word`, `id_page`), FOREIGN KEY(`id_word`) REFERENCES `Word`(`id_word`) ON UPDATE NO ACTION ON DELETE NO ACTION , FOREIGN KEY(`id_page`) REFERENCES `Page`(`id_page`) ON UPDATE NO ACTION ON DELETE NO ACTION )");
        _db.execSQL("CREATE  INDEX `index_WordPage_id_word`\n"
                + "ON `WordPage` (`id_word`)");
        _db.execSQL("CREATE  INDEX `index_WordPage_id_page`\n"
                + "ON `WordPage` (`id_page`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"b8f34514d17c820e2283c2864b1d95a6\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Book`");
        _db.execSQL("DROP TABLE IF EXISTS `Page`");
        _db.execSQL("DROP TABLE IF EXISTS `Word`");
        _db.execSQL("DROP TABLE IF EXISTS `WordPage`");
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsBook = new HashMap<String, TableInfo.Column>(3);
        _columnsBook.put("id_book", new TableInfo.Column("id_book", "INTEGER", 1));
        _columnsBook.put("name", new TableInfo.Column("name", "TEXT", 0));
        _columnsBook.put("number_pages", new TableInfo.Column("number_pages", "INTEGER", 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBook = new HashSet<TableInfo.ForeignKey>(0);
        final TableInfo _infoBook = new TableInfo("Book", _columnsBook, _foreignKeysBook);
        final TableInfo _existingBook = TableInfo.read(_db, "Book");
        if (! _infoBook.equals(_existingBook)) {
          throw new IllegalStateException("Migration didn't properly handle Book(Model.entity.Book).\n"
                  + " Expected:\n" + _infoBook + "\n"
                  + " Found:\n" + _existingBook);
        }
        final HashMap<String, TableInfo.Column> _columnsPage = new HashMap<String, TableInfo.Column>(3);
        _columnsPage.put("id_page", new TableInfo.Column("id_page", "INTEGER", 1));
        _columnsPage.put("page_number", new TableInfo.Column("page_number", "INTEGER", 0));
        _columnsPage.put("id_book", new TableInfo.Column("id_book", "INTEGER", 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPage = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPage.add(new TableInfo.ForeignKey("Book", "NO ACTION", "NO ACTION",Arrays.asList("id_book"), Arrays.asList("id_book")));
        final TableInfo _infoPage = new TableInfo("Page", _columnsPage, _foreignKeysPage);
        final TableInfo _existingPage = TableInfo.read(_db, "Page");
        if (! _infoPage.equals(_existingPage)) {
          throw new IllegalStateException("Migration didn't properly handle Page(Model.entity.Page).\n"
                  + " Expected:\n" + _infoPage + "\n"
                  + " Found:\n" + _existingPage);
        }
        final HashMap<String, TableInfo.Column> _columnsWord = new HashMap<String, TableInfo.Column>(4);
        _columnsWord.put("id_word", new TableInfo.Column("id_word", "INTEGER", 1));
        _columnsWord.put("word", new TableInfo.Column("word", "TEXT", 0));
        _columnsWord.put("amount", new TableInfo.Column("amount", "INTEGER", 0));
        _columnsWord.put("id_book", new TableInfo.Column("id_book", "INTEGER", 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWord = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysWord.add(new TableInfo.ForeignKey("Book", "NO ACTION", "NO ACTION",Arrays.asList("id_book"), Arrays.asList("id_book")));
        final TableInfo _infoWord = new TableInfo("Word", _columnsWord, _foreignKeysWord);
        final TableInfo _existingWord = TableInfo.read(_db, "Word");
        if (! _infoWord.equals(_existingWord)) {
          throw new IllegalStateException("Migration didn't properly handle Word(Model.entity.Word).\n"
                  + " Expected:\n" + _infoWord + "\n"
                  + " Found:\n" + _existingWord);
        }
        final HashMap<String, TableInfo.Column> _columnsWordPage = new HashMap<String, TableInfo.Column>(2);
        _columnsWordPage.put("id_word", new TableInfo.Column("id_word", "INTEGER", 1));
        _columnsWordPage.put("id_page", new TableInfo.Column("id_page", "INTEGER", 2));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWordPage = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysWordPage.add(new TableInfo.ForeignKey("Word", "NO ACTION", "NO ACTION",Arrays.asList("id_word"), Arrays.asList("id_word")));
        _foreignKeysWordPage.add(new TableInfo.ForeignKey("Page", "NO ACTION", "NO ACTION",Arrays.asList("id_page"), Arrays.asList("id_page")));
        final TableInfo _infoWordPage = new TableInfo("WordPage", _columnsWordPage, _foreignKeysWordPage);
        final TableInfo _existingWordPage = TableInfo.read(_db, "WordPage");
        if (! _infoWordPage.equals(_existingWordPage)) {
          throw new IllegalStateException("Migration didn't properly handle WordPage(Model.entity.WordPage).\n"
                  + " Expected:\n" + _infoWordPage + "\n"
                  + " Found:\n" + _existingWordPage);
        }
      }
    }, "b8f34514d17c820e2283c2864b1d95a6");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .version(6)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Book","Page","Word","WordPage");
  }

  @Override
  public BookDao bookDao() {
    if (_bookDao != null) {
      return _bookDao;
    } else {
      synchronized(this) {
        if(_bookDao == null) {
          _bookDao = new BookDao_Impl(this);
        }
        return _bookDao;
      }
    }
  }

  @Override
  public PageDao pageDao() {
    if (_pageDao != null) {
      return _pageDao;
    } else {
      synchronized(this) {
        if(_pageDao == null) {
          _pageDao = new PageDao_Impl(this);
        }
        return _pageDao;
      }
    }
  }

  @Override
  public WordDao wordDao() {
    if (_wordDao != null) {
      return _wordDao;
    } else {
      synchronized(this) {
        if(_wordDao == null) {
          _wordDao = new WordDao_Impl(this);
        }
        return _wordDao;
      }
    }
  }

  @Override
  public WordPageDao wordPageDao() {
    if (_wordPageDao != null) {
      return _wordPageDao;
    } else {
      synchronized(this) {
        if(_wordPageDao == null) {
          _wordPageDao = new WordPageDao_Impl(this);
        }
        return _wordPageDao;
      }
    }
  }
}
