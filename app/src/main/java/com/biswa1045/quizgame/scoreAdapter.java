package com.biswa1045.quizgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class scoreAdapter extends RecyclerView.Adapter<scoreAdapter.ScoreViewAdapter> {
    List<scoreData> list2;
    Context context2;

    public scoreAdapter(List<scoreData> list2, Context context2) {
        this.list2 = list2;
        this.context2 = context2;

    }

    @NonNull
    @Override
    public ScoreViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context2).inflate(R.layout.score_list_item,parent,false);
        return new ScoreViewAdapter(view);

    }
int k;
    @Override
    public void onBindViewHolder(@NonNull ScoreViewAdapter holder, int position) {
        k = list2.size() - 10;
        scoreData currentItem = list2.get(position+k);
        holder.name.setText(currentItem.getName());
        holder.score.setText(String.valueOf(currentItem.getScore()));
        holder.rank.setText(String.valueOf(list2.size()-(position+k)));
      //  Glide.with(context2).load(currentItem.getImage()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
      //  if(list2.size()>10)

        //return 10;
        //else
          //  return list2.size();
        return Math.min(list2.size(), 10);
    }

    public class ScoreViewAdapter extends RecyclerView.ViewHolder {
//ImageView imageView;
TextView name,score,rank;

        public ScoreViewAdapter(@NonNull View itemView) {
            super(itemView);
           // imageView=itemView.findViewById(R.id.user_img);
            name=itemView.findViewById(R.id.user_name);
            score=itemView.findViewById(R.id.user_score);
            rank=itemView.findViewById(R.id.user_rank);

        }
    }

}
