package com.example.gratitude;

import android.provider.Telephony;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.internal.$Gson$Preconditions;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.gratitude.MainActivity.preferences;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CardViewHolder> {

    private ArrayList<CardObject>mCardObjects;

    private RecyclerAdapter.OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }
    public void setOnItemClickListener(RecyclerAdapter.OnItemClickListener listener){
        onItemClickListener=listener; jgkc,ko
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView title;
        public TextView description;
        public TextView date;
        public Button delete;
        public CardViewHolder(@NonNull View itemView, final RecyclerAdapter.OnItemClickListener listener) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cardImage);
            title=itemView.findViewById(R.id.cardTitle);
            description=itemView.findViewById(R.id.cardDescripion);
            date=itemView.findViewById(R.id.cardDate);
            delete=itemView.findViewById(R.id.cardDelete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    listener.onDeleteClick(position);
                }
            });
        }
    }

    public RecyclerAdapter(ArrayList<CardObject>cardObjects){
        mCardObjects=cardObjects;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        CardViewHolder cardViewHolder=new CardViewHolder(view,onItemClickListener);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardObject currentCardObject=mCardObjects.get(position);
        holder.imageView.setImageResource(currentCardObject.returnImage());
        holder.title.setText(currentCardObject.returnTitle());
        holder.description.setText(currentCardObject.returnDescription());
        holder.date.setText(currentCardObject.returnDate());
    }

    @Override
    public int getItemCount() {
        return mCardObjects.size();
    }

}
