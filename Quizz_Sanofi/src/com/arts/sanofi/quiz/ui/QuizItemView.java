package com.arts.sanofi.quiz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arts.sanofi.quiz.R;

public class QuizItemView extends RelativeLayout implements OnClickListener {

    protected Context mContext;

    private LinearLayout mQuizItemMainLayout;
    private CheckBox mQuizItemCheckBox;
    private TextView mQuizItemText;
    private ImageView mQuizItemCircle;

    private int mAnswerState;

    public QuizItemView(Context context) {
        this(context, null);
    }

    public QuizItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initViews(mContext);

    }

    private void initViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.quiz_item_layout, this);

        mQuizItemMainLayout = (LinearLayout) view
                .findViewById(R.id.quiz_answer_layout);
        mQuizItemMainLayout.setOnClickListener(this);
        mQuizItemCheckBox = (CheckBox) view
                .findViewById(R.id.quiz_answer_checkbox);
        mQuizItemText = (TextView) view.findViewById(R.id.quiz_answer_text);
        mQuizItemCircle = (ImageView) view
                .findViewById(R.id.quiz_answer_circle);
    }

    public void reset() {
        mQuizItemCircle.setVisibility(View.INVISIBLE);
        mQuizItemMainLayout.setBackgroundResource(R.drawable.answer);
        mQuizItemCheckBox.setChecked(false);
    }

    public void setQuizItemText(String text) {
        mQuizItemText.setText(text);
    }

    public void setQuizAnswerState(int state) {
        mAnswerState = state;
    }

    public void setChecked(boolean isChecked) {
        mQuizItemCheckBox.setChecked(isChecked);
    }

    public boolean isChecked() {
        return mQuizItemCheckBox.isChecked();
    }

    public void showAnswer() {
        if (mAnswerState == 0) {
            mQuizItemCircle.setImageResource(R.drawable.red_circle);
            mQuizItemMainLayout.setBackgroundResource(R.drawable.error);
        } else {
            mQuizItemCircle.setImageResource(R.drawable.green_circle);
            mQuizItemMainLayout.setBackgroundResource(R.drawable.answer);
        }
        mQuizItemCircle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        mQuizItemCheckBox.setChecked(!mQuizItemCheckBox.isChecked());

    }

}
