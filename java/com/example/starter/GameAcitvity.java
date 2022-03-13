package com.example.starter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameAcitvity extends AppCompatActivity {
    String[] listAnswer;
    CustomDialog alert;
    static List<Integer> flagsToLearnImageResources;
    ArrayAdapter<String> mAdapter;
    static int errors=0;
    static int error=0;
    static String[] flag_list={"","","","","","","","","",""};
    static List<Integer> result_count;
    static List<String> _data;


    @RequiresApi(api= Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Makeup continent selected
        Bundle extract=getIntent().getExtras();
        String[] ContinentCountryNames ={};
        switch (extract.getString("Continent")){
            case "Asia":
                ContinentCountryNames=ContinentClass.Asia;
                break;
            case "Europe":
                ContinentCountryNames=ContinentClass.Europe;
                break;
            case "Africa":
                ContinentCountryNames=ContinentClass.Africa;
                break;
            case "Oceania":
                ContinentCountryNames=ContinentClass.Oceania;
                break;
            case "America":
                ContinentCountryNames=ContinentClass.America;
                break;
            case "World":
                // Prepare data
                List<String> _Asia=Arrays.asList(ContinentClass.Asia);
                List<String> _Europe=Arrays.asList(ContinentClass.Europe);
                List<String> _Africa=Arrays.asList(ContinentClass.Africa);
                List<String> _Oceania=Arrays.asList(ContinentClass.Oceania);
                List<String> _America=Arrays.asList(ContinentClass.America);
                // Make all continent data in random order.
                Collections.shuffle(_Asia);
                Collections.shuffle(_Europe);
                Collections.shuffle(_Africa);
                Collections.shuffle(_Oceania);
                Collections.shuffle(_America);
                // Chose 10 countries from each continent.
                List<String> world=new ArrayList<>();
                world.addAll(_Asia.subList(0,10));
                world.addAll(_Europe.subList(0,10));
                world.addAll(_Africa.subList(0,10));
                world.addAll(_Oceania.subList(0,10));
                world.addAll(_America.subList(0,10));
                // Input data
                String[] temp = new String[50];
                for(int i=0;i<50;i++){
                    temp[i]=world.get(i);
                }
                ContinentCountryNames=temp;
                break;
            default:break;
        }
        int number_flags_to_learn=10;
        _data= Arrays.asList(ContinentCountryNames);
        // Change the order to random.
        Collections.shuffle(_data);
        // Put 10 countries name from _data into flagsToLearn.
        List<String> flagsToLearn=_data.subList(0, number_flags_to_learn);
        // Getting the flags image resources ready.
        flagsToLearnImageResources=new ArrayList<>();
        result_count=new ArrayList<>();
        for(int i=0;i<flagsToLearn.size();i++){
            String imageId=flagsToLearn.get(i);
            flag_list[i]=imageId;
            // Using country names to find country flag images in the drawable.
            int flagResIDs=getResources().getIdentifier(imageId,"drawable",getPackageName());
            flagsToLearnImageResources.add(flagResIDs);
            result_count.add(getResources().getIdentifier("ic_baseline_check_24","drawable",getPackageName()));
        }
        /**
         * Game logic:
         * generate the first flag with the possible answers.
         * generate a dialog if the answer is wrong, stay on the same question.
         * When the correct answer is selected, move to the next question.
         * */
        GameStart(0,flagsToLearn,extract.getString("Continent"));
    }
    public void GameStart(int tracker_current_correct_answer, List<String> flagsToLearn, String Continent){
        ((ImageView)findViewById(R.id.imageViewFlag)).setImageResource(flagsToLearnImageResources.get(tracker_current_correct_answer));
        ((TextView)findViewById(R.id.tvStatusGame)).setText("10/"+(10-tracker_current_correct_answer));
        // Loaded the possible answers
        String text_answer=flagsToLearn.get(tracker_current_correct_answer).replace("flag_","").replace("_"," ");
        // Make sure wrong answers not equals correct ones.(This method may not be reliable)
        String []wrong={"","",""};
        List<String> wrongAnswer=new ArrayList<String>(_data);
        Collections.shuffle(wrongAnswer);
        for(int i=0;i<3;i++){
            if(wrongAnswer.get(i).replace("flag_","").replace("_"," ").equals(text_answer)){
                wrong[i]=wrongAnswer.get(i+4);
            }else{
                wrong[i]=wrongAnswer.get(i);
            }
        }
        // Loaded correct answer and wrong answers.
        listAnswer=new String[]{
                text_answer,
                wrong[0].replace("flag_","").replace("_"," "),
                wrong[1].replace("flag_","").replace("_"," "),
                wrong[2].replace("flag_","").replace("_"," ")};
        List<String> rand_answer=Arrays.asList(listAnswer);
        Collections.shuffle(rand_answer);
        mAdapter=new ArrayAdapter<String>(
                this,
                R.layout.item_game_question,
                R.id.tvCountry,
                rand_answer
        );
        ListView myListView=findViewById(R.id.listFlagGameQuestion);
        myListView.setAdapter(mAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPostion, long l) {
                // Open the question
                // Toast.makeText(getApplicationContext(),listAnswer[itemPostion],Toast.LENGTH_SHORT).show();
                // Not correct answer selected, set the radio checked to red and operate a dialog.
                if(!listAnswer[itemPostion].equals(text_answer)){
                    ((RadioButton) view.findViewById(R.id.radioCountrySelector)).setChecked(true);
                    alert=new CustomDialog(GameAcitvity.this);
                    alert.showDialog(text_answer);
                    error=1;
                }else{
                    if(tracker_current_correct_answer==9){
                        if(error!=0){
                            result_count.set(tracker_current_correct_answer,getResources().getIdentifier("ic_baseline_clear_24","drawable",getPackageName()));
                            errors+=1;
                            error=0;
                        }
                        startActivity(new Intent(GameAcitvity.this,GameReport.class).putExtra("GameActivity",errors));
                        finish();

                    }
                    else{
                        if(error!=0){
                            result_count.set(tracker_current_correct_answer,getResources().getIdentifier("ic_baseline_clear_24","drawable",getPackageName()));
                            errors+=1;
                            error=0;
                        }
                        // Continue the game.
                        GameStart(tracker_current_correct_answer+1,flagsToLearn,Continent);
                    }

                }
            }
        });
    }
    // Close the dialog
    public void closeDialog(View view){
        alert.closeDialog();
    }
    // Change the TinColor of a radio button.
    public void changeColorRadio(RadioButton radio){
        ColorStateList colorStateList=new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_enabled},// Disabled
                        new int[]{android.R.attr.state_enabled}// Enabled
                },
                new int[]{
                        // Disabled
                        Color.BLACK,
                        Color.RED
                });
        // Set the color tint list
        radio.setButtonTintList(colorStateList);
        radio.invalidate();
    }
}
