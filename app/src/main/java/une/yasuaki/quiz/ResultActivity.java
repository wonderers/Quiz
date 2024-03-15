package une.yasuaki.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultLabel = findViewById(R.id.resultLabel);
        TextView totalScoreLabel = findViewById(R.id.totalScoreLabel);

        // 正解数をインテントから取得
        int score = getIntent().getIntExtra("RIGHT_ANSWER_COUNT", 0);

        // (SharedPreferencesは端末保存、MODE_PRIVATEは自アプリからのみ変更可)Android端末のトータルスコアの読み出し
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        int totalScore = prefs.getInt("TOTAL_SCORE", 0);

        // トータルスコアに今回のスコアを加算
        totalScore += score;

        // TextViewに表示する
        resultLabel.setText(getString(R.string.result_score, score));
        totalScoreLabel.setText(getString(R.string.result_total_score, totalScore));

        // トータルスコアを保存
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("TOTAL_SCORE", totalScore);
        editor.apply();

        //戻るボタン処理
        Button resultReturn=findViewById(R.id.resultReturn);
        resultReturn.setOnClickListener(view -> {
            Intent intent = new Intent(ResultActivity.this, StartActivity.class);
            //intent.putExtra("",);
            startActivity(intent);
        });
    }
}
