package com.example.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ScrollingActivity_Table extends Activity {
    private static final int REQUEST_CODE = 1234;
    private static final int REQUEST_CODE1 = 2345;
    private static final int REQUEST_CODE2 = 3456;
    private static final int REQUEST_CODE3 = 4567;
    private static final int REQUEST_CODE4 = 5678;
    private static final int REQUEST_CODE5 = 6789;

    Button Name, id, phone,location,nowaddress, reason;
    EditText Name1,id_,phone_, masterphone,phone1_,Name4,Name5,nowaddress_,location_,reason1,reason2,Name6;
    Dialog match_text_dialog;
    ListView textlist;
    ArrayList<String> matches_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling__table);
        Name = (Button)findViewById(R.id.Name);
        id = (Button)findViewById(R.id.id);
        phone= (Button)findViewById(R.id.phone);
        nowaddress= (Button)findViewById(R.id.nowaddress);
        location= (Button)findViewById(R.id.location);
        reason = (Button)findViewById(R.id.reason);


        Name1= (EditText)findViewById(R.id.Name1);
        Name4= (EditText)findViewById(R.id.Name4);
        Name5= (EditText)findViewById(R.id.Name5);
        Name6= (EditText)findViewById(R.id.Name6);
        nowaddress_ = (EditText)findViewById(R.id.nowaddress_);
        id_= (EditText)findViewById(R.id.id_);
        phone_ = (EditText)findViewById(R.id.phone_);
        phone1_ = (EditText)findViewById(R.id.phone1_);
        masterphone = (EditText)findViewById(R.id.masterphone);
        location_ = (EditText)findViewById(R.id.location_);
        reason1= (EditText)findViewById(R.id.reason1);
        reason2= (EditText)findViewById(R.id.reason2);

        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });



        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE1);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE2);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE3);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });


        nowaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE4);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });

        reason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()){
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    startActivityForResult(intent, REQUEST_CODE5);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                }}

        });



    }
    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            match_text_dialog = new Dialog(ScrollingActivity_Table.this);
            match_text_dialog.setContentView(R.layout.fragment_dialog__list);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);


            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    Name1.setText( matches_text.get(position));
                    Name4.setText( matches_text.get(position));
                    Name5.setText( matches_text.get(position));
                    Name6.setText( matches_text.get(position));
                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();

        }

        else  if (requestCode == REQUEST_CODE1 && resultCode == RESULT_OK) {

            match_text_dialog = new Dialog(ScrollingActivity_Table.this);
            match_text_dialog.setContentView(R.layout.fragment_dialog__list);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);


            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    id_.setText( matches_text.get(position));
                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();

        }
        else  if (requestCode == REQUEST_CODE2 && resultCode == RESULT_OK) {

            match_text_dialog = new Dialog(ScrollingActivity_Table.this);
            match_text_dialog.setContentView(R.layout.fragment_dialog__list);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);


            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    phone_.setText( matches_text.get(position));
                    masterphone.setText(matches_text.get(position));
                    phone1_.setText(matches_text.get(position));
                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();

        }

        else if (requestCode == REQUEST_CODE3 && resultCode == RESULT_OK) {

            match_text_dialog = new Dialog(ScrollingActivity_Table.this);
            match_text_dialog.setContentView(R.layout.fragment_dialog__list);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);


            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    location_.setText( matches_text.get(position));

                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();

        }


        else if (requestCode == REQUEST_CODE4 && resultCode == RESULT_OK) {

            match_text_dialog = new Dialog(ScrollingActivity_Table.this);
            match_text_dialog.setContentView(R.layout.fragment_dialog__list);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);


            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    nowaddress_.setText( matches_text.get(position));

                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();

        }

        else if (requestCode == REQUEST_CODE5 && resultCode == RESULT_OK) {

            match_text_dialog = new Dialog(ScrollingActivity_Table.this);
            match_text_dialog.setContentView(R.layout.fragment_dialog__list);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);


            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    reason1.setText( matches_text.get(position));
                    reason2.setText("  " );

                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();

        }


        super.onActivityResult(requestCode, resultCode, data);


    }



}