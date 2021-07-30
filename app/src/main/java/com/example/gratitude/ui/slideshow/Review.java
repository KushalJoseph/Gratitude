package com.example.gratitude.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gratitude.R;
import com.example.gratitude.ShowList;

import static com.example.gratitude.MainActivity.preferences;

public class Review extends Fragment implements View.OnClickListener{

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        Button button1=view.findViewById(R.id.career2);
        button1.setOnClickListener(this);
        Button button2=view.findViewById(R.id.family2);
        button2.setOnClickListener(this);
        Button button3=view.findViewById(R.id.knowledge2);
        button3.setOnClickListener(this);
        Button button4=view.findViewById(R.id.health2);
        button4.setOnClickListener(this);
        Button button5=view.findViewById(R.id.money2);
        button5.setOnClickListener(this);
        Button button6=view.findViewById(R.id.miscellaneous2);
        button6.setOnClickListener(this);
        Button reviewAll=view.findViewById(R.id.reviewAll);
        reviewAll.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this.getActivity(), ShowList.class);
        int indexNumber=-1;
        if(v.getId()==R.id.career2){
            indexNumber=0;
        }else if(v.getId()==R.id.family2){
            indexNumber=1;
        }else if(v.getId()==R.id.health2){
            indexNumber=2;
        }else if(v.getId()==R.id.money2){
            indexNumber=3;
        }else if(v.getId()==R.id.knowledge2){
            indexNumber=4;
        }else if(v.getId()==R.id.miscellaneous2){
            indexNumber=5;
        }
        intent.putExtra("indexNumber",indexNumber);
        if(indexNumber!=-1) {
            if (preferences.get(indexNumber).size() == 0) {
                Toast.makeText(this.getActivity(), "You have not added any gratitude in this field!", Toast.LENGTH_SHORT).show();
            } else {
                startActivity(intent);
            }
        }else{
            boolean flag=false;
            for(int i=0;i<preferences.size();i++){
                if(preferences.get(i).size()!=0){
                    flag=true;
                }
            }
            if(flag){
                startActivity(intent);
            }else{
                Toast.makeText(this.getActivity(), "You have not added any gratitude yet!", Toast.LENGTH_SHORT).show();
            }
        }

    }
}