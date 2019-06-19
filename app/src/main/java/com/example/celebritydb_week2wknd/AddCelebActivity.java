package com.example.celebritydb_week2wknd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class AddCelebActivity extends AppCompatActivity {

    EditText etFullName;
    EditText etAge;
    EditText etProfession;

    DatabaseHelper dbHelper;
    Celebrity celeb;
    Intent intent;

    public static final String TAG = "TAG_ADD_CELEB_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_celeb);

        etFullName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etProfession = findViewById(R.id.etProfession);
        intent = getIntent();
        dbHelper = new DatabaseHelper(this);
    }

    public void onClick(View view) {
        String fullName = etFullName.getText().toString();
        String age = etAge.getText().toString();
        String profession = etProfession.getText().toString();

        Bundle bundle = new Bundle();
        bundle.putParcelable("celeb", celeb);
        intent.putExtras(bundle);

        if((!fullName.isEmpty() && !age.isEmpty() && !profession.isEmpty()))
            dbHelper.insertCelebrity(celeb);

        /*
        for(Celebrity celebrity : dbHelper.queryForAllCelebrities()) {
            Log.d(TAG, String.format(Locale.US, "%s %s %s ", celebrity.getName(), celebrity.getAge(),
                    celebrity.getProfession()));
        } */
        dbHelper.close();
        setResult(0, intent);
    }
}
