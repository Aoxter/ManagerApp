package com.example.managerapp;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PerProject {
    public static void writeDataProjectToDo(ArrayList<String> items, Context context, String name){

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(name+"todo.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(items);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDataProjectInWork(ArrayList<String> items, Context context, String name){

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(name+"inwork.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(items);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDataProjectTesting(ArrayList<String> items, Context context, String name){

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(name+"test.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(items);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeDataProjectFinished(ArrayList<String> items, Context context, String name){

        try {
            FileOutputStream fileOutputStream = context.openFileOutput(name+"finished.dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(items);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readDataProjectToDo(Context context, String name){
        ArrayList<String> projectsList = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(name+"todo.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            projectsList = (ArrayList<String>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {

            projectsList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return projectsList;
    }
    public static ArrayList<String> readDataProjectInWork(Context context, String name){
        ArrayList<String> projectsList = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(name+"inwork.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            projectsList = (ArrayList<String>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {

            projectsList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return projectsList;
    }
    public static ArrayList<String> readDataProjectTesting(Context context, String name){
        ArrayList<String> projectsList = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(name+"test.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            projectsList = (ArrayList<String>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {

            projectsList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return projectsList;
    }
    public static ArrayList<String> readDataProjectFinished(Context context, String name){
        ArrayList<String> projectsList = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(name+"finished.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            projectsList = (ArrayList<String>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {

            projectsList = new ArrayList<>();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return projectsList;
    }
}
