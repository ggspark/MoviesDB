package com.fastacash.moviesdb.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.fastacash.moviesdb.utils.QLog;

public class BaseActivity extends AppCompatActivity {
    protected ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        loadingDialog = new ProgressDialog(BaseActivity.this);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setMessage("Loading");
        loadingDialog.setCancelable(false);
        loadingDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    protected void onStart() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onStart();
    }

    @Override
    protected void onRestart() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onRestart();
    }

    @Override
    protected void onResume() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onResume();
    }

    @Override
    protected void onPause() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onPause();
    }

    @Override
    protected void onStop() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        QLog.v("Class:" + ((Object) this).getClass().getSimpleName());
        return super.onOptionsItemSelected(item);
    }
}
