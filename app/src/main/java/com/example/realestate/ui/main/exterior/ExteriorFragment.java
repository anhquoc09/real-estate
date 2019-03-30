package com.example.realestate.ui.main.exterior;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestate.R;
import com.example.realestate.data.model.ConstructionDetail;
import com.example.realestate.data.model.ExteriorDetail;
import com.example.realestate.ui.main.construction.ConstructionAdapter;
import com.example.realestate.ui.main.construction.ConstructionPresenter;
import com.example.realestate.ui.main.listestate.ListEstateFragment;
import com.example.realestate.ui.widget.CustomListLayout;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ExteriorFragment extends Fragment
        implements ExteriorView,
        ExteriorAdapter.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.list_layout)
    CustomListLayout mListLayout;

    private Unbinder mUnbinder;

    private ExteriorAdapter mAdapter;

    private ExteriorPresenter mPresenter;

    public static Fragment newInstance() {
        ListEstateFragment fragment = new ListEstateFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initPresenter();
    }

    private void initPresenter() {
        mPresenter = new ExteriorPresenter();
        mPresenter.attachView(this);
        mPresenter.fetchData();
    }

    private void initView() {
        mAdapter = new ExteriorAdapter();
        mAdapter.setItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mListLayout.setLayoutManager(layoutManager);
        mListLayout.setAdapter(mAdapter);
        mListLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onRefresh() {
        if (mPresenter != null) {
            mPresenter.fetchData();
        }
    }

    @Override
    public void onItemSelected(ExteriorDetail item) {

    }

    @Override
    public void fetchDataSuccess(List<ExteriorDetail> list) {
        if (mListLayout.isRefreshing()) {
            mListLayout.setRefreshing(false);
        }

        if (CollectionUtils.isEmpty(list)) {
            mListLayout.showEmptyLayout();
        } else {
            mListLayout.hideEmptyLayout();
            mAdapter.setData(list);
        }
    }
}