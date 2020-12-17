package by.htp.first.homework4_2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomView extends View {

    interface OnCustomViewActionListener {
        void onActionDown (float x, float y);
    }

    private int diameter;

    private int centreX;
    private int centreY;
    private int radius1;
    private int radius2;
    private Paint paint = new Paint();
    private RectF oval = new RectF();
    private OnCustomViewActionListener onCustomViewActionListener;


    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        try {
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.CustomView);
           paint.setColor(typedArray.getColor(R.styleable.CustomView_circleColor, Color.BLUE));
           diameter = (int) typedArray.getDimension(R.styleable.CustomView_circleDefaultDiameter, 200);
            typedArray.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        centreX = viewWidth / 2;
        centreY = viewHeight / 2;
        calculateCords();

    }

    public void updateView(int diameter) {
        this.diameter = diameter;

    }

    public void calculateCords() {

        radius1 = diameter / 2;
        radius2 = diameter / 6;

        oval.set(centreX - radius1, centreY - radius1, centreX + radius1,
                centreY + radius1);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        canvas.drawArc(oval, 0, 90, true, paint);
        paint.setColor(Color.BLUE);
        canvas.drawArc(oval, 90, 90, true, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(oval, 180, 90, true, paint);
        paint.setColor(Color.GREEN);
        canvas.drawArc(oval, 0, -90, true, paint);
        paint.setColor(Color.MAGENTA);
        canvas.drawCircle(centreX, centreY, radius2, paint);
        super.onDraw(canvas);
    }

    public void setOnCustomViewActionListener(OnCustomViewActionListener onCustomViewActionListener) {
        this.onCustomViewActionListener = onCustomViewActionListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (onCustomViewActionListener != null) {
                onCustomViewActionListener.onActionDown(x, y);

            }
        }
        return super.onTouchEvent(event);
    }
}
