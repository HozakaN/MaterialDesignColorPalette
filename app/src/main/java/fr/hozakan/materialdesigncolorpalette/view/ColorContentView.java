package fr.hozakan.materialdesigncolorpalette.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.hozakan.materialdesigncolorpalette.R;

/**
 * Created by gimbert on 2014-10-06.
 */
public class ColorContentView extends ViewGroup {

    private TextView mColorTextView;
    private ImageView mSetHeaderImage;
    private ImageView mAddContentImage;

    public ColorContentView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ColorContentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater.from(context).inflate(R.layout.view_card_content, this, true);

        mColorTextView = (TextView) findViewById(R.id.color_text);
        mColorTextView.setText("test");

        mAddContentImage = (ImageView) findViewById(R.id.add_to_content_image);
        mSetHeaderImage = (ImageView) findViewById(R.id.set_header_image);
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }

    private int getMeasuredHeightWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
    }

    private int getMeasuredWidthWithMargins(View child) {
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        return child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int widthUsed = 0;
        int heightUsed = 0;

        measureChildWithMargins(mColorTextView,
                                widthMeasureSpec, widthUsed,
                                heightMeasureSpec, heightUsed);
        heightUsed += getMeasuredHeightWithMargins(mColorTextView);

        measureChildWithMargins(mAddContentImage,
                                widthMeasureSpec, widthUsed,
                                heightMeasureSpec, heightUsed);

        heightUsed += getMeasuredHeightWithMargins(mAddContentImage);

        measureChildWithMargins(mSetHeaderImage,
                                widthMeasureSpec, widthUsed,
                                heightMeasureSpec, heightUsed);

        heightUsed += getMeasuredHeightWithMargins(mSetHeaderImage);

        int heightSize = heightUsed + getPaddingTop() + getPaddingBottom();
        setMeasuredDimension(widthSize, heightSize);

    }

    private void layoutView(View view, int left, int top, int width, int height) {
        MarginLayoutParams margins = (MarginLayoutParams) view.getLayoutParams();
        final int leftWithMargins = left + margins.leftMargin;
        final int topWithMargins = top + margins.topMargin;

        view.layout(leftWithMargins, topWithMargins,
                leftWithMargins + width, topWithMargins + height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();

        int currentTop = paddingTop;

        layoutView(mColorTextView, paddingLeft, currentTop,
                   mColorTextView.getMeasuredWidth(),
                   mColorTextView.getMeasuredHeight());

        currentTop += getMeasuredHeightWithMargins(mColorTextView);
        int currentLeft = paddingLeft;

        layoutView(mAddContentImage, currentLeft, currentTop,
                   mAddContentImage.getMeasuredWidth(),
                   mAddContentImage.getMeasuredHeight());

        currentLeft += getMeasuredWidthWithMargins(mAddContentImage);

        layoutView(mSetHeaderImage, currentLeft, currentTop,
                   mSetHeaderImage.getMeasuredWidth(),
                   mSetHeaderImage.getMeasuredHeight());


    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }
}
