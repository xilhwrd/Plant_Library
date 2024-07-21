package com.example.plant_library.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.plant_library.R;

public class OnboardingFragment2 extends Fragment {
    TextView tvTitle;
    View mView;
    public OnboardingFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_onboarding2, container, false);
        tvTitle = mView.findViewById(R.id.tv_title_ob2);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder();
        String greenText = "Hỗ trợ";
        SpannableString greenSpannable = new SpannableString(greenText);
        greenSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#0D9276")), 0, greenText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(greenSpannable);

        String blackText = " tìm kiếm và phân loại thực vật nhanh chóng";
        SpannableString blackSpannable = new SpannableString(blackText);
        blackSpannable.setSpan(new ForegroundColorSpan(Color.BLACK), 0, blackText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        stringBuilder.append(blackSpannable);

        tvTitle.setText(stringBuilder);
        return mView;
    }
}