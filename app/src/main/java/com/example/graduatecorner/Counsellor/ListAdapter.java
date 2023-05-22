package com.example.graduatecorner.Counsellor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.graduatecorner.R;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<User> {

    public ListAdapter(Context context, ArrayList<User> userArrayAdapter){
        super(context, R.layout.list_counsellor,userArrayAdapter);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_counsellor, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.counsellor_pic);
        TextView name = convertView.findViewById(R.id.counsellor_name);
        TextView title = convertView.findViewById(R.id.counsellor_title);

        //Set the items to the element of each view
        imageView.setImageResource(user.imageId);
        name.setText(user.name);
        title.setText(user.title);

        return super.getView(position, convertView, parent);
    }
}
