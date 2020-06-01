package Model.Firebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Model.Firebase.Entities.Word;

/**
 * Created by frodriguez on 16/09/18.
 */

public class Firebase {
    // Write a message to the database
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();

    public static DatabaseReference Words = database.getReference("words");

    //public static String TAG = "error";
    public Firebase(){

    }

//    public DatabaseReference getUsers(){
//        if( Users == null )
//            Users = database.getReference("Users");
//        return Users;
//    }
}
