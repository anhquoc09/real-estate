package com.example.realestate.ui.main.home.map;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.realestate.R;
import com.example.realestate.UserManager;
import com.example.realestate.data.model.EstateDetail;
import com.example.realestate.ui.login.LoginActivity;
import com.example.realestate.ui.main.estatedetail.EstateDetailAcivity;
import com.example.realestate.ui.main.profile.ProfileActivity;
import com.example.realestate.ui.main.uppost.UpPostActivity;
import com.example.realestate.utils.PermissionUtils;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author anhquoc09
 * @since 10/03/2019
 */

public class MapFragment extends Fragment
        implements MapView,
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = MapFragment.class.getSimpleName();

    private Unbinder mUnbinder;

    private GoogleMap mGoogleMap;

    private MapPresenter mPresenter;

    private CustomGoogleMapAdapter mMapAdapter;

    private OnCallBackListener mCallBackListener;

    private LocationManager mLocationManager;

    private Location mLocation;

    private final List<EstateDetail> mList = new ArrayList<>();

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public static MapFragment newInstance(OnCallBackListener listener) {
        MapFragment fragment = new MapFragment();
        fragment.setCallBackListener(listener);
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);

        mLocation = new Location("HCMUS");
        mLocation.setLatitude(10.763147);
        mLocation.setLongitude(106.682203);

        initPresenter();

        initMap();
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }

    private void initMap() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
    }

    private void initPresenter() {
        mPresenter = new MapPresenter();
        mPresenter.attachView(this);
    }


    public void setCallBackListener(OnCallBackListener listener) {
        mCallBackListener = listener;
    }

    public void setData(List<EstateDetail> list) {
        mList.clear();
        if (!CollectionUtils.isEmpty(list)) {
            mList.addAll(list);
            notifyDataChange();
        }
    }

    private void notifyDataChange() {
        if (mGoogleMap != null) {
            mGoogleMap.clear();

            for (EstateDetail estate : mList) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(estate.getLatLng())
                        .title(estate.getTitle())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                Marker marker = mGoogleMap.addMarker(markerOptions);
                marker.setTag(estate);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mMapAdapter = new CustomGoogleMapAdapter(getContext());
        mGoogleMap.setInfoWindowAdapter(mMapAdapter);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        Activity activity = getActivity();
        if (activity != null) {
            mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && activity.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                PermissionUtils.Request_FINE_LOCATION(activity, 1);

            } else {
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 50, mLocationListener);
                mGoogleMap.setMyLocationEnabled(true);
            }
        }
        getMyLocation();

        notifyDataChange();
    }

    private void getMyLocation() {
        mGoogleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                        new LatLng(mLocation.getLatitude(), mLocation.getLongitude()), 15));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        EstateDetail estateDetail = (EstateDetail) marker.getTag();
        if (estateDetail != null) {
            startActivity(EstateDetailAcivity.intentFor(getActivity(), estateDetail));
        }
    }

    @OnClick(R.id.btn_up_post)
    public void onUpPostClick() {
        if (UserManager.isUserLoggedIn()) {
            startActivity(UpPostActivity.intentFor(getActivity()));
        } else {
            startActivity(LoginActivity.intentFor(getActivity()));
        }
    }

    @OnClick(R.id.btn_my_location)
    public void onMyLocationClick() {
        getMyLocation();
    }

    /**
     * {@link OnCallBackListener}
     */
    public interface OnCallBackListener {
        void onMoveMap();

        void onRefreshMap();
    }
}
