package com.example.plant_library;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;

public class EventDecorator implements DayViewDecorator {

    private final CalendarDay eventDay;
    private final int color;

    private final boolean isVEvent; // true nếu là sự kiện A (V), false nếu là sự kiện B (X)

    public EventDecorator(CalendarDay eventDay, int color, boolean isVEvent) {
        this.eventDay = eventDay;
        this.color = color;
        this.isVEvent = isVEvent;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(eventDay); // Trả về true nếu ngày này là ngày có sự kiện
    }

    @Override
    public void decorate(@NonNull DayViewFacade view) {
        // Đặt màu nền cho ngày có sự kiện
        ShapeDrawable circleDrawable = new ShapeDrawable(new OvalShape());
        circleDrawable.getPaint().setColor(color);
        view.setBackgroundDrawable(circleDrawable);
        // Thêm dấu V hoặc X
        view.addSpan(new EventSpan(isVEvent));
    }

    private static class EventSpan extends Drawable {

        private final boolean isVEvent;

        EventSpan(boolean isVEvent) {
            this.isVEvent = isVEvent;
        }

        @Override
        public void draw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setStrokeWidth(5); // Độ dày của đường vẽ
            paint.setStyle(Paint.Style.STROKE); // Vẽ chỉ viền

            float centerX = canvas.getWidth() / 2f;
            float centerY = canvas.getHeight() / 2f;
            float radius = Math.min(centerX, centerY) - paint.getStrokeWidth() / 2f;

            if (isVEvent) {
                paint.setColor(Color.BLACK); // Màu đường vẽ cho dấu V
                // Vẽ dấu V
                canvas.drawLine(centerX, centerY + radius / 2, centerX - radius / 2, centerY - radius / 2, paint);
                canvas.drawLine(centerX, centerY + radius / 2, centerX + radius / 2, centerY - radius / 2, paint);
            } else {
                paint.setColor(Color.RED); // Màu đường vẽ cho dấu X
                // Vẽ dấu X
                canvas.drawLine(centerX - radius / 2, centerY - radius / 2, centerX + radius / 2, centerY + radius / 2, paint);
                canvas.drawLine(centerX - radius / 2, centerY + radius / 2, centerX + radius / 2, centerY - radius / 2, paint);
            }
        }

        @Override
        public void setAlpha(int alpha) {
            // Không cần implement
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }


    }
}


