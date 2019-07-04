package com.example.realestate.ui.main;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.realestate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by quocha2
 * On 04/07/2019
 */
public class AboutUsFragment extends DialogFragment {

    @BindView(R.id.tv_about_us)
    TextView mTextView;

    public static final String TAG = AboutUsFragment.class.getSimpleName();

    public static AboutUsFragment getInstance() {
        return new AboutUsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btn_close_dialog)
    public void onCloseClick() {
        dismiss();
    }

    @OnClick(R.id.support_feedback)
    public void onFacebookClick() {
        FragmentActivity activity = getActivity();
        String facebookScheme = "";
        if (activity != null) {
            try {
                // open in Facebook app
                int versionCode = activity.getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
                if (versionCode >= 3002850) {
                    facebookScheme =  "fb://facewebmodal/f?href=https://www.facebook.com/bcmtung";
                } else {
                    facebookScheme = "fb://profile/bcmtung";
                }
            } catch (Exception e) {
                // open in browser
                facebookScheme = "https://www.facebook.com/bcmtung";
            }
        }
        if (!facebookScheme.isEmpty()) {
            Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookScheme));
            startActivity(facebookIntent);
        }
    }
}