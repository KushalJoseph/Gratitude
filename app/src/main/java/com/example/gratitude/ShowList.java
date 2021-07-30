package com.example.gratitude;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gratitude.R;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.gratitude.MainActivity.preferences;

public class ShowList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<CardObject>cardObjects;

    int indexNumber;

    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        TextView textView=findViewById(R.id.textView3);

        Intent intent=getIntent();
        indexNumber=intent.getIntExtra("indexNumber",0);
        cardObjects=new ArrayList<>();
        if(indexNumber!=-1) {
            for (int i = 0; i < preferences.get(indexNumber).size(); i++) {
                GratitudeObject object = preferences.get(indexNumber).get(i);
                int imageResource=returnImage(object);
                CardObject cardObject = new CardObject(imageResource, object.returnTitle(), object.returnDescription(), object.returnDate(), object.returnType());
                cardObjects.add(cardObject);
            }
        }else{
            for(int i=0;i<preferences.size();i++){
                for(int j=0;j<preferences.get(i).size();j++){
                    GratitudeObject object = preferences.get(i).get(j);
                    int imageResource=returnImage(object);
                    CardObject cardObject = new CardObject(imageResource, object.returnTitle(), object.returnDescription(), object.returnDate(), object.returnType());
                    cardObjects.add(cardObject);
                }
            }
        }

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=(RecyclerView.LayoutManager) new LinearLayoutManager(this);
        adapter=new RecyclerAdapter(cardObjects);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Log.i("TAG",Integer.toString(indexNumber));
                if(indexNumber!=-1) {
                    adapter.notifyItemRemoved(position);
                    cardObjects.remove(position);
                    preferences.get(indexNumber).remove(position);
                    SharedPreferences sharedPreferences = getSharedPreferences("Gratitude", MODE_PRIVATE);
                    Gson gson = new Gson();
                    String json = gson.toJson(preferences);
                    sharedPreferences.edit().putString("GratitudeArray", json).commit();
                }else{
                    counter=-1;
                    adapter.notifyItemRemoved(position);
                    cardObjects.remove(position);
                    int flag=0;
                    for(int i=0;i<preferences.size();i++){
                        for(int j=0;j<preferences.get(i).size();j++){
                            counter++;
                            if(counter==position){
                                preferences.get(i).remove(j);
                                flag=1;
                                break;
                            }
                        }
                        if(flag==1){
                            break;
                        }
                    }
                    SharedPreferences sharedPreferences=getSharedPreferences("Gratitude",MODE_PRIVATE);
                    Gson gson=new Gson();
                    String json=gson.toJson(preferences);
                    sharedPreferences.edit().putString("GratitudeArray",json).commit();
                }
                Toast.makeText(ShowList.this, "Deleted!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int returnImage(GratitudeObject object){
        int imageResource=0;
        if(object.returnType().matches("Career")){
            imageResource=R.drawable.career;
        }else if(object.returnType().matches("Family")){
            imageResource=R.drawable.family_sil;
        }else if(object.returnType().matches("Money")){
            imageResource=R.drawable.money_clipart;
        }else if(object.returnType().matches("Knowledge")){
            imageResource=R.drawable.knowledge;
        }else if(object.returnType().matches("Health")){
            imageResource=R.drawable.health_clipart;
        }else if(object.returnType().matches("Miscellaneous")){
            imageResource=R.drawable.misc;
        }
        return imageResource;
    }
}
