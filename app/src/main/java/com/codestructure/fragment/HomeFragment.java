package com.codestructure.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codestructure.R;
import com.codestructure.adapter.HomeAdapter;
import com.codestructure.bean.HomeBean;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView rv_home_list;
    private ArrayList<HomeBean> homeBeanArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        bindHome();
        return view;
    }

    /**
     * View initialization
     *
     * @param view Returns specific id of view
     */
    private void init(View view) {
        rv_home_list = view.findViewById(R.id.rv_home_list);

        rv_home_list.setLayoutManager(new LinearLayoutManager(getActivity()));

        homeBeanArrayList = new ArrayList<>();
    }

    /**
     * Bind home adapter
     */
    private void bindHome() {
        for (int i = 0; i < 4; i++) {
            HomeBean homeBean = new HomeBean();
            if (i == 0) {
                homeBean.name = "Vending Machine";
                homeBean.description = "Most purchased product";
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_vending_machine);
                homeBean.drawable = icon;
            } else if (i == 1) {
                homeBean.name = "Hover Board";
                homeBean.description = "Most trending product";
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_hoverbaord);
                homeBean.drawable = icon;
            } else if (i == 2) {
                homeBean.name = "Basketball Kit";
                homeBean.description = "Sports category";
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_baceball);
                homeBean.drawable = icon;
            } else {
                homeBean.name = "Skates";
                homeBean.description = "For fun activity";
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_skatebaord);
                homeBean.drawable = icon;
            }
            homeBeanArrayList.add(homeBean);
        }
        if (homeBeanArrayList != null && homeBeanArrayList.size() > 0) {
            HomeAdapter homeAdapter = new HomeAdapter(getActivity(), homeBeanArrayList);
            rv_home_list.setAdapter(homeAdapter);
        }
    }

}