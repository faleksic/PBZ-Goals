package com.example.faleksic.pbzgoals;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class SavingActivity extends AppCompatActivity {

    private int id = 0, progress = 0;
    private DBHelper dbHelper;
    private ImageView categoryImageView;
    private TextView title, money, date, remaining, editGoal;
    private DonutProgress donut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving);

        Toolbar toolbar = (Toolbar) findViewById(R.id.saving_toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setLogo(R.drawable.pbz_goals);

        dbHelper = new DBHelper(this);

        categoryImageView = (ImageView)findViewById(R.id.category_image_view);
        title = (TextView) findViewById(R.id.title_text_view);
        money = (TextView) findViewById(R.id.money_text_view);
        date = (TextView) findViewById(R.id.date_text_view);
        donut = (DonutProgress) findViewById(R.id.donut_progress);
        remaining = (TextView) findViewById(R.id.remaining_text_view);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id = extras.getInt("id");
            progress = extras.getInt("progress");
            Cursor cursor = dbHelper.getSaving(id);
            while(cursor.moveToNext()) {
                title.setText(cursor.getString(1));
                money.setText(String.valueOf(cursor.getDouble(3)));
                date.setText(cursor.getString(2));
                int categoryId = cursor.getInt(5);
                int category = R.drawable.ic_9;
                switch (categoryId) {
                    case 1:
                        category = R.drawable.ic_0;
                        break;
                    case 2:
                        category = R.drawable.ic_1;
                        break;
                    case 3:
                        category = R.drawable.ic_2;
                        break;
                    case 4:
                        category = R.drawable.ic_3;
                        break;
                    case 5:
                        category = R.drawable.ic_4;
                        break;
                    case 6:
                        category = R.drawable.ic_5;
                        break;
                    case 7:
                        category = R.drawable.ic_6;
                        break;
                    case 8:
                        category = R.drawable.ic_7;
                        break;
                    case 9:
                        category = R.drawable.ic_8;
                        break;
                    case 10:
                        category = R.drawable.ic_9;
                        break;
                }
                categoryImageView.setImageResource(category);
                donut.setProgress(progress);
                double amount = cursor.getDouble(3);
                double num = amount*(1-((double)progress/100));
                DecimalFormat df = new DecimalFormat("0.00");
                df.setRoundingMode(RoundingMode.HALF_UP);
                remaining.setText(String.valueOf(df.format(num)));
            }
        }
    }

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
