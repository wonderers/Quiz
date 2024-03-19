package une.yasuaki.quiz;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int correctSound;
    private static int wrongSound;

    public SoundPlayer(Context context) {
        int SOUND_POOL_MAX = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();

        } else {
            soundPool = new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC, 0);
        }

        correctSound = soundPool.load(context, R.raw.correct, 1);
        wrongSound = soundPool.load(context, R.raw.incorrect, 1);
    }

    public void playCorrectSound() {
        soundPool.play(correctSound, 1.0f, 1.0f, 1, 0, 1.3f);
    }

    public void playWrongSound() {
        soundPool.play(wrongSound, 1.0f, 1.0f, 1, 0, 1.3f);
    }
}