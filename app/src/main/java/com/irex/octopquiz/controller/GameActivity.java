package com.irex.octopquiz.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.irex.octopquiz.R;
import com.irex.octopquiz.model.Question;
import com.irex.octopquiz.model.QuestionBank;
import com.irex.octopquiz.model.User;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestionTextView;
    private Button ga1;
    private Button ga2;
    private Button ga3;


    private Button ga4;
    private QuestionBank mQuestionBank;
    private Question mCurrentQuestion;

    private int mScore;
    private int mnbOfQuestions;
    private boolean mEnableTouchEvents;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "currentScore";
    public static final String BUNDLE_STATE_QUESTION = "currentQuestion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mnbOfQuestions = savedInstanceState.getInt((BUNDLE_STATE_QUESTION));
        } else {
            mScore = 0;
            mnbOfQuestions = 4;
        }

        mQuestionTextView = findViewById(R.id.activity_game_question_text);
        ga1 = findViewById(R.id.activity_game_a1_btn);
        ga2 = findViewById(R.id.activity_game_a2_btn);
        ga3 = findViewById(R.id.activity_game_a3_btn);
        ga4 = findViewById(R.id.activity_game_a4_btn);

        ga1.setTag(0);
        ga2.setTag(1);
        ga3.setTag(2);
        ga4.setTag(3);


        ga1.setOnClickListener(this);
        ga2.setOnClickListener(this);
        ga3.setOnClickListener(this);
        ga4.setOnClickListener(this);



        mEnableTouchEvents = true;

        mQuestionBank = this.generateQuestions();
        mCurrentQuestion = mQuestionBank.getQuestion();
        this.displayQuestion(mCurrentQuestion);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mnbOfQuestions);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        int responseId = (int) v.getTag();
        final Button currentButton = (Button) v;
        if (responseId == mCurrentQuestion.getAnswerIndex()){
            //Good answer
            currentButton.setBackgroundColor(Color.GREEN);
            Toast.makeText(this,"Correct!", Toast.LENGTH_SHORT).show();
            mScore++;
        } else {
            //Wrong answer
            currentButton.setBackgroundColor(Color.RED);
            Toast.makeText(this,"Wrong answer!", Toast.LENGTH_SHORT).show();
        }
        mEnableTouchEvents = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentButton.setBackgroundColor(Color.WHITE);

                if (--mnbOfQuestions <= 0) {
                    //End
                    endGame();
                    finish();

                } else {
                    mEnableTouchEvents = true;
                    mCurrentQuestion = mQuestionBank.getQuestion();
                    displayQuestion(mCurrentQuestion);
                }
            }
        },2000);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }

    private void endGame(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();

    }

    private void displayQuestion(final Question question) {
        mQuestionTextView.setText(question.getQuestion());
        ga1.setText(question.getChoiceList().get(0));
        ga2.setText(question.getChoiceList().get(1));
        ga3.setText(question.getChoiceList().get(2));
        ga4.setText(question.getChoiceList().get(3));
    }



    private QuestionBank generateQuestions() {
        Question question1 = new Question("What is the name of the current french president?",
                Arrays.asList("François Hollande", "Emmanuel Macron", "Jacques Chirac", "François Mitterand"),
                1);

        Question question2 = new Question("How many countries are there in the European Union?",
                Arrays.asList("15", "24", "28", "32"),
                2);

        Question question3 = new Question("Who is the creator of the Android operating system?",
                Arrays.asList("Andy Rubin", "Steve Wozniak", "Jake Wharton", "Paul Smith"),
                0);

        Question question4 = new Question("When did the first man land on the moon?",
                Arrays.asList("1958", "1962", "1967", "1969"),
                3);

        Question question5 = new Question("What is the capital of Romania?",
                Arrays.asList("Bucarest", "Warsaw", "Budapest", "Berlin"),
                0);

        Question question6 = new Question("Who did the Mona Lisa paint?",
                Arrays.asList("Michelangelo", "Leonardo Da Vinci", "Raphael", "Carravagio"),
                1);

        Question question7 = new Question("In which city is the composer Frédéric Chopin buried?",
                Arrays.asList("Strasbourg", "Warsaw", "Paris", "Moscow"),
                2);

        Question question8 = new Question("What is the country top-level domain of Belgium?",
                Arrays.asList(".bg", ".bm", ".bl", ".be"),
                3);

        Question question9 = new Question("What is the house number of The Simpsons?",
                Arrays.asList("42", "101", "666", "742"),
                3);

        return new QuestionBank(Arrays.asList(question1,
                question2,
                question3,
                question4,
                question5,
                question6,
                question7,
                question8,
                question9));
    }


}
