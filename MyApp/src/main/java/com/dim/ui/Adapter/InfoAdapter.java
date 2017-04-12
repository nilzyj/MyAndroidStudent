package com.dim.ui.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dim.ui.Model.Info;
import com.dim.ui.R;

import java.util.List;

/**
 * Created by dim on 2017/3/31.
 */

public class InfoAdapter extends ArrayAdapter {

    public int resourceId;

    public InfoAdapter(Context context, int resource, List<Info> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Info info = (Info) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_info);
        textView.setText(info.getInfo() + ":" + info.getContent());
        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }
}
