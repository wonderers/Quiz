package une.yasuaki.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private SoundPlayer soundPlayer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        soundPlayer = new SoundPlayer(this);

        //アンドロイド端末の戻るボタン無効化処理
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                //戻るボタンで何もしてほしくないので中身は書かない
            }
        });

        ImageView resultImage=findViewById(R.id.resultImage);
        TextView resultRate=findViewById(R.id.resultRate);
        TextView resultScoreLabel = findViewById(R.id.resultScoreLabel);
        TextView resultCountLabel = findViewById(R.id.resultCountLabel);
        TextView totalLabel = findViewById(R.id.totalScoreLabel);

        // 正解数をインテントから取得
        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);
        int quizcount = getIntent().getIntExtra("QUIZ_COUNT", 5);

        // (SharedPreferencesは端末保存、MODE_PRIVATEは自アプリからのみ変更可)Android端末のトータルスコアの読み出し
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        int totalScore = prefs.getInt("TOTAL_SCORE", 0);

        // トータルスコアに今回のスコアを加算 没
        //totalScore += score;

        // 結果に応じて結果画像をセット
        int resultrate= (int) (((float) score /quizcount)*100);
        resultRate.setText(getString(R.string.result_rate, resultrate));
        if(resultrate>=60){
            resultImage.setImageResource(
                    getResources().getIdentifier("inu_maru", "drawable", getPackageName())
            );
            soundPlayer.playCorrectSound();
        }else{
            resultImage.setImageResource(
                    getResources().getIdentifier("inu_batsu", "drawable", getPackageName())
            );
            soundPlayer.playWrongSound();
        }

        // TextViewに表示する
        resultScoreLabel.setText(getString(R.string.result_score, score));
        resultCountLabel.setText(getString(R.string.result_count, quizcount));

        // トータルスコアを保存
        if(resultrate>=totalScore){
            totalScore=resultrate;
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("TOTAL_SCORE", totalScore);
            editor.apply();
        }
        totalLabel.setText(getString(R.string.result_total_score, totalScore));

        //戻るボタン処理
        Button resultReturn=findViewById(R.id.resultReturn);
        resultReturn.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, StartActivity.class);
            //intent.putExtra("",);
            startActivity(intent);
        });
    }

    // アプリの終了クリック処理
    public void onClose( View v){
        Intent intent = new Intent(ResultActivity.this, StartActivity.class);
        //intent.putExtra("",);
        startActivity(intent);
        moveTaskToBack(true);
    }
}
