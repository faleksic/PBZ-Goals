package com.example.faleksic.pbzgoals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class DetailSavingActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    private String title, date;
    private double amount;
    private int category, frequency;

    private DBHelper dbHelper;

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_saving);

        dbHelper = new DBHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_saving_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setLogo(R.drawable.pbz_goals);

        Intent intent = getIntent();
        if(intent != null) {
            title = intent.getStringExtra("title");
            date = intent.getStringExtra("date");
            amount = intent.getDoubleExtra("amount",0.0);
            category = intent.getIntExtra("category", 0);
        }

        Spinner spinner = (Spinner)findViewById(R.id.frequency_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.frequency_list, R.layout.spinner_row);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        textView = (TextView)findViewById(R.id.money_per_frequency_text_view);
        textView.setText(String.valueOf(amount/30));

        Button button = (Button)findViewById(R.id.agree_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random randomGenerator = new Random();
                dbHelper.insertSaving(title, date, amount, frequency, category, amount*randomGenerator.nextDouble(), true);
                Toast.makeText(DetailSavingActivity.this, "Saving added to wish list", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailSavingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        frequency = i;
        switch (i){
            //// TODO: dodati racuanje prema bas onome koliko je preostalo
            case 0:
                textView.setText(String.valueOf(amount/30));
                break;
            case 1:
                textView.setText(String.valueOf(amount/4));
                break;
            case 2:
                textView.setText(String.valueOf(amount/1));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        amount = 2;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
