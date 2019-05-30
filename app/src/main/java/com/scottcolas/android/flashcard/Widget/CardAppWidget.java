package com.scottcolas.android.flashcard.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.scottcolas.android.flashcard.Model.Exam;
import com.scottcolas.android.flashcard.R;
import com.scottcolas.android.flashcard.View.ExamViewModel;

import java.util.List;

public class CardAppWidget extends AppWidgetProvider {
    private ExamViewModel mExamViewModel;
    private List<Exam> mExams;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

        mExamViewModel.getAllExams().observe((LifecycleOwner) context, new Observer<List<Exam>>() {
            @Override
            public void onChanged(@Nullable List<Exam> exams) {
                mExams = exams;
            }
        });
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.card_widget);
        // Construct an Intent object includes web adresss.
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://erenutku.com"));
        // In widget we are not allowing to use intents as usually. We have to use PendingIntent instead of 'startActivity'
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Here the basic operations the remote view can do.
        views.setTextViewText(R.id.subjectTxtView,"Math subject");
        views.setTextViewText(R.id.questionCountTxt,"12 counts ye");
        views.setTextViewText(R.id.dueDateTxtView,"due soon king");
        views.setTextViewText(R.id.appwidget_text,"this just text");
        views.setOnClickPendingIntent(R.id.tvWidget, pendingIntent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}