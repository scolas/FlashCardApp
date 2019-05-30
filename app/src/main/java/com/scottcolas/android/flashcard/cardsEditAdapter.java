package com.scottcolas.android.flashcard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.scottcolas.android.flashcard.Model.Card;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class cardsEditAdapter extends RecyclerView.Adapter<cardsEditAdapter.CardEditViewHolder> implements ListItemClickListener{
    private final LayoutInflater mInflater;
    private List<Card> mCards; // Cached copy of words
    final private ListItemClickListener mOnClickListener;

    cardsEditAdapter(Context context, ListItemClickListener mOnClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public cardsEditAdapter.CardEditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        View itemView = mInflater.inflate(R.layout.recyclerview_item2, parent, false);
        return new cardsEditAdapter.CardEditViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(@NonNull cardsEditAdapter.CardEditViewHolder holder, final int position) {
        if (mCards != null) {
            Card current = mCards.get(position);
            //Log.i("cards adapter","this is question"+current.getQuestion());
            holder.cardItemView.setText(current.getQuestion());
        } else {
            // Covers the case of data not being ready yet.
            holder.cardItemView.setText("No Exam");
        }


    }


    @Override
    public int getItemCount() {
        if (mCards != null)
            return mCards.size();
        else return 0;
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        int i = 1;
    }

    @Override
    public void onListItemDelete(int clickedItemIndex) {

    }



/*
    class ExamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView examItemView;

        private ExamViewHolder(View itemView){
            super(itemView);
            examItemView = itemView.findViewById(R.id.textView);
        }

        @OnClick
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
            Log.i("TAG","ok click");
        }
    }

*/

    class CardEditViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textView)
        TextView cardItemView;
        Button deleteButton;

        private CardEditViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick
        void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

            //Log.i("TAG","ok click"+getAdapterPosition());
        }

        @OnClick(R.id.delete_listItem)
        void deleteButton(View view){
            //Log.i("TAG DELETE","DELETE ok click"+getAdapterPosition());
            mOnClickListener.onListItemDelete(getAdapterPosition());

        }
    }





    void setCards(List<Card> cards){
        mCards = cards;
        notifyItemRangeRemoved(0,mCards.size());
        notifyDataSetChanged();
    }

    void refreash(){
        notifyDataSetChanged();
    }


}
