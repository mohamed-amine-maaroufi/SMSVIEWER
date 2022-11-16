package com.amine.smsviewer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private HashMap<Integer, List<Message>> recordsStored;
    ExpandableListView expandableListView;
    EditText edtSearch;
    TextView totalPrice;
    ExpandableListAdapter expandableListAdapter;
    List<Integer> expandableListTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        askPermission();
        initViews();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (edtSearch.getText().toString().length() == 0) {
                    expandableListTitle = new ArrayList<Integer>(recordsStored.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(MainActivity.this, expandableListTitle, recordsStored);

                    expandableListView.setAdapter(expandableListAdapter);
                    totalPrice.setText("ADE " + String.valueOf(new DecimalFormat("##.###").format(Utils.countTotalPrice(recordsStored)) ));


                } else {

                    HashMap<Integer, List<Message>> lv_search = new HashMap<Integer, List<Message>>();

                    for (Map.Entry<Integer, List<Message>> set :
                            recordsStored.entrySet()) {

                        if(recordsStored.get(set.getKey()).get(0).getMessageNumber().toString().toLowerCase().contains(edtSearch.getText().toString())){
                            lv_search.put(set.getKey(), recordsStored.get(set.getKey()));
                        }
                    }


                    expandableListTitle = new ArrayList<Integer>(lv_search.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(MainActivity.this, expandableListTitle, lv_search);

                    expandableListView.setAdapter(expandableListAdapter);
                    totalPrice.setText("ADE " + String.valueOf(new DecimalFormat("##.###").format(Utils.countTotalPrice(lv_search)) ));


                    //expand item
                    /*for (int i = 0; i < expandableListAdapter.getGroupCount(); i++) {
                        expandableListView.expandGroup(i);
                    }*/


                }

            }
        });

    }

   private HashMap<Integer, List<Message>> fetchInbox()
    {
        HashMap<Integer, List<Message>> sms = new HashMap<Integer, List<Message>>();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);

        int id = Utils.ID_START_FROM; //1
        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);
            Date date = new Date(cursor.getLong(2));
            String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
            String fee = Utils.substringFee(Utils.formatMessageContent(body));
            float floatFee = Utils.strToFloat(fee);

            Message msg = new Message(address, Utils.formatMessageContent(body), formattedDate, floatFee);

            ArrayList<Message> listContent = new ArrayList<>();
            listContent.add(msg);
            sms.put(id, listContent);
            ++id;
        }
        return sms;

    }

    private void populateMessageList(){

        recordsStored =  fetchInbox();
        expandableListTitle = new ArrayList<Integer>(recordsStored.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, recordsStored);
        expandableListView.setAdapter(expandableListAdapter);
        totalPrice.setText("ADE " + String.valueOf(new DecimalFormat("##.###").format(Utils.countTotalPrice(recordsStored)) ));
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
               /* Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + recordsStored.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/
                return false;
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        populateMessageList();
    }

    private void askPermission(){

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_SMS))
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }
            else
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_SMS}, 1);
            }

        }
        else
        {
            /* do nothing */
            /* permission is granted */
        }
    }

    private void initViews() {

        recordsStored = new HashMap<Integer, List<Message>>();
        expandableListView = (ExpandableListView) findViewById(R.id.messageList);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        totalPrice = (TextView) findViewById(R.id.totalPrice);

        //fill the list of messages
        populateMessageList();
    }

    private void search () {


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "No Permission granted", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                populateMessageList();
                Toast.makeText(this, "Refreshing List ...", Toast.LENGTH_SHORT)
                        .show();
                break;
            default:
                break;
        }

        return true;
    }

}