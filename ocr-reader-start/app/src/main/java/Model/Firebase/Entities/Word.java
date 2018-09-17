package Model.Firebase.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

public class Word  implements Serializable {
    private String word;

    private int amount;

    //private int fila;

    public Word(){

    }
    public Word(String word, int amount, int id_book) {
        this.word = word;
        this.amount = amount;
    }
    public Word(String word, int amount) {
        this.word = word;
        this.amount = amount;
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


}
