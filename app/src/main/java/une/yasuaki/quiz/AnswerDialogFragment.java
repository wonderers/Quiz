package une.yasuaki.quiz;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AnswerDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // MainActivityから値を受け取る
        String alertTitle = requireArguments().getString("alertTitle");
        String rightAnswer = requireArguments().getString("rightAnswer");

        // ダイアログを作成
        return new MaterialAlertDialogBuilder(requireActivity())
                .setTitle(alertTitle)
                .setMessage("答え：" + rightAnswer)
                .setPositiveButton("OK", (dialog, which) -> ((MainActivity) requireActivity()).okBtnClicked())
                .create();
    }
}
