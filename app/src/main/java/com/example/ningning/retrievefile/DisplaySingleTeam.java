package com.example.ningning.retrievefile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.support.v4.widget.TextViewCompat;


public class DisplaySingleTeam extends Fragment {
    RoboInfo myRobo = RoboInfo.getInstance();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View in = inflater.inflate(R.layout.fragment_display_single_team, container, false);

        TableLayout tl = (TableLayout) in.findViewById(R.id.tablelayoutsingleteam);
        TextView name = (TextView) in.findViewById(R.id.teamname);

        try {
            verifyStoragePermissions(this.getActivity());
            String yourFilePath = "/sdcard/RobotInfo/" + myRobo.getSingleTeam() + ".txt";
            name.setText(myRobo.getSingleTeam());

            File yourFile = new File(yourFilePath);

            if (!yourFile.exists()) {
                Toast.makeText(getActivity(), "File does not exist!", Toast.LENGTH_SHORT).show();
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(yourFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");

                    TableRow tr = new TableRow(this.getActivity());
                    TableRow.LayoutParams layoutParams = new TableRow.LayoutParams
                            (TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);

                    if (!values[0].equals("Matches")) {
                        TextView tv1 = new TextView(this.getActivity());
                        tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.65f));
                        tv1.setPadding(0, 5, 20, 5);
                        tv1.setText(values[0]);

                        TextView tv2 = new TextView(this.getActivity());
                        tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.35f));
                        tv2.setPadding(30, 5, 5, 5);
                        tv2.setText(values[1]);

                        tr.addView(tv1);
                        tr.addView(tv2);
                        tl.addView(tr, layoutParams);
                    } else {
                        TextView tv1 = new TextView(this.getActivity());
                        tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));
                        tv1.setPadding(0, 20, 20, 5);
                        tv1.setText(values[0]);
                        tr.addView(tv1);

                        String[] parts = values[1].split(" ");

                        Spinner spin = new Spinner(this.getActivity());
                        spin.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.5f));

                        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this.getActivity(),
                                android.R.layout.simple_spinner_item, parts);
                        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                        spin.setAdapter(spinnerArrayAdapter);

                        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String num = parent.getSelectedItem().toString();

                                if (!num.equals("") && !num.equals(" ")) {

                                    myRobo.setMatchNumber(num);

                                    Class fragmentClass = DisplaySingleMatch.class;
                                    Fragment fragment = null;
                                    try {
                                        fragment = (Fragment) fragmentClass.newInstance();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.wheretheshitgoes, fragment).commit();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        tr.addView(spin);
                        tl.addView(tr, layoutParams);
                    }
                }
                Log.d("TAG", yourFilePath);
                reader.close();
            }
        } catch(IOException e){
            e.printStackTrace();
            Log.d("file error", "" + e.getMessage());
        }

        return in;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
