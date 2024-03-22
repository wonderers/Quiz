package une.yasuaki.quiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //テキストビュー宣言
    private TextView countLabel,questionLabel;

    //ボタン宣言
    private Button btna1,btna2,btna3,btna4;

    //正解を入れるための宣言
    private String rightAnswer;

    //正解数をカウントするための宣言
    private int rightAnswerCount;

    //クイズ問題数カウント宣言
    private int quizCount=1;

    //クイズ問題数を選択ではなく定数にする場合
    //static final private int QUIZ_COUNT = 10;

    //クイズ問題数を選択にした場合の処理
    int QUIZ_COUNT;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    private SoundPlayer soundPlayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //アンドロイド端末の戻るボタン無効化処理
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //戻るボタンで何もしてほしくないので中身は書かない
            }
        });

        soundPlayer = new SoundPlayer(this);

        QUIZ_COUNT=getIntent().getIntExtra("QUIZ_LIMIT",5);

        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        btna1 = findViewById(R.id.btna1);
        btna2 = findViewById(R.id.btna2);
        btna3 = findViewById(R.id.btna3);
        btna4 = findViewById(R.id.btna4);

        btna1.setOnClickListener(this);
        btna2.setOnClickListener(this);
        btna3.setOnClickListener(this);
        btna4.setOnClickListener(this);

        // StartActivityからクイズカテゴリを取得
        int quizCategory = getIntent().getIntExtra("QUIZ_CATEGORY", 0);

        QuizDatabaseHelper dbHelper = new QuizDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String table = "quiz";
        String[] column = {"*"}; // 全てのカラム
        String selection = "category = ?"; // どのカテゴリのクイズを取得するか
        String[] selectionArgs = {String.valueOf(quizCategory)}; // カテゴリをセット
        String orderBy = "RANDOM()"; // ランダムに取得
        String limit = String.valueOf(QUIZ_COUNT); // 何問取得するか

        Cursor cursor = null;

        try {
            if (quizCategory != 0) {
                // カテゴリが「全て」以外の場合
                cursor = db.query(table, column, selection, selectionArgs,
                        null, null, orderBy, limit);
            } else {
                // カテゴリが「全て」の場合
                cursor = db.query(table, column, null, null, null, null, orderBy, limit);
            }

            // quizArrayを作成
            while (cursor.moveToNext()) {
                ArrayList<String> tmpArray = new ArrayList<>();
                tmpArray.add(cursor.getString(1)); // 問題
                tmpArray.add(cursor.getString(2)); // 正解
                tmpArray.add(cursor.getString(3)); // 選択肢１
                tmpArray.add(cursor.getString(4)); // 選択肢２
                tmpArray.add(cursor.getString(5)); // 選択肢３
                quizArray.add(tmpArray);
            }
        } finally {
            // Cursor とデータベースを閉じる
            if (cursor != null) cursor.close();
            db.close();
        }

        showNextQuiz();
    }

    public void showNextQuiz() {
        // クイズカウントラベルを更新
        countLabel.setText(getString(R.string.count_label, quizCount));

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 問題文を表示
        questionLabel.setText(quiz.get(0));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(1);

        // クイズ配列から問題文を削除
        quiz.remove(0);

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(quiz);

        // 解答ボタンに正解と選択肢３つを表示
        btna1.setText(quiz.get(0));
        btna2.setText(quiz.get(1));
        btna3.setText(quiz.get(2));
        btna4.setText(quiz.get(3));

        // このクイズをquizArrayから削除
        quizArray.remove(randomNum);
    }

    @Override
    public void onClick(View view) {
        // どの解答ボタンが押されたか
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;
        if (btnText.equals(rightAnswer)) {
            alertTitle = "正解!";
            rightAnswerCount++;
            soundPlayer.playCorrectSound();
        } else {
            alertTitle = "不正解...";
            soundPlayer.playWrongSound();
        }

        //ダイアログオブジェクトの作成
        DialogFragment dialogFragment = new AnswerDialogFragment();

        //ダイアログに「正解・解説文」を渡す
        Bundle args= new Bundle();
        args.putString("alertTitle",alertTitle);
        args.putString("rightAnswer",rightAnswer);
        dialogFragment.setArguments(args);

        //ダイアログが閉じないようにする
        dialogFragment.setCancelable(false);

        //ダイアログの表示
        dialogFragment.show(getSupportFragmentManager(),"answer_dialog");
    }

    //「OK」ボタンを押した時に呼ばれるメソッド
    public void okBtnClicked(){
        if (quizCount == QUIZ_COUNT) {
            // 結果画面へ移動
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
            intent.putExtra("QUIZ_COUNT",QUIZ_COUNT);
            startActivity(intent);
        } else {
            quizCount++;
            showNextQuiz();
        }
    }
}