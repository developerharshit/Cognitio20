package in.nitjsr.cognitio.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Utilities;

public class Itinerary extends AppCompatActivity implements View.OnClickListener {

    ImageView body, back;
    TextView prev, next;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utilities.hideStatusBarColor(this);
        setContentView(R.layout.activity_itinerary);

        body = findViewById(R.id.body);
        prev = findViewById(R.id.ib_prev);
        next = findViewById(R.id.ib_next);
        back = findViewById(R.id.back_arrow);

        next.setText("NEXT>>");
        prev.setText("<<PREV");

        prev.setVisibility(View.GONE);
        body.setImageResource(R.drawable.day1);

        flag = 0;

        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (flag == 0) prev.setVisibility(View.GONE);
        if (v == next) {
            flag++;
            if (flag == 1) {
                next.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
                body.setImageResource(R.drawable.day2);
            } else {
                next.setVisibility(View.GONE);
                body.setImageResource(R.drawable.day3);
            }
        } else if (v == prev) {
            flag--;
            if (flag == 1) {
                next.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
                body.setImageResource(R.drawable.day2);
            } else {
                prev.setVisibility(View.GONE);
                body.setImageResource(R.drawable.day1);
            }
        } else if (v == back) {
            finish();
        }
    }
}
