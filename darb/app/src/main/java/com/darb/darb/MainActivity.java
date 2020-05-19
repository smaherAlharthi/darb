package com.darb.darb;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Locale;

import classes.GPSTracker;
import classes.GetToast;
import classes.LocalLang;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, OnMapReadyCallback {
    Context context = this;
    private View navHeader;
    NavigationView navigationView;

Dialog dialog;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new LocalLang().setLocale(context, "ar");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager=getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.contentPanel,new ShowMain()).commit();

    }

    @Override
    public void onBackPressed() {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("هل أنت متأكد من تسجيل الخروج");
            builder.setCancelable(false);
            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                   MainActivity.this.finish();
                   startActivity(new Intent(context,login.class));
                }
            });
            builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Exit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("هل أنت متأكد من تسجيل الخروج");
            builder.setCancelable(false);
            builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MainActivity.this.finish();
                    startActivity(new Intent(context,login.class));
                }
            });
            builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else if (id==R.id.mnCallUs){
            try {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
                dialog.setContentView(R.layout.about_us);


                dialog.show();
            } catch (Exception e) {
                GetToast.Toast(context, e.getMessage());
            }
        }else if (id==R.id.mnOrder){
            FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel,new Trasnaction_List()).commit();
        }else if (id==R.id.mnMain){
            FragmentManager fragmentManager=getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.contentPanel,new ShowMain()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            default:
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
