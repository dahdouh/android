package com.example.ecoleenligne.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ecoleenligne.R;
import com.example.ecoleenligne.model.Item;
import com.example.ecoleenligne.model.Offre;
import com.example.ecoleenligne.model.ToDoItem;

import java.util.ArrayList;
import java.util.List;


public class ToDoListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Offre> offres = new ArrayList<Offre>();

    public ToDoListAdapter(Context context, List<Offre> offres) {
        this.offres.addAll(offres);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Offre offre = (Offre) getItem(position);
        if (view == null) {
            view = inflater.inflate(R.layout.to_do_item, null);
        }

        TextView textView = view.findViewById(R.id.to_do_message);
        textView.setText(offre.getNomHotel()+".");

        return view;
    }

    @Override
    public Object getItem(int position) {
        return offres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return offres.size();
    }

    public void setOffres(List<Offre> offres) {
        this.offres = offres;
        notifyDataSetChanged();
    }
}

/*public class ToDoListAdapter extends BaseAdapter {
    private Context context; //context
    private LayoutInflater inflater;
    private List<String> items = new ArrayList<>();


    public ToDoListAdapter(Context context, ArrayList<String> items) {
        this.context = context;
        this.items = items;
        //toDoList.add("My first task");
        //toDoList.add("My second task");
        //inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Object getItem(int position) {
        return items.get(position); //returns list item at the specified position
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String message = (String) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.to_do_item, parent, false);
        }

        // get current item to be displayed
        String currentItem = (String) getItem(position);

        // get the TextView for item name and item description
        TextView textViewMsg= (TextView) convertView.findViewById(R.id.to_do_message);
        textViewMsg.setText(currentItem);

        // returns the view for the current row
        return convertView;

    }

}

 */