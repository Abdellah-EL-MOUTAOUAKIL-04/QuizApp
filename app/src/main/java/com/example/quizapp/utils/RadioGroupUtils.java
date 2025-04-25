package com.example.quizapp.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.core.content.ContextCompat;
import com.example.quizapp.R;

public class RadioGroupUtils {

    public static void setupCustomRadioGroup(RadioGroup rg, Context context) {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    View child = radioGroup.getChildAt(i);
                    if (child instanceof RadioButton) {
                        RadioButton radioButton = (RadioButton) child;

                        if (radioButton.getId() == checkedId) {
                            radioButton.setBackgroundResource(R.drawable.rounded_button_checked);
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_fixed, 0);
                        } else {
                            radioButton.setBackgroundResource(R.drawable.rounded_radio_button);
                            radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked_placeholder, 0);
                        }
                    }
                }
            }
        });
    }


    public static void markAnswerCorrect(RadioButton radioButton, Context context) {
        // Set the background resource for the correct answer
        radioButton.setBackgroundResource(R.drawable.rounded_button_correct);

        // Get the Drawable for the correct icon
        Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_checked_fixed);
        icon.setTint(ContextCompat.getColor(context, R.color.green_900));
        // Set the compound drawable (right side icon) using the Drawable object
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);

        // Optionally: Vibrate for the correct answer
        // vibratePhone(context);
    }

    // Méthode pour indiquer que la réponse est incorrecte
    public static void markAnswerIncorrect(RadioButton radioButton, Context context) {
        radioButton.setBackgroundResource(R.drawable.rounded_button_incorrect);

        Drawable icon = ContextCompat.getDrawable(context, R.drawable.ic_incorrect_fixed);
        icon.setTint(ContextCompat.getColor(context, R.color.red_900));
        radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
        // Vibration pour la mauvaise réponse
        vibratePhone(context);
    }

    // Fonction de vibration du téléphone
    private static void vibratePhone(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            // Vibration courte de 100 ms
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // Pour les versions antérieures à O
                vibrator.vibrate(100);
            }
        }
    }
}
