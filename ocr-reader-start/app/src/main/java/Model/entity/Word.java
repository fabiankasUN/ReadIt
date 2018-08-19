package Model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "Word", foreignKeys = @ForeignKey(entity = Book.class,parentColumns = "id_book",
        childColumns = "id_book" )
, indices = {@Index(value = {"id_word", "id_book"},
        unique = true)} )
public class Word implements Serializable{

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_word", index = true)
    private int idWord;

    @ColumnInfo(name = "word")
    private String word;

    @ColumnInfo(name = "amount")
    private int amount;

    @ColumnInfo(name = "id_book", index = true)
    private int id_book;

    @Ignore
    private int fila;

    @Ignore
    public Word(int idWord, String word, int amount, int id_book) {
        this.idWord = idWord;
        this.word = word;
        this.amount = amount;
        this.id_book = id_book;
    }

    public Word(String word, int amount, int id_book) {
        this.word = word;
        this.amount = amount;
        this.id_book = id_book;
    }
    @Ignore
    public Word(String word, int amount) {
        this.word = word;
        this.amount = amount;
    }


    public int getIdWord() {
        return idWord;
    }

    public void setIdWord(int idWord) {
        this.idWord = idWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getId_book() {
        return id_book;
    }

    public void setId_book(int id_book) {
        this.id_book = id_book;
    }

    public int getFila(){
        return fila;
    }
}