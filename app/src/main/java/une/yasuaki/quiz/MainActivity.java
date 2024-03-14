package une.yasuaki.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.Random;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //テキストビュー宣言
    private TextView countLabel,questionLabel;

    //ボタン宣言
    private Button btna1,btna2,btna3,btna4;

    //正解宣言（？）
    private int rightAnswer;

    //正解数宣言
    private int rightAnswerCount;

    //クイズ問題数カウント宣言
    private int quizCount;

    //クイズ問題をデータの大きさに応じて取り出す
    //for(int i=0;i<quizData.length; i++){
        //問題リストの宣言
        ArrayList<ArrayList<String>> tmpArray = new ArrayList<>();
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}