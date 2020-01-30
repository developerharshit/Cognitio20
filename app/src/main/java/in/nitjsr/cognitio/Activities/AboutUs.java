package in.nitjsr.cognitio.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import in.nitjsr.cognitio.R;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {

    ImageView back, share, rate;
    String appUrl = "https://play.google.com/store/apps/details?id=in.nitjsr.cognitio&hl=en";
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        MainActivity1.isWarningShown = false;

        back = findViewById(R.id.back_arrow);
        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
        rate.setOnClickListener(this);
        //mainBody=findViewById(R.id.mainbody);
        title = findViewById(R.id.title);
        Typeface Title_font = Typeface.createFromAsset(getAssets(), "font/Museo_Slab_500italic.otf");
        title.setTypeface(Title_font);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                finish();
                break;
            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, appUrl);
                intent.setType("text/plain");
                startActivity(intent);
                break;
            case R.id.rate:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(appUrl));
                startActivity(Intent.createChooser(intent, "Open with..."));

                break;
            default:
                break;
        }
    }
}
