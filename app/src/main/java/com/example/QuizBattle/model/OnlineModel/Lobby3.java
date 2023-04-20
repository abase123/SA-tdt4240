package com.example.QuizBattle.model.OnlineModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.QuizBattle.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Lobby3 extends AppCompatActivity {

    Button button;

    String playerName = "";
    String roomName = "";
    String role = "";
    String message = "";

    FirebaseDatabase database;
    DatabaseReference messageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby3);

        button = findViewById(R.id.button);
        button.setEnabled(false);

        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("playerName", "");

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            roomName = extras.getString("roomName");
            if (roomName.equals(playerName)) {
                role = "host";
            } else {
                role = "guest";
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                //Notify guest or host
                button.setEnabled(false);
                message = role + "";
                messageRef.setValue(message);
            }
        });

        //listen for incoming notification
        messageRef = database.getReference("rooms/" + roomName + "/message");
        message = role + "";
        messageRef.setValue(message);
        addRoomEventListener();
    }

    private void addRoomEventListener() {
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Notification received
                if (role.equals("host")) {
                    if (dataSnapshot.getValue(String.class).contains("guest:")) {
                        button.setEnabled(true);
                        Toast.makeText(Lobby3.this, "" + dataSnapshot.getValue(String.class).replace("guest:", ""), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (dataSnapshot.getValue(String.class).contains("host:")) {
                        button.setEnabled(true);
                        Toast.makeText(Lobby3.this, "" + dataSnapshot.getValue(String.class).replace("host", ""), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //ERROR
                messageRef.setValue(message);
            }
        });
    }
}