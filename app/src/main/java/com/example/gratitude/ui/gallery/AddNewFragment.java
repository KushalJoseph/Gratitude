package com.example.gratitude.ui.gallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.example.gratitude.GratitudeObject;
import com.example.gratitude.R;
import com.example.gratitude.ui.home.HomeFragment;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.gratitude.MainActivity.preferences;

public class AddNewFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    int indexNumber;
    EditText title;
    EditText description;
    Button cancel;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_add_new, container, false);

        TextView textView=(TextView)view.findViewById(R.id.textView);
        final TextView gratitudeDescription=(TextView)view.findViewById(R.id.gratDes);

        Button saveButton=(Button)view.findViewById(R.id.save);
        cancel=(Button)view.findViewById(R.id.cancel);

        title=(EditText)view.findViewById(R.id.title);
        description=(EditText) view.findViewById(R.id.gratDes);


        if(this.getActivity().getSharedPreferences("Gratitude",Context.MODE_PRIVATE)!=null) {
            sharedPreferences = this.getActivity().getSharedPreferences("Gratitude", Context.MODE_PRIVATE);
            editor=sharedPreferences.edit();
        }

        //String type=getArguments().getString("type");
        Bundle bundle = this.getArguments();

        if(bundle != null){
            String type=getArguments().getString("type");
            if(type=="Miscellaneous"){
                textView.setText("What made you feel grateful today?");
            }else if(type=="Family"){
                textView.setText("What made you feel grateful about your Family/Relationships today>");
            }else{
                textView.setText("What made you feel grateful about your "+type+" today?");
            }
            indexNumber=getArguments().getInt("indexNumber");
            Log.i("INDEX",Integer.toString(indexNumber));

        }else{
            textView.setText("What made you feel grateful today?");
            indexNumber=5;
            Log.i("INDEX",Integer.toString(indexNumber));
        }
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gratitudeDescription.getText().toString().matches("") || title.getText().toString().matches("")) {
                    Toast.makeText(getActivity(), "Please enter a Description and Title", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList toBeChanged = preferences.get(indexNumber);
                    Log.i("TAG", Integer.toString(indexNumber));
                    Log.i("TAG", toBeChanged.toString());

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");
                    String date = sdf.format(c.getTime());

                    String type="";
                    if(getArguments()==null){
                        type="Miscellaneous";
                    }else{
                        type=getArguments().getString("type");
                    }
                    GratitudeObject object = new GratitudeObject(type,
                            title.getText().toString(),
                            description.getText().toString(),
                            date);

                    toBeChanged.add(object);
                    Gson gson = new Gson();
                    String json = gson.toJson(preferences);
                    sharedPreferences.edit().putString("GratitudeArray", json).commit();
                    Toast.makeText(getActivity(), "Saved!", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                }
                Log.i("TAG2", preferences.toString());
                Log.i("TAG2", sharedPreferences.getString("GratitudeArray", ""));
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment,new HomeFragment()).commit();
            }
        });
        return view;
    }

}