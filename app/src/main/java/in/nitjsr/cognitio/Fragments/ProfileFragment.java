package in.nitjsr.cognitio.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import in.nitjsr.cognitio.Activities.PaymentActivity;
import in.nitjsr.cognitio.Activities.RegisterActivity;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Constants;
import in.nitjsr.cognitio.Utils.SharedPrefManager;
import in.nitjsr.cognitio.Utils.Utilities;

import static in.nitjsr.cognitio.Utils.Constants.FIREBASE_REF_NAME;
import static in.nitjsr.cognitio.Utils.Constants.FIREBASE_REF_USERS;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {


    private final static int RC_SIGN_IN = 1;
    private static final String TAG = "Login";
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private ProgressDialog pd;
    private RelativeLayout profileView, loginView;
    private SignInButton signInButton;

    private TextView tvUsername, tvUserCoId, tvEmail, tvNum, tvTshirtSize, tvStatus, tvProfileInstitute;
    private CircleImageView circleImageView;
    private ImageView ivtShirt, ivKit, ibLogOut;
    private Button btnClickRegister, btnClickPay;
    private boolean isWarningShown;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileView = view.findViewById(R.id.profile_page);
        loginView = view.findViewById(R.id.login_page);
        signInButton = view.findViewById(R.id.signin);

        tvUsername = view.findViewById(R.id.tv_profile_name);
        tvUserCoId = view.findViewById(R.id.tv_profile_cognitio_id);
        tvEmail = view.findViewById(R.id.tv_profile_email);
        tvNum = view.findViewById(R.id.tv_profile_number);
        circleImageView = view.findViewById(R.id.iv_profile_img);
        ivtShirt = view.findViewById(R.id.iv_tshirt);
        ivKit = view.findViewById(R.id.iv_kit);
        ibLogOut = view.findViewById(R.id.ib_logOut);
        btnClickRegister = view.findViewById(R.id.btn_click_to_register);
        btnClickPay = view.findViewById(R.id.btn_click_to_pay);
        tvTshirtSize = view.findViewById(R.id.tv_tshirt_size);
        tvStatus = view.findViewById(R.id.tv_profile_status);
        tvProfileInstitute = view.findViewById(R.id.tv_profile_institute);

        isWarningShown = false;

        signInButton.setOnClickListener(this);
        btnClickPay.setOnClickListener(this);
        ibLogOut.setOnClickListener(this);
        btnClickRegister.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Please wait...");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        updateUI();

        return view;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            pd.show();
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Google Sign in failed. Reason: " + e.getMessage());
                if (pd.isShowing()) pd.dismiss();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            isRegisteredUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "SignIn failed!!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Authentication failed. Reason: " + task.getException());
                            if (pd.isShowing()) pd.dismiss();
                        }
                    }
                });
    }

    private void isRegisteredUser() {
        final String fName = mAuth.getCurrentUser().getDisplayName().split(" ")[0];
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(FIREBASE_REF_USERS).child(mAuth.getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {
                        new SharedPrefManager(getContext()).setIsRegistered(true);
                        updateUI();
                        Toast.makeText(getContext(), "Welcome to Cognitio " + fName, Toast.LENGTH_LONG).show();
                        if (pd.isShowing()) pd.dismiss();
                        if (dataSnapshot.child(Constants.FIREBASE_REF_PAYMENT).exists()) {
                            new SharedPrefManager(getContext()).setHasPaid(true);
                        }
                    } else {
                        moveToRegisterActivity();
                        Toast.makeText(getContext(), "Hey " + fName + "! Let us know you better.", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void moveToRegisterActivity() {
        startActivity(new Intent(getContext(), RegisterActivity.class));
        getActivity().finish();
    }

    private void updateUI() {
        if (mAuth.getCurrentUser() != null) {
            profileView.setVisibility(View.VISIBLE);
            loginView.setVisibility(View.GONE);
            setProfileView();

        } else {
            profileView.setVisibility(View.GONE);
            loginView.setVisibility(View.VISIBLE);
        }
    }

    private void setProfileView() {
        pd.show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(FIREBASE_REF_USERS)
                .child(mAuth.getUid());
        Utilities.setPicassoImage(getContext(), Objects.requireNonNull(mAuth.getCurrentUser()).getPhotoUrl().toString(), circleImageView);
        ref.keepSynced(true);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                tvUsername.setText(mAuth.getCurrentUser().getDisplayName());
                tvEmail.setText(mAuth.getCurrentUser().getEmail());
                if (dataSnapshot.exists()) {
                    try {
                        tvUsername.setText(dataSnapshot.child(FIREBASE_REF_NAME).getValue().toString());
                        pd.cancel();
                        btnClickRegister.setVisibility(View.GONE);
                        if (dataSnapshot.child(Constants.FIREBASE_REF_PAYMENT).exists()) {
                            btnClickPay.setVisibility(View.GONE);
                            tvStatus.setText("Registered");
                            tvStatus.setTextColor(getResources().getColor(R.color.navGreen));
                            tvStatus.setTextSize(30f);
                        } else {
                            btnClickPay.setVisibility(View.VISIBLE);
                            tvStatus.setTextColor(Color.RED);
                            tvStatus.setText("Payment Due");
                        }
                        if (dataSnapshot.child(Constants.FIREBASE_REF_COGNITIO_ID).exists()) {
                            tvUserCoId.setVisibility(View.VISIBLE);
                            int cogId = Integer.parseInt(dataSnapshot.child(Constants.FIREBASE_REF_COGNITIO_ID).getValue().toString());
                            if (cogId < 10) {
                                tvUserCoId.setText("COG00" + cogId);
                            } else if (cogId < 100) {
                                tvUserCoId.setText("COG0" + cogId);
                            } else {
                                tvUserCoId.setText("COG" + cogId);
                            }

                        }
                        tvProfileInstitute.setText((dataSnapshot.child(Constants.FIREBASE_REF_COLLEGE).getValue().toString()));
                        tvNum.setText("+91 " + dataSnapshot.child(Constants.FIREBASE_REF_MOBILE).getValue().toString());
                        tvTshirtSize.setText("Tshirt (" + dataSnapshot.child(Constants.FIREBASE_REF_TSHIRT_SIZE).getValue().toString() + ")");
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (dataSnapshot.child(Constants.FIREBASE_REF_TSHIRT).exists()) {
                                ivtShirt.setImageDrawable(getActivity().getDrawable(android.R.drawable.checkbox_on_background));
                            } else {
                                ivtShirt.setImageDrawable(getActivity().getDrawable(android.R.drawable.checkbox_off_background));
                            }
                            if (dataSnapshot.child(Constants.FIREBASE_REF_KIT).exists()) {
                                ivKit.setImageDrawable(getActivity().getDrawable(android.R.drawable.checkbox_on_background));
                            } else {
                                ivKit.setImageDrawable(getActivity().getDrawable(android.R.drawable.checkbox_off_background));
                            }
                        } else {
                            ivtShirt.setVisibility(View.GONE);
                            ivKit.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        pd.dismiss();
                    }
                } else {
                    pd.dismiss();
                    tvStatus.setText(Constants.NOT_REGISTERED);
                    tvStatus.setTextColor(Color.RED);
                    tvUserCoId.setVisibility(View.GONE);
                    Toast.makeText(getContext(), Constants.NOT_REGISTERED, Toast.LENGTH_SHORT).show();
                    btnClickRegister.setVisibility(View.VISIBLE);
                    btnClickPay.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        if (pd.isShowing()) pd.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signin:
                signIn();
                break;
            case R.id.ib_logOut:
                if (isWarningShown) {
                    signout();
                    new SharedPrefManager(getContext()).setHasPaid(false);
                    new SharedPrefManager(getContext()).setIsRegistered(false);
                    updateUI();
                } else {
                    Toast.makeText(getContext(), "Press again to logout", Toast.LENGTH_SHORT).show();
                    isWarningShown = true;
                }
                break;
            case R.id.btn_click_to_register:
                startActivity(new Intent(getContext(), RegisterActivity.class));
                getActivity().finish();
                break;
            case R.id.btn_click_to_pay:
                startActivity(new Intent(getContext(), PaymentActivity.class));
                getActivity().finish();
                break;
        }
    }

    private void signout() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        mGoogleSignInClient.signOut();
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getContext(), "Signed out", Toast.LENGTH_SHORT).show();
    }
}
