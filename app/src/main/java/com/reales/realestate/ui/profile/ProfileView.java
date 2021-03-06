package com.reales.realestate.ui.profile;

import com.reales.realestate.data.model.EstateDetail;
import com.reales.realestate.data.model.Profile;

import java.util.List;

public interface ProfileView {
    void onFetchProfileSuccess(Profile profile);

    void onFetchListEstateSuccess(List<EstateDetail> data);

    void onLoadMoreListSuccess(List<EstateDetail> data);

    void editProfileSuccess();

    void deletePostSuccess(int position);

    void hideListProgress();

    void showListProgress();

    void hideNoNetwork();

    void showNoNetwork();

    void showProgress();

    void hideProgress();
}
