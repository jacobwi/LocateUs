package com.example.jacob.locateus;

import android.os.Bundle;
import android.text.LoginFilter;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jacob.locateus.Data.CurrentUser;
import com.example.jacob.locateus.Data.Info;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.security.acl.Group;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentGroup extends Fragment {
    View view;
    FloatingActionButton addBtn;

    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference group_table = database.getReference("Groups");

    private FirebaseListAdapter<Info> mAdapter;
    public FragmentGroup() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group, container, false);
        addBtn = (FloatingActionButton) view.findViewById(R.id.addBtn);
        mAuth = FirebaseAuth.getInstance();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_table = group_table.push();
                Info group = new Info("My Group", 1);
                // group_table.push().child("Members").setValue(group);
                String key = group_table.getKey();
                Map<String,Object> taskMap = new HashMap<>();
                taskMap.put("phoneNumber", CurrentUser.phonenumber);
                taskMap.put("admin", true);
                group_table.child("Members").updateChildren(taskMap);
                group_table.child("Info").setValue(group);
            }

        });
        displayGroups(view);
        return view;
    }

    private void displayGroups(View view) {

        Query query = FirebaseDatabase.getInstance().getReference("Groups");
        ListView listOfMessage = (ListView)view.findViewById(R.id.groupList);
        FirebaseListOptions<Info> options = new FirebaseListOptions.Builder<Info>()
                .setQuery(query, Info.class).setLayout(R.layout.group_item).build();
        Log.e("Testing","Function init");
        mAdapter = new FirebaseListAdapter<Info>(options)
        {
            @Override
            protected void populateView(final View v, final Info model, int position) {

                //Get references to the views of list_item.xml

                final String groupRef = getRef(position).getKey();
                DatabaseReference group_table1 = database.getReference().child("Groups").child("Info");
                group_table1.child(groupRef).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final TextView messageText;
                         messageText = (TextView) v.findViewById(R.id.groupName);


                        Log.e("Program", ""+model.getMemberCount() + model.getGroupName() + groupRef);
                        FirebaseDatabase.getInstance().getReference().child("Groups")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Info user = snapshot.child("Info").getValue(Info.class);
                                            Log.e("Testing","" + snapshot.child("Info/groupName").getValue().toString());
                                            messageText.setText(snapshot.child("Info/groupName").getValue().toString());
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Testing",databaseError.getMessage());
                    }
                });



            }
        };
        listOfMessage.setAdapter(mAdapter);

    }


    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
