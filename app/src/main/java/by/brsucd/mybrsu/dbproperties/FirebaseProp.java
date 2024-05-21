package by.brsucd.mybrsu.dbproperties;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseProp {


    private final static String DATABASE_FIREBASE_URL = "https://my-brsu-default-rtdb.asia-southeast1.firebasedatabase.app";
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(DATABASE_FIREBASE_URL);
    DatabaseReference databaseReference = firebaseDatabase.getReference("message");
    private String mainText;

    public FirebaseProp(){
        //FirebaseApp.initializeApp();
    }
    public void test(){
        databaseReference.setValue("bul");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = (String) snapshot.getValue();
                System.out.printf(value);
                setMainText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        this.mainText = mainText;
    }
}
