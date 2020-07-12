package org.android_practice_app.firebasenotification.Others;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.android_practice_app.firebasenotification.Model.ChatMessageModel;

import java.util.HashMap;
import java.util.Map;

public class FirebaseDbCall {

    // Write a message to the database
    private static FirebaseDatabase database;
    private static DatabaseReference myRef;

    public static void init() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Chat");
    }

    public static DatabaseReference getMyRef() {
        return myRef;
    }

    public static void insert(ChatMessageModel chatMessageModel, final Context context) {
        myRef.push().setValue(chatMessageModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Write Success", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Write Failure!!", Toast.LENGTH_SHORT).show();
                    }
                });
    }


//    public static void update(String id, ChatMessageModel chatMessageModel) {
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("user_name", chatMessageModel.getName());
//        childUpdates.put("date", chatMessageModel.getDate_time());
//        childUpdates.put("msg", chatMessageModel.getMsg());
//        myRef.child(id).updateChildren(childUpdates);
//
//    }
//
//    public static void delete(String id, final Context context) {
//        myRef.child(id).setValue(null)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "Deleted Success", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Failure!!", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//    }


}
