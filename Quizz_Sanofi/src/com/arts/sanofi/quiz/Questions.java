package com.arts.sanofi.quiz;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arts.sanofi.quiz.report.ReportHelper.ReportJsonReadListener;
import com.arts.sanofi.quiz.ui.QuizAnswersView;

public class Questions extends CounterActivity implements OnClickListener,
        ReportJsonReadListener {

    private static final int QUESTIONS_NUMBER = 7;

    private TextView mQuizQuestion;
    private Button mQuizShowAnswersButton;
    private ImageView mQuizArrow;

    private QuizAnswersView mQuizAnswersView;

    private int mQuestionIdx = 0;

    private String[] mQuestionsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_page);

        mQuizQuestion = (TextView) findViewById(R.id.quiz_question);
        mQuizShowAnswersButton = (Button) findViewById(R.id.answer_button);
        mQuizShowAnswersButton.setOnClickListener(this);
        mQuizArrow = (ImageView) findViewById(R.id.arrow_button);
        mQuizArrow.setOnClickListener(this);

        mQuizAnswersView = (QuizAnswersView) findViewById(R.id.quiz_answers_view);

        initQuestionsArray();

        updateQuestion();

    }

    @Override
    public void onBackPressed() {
        if (mQuestionIdx == 0) {
            finish();
        } else {
            mQuestionIdx--;

            updateQuestion();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.answer_button:
            mQuizAnswersView.showAnswer();
            break;
        case R.id.arrow_button:
            arrowClickPerform();
            break;
        default:
            break;
        }

    }

    private void initQuestionsArray() {
        int[] questionsResIdArray = { R.string.quiz_q1_question,
                R.string.quiz_q2_question, R.string.quiz_q3_question,
                R.string.quiz_q4_question, R.string.quiz_q5_question,
                R.string.quiz_q6_question, R.string.quiz_q7_question };

        mQuestionsArray = new String[QUESTIONS_NUMBER];
        for (int i = 0; i < QUESTIONS_NUMBER; i++) {
            mQuestionsArray[i] = getResources().getString(
                    questionsResIdArray[i]);
        }
    }

    private void updateQuestion() {
        mQuizQuestion.setText(mQuestionsArray[mQuestionIdx]);
        mQuizAnswersView.reset();

        if (mQuestionIdx == QUESTIONS_NUMBER - 1) {
            mQuizArrow.setImageResource(R.drawable.fa);
        }

        switch (mQuestionIdx) {
        case 0:
            mQuizAnswersView.setQuizItemsText(getResources().getStringArray(
                    R.array.quiz_q1_values));
            mQuizAnswersView.setQuizItemsAnswerState(getResources()
                    .getIntArray(R.array.quiz_q1_answers));
            break;
        case 1:
            mQuizAnswersView.setQuizItemsText(getResources().getStringArray(
                    R.array.quiz_q2_values));
            mQuizAnswersView.setQuizItemsAnswerState(getResources()
                    .getIntArray(R.array.quiz_q2_answers));
            break;
        case 2:
            mQuizAnswersView.setQuizItemsText(getResources().getStringArray(
                    R.array.quiz_q3_values));
            mQuizAnswersView.setQuizItemsAnswerState(getResources()
                    .getIntArray(R.array.quiz_q3_answers));
            break;
        case 3:
            mQuizAnswersView.setQuizItemsText(getResources().getStringArray(
                    R.array.quiz_q4_values));
            mQuizAnswersView.setQuizItemsAnswerState(getResources()
                    .getIntArray(R.array.quiz_q4_answers));
            break;
        case 4:
            mQuizAnswersView.setQuizItemsText(getResources().getStringArray(
                    R.array.quiz_q5_values));
            mQuizAnswersView.setQuizItemsAnswerState(getResources()
                    .getIntArray(R.array.quiz_q5_answers));
            break;
        case 5:
            mQuizAnswersView.setQuizItemsText(getResources().getStringArray(
                    R.array.quiz_q6_values));
            mQuizAnswersView.setQuizItemsAnswerState(getResources()
                    .getIntArray(R.array.quiz_q6_answers));
            break;
        case 6:
            mQuizAnswersView.setQuizItemsText(getResources().getStringArray(
                    R.array.quiz_q7_values));
            mQuizAnswersView.setQuizItemsAnswerState(getResources()
                    .getIntArray(R.array.quiz_q7_answers));
            break;
        default:
            break;
        }
    }

    private void arrowClickPerform() {
        if (mQuestionIdx < QUESTIONS_NUMBER - 1) {
            mQuestionIdx++;
            updateQuestion();
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        // we have to call this for use reports
        super.onDestroy();
    }

}
