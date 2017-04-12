package com.dim.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dim.ui.R;

/**
 * Created by dim on 2017/3/9.
 */

public class AccountFragment extends Fragment {
    private Button btn_confirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_confirm, container, false);
        return view;
    }
}
