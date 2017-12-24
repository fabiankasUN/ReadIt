package Model.Calls;

import android.content.Context;
import android.util.Log;

import Model.entity.Book;
import Model.entity.Database;


public class BookHandler {

    private Database db;


    public BookHandler(Database db ){

        this.db = db;
    }

    public void insertBook( final Book book ){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                try{
                    int total = db.bookDao().existBook("OliverTwist");
                    Book b = db.bookDao().getBook("OliverTwist");
                    //Log.d("mydb","total : " + total);
                    if( total == 0){
                        db.bookDao().insert(book);
                        //Log.d("mydb","inserted book Oliver ");
                    }else{
                        Log.d("mydb","the book has already was inserted ");
                    }
                    //db.close();
                }catch (Exception ex){
                    db.close();
                    Log.d("mydbd",ex.getMessage());
                }
            }
        };
        new Thread(runnable).start();
    }
}
