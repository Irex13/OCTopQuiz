package com.irex.octopquiz.model;


import java.util.Collections;
import java.util.List;

public class QuestionBank {
    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        // Shuffle the question list before storing it
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList);
        mNextQuestionIndex = 0;
    }

    public Question getQuestion() {
        // Loop over the questions and return a new one at each call
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }

        // Please note the post-incrementation
        return mQuestionList.get(mNextQuestionIndex++);
    }
}

