package pt.simov.pl4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class LVAdapter extends BaseAdapter {
    private Context context; //context
    private ArrayList<Subject> items; //data source of the list adapter

    //public constructor
    public LVAdapter(Context context, ArrayList<Subject> items) {
        this.context = context;
        this.items = items;
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
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.note_item, parent, false);
        }

        // get current item to be displayed
        Subject currentItem = (Subject) getItem(position);

        // get the TextView for note title and item description
        TextView title = (TextView) convertView.findViewById(R.id.note_title);
        TextView description = (TextView) convertView.findViewById(R.id.note_description);

        //sets the text for item name and item description from the current item object
        title.setText(currentItem.getTitle());
        description.setText(currentItem.getDescription());

        // returns the view for the current row
        return convertView;
    }
}
