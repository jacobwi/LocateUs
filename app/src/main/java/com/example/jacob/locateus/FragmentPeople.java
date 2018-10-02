package com.example.jacob.locateus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentPeople extends Fragment {
    View view;
    FloatingActionButton addBtn;
    String currentGroupSelected = "My Group" + "'s Members";

    TextView memberTitle;
    FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference group_table = database.getReference("Groups");

    private FirebaseListAdapter<Members> mAdapter;
    public FragmentPeople() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member, container, false);
        addBtn = (FloatingActionButton) view.findViewById(R.id.addBtn);
        memberTitle = (TextView) view.findViewById(R.id.memberNames);
        mAuth = FirebaseAuth.getInstance();
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPerson(v);
            }

        });
        memberTitle.setText(currentGroupSelected);
        displayGroups(view);
        return view;
    }

    private void addPerson(View v) {

    }

    private void displayGroups(View view) {

        Query query = FirebaseDatabase.getInstance().getReference("Groups");
        ListView listOfMessage = (ListView)view.findViewById(R.id.memberList);
        FirebaseListOptions<Members> options = new FirebaseListOptions.Builder<Members>()
                .setQuery(query, Members.class).setLayout(R.layout.member_item).build();
        Log.e("Testing","Function init");
        mAdapter = new FirebaseListAdapter<Members>(options)
        {
            @Override
            protected void populateView(final View v, final Members model, int position) {

                //Get references to the views of list_item.xml

                final String groupRef = getRef(position).getKey();
                DatabaseReference group_table1 = database.getReference().child("Groups").child("Members");
                group_table1.child(groupRef).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        final TextView messageText;
                        messageText = (TextView) v.findViewById(R.id.memberName);


                        FirebaseDatabase.getInstance().getReference().child("Groups")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Info user = snapshot.child("Members").getValue(Info.class);
                                            Log.e("Testing","" + snapshot.child("Members/phoneNumber").getValue().toString());
                                            messageText.setText(snapshot.child("Members/phoneNumber").getValue().toString());

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

        listOfMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.memberName);
                String text = textView.getText().toString();
                Log.e("People", "" + text);
            }
        });
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
