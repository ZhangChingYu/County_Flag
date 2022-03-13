package com.example.starter;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DataSave extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    static int mark=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);
        Bundle extract=getIntent().getExtras();
        mark=extract.getInt("mark");
        ((TextView)findViewById(R.id.marks)).setText(""+mark);

        databaseHelper=new DatabaseHelper(this);
    }

    public void btnAdd(View view){
        String firstName=((TextView)findViewById(R.id.first_name)).getText().toString();
        String lastName=((TextView)findViewById(R.id.last_name)).getText().toString();
        String marks=((TextView)findViewById(R.id.marks)).getText().toString();

        boolean dataInserted=databaseHelper.insertData(firstName,lastName,marks);
        if(dataInserted){
            // While is true, Do something.
            Log.d(getPackageName(),"Data inserted.");
            ((TextView)findViewById(R.id.tv_display)).setText("Data inserted.");
            // Clear the edit text
            ((TextView) findViewById(R.id.first_name)).setText("");
            ((TextView) findViewById(R.id.last_name)).setText("");
            ((TextView) findViewById(R.id.marks)).setText("");
        }else{
            // While insert unsuccessfully, show an error.
            Log.e(getPackageName()," Failed inserted data!");
            ((TextView)findViewById(R.id.tv_display)).setText(" Failed inserted data!");
        }
    }

    // Read after data insertion
    public void btnReadDb(View view){
        Cursor cursor=databaseHelper.readData();
        if (cursor.getCount()==0){
            // The table is empty.
            Log.e(getPackageName(),"Reader: empty");
            ((TextView)findViewById(R.id.tv_display)).setText("The database is empty!");
        }else{
            Log.e(getPackageName(),"Reading the content...");
            StringBuffer stringBuffer=new StringBuffer();
            while (cursor.moveToNext()){
                stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                stringBuffer.append("First name: "+cursor.getString(1)+"\n");
                stringBuffer.append("Last name: "+cursor.getString(2)+"\n");
                stringBuffer.append("Marks: "+cursor.getString(3)+"\n--------\n");
            }
            ((TextView)findViewById(R.id.tv_display)).setText(stringBuffer.toString());
        }
    }
}
