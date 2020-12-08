package com.example.managerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public String projectName = "";
    private Boolean chosen = Boolean.FALSE;
    private Boolean canAddProject = Boolean.FALSE;
    private RewardedAd rewardedAd;
    // New Project
    private EditText editNewProject;
    private Button buttonNewProject;
    private ListView listViewProjects;

    private ArrayList<String> listProjects;
    private ArrayAdapter<String> adapterProjects;
    //To Do
    private EditText editToDo;
    private EditText editName1;
    private Button buttonToDo;
    private ListView listViewToDo;

    private ArrayList<String> listToDo;
    private ArrayAdapter<String> adapterToDo;
    //In work
    private EditText editInWork;
    private EditText editName2;
    private Button buttonInWork;
    private ListView listViewInWork;

    private ArrayList<String> listInWork;
    private ArrayAdapter<String> adapterInWork;
    //Testing
    private EditText editTest;
    private EditText editName3;
    private Button buttonTest;
    private ListView listViewTest;

    private ArrayList<String> listTest;
    private ArrayAdapter<String> adapterTest;
    //Finished
    private EditText editFinished;
    private EditText editName4;
    private Button buttonFinished;
    private ListView listViewFinished;

    private ArrayList<String> listFinished;
    private ArrayAdapter<String> adapterFinished;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ---------- ADMOB AND ADS ----------
        // AdMob initialize
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        //Banner AD
        //AdView
        AdView adView = (AdView)findViewById(R.id.adView);
        //Build a request
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        //Load an ad
        adView.loadAd(adRequest);

        //Reward Ad
        rewardedAd = new RewardedAd(this, "ca-app-pub-5947679658512901/5708880264");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        // ---------- END ----------

        // New Project
        editNewProject = findViewById(R.id.editProjectName);
        buttonNewProject = findViewById(R.id.buttonNewProject);
        listViewProjects = findViewById(R.id.projectsList);

        listProjects = AllProjectsManager.readProjectsList(this);
        adapterProjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listProjects)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position % 2 ==1 ){
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                }
                else{
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                }
                return view;
            }
        };
        listViewProjects.setAdapter(adapterProjects);

        buttonNewProject.setOnClickListener(this);
        listViewProjects.setOnItemClickListener(this);
        listViewProjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listProjects.remove(position);
                adapterProjects.notifyDataSetChanged();
                AllProjectsManager.writeProjectsLists(listProjects, getApplicationContext());
                Toast.makeText(getApplicationContext(), "Deleted project", Toast.LENGTH_SHORT).show();
                listViewInWork.setAdapter(null);
                listViewToDo.setAdapter(null);
                listViewTest.setAdapter(null);
                listViewFinished.setAdapter(null);
                return false;
            }
        });
        // To Do
        listViewToDo = findViewById(R.id.toBeginList);
        buttonToDo = findViewById(R.id.buttonToDo);
        editToDo = findViewById(R.id.editToDo);
        editName1 = findViewById(R.id.editName1);

        buttonToDo.setOnClickListener(this);
        listViewToDo.setOnItemLongClickListener(this);
        //In Work
        listViewInWork = findViewById(R.id.inWorkList);
        buttonInWork = findViewById(R.id.buttonInWork);
        editInWork = findViewById(R.id.editInWork);
        editName2 = findViewById(R.id.editName2);

        buttonInWork.setOnClickListener(this);
        listViewInWork.setOnItemLongClickListener(this);
        //Testing
        listViewTest = findViewById(R.id.inTestList);
        buttonTest = findViewById(R.id.buttonTest);
        editTest = findViewById(R.id.editTest);
        editName3 = findViewById(R.id.editName3);

        buttonTest.setOnClickListener(this);
        listViewTest.setOnItemLongClickListener(this);
        //Finished
        listViewFinished = findViewById(R.id.finishedList);
        buttonFinished = findViewById(R.id.buttonFinished);
        editFinished = findViewById(R.id.editFinished);
        editName4 = findViewById(R.id.editName4);

        buttonFinished.setOnClickListener(this);
        listViewFinished.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonNewProject: {
                canAddProject = Boolean.FALSE;
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setCancelable(true);
                builder.setTitle("New project");
                builder.setMessage("Do you want to view the ad to create a new project");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                createProject(v.getContext());
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            }
            case R.id.buttonToDo: {
                if (this.chosen.equals(Boolean.TRUE)) {
                String task = editToDo.getText().toString();
                String name = editName1.getText().toString();
                adapterToDo.add(task + " - " + name);
                editToDo.setText("");
                editName1.setText("");
                PerProject.writeDataProjectToDo(listToDo, this, this.projectName);
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                break;
                }
            }
            case R.id.buttonInWork: {
                if (this.chosen.equals(Boolean.TRUE)) {
                String task = editInWork.getText().toString();
                String name = editName2.getText().toString();
                adapterInWork.add(task + " - " + name);
                editInWork.setText("");
                editName2.setText("");
                PerProject.writeDataProjectInWork(listInWork, this, this.projectName);
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                break;
                }
            }
            case R.id.buttonTest: {
                if (this.chosen.equals(Boolean.TRUE)) {
                    String task = editTest.getText().toString();
                    String name = editName3.getText().toString();
                    adapterTest.add(task + " - " + name);
                    editTest.setText("");
                    editName3.setText("");
                    PerProject.writeDataProjectTesting(listTest, this, this.projectName);
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            case R.id.buttonFinished: {
                if (this.chosen.equals(Boolean.TRUE)) {
                    String task = editFinished.getText().toString();
                    String name = editName4.getText().toString();
                    adapterFinished.add(task + " - " + name);
                    editFinished.setText("");
                    editName4.setText("");
                    PerProject.writeDataProjectFinished(listFinished, this, this.projectName);
                    Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.chosen = Boolean.TRUE;
        this.projectName = listProjects.get(position);
        // To Do
        listViewToDo.setAdapter(null);
        listToDo = PerProject.readDataProjectToDo(this, this.projectName);
        adapterToDo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listToDo)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position % 2 ==1 ){
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                }
                else{
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
                }
                return view;
            }
        };
        listViewToDo.setAdapter(adapterToDo);
        // In Work
        listViewInWork.setAdapter(null);
        listInWork = PerProject.readDataProjectInWork(this, this.projectName);
        adapterInWork = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listInWork)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position % 2 ==1 ){
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                }
                else{
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                }
                return view;
            }
        };
        listViewInWork.setAdapter(adapterInWork);
        Toast.makeText(this, "Loaded project: "+this.projectName, Toast.LENGTH_SHORT).show();
        //Testing
        listViewTest.setAdapter(null);
        listTest = PerProject.readDataProjectTesting(this, this.projectName);
        adapterTest = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTest)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position % 2 ==1 ){
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
                else{
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                }
                return view;
            }
        };
        listViewTest.setAdapter(adapterTest);
        Toast.makeText(this, "Loaded project: "+this.projectName, Toast.LENGTH_SHORT).show();
        //Finished
        listViewFinished.setAdapter(null);
        listFinished = PerProject.readDataProjectFinished(this, this.projectName);
        adapterFinished = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listFinished)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position % 2 ==1 ){
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                }
                else{
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
                }
                return view;
            }
        };
        listViewFinished.setAdapter(adapterFinished);
        Toast.makeText(this, "Loaded project: "+this.projectName, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.toBeginList:
                listToDo.remove(position);
                adapterToDo.notifyDataSetChanged();
                PerProject.writeDataProjectToDo(listToDo, this, this.projectName);
                Toast.makeText(getApplicationContext(), "Deleted task", Toast.LENGTH_SHORT).show();
                break;
            case R.id.inWorkList:
                listInWork.remove(position);
                adapterInWork.notifyDataSetChanged();
                PerProject.writeDataProjectInWork(listInWork, this, this.projectName);
                Toast.makeText(getApplicationContext(), "Deleted task", Toast.LENGTH_SHORT).show();
                break;
            case R.id.inTestList:
                listTest.remove(position);
                adapterTest.notifyDataSetChanged();
                PerProject.writeDataProjectTesting(listInWork, this, this.projectName);
                Toast.makeText(getApplicationContext(), "Deleted task", Toast.LENGTH_SHORT).show();
                break;
            case R.id.finishedList:
                listFinished.remove(position);
                adapterFinished.notifyDataSetChanged();
                PerProject.writeDataProjectFinished(listFinished, this, this.projectName);
                Toast.makeText(getApplicationContext(), "Deleted task", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    // load new rewarded ad after previous one end
    public RewardedAd createAndLoadRewardedAd() {
        RewardedAd rewardedAd = new RewardedAd(this,
                "ca-app-pub-3940256099942544/5224354917");
        RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                // Ad successfully loaded.
            }

            @Override
            public void onRewardedAdFailedToLoad(LoadAdError adError) {
                // Ad failed to load.
            }
        };
        rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        return rewardedAd;
    }

    public void createProject(Context context){
        if (rewardedAd.isLoaded()) {
            Activity activityContext = MainActivity.this;
            RewardedAdCallback adCallback = new RewardedAdCallback() {
                @Override
                public void onRewardedAdOpened() {
                    // Ad opened.
                }

                @Override
                public void onRewardedAdClosed() {
                    // Ad closed.
                    rewardedAd = createAndLoadRewardedAd();
                }

                @Override
                public void onUserEarnedReward(@NonNull RewardItem reward) {
                    // User earned reward.
                    canAddProject = Boolean.TRUE;
                    String itemEntered = editNewProject.getText().toString();
                    adapterProjects.add(itemEntered);
                    editNewProject.setText("");
                    AllProjectsManager.writeProjectsLists(listProjects, context);
                    //Toast.makeText(this, "Project added", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRewardedAdFailedToShow(AdError adError) {
                    // Ad failed to display.
                }
            };
            rewardedAd.show(activityContext, adCallback);
        } else {
            Log.d("TAG", "The rewarded ad wasn't loaded yet.");
        }
        if(canAddProject == Boolean.TRUE){

        }

    }
}