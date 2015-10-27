package com.fastacash.moviesdb.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fastacash.moviesdb.utils.QLog;


/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 11/Mar/2015
 */
public class BaseFragment extends Fragment {
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onInflate(activity, attrs, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onStart();
    }

    @Override
    public void onResume() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onResume();
    }

    @Override
    public void onPause() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onPause();
    }

    @Override
    public void onStop() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onDetach();
    }

    @Override
    public void onLowMemory() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onLowMemory();
    }
}
