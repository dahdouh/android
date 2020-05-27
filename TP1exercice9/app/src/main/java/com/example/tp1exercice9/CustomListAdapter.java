package com.example.tp1exercice9;
   import android.content.Context;
   import android.graphics.Color;
   import android.util.Log;
   import android.view.LayoutInflater;
   import android.view.View;
   import android.view.ViewGroup;
   import android.widget.BaseAdapter;
   import android.widget.ImageView;
   import android.widget.TextView;
   import java.util.List;

public class CustomListAdapter  extends BaseAdapter {

    private List<Evenement> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomListAdapter(Context aContext,  List<Evenement> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_layout, null);
            holder = new ViewHolder();
            holder.dateEvenementView = (TextView) convertView.findViewById(R.id.textView_dateEvenement);
            holder.nomEvenementView = (TextView) convertView.findViewById(R.id.textView_nomEvenement);
            holder.heureEvenmentView = (TextView) convertView.findViewById(R.id.textView_heureEvenement);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Evenement evenement = this.listData.get(position);
        holder.dateEvenementView.setText(evenement.getDate());
        holder.nomEvenementView.setText(evenement.getNom());
        holder.heureEvenmentView.setText(evenement.getHeure() +" - " + evenement.getDescription());
        holder.dateEvenementView.setTextColor(Color.RED);

        return convertView;
    }

    static class ViewHolder {
        TextView dateEvenementView;
        TextView nomEvenementView;
        TextView heureEvenmentView;
    }

}