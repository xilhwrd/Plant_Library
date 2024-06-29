package com.example.plant_library.Decorator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.LineBackgroundSpan;

import androidx.annotation.NonNull;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

public class VDotDecorator implements DayViewDecorator {

    private final Paint paint = new Paint();
    private final float dotRadius;
    private final int color;

    public VDotDecorator(Context context, int color) {
        this.color = color;
        this.dotRadius = context.getResources().getDisplayMetrics().density * 4; // Độ dày của dấu V
        paint.setColor(color);
        paint.setAntiAlias(true);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        // Trả về true nếu ngày này có sự kiện loại V
        return true; // Thay đổi logic để xác định ngày nào cần vẽ dấu V
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        view.addSpan(new VDotSpan(color, dotRadius));
    }

    private static class VDotSpan implements LineBackgroundSpan {

        private final int color;
        private final float dotRadius;

        private VDotSpan(int color, float dotRadius) {
            this.color = color;
            this.dotRadius = dotRadius;
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
            paint.setColor(color);

            float centerX = (left + right) / 2f;
            float centerY = (top + bottom) / 2f;

            // Vẽ dấu V
            canvas.drawLine(centerX - dotRadius, centerY + dotRadius,
                    centerX, centerY - dotRadius, paint);
            canvas.drawLine(centerX, centerY - dotRadius,
                    centerX + dotRadius, centerY + dotRadius, paint);

            paint.setColor(oldColor);
        }
    }
}

