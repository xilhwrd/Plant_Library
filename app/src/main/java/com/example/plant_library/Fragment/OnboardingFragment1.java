package com.example.plant_library.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.plant_library.R;

public class OnboardingFragment1 extends Fragment {
    TextView tvTitle;
    View mView;
    public OnboardingFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_onboarding1, container, false);
        tvTitle = mView.findViewById(R.id.tv_title_ob1);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();

        String blackText = "Update extremely useful\n" +
                "plant";
        SpannableString firstSpannable = new SpannableString(blackText);
        firstSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, blackText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(firstSpannable);

        String greenText = " knowledge";
        SpannableString secondSpannable = new SpannableString(greenText);
        secondSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#0D9276")), 0, greenText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(secondSpannable);

        tvTitle.setText(stringBuilder);

        return mView;
    }
}