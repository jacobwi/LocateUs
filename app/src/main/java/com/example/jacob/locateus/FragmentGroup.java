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
import com.example.jacob.locateus.Data.Members;
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

import java.lang.reflect.Member;
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
                Info group = new Info("My Group", 1);
                DatabaseReference pushRef = database.getReference("Groups").push();

                // group_table.push().child("Members").setValue(group);
                String key = pushRef.getKey();
                Log.e("Pr", "" + key);
                pushRef.child("Members/phoneNumber").setValue(CurrentUser.phonenumber);
                pushRef.child("Info").setValue(group);
            }

        });
         displayGroups(view);
        return view;
    }

    private void displayGroups(View view) {

        Query query = FirebaseDatabase.getInstance().getReference("Groups").orderByChild("/Members/phoneNumber").equalTo(CurrentUser.phonenumber);
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
                DatabaseReference group_table1 = database.getReference().child("Groups");
                group_table1.child(groupRef).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final TextView messageText, groupCount;

                         messageText = (TextView) v.findViewById(R.id.groupName);

                        groupCount = (TextView) v.findViewById(R.id.groupCount);
                        Log.e("Program", ""+model.getMemberCount() + model.getGroupName() + groupRef);
                        FirebaseDatabase.getInstance().getReference().child("Groups")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                                Info group = snapshot.child("Info").getValue(Info.class);
                                                Members user = snapshot.child("Members").getValue(Members.class);
                                                Log.e("Group", "" + user.getPhoneNumber());
                                                if (user.getPhoneNumber().equals(CurrentUser.phonenumber)){
                                                    Log.e("Group", "Found " + user.getPhoneNumber());
                                                    messageText.setText(group.getGroupName());
                                                    groupCount.setText("Members: " + group.getMemberCount());
                                                }


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
