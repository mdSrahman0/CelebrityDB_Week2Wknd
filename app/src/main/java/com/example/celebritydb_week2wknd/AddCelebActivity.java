package com.example.celebritydb_week2wknd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddCelebActivity extends AppCompatActivity {

    EditText etFullName;
    EditText etAge;
    EditText etProfession;

    DatabaseHelper dbHelper;
    Celebrity celeb;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_celeb);

        etFullName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etProfession = findViewById(R.id.etProfession);
        intent = getIntent();
    }

    public void onClick(View view) {
        String fullName = etFullName.getText().toString();
        String age = etAge.getText().toString();
        String profession = etProfession.getText().toString();

        celeb = new Celebrity(fullName,age,profession);

        Bundle bundle = new Bundle();
        bundle.putParcelable("celeb", celeb);
        intent.putExtras(bundle);
        dbHelper = new DatabaseHelper(this);
        dbHelper.insertCelebrity(celeb);
        setResult(0, intent);
        finish();
       // dbHelper.close();
    }
}
