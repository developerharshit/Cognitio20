package in.nitjsr.cognitio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Utilities;

public class SplashScreen extends AppCompatActivity {

    int SPLASH_DISPLAY_LENGTH = 1500;
    ImageView logo, cognitio, cloud, green;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Utilities.hideStatusBarColor(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo = findViewById(R.id.splash_icon);
        cognitio = findViewById(R.id.splash_cognitio);
        cloud = findViewById(R.id.splash_cloud);
        green = findViewById(R.id.splash_green_tech);

//        OneSignal.startInit(this)
//                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
//                .unsubscribeWhenNotificationsAreDisabled(true)
//                .init();
//        OneSignal.sendTag("Admin", "Admin");

        Thread mythread = new Thread() {
            @Override
            public void run() {
                try {
                    Animation anim_cognitio = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.slide_in_right);
                    Animation anim_green = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.slide_in_left);
                    Animation anim_logo = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.bounce);
                    Animation anim_cloud = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.fade_in);
                    cognitio.startAnimation(anim_cognitio);
                    logo.startAnimation(anim_logo);
                    cloud.startAnimation(anim_cloud);
                    green.startAnimation(anim_green);

                    sleep(SPLASH_DISPLAY_LENGTH);
                    Intent i = new Intent(SplashScreen.this, MainActivity1.class);
                    startActivity(i);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        mythread.start();
    }
}
