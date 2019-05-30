package com.scottcolas.android.flashcard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.scottcolas.android.flashcard.Model.Exam;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ExamViewHolder> implements ListItemClickListener{
    private final LayoutInflater mInflater;
    private List<Exam> mExams; // Cached copy of words
    final private ListItemClickListener mOnClickListener;

    ExamListAdapter(Context context, ListItemClickListener mOnClickListener) {
        mInflater = LayoutInflater.from(context);
        this.mOnClickListener = mOnClickListener;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View itemView = mInflater.inflate(R.layout.recyclerview_item2, parent, false);
        View itemView = mInflater.inflate(R.layout.recipe, parent, false);
        return new ExamViewHolder(itemView);
    }




    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        if (mExams != null) {
            Exam current = mExams.get(position);
            String  senderFirstLetter = (String) current.getExamName().subSequence(0, 1);
            holder.imageTextView.setText(senderFirstLetter.toUpperCase());
            holder.examItemView.setText(current.getExamName());


        } else {
            // Covers the case of data not being ready yet.
            holder.examItemView.setText("No Exam");
        }
    }


    @Override
    public int getItemCount() {
        if (mExams != null)
            return mExams.size();
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

    class ExamViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.textView)
        TextView examItemView;
        @BindView(R.id.imageView)
        TextView imageTextView;


        private ExamViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick
        void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
           // Log.i("TAG","ok click"+getAdapterPosition());
        }

        @OnClick(R.id.delete_listItem)
        void deleteButton(View view){
           // Log.i("TAG DELETE","DELETE ok click"+getAdapterPosition());
            mOnClickListener.onListItemDelete(getAdapterPosition());

        }
    }





    void setExams(List<Exam> exams){
        mExams = exams;
        notifyDataSetChanged();
    }



}
