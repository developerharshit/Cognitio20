package in.nitjsr.cognitio.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import in.nitjsr.cognitio.R;

public class MapsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Utilities.hideStatusBarColor(this);
        setContentView(R.layout.activity_maps);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
