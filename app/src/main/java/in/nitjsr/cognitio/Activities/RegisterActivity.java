package in.nitjsr.cognitio.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;
import in.nitjsr.cognitio.Utils.DialogPop;
import in.nitjsr.cognitio.Utils.SharedPrefManager;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DialogPop.CollegeName {

    private EditText inputName, inputEmail, inputMobile, inputRegId;
    private TextView inputCollege;
    private String college_code;
    private Spinner spinner;
    private String tshirtSize;
    private Button nextButton;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        inputName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputMobile = findViewById(R.id.mobile);
        inputCollege = findViewById(R.id.college);
        inputRegId = findViewById(R.id.regno);
        spinner = findViewById(R.id.tshirtsize);
        spinner.setSelection(2);
        college_code = "0";

        nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(this);
        inputCollege.setOnClickListener(this);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tshirtSize = parent.getContext().getResources().getStringArray(R.array.tshirt_size)[position].split(" ")[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        inputName.setText(mUser.getDisplayName());
        inputEmail.setText(mUser.getEmail());
        inputName.setEnabled(false);
        inputEmail.setEnabled(false);

    }

    public boolean validate() {

        boolean valid = true;
        if (inputEmail.getText().toString().trim().isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(inputEmail.getText().toString().trim()).matches()) {
            inputEmail.setError("Please Enter Valid Email Address");
            valid = false;
        }
        if (inputMobile.getText().toString().trim().isEmpty() || !Patterns.PHONE.matcher(inputMobile.getText().toString().trim()).matches()) {
            inputMobile.setError("Please Enter Valid Mobile Number");
            valid = false;
        }

        if (inputName.getText().toString().trim().isEmpty()) {
            inputName.setError("Please Enter Your Name");
            valid = false;
        }

//        if(inputCollege.getText().toString().trim().isEmpty() )
//        {
//            inputCollege.setError("Please Enter Your College");
//            valid=false;
//        }

        if (inputRegId.getText().toString().trim().isEmpty()) {
            inputRegId.setError("Please Enter Your RegId");
            valid = false;
        }

        return valid;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.next) {
            if (validate()) {
                registerUser();
                startActivity(new Intent(this, PaymentActivity.class));
                finish();
            }
        } else if (view.getId() == R.id.college) {
            DialogPop dialogPop = new DialogPop();
            dialogPop.show(getSupportFragmentManager(), "college");
        }
    }

    private void registerUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_REF_USERS).child(mAuth.getUid());
        reference.child(Constants.FIREBASE_REF_EMAIL).setValue(mUser.getEmail());
        reference.child(Constants.FIREBASE_REF_NAME).setValue(mUser.getDisplayName());
        reference.child(Constants.FIREBASE_REF_PHOTO).setValue(mUser.getPhotoUrl().toString());
        reference.child(Constants.FIREBASE_REF_MOBILE).setValue(inputMobile.getText().toString());
        reference.child(Constants.FIREBASE_REF_COLLEGE).setValue(inputCollege.getText().toString());
        reference.child(Constants.FIREBASE_REF_COLLEGE_REG_ID).setValue(inputRegId.getText().toString());
        reference.child(Constants.FIREBASE_REF_COLLEGE_CODE).setValue(college_code);
        reference.child(Constants.FIREBASE_REF_TSHIRT_SIZE).setValue(tshirtSize);
        createCognitioId();

        new SharedPrefManager(this).setIsRegistered(true);


    }

    private void createCognitioId() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref = ref.child(Constants.FIREBASE_REF_COGNITIO_ID);
        ref.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                Integer CurrentValue = mutableData.getValue(Integer.class);
                if (CurrentValue != null) {
                    cid = CurrentValue + 1;
                    mutableData.setValue(CurrentValue + 1);
                } else {
                    cid = 1;
                    mutableData.setValue(1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_REF_USERS).child(mAuth.getUid());
                reference.child(Constants.FIREBASE_REF_COGNITIO_ID).setValue(cid);
            }
        });
    }

    @Override
    public void getCollege(String college, String college_code) {
        this.inputCollege.setText(college);
        this.college_code = college_code;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity1.class);
        intent.putExtra(MainActivity1.SCREEN_NUMBER, 3);
        startActivity(intent);
        finish();
    }
}