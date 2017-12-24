package Model.entity;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import Model.dao.BookDao;
import Model.dao.PageDao;
import Model.dao.WordDao;
import Model.dao.WordPageDao;

/**
 * Created by Fabian on 14/12/2017.
 */

@android.arch.persistence.room.Database(entities = { Book.class, Page.class, Word.class, WordPage.class},
        version = 5)
public abstract class Database extends RoomDatabase {

    private static Database INSTANCE;

    public abstract BookDao bookDao();
    public abstract PageDao pageDao();
    public abstract WordDao wordDao();
    public abstract WordPageDao wordPageDao();

    public static Database getDB(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), Database.class, "readDB")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }


}
