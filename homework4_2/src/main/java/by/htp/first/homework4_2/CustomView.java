package by.htp.first.homework4_2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class CustomView extends View {

    interface OnCustomViewActionListener {
        void onActionDown(float x, float y, int color);
    }

    private int diameter;
    boolean defaultStateFlag = true;
    private int centreX;
    private int centreY;
    private int radius1;
    private int radius2;


    private final Region[] regions = {new Region(), new Region(), new Region(), new Region(), new Region()};
    private final Paint[] paints = {new Paint(), new Paint(), new Paint(), new Paint(), new Paint()};
    private final RectF oval = new RectF();
    private OnCustomViewActionListener onCustomViewActionListener;
    private final Random rand = new Random();

   /* private int[] colors = {
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 )),
            Color.rgb((int) (Math.random() * 255 ), (int) (Math.random() * 255 ), (int) (Math.random() * 255 ))
    };*/
    private int[] colors = {-16007990,  -16766976 , -15277667, -58998, -7798531, -10233776, -6765239, -48340, -15753874,
           -16766287, -16747109, -7798531, -11766015};


    public int getRandArrayElement() {
        return colors[rand.nextInt(colors.length)];
    }


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
            //paint.setColor(typedArray.getColor(R.styleable.CustomView_circleColor, Color.BLUE));
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
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        regions[0] = new Region(centreX - radius1, centreY - radius1, centreX, centreY);// лево верх
        regions[1] = new Region(centreX, centreY - radius1, centreX + radius1, centreY);// право верх
        regions[2] = new Region(centreX - radius1, centreY, centreX, centreY + radius1); // низ лево
        regions[3] = new Region(centreX, centreY, centreX + radius1, centreY + radius1); // низ право
        regions[4] = new Region(centreX - 100, centreY - 100, centreX + 100, centreY + 100);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (defaultStateFlag) {
            paints[0].setColor(Color.YELLOW);
            paints[1].setColor(Color.BLUE);
            paints[2].setColor(Color.RED);
            paints[3].setColor(Color.GREEN);
            paints[4].setColor(Color.MAGENTA);

            defaultStateFlag = false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // paint.setColor(Color.YELLOW);
        canvas.drawArc(oval, 0, 90, true, paints[0]);
        //paint.setColor(Color.BLUE);
        canvas.drawArc(oval, 90, 90, true, paints[1]);
        //  paint.setColor(Color.RED);
        canvas.drawArc(oval, 180, 90, true, paints[2]);
        // paint.setColor(Color.GREEN);
        canvas.drawArc(oval, 0, -90, true, paints[3]);
        //paint.setColor(Color.MAGENTA);
        canvas.drawCircle(centreX, centreY, radius2, paints[4]);
        super.onDraw(canvas);
    }

    public void setOnCustomViewActionListener(OnCustomViewActionListener onCustomViewActionListener) {
        this.onCustomViewActionListener = onCustomViewActionListener;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        performClick();

        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (onCustomViewActionListener != null) {

                for (int i = 0; i < 4; i++) {
                    if (regions[i].contains((int)x, (int)y )) {

                    paints[i].setColor(getRandArrayElement());

                    onCustomViewActionListener.onActionDown(x, y, paints[i].getColor());
                    invalidate();

                    }

                }


                //onCustomViewActionListener.onActionDown(x, y);


            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}
