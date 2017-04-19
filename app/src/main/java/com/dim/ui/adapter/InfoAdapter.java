package com.dim.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.dim.ui.model.Info;
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
        ViewHolder holder;
        Info info = (Info) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resourceId, null);
            holder = new ViewHolder();
            holder.mTvInfo = (TextView) convertView.findViewById(R.id.tv_info);
            holder.mTvContent = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mTvInfo.setText(info.getInfo());
        holder.mTvContent.setText(info.getContent());
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder {
        TextView mTvInfo, mTvContent;
    }
}
