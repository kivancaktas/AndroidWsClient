package com.acron.kivanc.androidwsclient;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.acron.kivanc.androidwsclient.model.Contacts;

import java.util.List;

/**
 * Created by KIVANC on 21.5.2015.
 */
public class ContactAdapter extends ArrayAdapter<Contacts>{
    private Context context;
    private List<Contacts> contactList;
    public ContactAdapter(Context context, int  resource, List<Contacts> objects){
        super(context, resource, objects);
        this.context = context;
        this.contactList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_contact, parent, false);
        Contacts contact = contactList.get(position);
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        tv.setText(contact.getNAME_FIRST());


        return view;
    }
}
