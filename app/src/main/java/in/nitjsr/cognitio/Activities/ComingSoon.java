package in.nitjsr.cognitio.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Utilities;

public class ComingSoon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);
        Utilities.changeStatusBarColor(this);
        MainActivity1.isWarningShown = false;
    }
}
