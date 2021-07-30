package com.example.gratitude.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.gratitude.R;
import com.example.gratitude.ui.gallery.AddNewFragment;
import com.example.gratitude.ui.slideshow.Review;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button button1=(Button) view.findViewById(R.id.career2);
        button1.setOnClickListener(this);
        Button button2=(Button) view.findViewById(R.id.family2);
        button2.setOnClickListener(this);
        Button button3=(Button) view.findViewById(R.id.knowledge2);
        button3.setOnClickListener(this);
        Button button4=(Button) view.findViewById(R.id.health2);
        button4.setOnClickListener(this);
        Button button5=(Button) view.findViewById(R.id.money2);
        button5.setOnClickListener(this);
        Button button6=(Button) view.findViewById(R.id.miscellaneous2);
        button6.setOnClickListener(this);

        Button review=(Button)view.findViewById(R.id.review);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,new Review()).commit();
            }
        });

        return view;

    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager=getFragmentManager();
        Bundle bundle = new Bundle();
        String type="";
        int indexNumber=0;
        if(v.getId()==R.id.career2){
            type="Career";
        }else if(v.getId()==R.id.family2){
            type="Family";
            indexNumber=1;
        }else if(v.getId()==R.id.health2){
            type="Health";
            indexNumber=2;
        }else if(v.getId()==R.id.money2){
            type="Money";
            indexNumber=3;
        }else if(v.getId()==R.id.knowledge2){
            type="Knowledge";
            indexNumber=4;
        }else if(v.getId()==R.id.miscellaneous2){
            type="Miscellaneous";
            indexNumber=5;
        }

        bundle.putString("type",type); // Put anything what you want
        bundle.putInt("indexNumber",indexNumber);
        AddNewFragment addNewFragment = new AddNewFragment();
        addNewFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, addNewFragment).commit();
    }
}