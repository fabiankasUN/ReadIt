package Model.Calls;

import android.content.Context;
import android.util.Log;

import Model.entity.Book;
import Model.entity.Database;
import Model.entity.Word;
import Model.utils.Erros;

/**
 * Created by Fabian on 17/12/2017.
 */
public class WordHandler {

    private Database db;

    public WordHandler( Database db ){
        this.db = db;
    }

    public void addOrUpdateWord( final String value, final int id_book ){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try{
                    int total = db.wordDao().existWord(value);
                    //Log.d("mydb","total : " + total);
                    if( total == 0){
                        db.wordDao().insert(new Word(value,1,id_book));
                        //Log.d("mydb","word : " + value + " was added");
                    }else{
                        Word word = db.wordDao().getWordByValue(value);
                        word.setAmount(word.getAmount()+1);
                        db.wordDao().update(word);
                        //Log.d("mydb","word : " + value + " was added with " + word.getAmount() + " occurrences");
                    }
                }catch (Exception ex){
                    Log.d("mydb",ex.getMessage());
                }
            }
        };
        new Thread(runnable).start();
    }


    public void deleteAll(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    db.wordDao().deleteAll();
                }catch (Exception ex){
                    Log.d(Erros.DATABASE,ex.getMessage());
                }
            }
        };
        new Thread(runnable).start();
    }

    public void countWords(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    int total = db.wordDao().countWords();
                    Log.e(Erros.MAP_PROCESSOR, "TOTAL :  " + total + "");
                }catch (Exception ex){
                    Log.d(Erros.DATABASE,ex.getMessage());
                }
            }
        };
        new Thread(runnable).start();
    }


}
