package com.example.quizapp.utils;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.quizapp.R;

public class RadioGroupUtils {

    public static void setupCustomRadioGroup(RadioGroup rg) {
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
}
