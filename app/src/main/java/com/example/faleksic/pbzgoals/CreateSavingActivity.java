package com.example.faleksic.pbzgoals;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class CreateSavingActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{

    private EditText dateEditText, nameEditText, amountEditText;
    private Calendar myCalendar;
    private DBHelper dbHelper;
    private String title;
    private int category = -1;
    private double amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_saving);

        dbHelper = new DBHelper(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.create_saving_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setLogo(R.drawable.pbz_goals);

        Spinner spinner = (Spinner)findViewById(R.id.category_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.category_list, R.layout.spinner_row);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        myCalendar = Calendar.getInstance();

        dateEditText = (EditText)findViewById(R.id.due_edit_text);
        nameEditText = (EditText)findViewById(R.id.saving_name_edit_text);
        amountEditText = (EditText) findViewById(R.id.amount_save_edit_text);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(CreateSavingActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        TextView wishListTextView = (TextView) findViewById(R.id.wish_list_text_view);
        wishListTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(dateEditText.getText().toString(), "") &&
                        !Objects.equals(nameEditText.getText().toString(), "") &&
                        !Objects.equals(amountEditText.getText().toString(), "") && category != -1) {
                    title = nameEditText.getText().toString();

                    dbHelper.insertSaving(title, dateEditText.getText().toString(), amount, 0, category,  0, false);
                    Toast.makeText(CreateSavingActivity.this, "Saving added to wish list", Toast.LENGTH_SHORT).show();
                    (CreateSavingActivity.this).finish();
                } else {
                    Toast.makeText(CreateSavingActivity.this, "Fill all the data!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button savingButton = (Button)findViewById(R.id.saving_button);
        savingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(dateEditText.getText().toString(), "") &&
                        !Objects.equals(nameEditText.getText().toString(), "") &&
                        !Objects.equals(amountEditText.getText().toString(), "") && category != -1) {

                    amount = Double.parseDouble(amountEditText.getText().toString());
                    title = nameEditText.getText().toString();

                    Intent intent = new Intent(CreateSavingActivity.this, DetailSavingActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("date", dateEditText.getText().toString());
                    intent.putExtra("amount", amount);
                    intent.putExtra("category", category);
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateSavingActivity.this, "Fill all the data!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
        dateEditText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        category = i+1;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        category = 9;
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
