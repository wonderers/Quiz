package une.yasuaki.quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class QuizDatabaseHelper extends SQLiteOpenHelper {

    static final private String DBNAME = "quiz.sqlite";

    static final private int VERSION = 1;

    String[][] quizData = {

            // {"ID", "カテゴリー","画像","問題", "正解", "選択肢１", "選択肢２", "選択肢３"}
            //レトロゲーム
            {"1","1","image_circle","スーパーマリオブラザーズが初めてリリースされたのは何年ですか？", "1985年", "1980年", "1983年", "1987年"},
            {"2","1","image_triangle","ゼルダの伝説シリーズの主人公の名前は何ですか？", "リンク", "マリオ", "ソニック", "ピカチュウ"},
            {"3","1","image_square","ドンキーコングで樽を投げる敵の名前は何ですか？", "ドンキーコング","ボウザ", "ガノン", "ワリオ"},
            {"4","1","image_pentagon","パックマンがゴーストに反撃するために食べるアイテムは何ですか？", "パワーエサ", "スーパーマッシュルーム", "スターマン", "ファイアフラワー"},
            {"5","1","image_hexagon", "メトロイドシリーズの主人公の名前は何ですか？", "サムス・アラン","ピーチ姫", "ヨッシー", "ルイージ"},
            {"6","1","image_circle","ファイナルファンタジーVIIの主人公の名前は何ですか？", "クラウド","スコール", "ティーダ", "ゼィダン"},
            {"7","1","g1_7","ストリートファイターIIで、昇竜拳を使うキャラクターは誰ですか？", "リュウ", "ブランカ", "チュンリー", "ザンギエフ"},
            {"8","1","image_square","テトリスで最も高得点を出すためには何ラインを一度に消す必要がありますか？", "4ライン", "1ライン", "2ライン", "3ライン"},
            {"9","1","g1_9","ドラゴンクエストVで、主人公が最初に出会う仲間の名前は何ですか？", "ビアンカ", "ピッコロ", "フローラ", "ルフィア"},
            {"10","1","image_hexagon","ポケモンレッド/グリーンで、最初に選べるポケモンは何ですか？", "フシギダネ", "ピカチュウ", "ニャース", "ポッポ"},
            {"11","1","g1_11","ゼビウスで、主人公が操作する戦闘機の名前は何ですか？", "ソルバルウ", "ヴァルキリー", "ファイター", "ギャラクシアン"},
            {"12","1","g1_12","グラディウスで、主人公が操作する戦闘機の名前は何ですか？", "ビックバイパー", "スペースシップ", "ファルコン", "メタルスラッグ"},
            //アニメ
            {"13","2","image_circle","ドラゴンボールの主人公の名前は何でしょうか？", "ゴクウ", "ナルト", "ルフィ", "イチゴ"},
            {"14","2","image_triangle","鬼滅の刃で、炭治郎の妹の名前は何でしょうか？", "禰豆子", "さくら", "ヒナタ", "ユミコ"},
            {"15","2","image_square","ワンピースで、ルフィの夢は何でしょうか？", "海賊王になる", "最強の忍者になる", "宇宙を旅する", "ギタリストになる"},
            {"16","2","image_pentagon","カウボーイビバップの主人公の名前は何でしょうか？", "スパイク・スピーゲル", "ナルト", "ルフィ", "イチゴ"},
            {"17","2","image_hexagon","新世紀エヴァンゲリオンで、初号機のパイロットは誰でしょうか？", "碇シンジ", "綾波レイ", "惣流・アスカ・ラングレー", "渚カヲル"},
            {"18","2","image_circle","名探偵コナンの主人公の正体の名前は何でしょうか？", "江戸川コナン", "工藤新一", "黒羽快斗", "毛利蘭"},
            {"19","2","image_triangle","ルパン三世の主人公の愛車は何でしょうか？", "フィアット500", "フェラーリ", "ランボルギーニ", "メルセデス・ベンツ"},
            {"20","2","image_square","ジョジョの奇妙な冒険で、第一部の主人公の名前は何でしょうか？", "ジョナサン・ジョースター", "ジョセフ・ジョースター", "ジョタロ・クジョー", "ジョルノ・ジョバァーナ"},
            {"21","2","image_pentagon","涼宮ハルヒの憂鬱で、ハルヒが設立した部活の名前は何でしょうか？", "SOS団", "軽音楽部", "文芸部", "科学部"},
            {"22","2","image_hexagon","銀魂で、坂田銀時が営む店の名前は何でしょうか？", "万事屋", "銀座", "銀魂堂", "銀時堂"},
            //漫画
            {"23","3","image_circle","ONE PIECEの作者は誰でしょうか？", "尾田栄一郎", "荒木飛呂彦", "鳥山明", "井上井恵介"},
            {"24","3","image_triangle","ドラゴンボールの主人公の名前は何でしょうか？", "孫悟空", "ナルト", "ルフィ", "ゴン"},
            {"25","3","image_square","東京喰種トーキョーグールの主人公の名前は何でしょうか？", "金木研", "佐々木琲世", "鈴屋什造", "有馬貴将"},
            {"26","3","image_pentagon","呪術廻戦の主人公の名前は何でしょうか？", "虎杖悠仁", "五条悟", "伏黒恵", "両面宿儺"},
            {"27","3","image_hexagon","ハイキュー!!の主人公の名前は何でしょうか？", "日向翔陽", "影山飛雄", "黒尾鉄朗", "澤村大地"},
            {"28","3","image_circle","SLAM DUNKの主人公の名前は何でしょうか？", "桜木花道", "流川楓", "三井寿", "安西光義"},
            {"29","3","image_triangle","キングダムの主人公の名前は何でしょうか？", "信", "昭文君", "王騎", "呂不韋"},
            {"30","3","image_square","チェンソーマンの主人公の名前は何でしょうか？", "デンジ", "パワー", "アキ", "ポチタ"},
            {"31","3","image_pentagon","ワンピースの主人公、モンキー・D・ルフィが最初に入手した悪魔の実は何でしょうか？", "ゴムゴムの実", "メラメラの実", "ゴラゴラの実", "ハナハナの実"},
            {"32","3","image_hexagon","「巨人の星」の主人公の名前は何でしょうか？", "星飛雄馬", "星一徹", "星二郎", "星三四郎"},
            //流行
            {"33","4","image_circle","1980年代に日本で大流行したファッションスタイルは何でしょうか？", "ボディコンスタイル", "ロココスタイル", "ヴィクトリアンスタイル", "フラッパースタイル"},
            {"34","4","image_triangle","2000年代に日本で流行したゲーム機は何でしょうか？", "プレイステーション2", "ファミコン", "スーパーファミコン", "ゲームボーイ"},
            {"35","4","image_square","2010年代に日本で流行したSNSは何でしょうか？", "ライン", "フェイスブック", "ツイッター", "インスタグラム"},
            {"36","4","image_pentagon","1990年代に日本で流行したゲームは何でしょうか？", "ポケットモンスター（ポケモン）", "スーパーマリオブラザーズ", "ドラゴンクエストIII", "ファイナルファンタジーVII"},
            {"37","4","image_hexagon","1980年代に日本で流行したテレビドラマは何でしょうか？", "西部警察", "太陽にほえろ！", "相棒", "HERO"},
            {"38","4","image_circle","1990年代に日本で流行したお笑い芸人は誰でしょうか？", "ダウンタウン", "ビートたけし", "明石家さんま", "とんねるず"},
            {"39","4","image_triangle","2000年代に日本で流行したアイドルグループは何でしょうか？", "AKB48", "SMAP", "嵐", "モーニング娘。"},
            {"40","4","image_square","1980年代に日本で流行したお菓子は何でしょうか？", "ポッキー", "ハイチュウ", "チョコボール", "ブラックサンダー"},
            {"41","4","image_pentagon","1990年代に日本で流行したおもちゃは何でしょうか？", "タミヤのミニ四駆", "バービー", "テレビゲーム", "レゴ"},
            {"42","4","image_hexagon","2010年代に日本で流行したゲームは何でしょうか？", "モンスターハンター", "ポケットモンスター", "ドラゴンクエスト", "ファイナルファンタジー"}

    };

    QuizDatabaseHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを作成
        db.execSQL("CREATE TABLE quiz (" +
                "ID TEXT PRIMARY KEY, category TEXT,questionImage TEXT,question TEXT, answer TEXT, choice1 TEXT," +
                "choice2 TEXT, choice3 TEXT)");

        // トランザクション開始
        db.beginTransaction();
        try {
            // SQL文を準備
            SQLiteStatement sql = db.compileStatement(
                    "INSERT INTO quiz (ID, category,questionImage,question, answer, choice1, choice2, choice3)" +
                            "VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
            );

            // クイズデータを１行ずつ追加する
            for (String[] quizDatum : quizData) {
                // Valueをセット
                // bindString(index, value)
                sql.bindString(1, quizDatum[0]); // ID
                sql.bindString(2, quizDatum[1]); // カテゴリ
                sql.bindString(3, quizDatum[2]); // 画像
                sql.bindString(4, quizDatum[3]); // 問題
                sql.bindString(5, quizDatum[4]); // 正解
                sql.bindString(6, quizDatum[5]); // 選択肢1
                sql.bindString(7, quizDatum[6]); // 選択肢2
                sql.bindString(8, quizDatum[7]); // 選択肢3

                sql.executeInsert();
            }
            // 成功
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            // 失敗
            e.printStackTrace();

        } finally {
            // トランザクション終了
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}