package Model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

@Entity(tableName = "WordPage",
        primaryKeys = { "id_word", "id_page" },
        foreignKeys = {
                @ForeignKey(entity = Word.class,
                        parentColumns = "id_word",
                        childColumns = "id_word"),
                @ForeignKey(entity = Page.class,
                        parentColumns = "id_page",
                        childColumns = "id_page")
        })
public class WordPage {

    @ColumnInfo(index = true)
    public final int id_word;
    @ColumnInfo(index = true)
    public final int id_page;

    public WordPage(final int id_word, final int id_page) {
        this.id_word = id_word;
        this.id_page = id_page;
    }

    public int getId_word() {
        return id_word;
    }

    public int getId_page() {
        return id_page;
    }
}
