package com.example.ningning.retrievefile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ningning.retrievefile.dummy.DummyContent;
import com.example.ningning.retrievefile.dummy.DummyContent.DummyItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class AllTeams extends Fragment {
    RoboInfo myRobo = RoboInfo.getInstance();
    ListView lv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View in = inflater.inflate(R.layout.fragment_all_teams, container, false);

        lv = (ListView) in.findViewById(R.id.listallteams);
        String[] names = getResources().getStringArray(R.array.RobotNumbers);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this.getActivity(),
                android.R.layout.simple_list_item_1,names
        );

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                //get text from list
                String entry = (String) parent.getAdapter().getItem(position);
                myRobo.setSingleTeam(entry);

                Class fragmentClass = DisplaySingleTeam.class;
                Fragment fragment = null;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.wheretheshitgoes, fragment).commit();

            }
        });
        return in;
    }




}
