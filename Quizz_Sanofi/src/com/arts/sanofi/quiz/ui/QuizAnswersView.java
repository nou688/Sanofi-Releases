package com.arts.sanofi.quiz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.arts.sanofi.quiz.R;

public class QuizAnswersView extends LinearLayout {

    protected Context mContext;

    private QuizItemView mQuizItem1;
    private QuizItemView mQuizItem2;
    private QuizItemView mQuizItem3;
    private QuizItemView mQuizItem4;

    public QuizAnswersView(Context context) {
        this(context, null);
    }

    public QuizAnswersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews(mContext);

    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.quiz_answers_layout, this);

        mQuizItem1 = (QuizItemView) view.findViewById(R.id.quiz_item_1);
        mQuizItem2 = (QuizItemView) view.findViewById(R.id.quiz_item_2);
        mQuizItem3 = (QuizItemView) view.findViewById(R.id.quiz_item_3);
        mQuizItem4 = (QuizItemView) view.findViewById(R.id.quiz_item_4);
    }

    public void setQuizItemsText(String[] quizItemsArray) {
        mQuizItem1.setQuizItemText(quizItemsArray[0]);
        mQuizItem2.setQuizItemText(quizItemsArray[1]);
        mQuizItem3.setQuizItemText(quizItemsArray[2]);
        mQuizItem4.setQuizItemText(quizItemsArray[3]);
    }

    public void setQuizItemsAnswerState(int[] quizItemsArray) {
        mQuizItem1.setQuizAnswerState(quizItemsArray[0]);
        mQuizItem2.setQuizAnswerState(quizItemsArray[1]);
        mQuizItem3.setQuizAnswerState(quizItemsArray[2]);
        mQuizItem4.setQuizAnswerState(quizItemsArray[3]);
    }

    public void showAnswer() {
        mQuizItem1.showAnswer();
        mQuizItem2.showAnswer();
        mQuizItem3.showAnswer();
        mQuizItem4.showAnswer();
    }

    public void reset() {
        mQuizItem1.reset();
        mQuizItem2.reset();
        mQuizItem3.reset();
        mQuizItem4.reset();
    }

}
