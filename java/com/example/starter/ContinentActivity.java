package com.example.starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContinentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void btnPress(View view){
        setContentView(R.layout.activity_continent);


        String[] continent={"Asia","Europe","Africa","Oceania","America", "World"};
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(
                this,
                R.layout.item_continent,
                R.id.tvContinent,
                continent
        ) ;
        ListView myListView=findViewById(R.id.listContinent);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                // Open the questions
                switch (continent[itemPosition]){
                    case "Asia":
                        startActivity(new Intent(ContinentActivity.this, GameAcitvity.class).putExtra("Continent","Asia"));
                        finish();
                        break;
                    case "Europe":
                        startActivity(new Intent(ContinentActivity.this, GameAcitvity.class).putExtra("Continent","Europe"));
                        finish();
                        break;
                    case "Africa":
                        startActivity(new Intent(ContinentActivity.this, GameAcitvity.class).putExtra("Continent","Africa"));
                        finish();
                        break;
                    case "Oceania":
                        startActivity(new Intent(ContinentActivity.this, GameAcitvity.class).putExtra("Continent","Oceania"));
                        finish();
                        break;
                    case "America":
                        startActivity(new Intent(ContinentActivity.this, GameAcitvity.class).putExtra("Continent","America"));
                        finish();
                        break;
                    case "World":
                        startActivity(new Intent(ContinentActivity.this, GameAcitvity.class).putExtra("Continent","World"));
                        finish();
                        break;
                    default:break;
                }
                /**if(!continent[itemPosition].equals("America")){

                }else{
                    startActivity(new Intent(ContinentActivity.this, GameAcitvity.class));
                    finish();
                }*/
            }
        });
    }
}