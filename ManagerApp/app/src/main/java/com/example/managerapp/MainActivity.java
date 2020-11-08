package com.example.managerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    public String projectName = "";
    private Boolean chosen = Boolean.FALSE;
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

        // New Project
        editNewProject = findViewById(R.id.editProjectName);
        buttonNewProject = findViewById(R.id.buttonNewProject);
        listViewProjects = findViewById(R.id.projectsList);

        listProjects = AllProjectsManager.readProjectsList(this);
        adapterProjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listProjects);
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
                String itemEntered = editNewProject.getText().toString();
                adapterProjects.add(itemEntered);
                editNewProject.setText("");
                AllProjectsManager.writeProjectsLists(listProjects, this);
                Toast.makeText(this, "Project added", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.buttonToDo: {
                if (this.chosen.equals(Boolean.TRUE)) {
                String task = editToDo.getText().toString();
                String name = editName1.getText().toString();
                adapterToDo.add(task + " - " + name);
                editToDo.setText("");
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
        adapterToDo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listToDo);
        listViewToDo.setAdapter(adapterToDo);
        // In Work
        listViewInWork.setAdapter(null);
        listInWork = PerProject.readDataProjectInWork(this, this.projectName);
        adapterInWork = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listInWork);
        listViewInWork.setAdapter(adapterInWork);
        Toast.makeText(this, "Loaded project: "+this.projectName, Toast.LENGTH_SHORT).show();
        //Testing
        listViewTest.setAdapter(null);
        listTest = PerProject.readDataProjectTesting(this, this.projectName);
        adapterTest = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTest);
        listViewTest.setAdapter(adapterTest);
        Toast.makeText(this, "Loaded project: "+this.projectName, Toast.LENGTH_SHORT).show();
        //Finished
        listViewFinished.setAdapter(null);
        listFinished = PerProject.readDataProjectFinished(this, this.projectName);
        adapterFinished = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listFinished);
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
}