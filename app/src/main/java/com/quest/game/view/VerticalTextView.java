package com.quest.game.view;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

public class VerticalTextView
  extends TextView
{
  public VerticalTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    TextPaint localTextPaint = getPaint();
    localTextPaint.setColor(getCurrentTextColor());
    localTextPaint.drawableState = getDrawableState();
    paramCanvas.save();
    paramCanvas.translate(getWidth(), 0.0F);
    paramCanvas.rotate(90.0F);
    getLayout().draw(paramCanvas);
    paramCanvas.restore();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt2, paramInt1);
    setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
  }
}
