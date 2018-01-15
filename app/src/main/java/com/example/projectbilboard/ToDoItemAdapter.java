package com.example.projectbilboard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Adapter to bind a ToDoItem List to a view
 */
public class ToDoItemAdapter extends ArrayAdapter<ToDoItem> {

    /**
     * Adapter context
     */
    private Context mContext;

    /**
     * Adapter View layout
     */
    private int mLayoutResourceId;

    public ToDoItemAdapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);

        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    /**
     * Returns the view for a specific item on the list
     */
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View row = convertView;

        final ToDoItem currentItem = getItem(position);

//        Log.e("Item position", getItem(position).toString());
//        Log.e("Context", mContext.toString());


        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(mLayoutResourceId, parent, false);
        }

        row.setTag(currentItem);

        ImageView imageView = (ImageView) row.findViewById(R.id.imageView);
        if (currentItem.getImage().length() > 5 ) {
            Picasso.with(mContext).load(currentItem.getImage()).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.brak_zdjecia);
        }

        final TextView textViewName = (TextView) row.findViewById(R.id.textViewName);
        textViewName.setText(currentItem.getText());

        TextView textViewDescription = (TextView) row.findViewById(R.id.textViewDescription);
        textViewDescription.setText(currentItem.getDescription());

        TextView learnMore = (TextView) row.findViewById(R.id.learnMore);
        learnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent itemDetailIntent = new Intent(getContext(), ItemDetailActivity.class);
                itemDetailIntent.putExtra("Key_id", currentItem.getId());
                itemDetailIntent.putExtra("Title", currentItem.getText());
                itemDetailIntent.putExtra("Description", currentItem.getDescription());
                itemDetailIntent.putExtra("Owner", currentItem.getOwner());
                itemDetailIntent.putExtra("ImageView", currentItem.getImage());
                itemDetailIntent.putExtra("Email", currentItem.getEmail());
                itemDetailIntent.putExtra("Phone", currentItem.getPhone());
                itemDetailIntent.putExtra("Region", currentItem.getRegion());
                itemDetailIntent.putExtra("Adress", currentItem.getAdress());
                itemDetailIntent.putExtra("ResourceId", mLayoutResourceId);

                mContext.startActivity(itemDetailIntent);

            }
        });

        TextView mapsTextView = (TextView) row.findViewById(R.id.textMap);
        mapsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mapIntent = new Intent(getContext(), MapsActivity.class);
                mapIntent.putExtra("LAT", currentItem.getRegion().toString());
                mapIntent.putExtra("LNG", currentItem.getAdress().toString());
                mapIntent.putExtra("Title", currentItem.getText());
                mContext.startActivity(mapIntent);
            }
        });


        return row;
    }

}