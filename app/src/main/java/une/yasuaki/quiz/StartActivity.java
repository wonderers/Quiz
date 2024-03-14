package une.yasuaki.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        //リスト項目をArray Listとして準備
        final ArrayList<String> data=new ArrayList<>();
        data.add("問題ジャンル1");
        data.add("問題ジャンル2");

        //配列アダプターを作成&ListViewに登録
        ListView list= findViewById(R.id.list);
        list.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_single_choice,data
        ));

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(StartActivity.this, String.format("選択したのは%sです",((TextView)view).getText()), Toast.LENGTH_LONG).show();
        });

        //スタートボタンクリック時に呼び出されるイベントリスナー
        Button btnStart=findViewById(R.id.btnStart);
        btnStart.setOnClickListener(view -> {
            // MainActivityへのインテントを作成
            Intent i=new Intent(StartActivity.this,MainActivity.class);
            startActivity(i);
        });
    }
}
