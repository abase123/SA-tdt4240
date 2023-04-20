package com.example.QuizBattle.model.OnlineModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.QuizBattle.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Lobby extends AppCompatActivity {

    EditText editText;
    Button button;

    String playerName = "";

    FirebaseDatabase database;
    DatabaseReference playerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        database = FirebaseDatabase.getInstance();

        //Check if the player exists
        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        playerName = preferences.getString("PlayerName", "");
        if (!playerName.equals("")){
            playerRef = database.getReference("players/" + playerName);
            addEventListener();
            playerRef.setValue("");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                //logging the player in
                playerName = editText.getText().toString();
                editText.setText("");
                if (!playerName.equals("")) {
                    button.setText("LOGGING IN");
                    button.setEnabled(false);
                    playerRef = database.getReference("players/" + playerName);
                    addEventListener();
                    playerRef.setValue("");
                }
            }
        });

    }

    private void addEventListener() {
        //read from database
        playerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //success amd continue to next screen
                if(!playerName.equals("")){
                    SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("playerName", playerName);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), Lobby2.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //error
                button.setText("LOG IN");
                button.setEnabled(true);
                Toast.makeText(Lobby.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }
}