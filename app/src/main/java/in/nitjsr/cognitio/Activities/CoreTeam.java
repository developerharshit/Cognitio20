package in.nitjsr.cognitio.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.nitjsr.cognitio.Adapters.CoreTeamAdapter;
import in.nitjsr.cognitio.Modals.TeamModal;
import in.nitjsr.cognitio.R;
import in.nitjsr.cognitio.Utils.Communicator;
import in.nitjsr.cognitio.Utils.DialogList;

public class CoreTeam extends AppCompatActivity implements View.OnClickListener, Communicator {

    RecyclerView recyclerView;
    ImageView back;
    FragmentManager manager;
    TextView portfolio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core_team);

        recyclerView = findViewById(R.id.rv_team);
        back = findViewById(R.id.back_arrow);
        portfolio = findViewById(R.id.portfolio);
        manager = getSupportFragmentManager();
        back.setOnClickListener(this);
        MainActivity1.isWarningShown = false;

        recyclerView.setAdapter(new CoreTeamAdapter(this, setAdministration()));
        FragmentTransaction transaction = manager.beginTransaction();
        DialogList dialogList = new DialogList();
        transaction.add(dialogList, "dialogList");
        transaction.commit();
    }

    private void prepareDataList(int position) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        switch (position) {
            case 0:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setAdministration()));
                break;
            case 1:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setCore()));
                break;
            case 2:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setPlanning()));
                break;
            case 3:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setPublicRelations()));
                break;
            case 4:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setAppDev()));
                break;
            case 5:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setManagement()));
                break;
            case 6:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setCA()));
                break;
            case 7:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setHospitality()));
                break;
            case 8:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setCreative()));
                break;
            case 9:
                recyclerView.setAdapter(new CoreTeamAdapter(this, setLiterary()));
                break;
        }
    }

    private ArrayList<TeamModal> setHospitality() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Balaga Naga Sai Vardhan", "Logistics & Hospitality Head", R.drawable.sai, "9177689604", null));
        memberList.add(new TeamModal("Shivam Ranjan", "Logistics & Hospitality Head", R.drawable.shivam, "7301681377", null));
        memberList.add(new TeamModal("Zaki Ahmed", "Coordinator", R.drawable.zaki, "6388032091", null));
        memberList.add(new TeamModal("Arnab Mishra", "Coordinator", R.drawable.arnab, "6200596044", null));
        memberList.add(new TeamModal("Alok Kumar", "Coordinator", R.drawable.alok, "8709938215", null));
        memberList.add(new TeamModal("Mani Kiran", "Coordinator", R.drawable.mani_kiran, "7981818030", null));
        return memberList;
    }

    private ArrayList<TeamModal> setCore() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Sudhanshu Prakash", "President", R.drawable.danton, "8862927236", null));
        memberList.add(new TeamModal("Manas Kumar Dey", "Executive Secretary", R.drawable.manas, "8252966466", null));
        memberList.add(new TeamModal("Pranav Prakash", "General Secretary", R.drawable.pranav, "7209669436", null));
        memberList.add(new TeamModal("Shatanik Bose", "Spokesperson", R.drawable.shatanik, "7654974223", null));
        memberList.add(new TeamModal("Onkar Kumar", "Technical Secretary", R.drawable.onkar, "9472903552", null));
        return memberList;
    }

    private ArrayList<TeamModal> setAdministration() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Prof. K.K. Shukla", "Director", R.drawable.director, null, "NIT Jamshedpur"));
        memberList.add(new TeamModal("Prof. M.K. Paswan", "HOD", R.drawable.hod, null, "Mechanical Dept."));
        memberList.add(new TeamModal("Prof. Shalendra Kumar", "Chairperson", R.drawable.chairperson, null, "Cognitio"));
        memberList.add(new TeamModal("Dr. Bipin Kumar", "Professor Incharge", R.drawable.pi, null, "Cognitio"));
        return memberList;
    }

    private ArrayList<TeamModal> setPlanning() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Kamisettty Sai Sudarshan", "Planning Head", R.drawable.sai_sudarshan, "8179132796", null));
        memberList.add(new TeamModal("Amar Arjun Gaikwad", "Planning Head", R.drawable.amar, "9447441366", null));
        memberList.add(new TeamModal("Manoranjan Kumar Mishra", "Planning Head", R.drawable.manoranjan, "9128254536", null));
        return memberList;
    }

    private ArrayList<TeamModal> setLiterary() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Arjun Goyal", "Literary Head", R.drawable.arjun, "9825113966", null));
        memberList.add(new TeamModal("Priyanshu Kumar", "Coordinator", R.drawable.priyanshu, "9672015492", null));
        memberList.add(new TeamModal("Pratyush Aryan", "Coordinator", R.drawable.pratyush, "9570075444", null));
        return memberList;
    }

    private ArrayList<TeamModal> setCA() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Bijay Mahapatra", "CA Head", R.drawable.bijay, "8235698592", null));
        memberList.add(new TeamModal("Vaibhaav Srinath Dev", "CA Head", R.drawable.vaibhav_dev, "9566137756", null));
        memberList.add(new TeamModal("Md Sajid Perwaiz", "Coordinator", R.drawable.sajid, "7677723906", null));
        memberList.add(new TeamModal("Aman Raj", "Coordinator", R.drawable.aman, "9973988973", null));
        memberList.add(new TeamModal("Harshit Shukla", "Coordinator", R.drawable.harshit_shukla, "7905610165", null));
        memberList.add(new TeamModal("Tile Datta", "Coordinator", R.drawable.datta, "9503822156", null));
        memberList.add(new TeamModal("Nashon Denis", "Coordinator", R.drawable.nash, "7900129559", null));
        return memberList;
    }

    private ArrayList<TeamModal> setCreative() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Pratik Singh", "Creative Head", R.drawable.pratik, "7000877611", null));
        memberList.add(new TeamModal("Rishikesh Sengor", "Coordinator", R.drawable.rishi, "7295954464", null));
        memberList.add(new TeamModal("Gaurav", "Coordinator", R.drawable.gaurav, "8825308833", null));
        memberList.add(new TeamModal("Ayush Yash", "Coordinator", R.drawable.ayush_yash, "7250279136", null));
        memberList.add(new TeamModal("Saurabh Mishra", "Coordinator", R.drawable.saurabh, "9454118318", null));
        memberList.add(new TeamModal("Chitiz Sambhav", "Coordinator", R.drawable.chitiz, "8789228636", null));
        return memberList;
    }

    private ArrayList<TeamModal> setAppDev() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Harshit Gupta", "App Head", R.drawable.harshit, "7488128330", null));
        memberList.add(new TeamModal("Ankit Choudhary", "Web Head", R.drawable.ankit, "6205110557", null));
        memberList.add(new TeamModal("Rishikesh Sengor", "Designer", R.drawable.rishi, "7295954464", null));
        return memberList;
    }

    private ArrayList<TeamModal> setManagement() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Mallikarjun Vankaraboena", "Management Head", R.drawable.malikkarjun, "9471391405", null));
        memberList.add(new TeamModal("Basant Kumar Jha", "Management Head", R.drawable.basant, "8757129280", null));
        memberList.add(new TeamModal("Abhishek Anand", "Media Relations Head", R.drawable.abhishek, "7070300444", null));
        memberList.add(new TeamModal("Md Sajid Perwaiz", "Coordinator", R.drawable.sajid, "7677723906", null));
        memberList.add(new TeamModal("Darshan Ambule", "Coordinator", R.drawable.darshan, "9545771706", null));
        memberList.add(new TeamModal("Nikhil Srivastav", "Coordinator", R.drawable.nikhil_srivastava, "7369077281", null));
        memberList.add(new TeamModal("Mithu Kumar", "Coordinator", R.drawable.mittu, "7479756788", null));
        memberList.add(new TeamModal("Ashutosh Kashyap", "Coordinator", R.drawable.ashutosh, "7909085778", null));
        memberList.add(new TeamModal("Prabhakar", "Coordinator", R.drawable.prabhakar, "7903559679", null));
        memberList.add(new TeamModal("Neel Kamal", "Coordinator", R.drawable.neel_kamal, "7292862376", null));
        memberList.add(new TeamModal("Ankit Modi", "Coordinator", R.drawable.ankit_modi, "9304105673", null));
        memberList.add(new TeamModal("Kumar Baibhav", "Coordinator", R.drawable.baibhav_bibhuti, "9113744962", null));
        memberList.add(new TeamModal("Himanshu Kumar", "Coordinator", R.drawable.himanshu, "6200844746", null));
        memberList.add(new TeamModal("Nikhil Raj", "Coordinator", R.drawable.nikhil_kashyap, "9155721469", null));
        return memberList;
    }

    private ArrayList<TeamModal> setPublicRelations() {
        ArrayList<TeamModal> memberList = new ArrayList<>();
        memberList.add(new TeamModal("Purushottam Kumar", "PR Head", R.drawable.purushottam, "9097784035", null));
        memberList.add(new TeamModal("Amulya Toppo", "PR Head", R.drawable.amulya, "9771229237", null));
        memberList.add(new TeamModal("Pankaj Chaupal", "PR Head", R.drawable.pankaj_chaupal, "7255081227", null));
        memberList.add(new TeamModal("Aman Raj", "Coordinator", R.drawable.aman, "9973988973", null));
        memberList.add(new TeamModal("Pankaj Verma", "Coordinator", R.drawable.pankaj, "8340445454", null));
        memberList.add(new TeamModal("K Sugandh", "Coordinator", R.drawable.sugandh, "8463993995", null));
        memberList.add(new TeamModal("Kuldeep Tiwary", "Coordinator", R.drawable.kuldeep_tiwari, "9616354442", null));
        return memberList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_arrow:
                FragmentTransaction transaction = manager.beginTransaction();
                DialogList dialogList = new DialogList();
                transaction.add(dialogList, "dialogList");
                transaction.commit();
                break;
        }
    }

    @Override
    public void respond(int position) {
        portfolio.setText(getResources().getStringArray(R.array.spinner)[position]);
        prepareDataList(position);
    }

    @Override
    public void backpressed(boolean backpressed) {
        if (backpressed) {
            finish();
        }
    }
}
