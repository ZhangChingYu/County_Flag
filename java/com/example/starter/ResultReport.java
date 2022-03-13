package com.example.starter;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultReport extends AppCompatActivity {
    private List<Map<String,Object>> lists;
    private SimpleAdapter adapter;
    String []flagNames={"","","","","","","","","",""};
    int[] flagImages={1,2,3,4,5,6,7,8,9,0};
    int[] result_count={1,2,3,4,5,6,7,8,9,0};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_list);

        ListView myListView=(ListView) findViewById(R.id.list_result);
        for(int i=0;i<10;i++){
            flagNames[i]=GameAcitvity.flag_list[i].replace("flag_","");
            flagImages[i]=GameAcitvity.flagsToLearnImageResources.get(i);
            result_count[i]=GameAcitvity.result_count.get(i);
        }
        lists=new ArrayList<>();
        for(int i=0;i<flagNames.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("image",flagImages[i]);
            map.put("name",flagNames[i]);
            map.put("count",result_count[i]);
            lists.add(map);
        }
        adapter=new SimpleAdapter(
                this,
                lists,
                R.layout.item_result,
                new String[]{"image","name","count"},
                new int[]{R.id.flag_image,R.id.flag_name,R.id.icon_result}
        );
        myListView.setAdapter(adapter);

    }
}
