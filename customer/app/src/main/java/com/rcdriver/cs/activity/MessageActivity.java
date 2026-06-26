package com.rcdriver.cs.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.constants.Functions;
import com.rcdriver.cs.item.MessageItem;
import com.rcdriver.cs.models.MessageModels;
import com.rcdriver.cs.models.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MessageActivity extends AppCompatActivity {

    private RecyclerView inboxList;
    private ArrayList<MessageModels> inboxArraylist;
    private ShimmerFrameLayout shimmer;
    private DatabaseReference rootRef;
    private MessageItem inboxItem;
    private ValueEventListener valueEventListener;
    private Query inboxQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recycle);

        rootRef = FirebaseDatabase.getInstance().getReference();

        inboxList = findViewById(R.id.inboxlist);
        shimmer = findViewById(R.id.shimmerwallet);
        inboxArraylist = new ArrayList<>();
        LinearLayoutManager layout = new LinearLayoutManager(this);
        inboxList.setLayoutManager(layout);
        inboxList.setHasFixedSize(false);
        inboxItem = new MessageItem(this, inboxArraylist, new MessageItem.OnItemClickListener() {
            @Override
            public void onItemClick(MessageModels item) {
                User loginuser = BaseApp.getInstance(getApplicationContext()).getLoginUser();
                if (checkReadStoragepermission()) {
                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                    intent.putExtra("senderid", loginuser.getId());
                    intent.putExtra("receiverid", item.getId());
                    intent.putExtra("name", item.getName());
                    intent.putExtra("tokendriver", loginuser.getToken());
                    intent.putExtra("tokenku", item.getTokenuser());
                    intent.putExtra("pic", item.getPicture());
                    startActivity(intent);
                }
            }
        });

        inboxList.setAdapter(inboxItem);

        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.hideSoftKeyboard(MessageActivity.this);
            }
        });
        shimmershow();
    }

    private void shimmershow() {
        inboxList.setVisibility(View.GONE);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmerAnimation();
    }

    private void shimmertutup() {
        inboxList.setVisibility(View.VISIBLE);
        shimmer.setVisibility(View.GONE);
        shimmer.stopShimmerAnimation();
    }

    @Override
    protected void onStart() {
        super.onStart();
        inboxQuery = rootRef.child("Inbox").child(Constants.USERID).orderByChild("date");
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shimmertutup();
                inboxArraylist.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MessageModels model = new MessageModels();
                    model.setId(ds.getKey());
                    model.setName(Objects.requireNonNull(ds.child("name").getValue()).toString());
                    model.setMessage(Objects.requireNonNull(ds.child("msg").getValue()).toString());
                    model.setTimestamp(Objects.requireNonNull(ds.child("date").getValue()).toString());
                    model.setStatus(Objects.requireNonNull(ds.child("status").getValue()).toString());
                    model.setPicture(Objects.requireNonNull(ds.child("pic").getValue()).toString());
                    model.setTokendriver(Objects.requireNonNull(ds.child("tokendriver").getValue()).toString());
                    model.setTokenuser(Objects.requireNonNull(ds.child("tokenuser").getValue()).toString());
                    inboxArraylist.add(model);
                }
                Collections.reverse(inboxArraylist);
                inboxItem.notifyDataSetChanged();

                if (inboxArraylist.isEmpty()) {
                    findViewById(R.id.rlnodata).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.rlnodata).setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        inboxQuery.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (inboxQuery != null)
            inboxQuery.removeEventListener(valueEventListener);
    }

    private boolean checkReadStoragepermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            try {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.permission_Read_data);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return false;
    }
}
