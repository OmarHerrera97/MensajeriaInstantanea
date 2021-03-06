package com.uisrael.mensajeriainstantanea;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uisrael.mensajeriainstantanea.Adapters.MessageAdapter;
import com.uisrael.mensajeriainstantanea.Models.Chat;
import com.uisrael.mensajeriainstantanea.Models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;

    ImageButton btn_send;
    EditText text_send;
    FirebaseUser fuser;
    DatabaseReference reference;

    MessageAdapter messageAdapter;
    List<Chat> mChat;

    RecyclerView recyclerView;

    Intent intent;

    ValueEventListener seenListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);

        intent = getIntent();
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    //Cambiar el id del que se chatea y en user Adapter
                    sendMessage(fuser.getUid(), "NYJVSac2BQVW8IlSnjE0Cz7mL392", msg);
                    //sendMessage(fuser.getUid(), "kQkUezIxkYWQiMiWB3xWucQT49s2", msg);
                } else {
                    Toast.makeText(MessageActivity.this, "No puedes enviar mensaje en blanco", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        //Cambiar el id del que se chatea y en user Adapter
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child("NYJVSac2BQVW8IlSnjE0Cz7mL392");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                assert user != null;
                username.setText(user.getUsername());
                if ("default".equals(user.getImageURL())) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);
                } else {

                    Glide.with(getApplicationContext()).load(user.getImageURL()).into(profile_image);
                }

                ReadMessage(fuser.getUid(), "NYJVSac2BQVW8IlSnjE0Cz7mL392", user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        seenMessage("NYJVSac2BQVW8IlSnjE0Cz7mL392");
    }

    private void seenMessage(String userid) {
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        seenListener = reference.addValueEventListener(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                                   Chat chat = snapshot.getValue(Chat.class);
                                                                   if (chat.getRecibe().equals("NYJVSac2BQVW8IlSnjE0Cz7mL392") && chat.getEnvia().equals("C6WTsT1MdsbqKLF6TT5DjjQ4How1") ||
                                                                           chat.getRecibe().equals("C6WTsT1MdsbqKLF6TT5DjjQ4How1") && chat.getEnvia().equals("NYJVSac2BQVW8IlSnjE0Cz7mL392")) {
                                                                       HashMap<String, Object> hashMap = new HashMap<>();
                                                                       hashMap.put("isseen", false);
                                                                       snapshot.getRef().updateChildren(hashMap);
                                                                   }
                                                               }
                                                           }

                                                           @Override
                                                           public void onCancelled(@NonNull DatabaseError error) {

                                                           }
                                                       }
        );
    }


    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("envia", sender);
        hashMap.put("recibe", receiver);
        hashMap.put("mensaje", message);
        hashMap.put("isseen", false);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void ReadMessage(final String myid, final String userid, final String imageurl) {
        mChat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getRecibe().equals("NYJVSac2BQVW8IlSnjE0Cz7mL392") && chat.getEnvia().equals("C6WTsT1MdsbqKLF6TT5DjjQ4How1") ||
                            chat.getRecibe().equals("C6WTsT1MdsbqKLF6TT5DjjQ4How1") && chat.getEnvia().equals("NYJVSac2BQVW8IlSnjE0Cz7mL392")) {
                        mChat.add(chat);


                    }
                }
                messageAdapter = new MessageAdapter(MessageActivity.this, mChat, imageurl);
                recyclerView.setAdapter(messageAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void status(String status) {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("En linea");
    }

    @Override
    protected void onPause() {
        super.onPause();
        reference.removeEventListener(seenListener);
        status("Desconectado ");
    }
}