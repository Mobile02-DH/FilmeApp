package com.digitalhouse.whatchapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.digitalhouse.whatchapp.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DataBase extends AppCompatActivity {

    //private ListView mListView;
    private Button mFirebaseBtn;

    private DatabaseReference mDatabase;
    private LoginActivity loginActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_base);

        /*loginActivity.getUserProfile();

        mFirebaseBtn = (Button) findViewById(R.id.edtbuttonDatabase);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mFirebaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 1- Create child in root object
                //2- Assign some value to the child object

                mDatabase.child(mAuth.getUid()).setValue();
            }
        });*/

       /* mListView = (ListView) findViewById(R.id.ListView);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("https://watchapp-22de8.firebaseio.com/Users");

        FirebaseListAdapter<String> firebaseListAdapter = new FirebaseListAdapter<String>(
                this,
                String.class,
                android.R.layout.simple_list_item_1,
                databaseReference
        ) {
            @Override
            protected void populateView(View v, String model, int position) {

                TextView textView = (TextView) v.findViewById(android.R.id.text1);
                textView.setText(model);

            }
        };

        mListView.setAdapter(firebaseListAdapter);
    }*/
    }
}
