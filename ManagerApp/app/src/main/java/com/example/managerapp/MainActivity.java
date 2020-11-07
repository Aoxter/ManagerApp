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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // New Project
        editNewProject = findViewById(R.id.editProjectName);
        buttonNewProject = findViewById(R.id.buttonNewProject);
        listViewProjects = findViewById(R.id.projectsList);

        listProjects = FileManager.readProjectsList(this);
        adapterProjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listProjects);
        listViewProjects.setAdapter(adapterProjects);

        buttonNewProject.setOnClickListener(this);
        listViewProjects.setOnItemClickListener(this);
        listViewProjects.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                listProjects.remove(position);
                adapterProjects.notifyDataSetChanged();
                FileManager.writeProjectsLists(listProjects, getApplicationContext());
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

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonNewProject:
                String itemEntered = editNewProject.getText().toString();
                adapterProjects.add(itemEntered);
                editNewProject.setText("");
                FileManager.writeProjectsLists(listProjects, this);
                Toast.makeText(this, "Project added", Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonToDo:
                if (this.chosen.equals(Boolean.TRUE)){
                String task = editToDo.getText().toString();
                String name = editName1.getText().toString();
                adapterToDo.add(task+" - "+name);
                editToDo.setText("");
                PerProject.writeDataProjectToDo(listToDo, this, this.projectName);
                Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.chosen = Boolean.TRUE;
        listViewToDo.setAdapter(null);
        this.projectName = listProjects.get(position);
        listToDo = PerProject.readDataProjectToDo(this, this.projectName);
        adapterToDo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listToDo);
        listViewToDo.setAdapter(adapterToDo);
        Toast.makeText(this, "Loaded project: "+this.projectName, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }
}