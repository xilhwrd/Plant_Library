package com.example.plant_library;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import androidx.annotation.NonNull;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

public class CircleDecorator implements DayViewDecorator {

    private final int color;
    private final float radius;

    public CircleDecorator(Context context, int color) {
        this.color = color;
        this.radius = context.getResources().getDisplayMetrics().density * 18; // Kích thước bán kính của hình tròn (tùy chỉnh)
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // Trả về true nếu có sự kiện cho ngày này (điều kiện có thể thay đổi)
        return true; // Thay đổi logic để xác định ngày nào cần vẽ hình tròn
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        view.addSpan(new CircleSpan(color, radius));
    }

    private static class CircleSpan implements LineBackgroundSpan {
        private final int color;
        private final float radius;

        private CircleSpan(int color, float radius) {
            this.color = color;
            this.radius = radius;
        }

        @Override
        public void drawBackground(
                @NonNull Canvas canvas,
                @NonNull Paint paint,
                int left,
                int right,
                int top,
                int baseline,
                int bottom,
                @NonNull CharSequence text,
                int start,
                int end,
                int lnum
        ) {
            int oldColor = paint.getColor();
            paint.setColor(color); // Độ trong suốt của hình tròn
            canvas.drawCircle((left + right) / 2, (top + bottom) / 2, radius, paint);
            paint.setColor(oldColor);
        }
    }
}
