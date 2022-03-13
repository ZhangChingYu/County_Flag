package com.example.starter;


import android.accessibilityservice.AccessibilityService;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;

public class GameReport extends AppCompatActivity {

    static int error=0;
    static int correct=0;

    @Override
    public void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_report);
        Bundle extract=getIntent().getExtras();
        error=extract.getInt("GameActivity");
        correct=10-error;
        ((TextView)findViewById(R.id.tv_to_learn)).setText("To learn: "+error);
        ((TextView)findViewById(R.id.tv_recognized)).setText("Recognized: "+correct);
    }

    // Show menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    // Click menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.result:
                //Toast.makeText(this,"result",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GameReport.this,ResultReport.class));
                break;
            case R.id.home_page:
                //Toast.makeText(this,"home page",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GameReport.this,ContinentActivity.class));
                finish();
                break;
            default:break;
        }
        return true;
    }

    // Need to add the menu and history.
    public void btnSave(View view){
        startActivity(new Intent(GameReport.this,DataSave.class).putExtra("mark",correct));
        finish();
    }

    // Back press should not directly close the app.
    @Override
    public void  onBackPressed(){
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?\n Press No to restart.")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        GameReport.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(getBaseContext(),ContinentActivity.class));
                        finish();
                    }
                })
                .show();
    }
}
