package une.yasuaki.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //スタート画面から戻るボタンで終了も考慮したいのでスタート画面での戻るボタン無効化は保留
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                moveTaskToBack(true);
            }
        });

        //スタートボタンクリック時に呼び出されるイベントリスナー
        Button btnStart=findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> {
            //スピナーから選択したクイズカテゴリーを渡すために値を入れる
            Spinner spinner = findViewById(R.id.quizCate);
            Spinner spinner2=findViewById(R.id.quizLimit);

            int quizCategory = spinner.getSelectedItemPosition();
            int quizLimit=Integer.parseInt(spinner2.getSelectedItem().toString());

            // MainActivityへのインテントを作成
            Intent i=new Intent(StartActivity.this,MainActivity.class);
            i.putExtra("QUIZ_CATEGORY", quizCategory);
            i.putExtra("QUIZ_LIMIT",quizLimit);
            startActivity(i);
        });
    }

    // アプリの終了クリック処理
    public void onClose( View v){
        moveTaskToBack(true);       // 画面を閉じる（アクティビティの終了）
    }
}
