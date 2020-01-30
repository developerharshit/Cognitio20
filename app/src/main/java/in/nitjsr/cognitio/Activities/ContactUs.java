package in.nitjsr.cognitio.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import in.nitjsr.cognitio.R;

public class ContactUs extends AppCompatActivity implements View.OnClickListener {

    ImageView fb, insta, back, whatsapp, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        fb = findViewById(R.id.facebook);
        insta = findViewById(R.id.instagram);
        back = findViewById(R.id.back_arrow);
        whatsapp = findViewById(R.id.whatsapp);
        mail = findViewById(R.id.mail);

        fb.setOnClickListener(this);
        insta.setOnClickListener(this);
        back.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        mail.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.facebook:
                try {
                    this.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/934857360037086")));
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/cognitio.me.nitjsr")));
                }
                break;
            case R.id.mail:
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setType("message/rfc822");
                i.setData(Uri.parse("mailto:cognitio.me@nitjsr.ac.in"));
                i.putExtra(Intent.EXTRA_EMAIL, "cognitio.me@nitjsr.ac.in");
                i.putExtra(Intent.EXTRA_SUBJECT, "Cognitio");
                i.putExtra(Intent.EXTRA_TEXT, "Hello");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.instagram:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/cognitio_nitjsr")));
                break;
            case R.id.whatsapp:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                String url = "https://api.whatsapp.com/send?phone=91" + "8862927236" + "&text=Hello!!";
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
            case R.id.back_arrow:
                finish();
        }
    }
}
