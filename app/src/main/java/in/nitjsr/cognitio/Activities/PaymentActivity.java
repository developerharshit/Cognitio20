package in.nitjsr.cognitio.Activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import in.nitjsr.cognitio.Paytm.PaytmPayments;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;

import static in.nitjsr.cognitio.Utils.Constants.FIREBASE_REF_COLLEGE_CODE;
import static in.nitjsr.cognitio.Utils.Constants.FIREBASE_REF_USERS;

public class PaymentActivity extends AppCompatActivity implements PaytmPayments.PaytmTxn, View.OnClickListener {

    DatabaseReference ref;
    FirebaseUser mUser;
    Button patmkaro, desk_payment;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        ref = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        patmkaro = findViewById(R.id.paytm);
        back = findViewById(R.id.back_arrow);
        desk_payment = findViewById(R.id.desk_payment);
        desk_payment.setOnClickListener(this);
        back.setOnClickListener(this);
        getTxnAmount();
        if (MainActivity1.isSchool) {
            patmkaro.setEnabled(false);
            patmkaro.setBackground(getResources().getDrawable(R.drawable.payment_button_bg2));
        } else {
            patmkaro.setEnabled(true);
            patmkaro.setBackground(getResources().getDrawable(R.drawable.payment_button_bg));
        }

        if (MainActivity1.switch_payment.equals("0")) {
            patmkaro.setEnabled(false);
//            patmkaro.setBackgroundColor(ContextCompat.getColor(this, R.color.navGrey));
            patmkaro.setBackground(getResources().getDrawable(R.drawable.payment_button_bg2));
        }

        setInstruction();
    }

    private void setInstruction() {
        TextView body = findViewById(R.id.body);
        Typeface comic = Typeface.createFromAsset(getAssets(), "font/Museo_Slab_500italic.otf");
        body.setTypeface(comic);
        body.setText(Html.fromHtml(MainActivity1.instruction));
    }

    @Override
    public void getTxn(JSONObject json) {
        try {
            if (json.getString("STATUS").equals("TXN_SUCCESS")) {
                ref.child(FIREBASE_REF_USERS).child(mUser.getUid()).child(Constants.FIREBASE_REF_PAYMENT).setValue(Constants.FIREBASE_REF_PAYTM_PAYMENT);
                ref.child(FIREBASE_REF_USERS).child(mUser.getUid()).child(Constants.FIREBASE_REF_PAID_AMOUNT).setValue(json.getString("TXNAMOUNT"));

                ref = ref.child(Constants.FIREBASE_REF_PAYMENT).child(Constants.FIREBASE_REF_PAYTM).child(mUser.getUid());
                ref.child(Constants.FIREBASE_REF_PAYTM_CHECKSUM).setValue(json.getString("CHECKSUMHASH"));
                ref.child(Constants.FIREBASE_REF_PAYTM_BANKNAME).setValue(json.getString("BANKNAME"));
                ref.child(Constants.FIREBASE_REF_PAYTM_TXNID).setValue(json.getString("TXNID"));
                ref.child(Constants.FIREBASE_REF_PAYTM_BANKTXNID).setValue(json.getString("BANKTXNID"));
                ref.child(Constants.FIREBASE_REF_PAYTM_PAYMENTMODE).setValue(json.getString("PAYMENTMODE"));
                ref.child(Constants.FIREBASE_REF_PAYTM_TXNTIME).setValue(json.getString("TXNDATE"));
                ref.child(Constants.FIREBASE_REF_PAYTM_GATEWAY).setValue(json.getString("GATEWAYNAME"));
                ref.child(Constants.FIREBASE_REF_PAYTM_ORDERID).setValue(json.getString("ORDERID"));
                ref.child(Constants.FIREBASE_REF_PAYTM_TXN_AMT).setValue(json.getString("TXNAMOUNT"));

                Intent intent = new Intent(this, MainActivity1.class);
                intent.putExtra(MainActivity1.SCREEN_NUMBER, 3);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, MainActivity1.class);
                intent.putExtra(MainActivity1.SCREEN_NUMBER, 3);
                startActivity(intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Intent intent = new Intent(this, MainActivity1.class);
            intent.putExtra(MainActivity1.SCREEN_NUMBER, 3);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity1.class);
        intent.putExtra(MainActivity1.SCREEN_NUMBER, 3);
        startActivity(intent);
        finish();
    }

    public void getTxnAmount() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref = ref.child(FIREBASE_REF_USERS).child(mUser.getUid()).child(FIREBASE_REF_COLLEGE_CODE);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue().toString().equals("2")) {
                        setTxnAmt(MainActivity1.payment_amt_outside);
                    } else setTxnAmt(MainActivity1.payment_amt_nit);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setTxnAmt(final String amt) {
        patmkaro.setText("Paytm \u20B9" + amt);
        patmkaro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaytmPayments payments = new PaytmPayments(PaymentActivity.this);
                payments.initiatePayment(amt);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == desk_payment) {
//            Intent intent = new Intent(this,MainActivity.class);
//            intent.putExtra(MainActivity.SCREEN_NUMBER, 3);
//            startActivity(intent);
//            finish();
            Intent intent = new Intent(this, MainActivity1.class);
            intent.putExtra(MainActivity1.SCREEN_NUMBER, 3);
            startActivity(intent);
            finish();
        } else if (v == back) {
            Intent intent = new Intent(this, MainActivity1.class);
            intent.putExtra(MainActivity1.SCREEN_NUMBER, 3);
            startActivity(intent);
            finish();
        }
    }
}
