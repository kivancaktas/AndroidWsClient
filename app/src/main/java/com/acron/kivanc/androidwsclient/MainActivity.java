package com.acron.kivanc.androidwsclient;

import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acron.kivanc.androidwsclient.model.Contacts;
import com.acron.kivanc.androidwsclient.parsers.ContactXMLParser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    List<Contacts> contactsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initilize the etxt view
//        output = (TextView) findViewById(R.id.textView);
//        output.setMovementMethod(new ScrollingMovementMethod());

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//http://saperpacd.acron.corp:8000/sap(bD10ciZjPTEwMA==)/bc/bsp/sap/zmt_mob_app02/iletisim_.xml
// http://destek.acron.com.tr/sap(bD10ciZjPTEwMA==)/bc/bsp/sap/zmt_mob_app02/iletisim_.xml
        if(item.getItemId() == R.id.action_do_task) {
            if(isOnLine()){
                requestData("http://destek.acron.com.tr/sap/bc/bsp/sap/zmt_mob_app02/iletisim_.xml");
            }else{
                Toast.makeText(this, "Network isn't avaliable", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {

        ContactAdapter adapter = new ContactAdapter(this, R.layout.item_contact,contactsList);
        setListAdapter(adapter);
    }

    protected boolean isOnLine(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if(netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else{
            return  false;
        }
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
//            updateDisplay("Starting task");

            if(tasks.size()==0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = HttpManager.getData(params[0], "AAKAL2", "54321");
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            tasks.remove(this);
            if(tasks.size()==0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if (result == null){
                Toast.makeText(MainActivity.this, "Can't connect the web service", Toast.LENGTH_LONG).show();
            }
            contactsList = ContactXMLParser.parseFeed(result);
            updateDisplay();

        }

        @Override
        protected void onProgressUpdate(String... values) {
//            updateDisplay(values[0]);
        }
    }
}
