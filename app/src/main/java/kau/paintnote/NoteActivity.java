package kau.paintnote;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.ArrayList;

import kau.paintnote.model.LineModel;

/**
 * Created by Jaewook on 2015-06-23.
 */
@EActivity()
public class NoteActivity extends Activity {

    private final float Width = 900;     // 길이 기준
    private float Height = 900;                // 전체 길이
    private float centerX , centerY;     // 기준점
    ArrayList<LineData> lines;

    @Extra
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyView myView = new MyView(this);

        setContentView(myView);

    }

    class MyView extends View {


        public MyView(Context context) {

            super(context);
            lines = GlobalValue.lineList.get(0).getLines().get(index).getLines();
        }


        @Override

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            centerX = getWidth() / 2 - Width / 2;
            centerY = (getHeight() - Height) / 2;
            super.onSizeChanged(w, h, oldw, oldh);

        }


        @Override
        protected void onDraw(Canvas canvas) {

            Paint paint = new Paint();
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.STROKE);

            for ( int i = 0; i < lines.size(); i++ ) {
                Path path = new Path();
                path.moveTo(lines.get(i).getX(), lines.get(i).getY());
                path.lineTo(lines.get(i).getX1(), lines.get(i).getY1());
                canvas.drawPath(path, paint);
            }
            super.onDraw(canvas);

        }
    }
}
