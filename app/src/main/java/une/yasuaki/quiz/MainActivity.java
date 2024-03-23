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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import une.yasuaki.quiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityMainBinding binding;

    //テキストビュー宣言
    private TextView countLabel,questionLabel;

    //ボタン宣言
    private Button btna1,btna2,btna3,btna4;

    //正解を入れるための宣言
    private String rightAnswer;

    //正解数をカウントするための宣言
    private int rightAnswerCount=0;

    //クイズ問題数カウント宣言
    private int quizCount=1;

    //クイズ問題数を選択ではなく定数にする場合
    //static final private int QUIZ_COUNT = 10;

    //クイズ問題数を選択にした場合の処理
    int QUIZ_COUNT;

    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();
    private SoundPlayer soundPlayer = null;

    //選択肢シャッフルの為の準備
    private String select1,select2,select3,select4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //アンドロイド端末の戻るボタン無効化処理
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //戻るボタンで何もしてほしくないので中身は書かない
            }
        });

        soundPlayer = new SoundPlayer(this);

        QUIZ_COUNT=getIntent().getIntExtra("QUIZ_LIMIT",5);

        binding.countLabel.setText(getString(R.string.count_label, quizCount));
        //questionLabel = findViewById(R.id.questionLabel);

        binding.btna1.setOnClickListener(this);
        binding.btna2.setOnClickListener(this);
        binding.btna3.setOnClickListener(this);
        binding.btna4.setOnClickListener(this);

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
                tmpArray.add(cursor.getString(0)); // ID
                tmpArray.add(cursor.getString(1)); // カテゴリーID
                tmpArray.add(cursor.getString(2)); // 画像
                tmpArray.add(cursor.getString(3)); // 問題
                tmpArray.add(cursor.getString(4)); // 正解
                tmpArray.add(cursor.getString(5)); // 選択肢１
                tmpArray.add(cursor.getString(6)); // 選択肢２
                tmpArray.add(cursor.getString(7)); // 選択肢３
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
        //countLabel.setText(getString(R.string.count_label, quizCount));

        // ランダムな数字を取得
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());

        // randomNumを使って、quizArrayからクイズを一つ取り出す
        ArrayList<String> quiz = quizArray.get(randomNum);

        // 問題画像をセット
        binding.questionImage.setImageResource(
                getResources().getIdentifier(quiz.get(2), "drawable", getPackageName())
        );

        // 問題文を表示
        binding.questionLabel.setText(quiz.get(3));

        // 正解をrightAnswerにセット
        rightAnswer = quiz.get(4);

        //正解と選択肢を一旦セット
        select1=quiz.get(4);
        select2=quiz.get(5);
        select3=quiz.get(6);
        select4=quiz.get(7);

        //シャッフル用に配列に入れる
        List<String> select = new ArrayList<>(Arrays.asList(select1, select2, select3, select4));

        // 正解と選択肢３つをシャッフル
        Collections.shuffle(select);

        // 解答ボタンに正解と選択肢３つを表示
        binding.btna1.setText(select.get(0));
        binding.btna2.setText(select.get(1));
        binding.btna3.setText(select.get(2));
        binding.btna4.setText(select.get(3));

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