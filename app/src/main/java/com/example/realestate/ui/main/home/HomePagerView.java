package com.example.realestate.ui.main.home;

import com.example.realestate.data.model.EstateDetail;

import java.util.List;

/**
 * @author anhquoc09
 * @since 24/03/2019
 */

public interface HomePagerView {
    void fetchDataSuccess(List<EstateDetail> list);
}