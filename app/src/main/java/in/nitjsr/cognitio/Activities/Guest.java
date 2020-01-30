package in.nitjsr.cognitio.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import in.nitjsr.cognitio.R;

public class Guest extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    WebView webView;
    ImageView back;
    ImageView oops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        back = findViewById(R.id.back_arrow);
        back.setOnClickListener(this);
        oops = findViewById(R.id.oops);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching images from server!!");
        progressDialog.setCancelable(false);
        progressDialog.show();

        webView = findViewById(R.id.webview);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webView.loadUrl("file:///android_asset/guests.html");
        webView.loadUrl("https://cognitio20.herokuapp.com/appguests");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                progressDialog.dismiss();
                oops.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == back) finish();
    }
}
