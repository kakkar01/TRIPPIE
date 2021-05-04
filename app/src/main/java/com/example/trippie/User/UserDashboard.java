package com.example.trippie.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.trippie.Common.LoginSignup.Login;
import com.example.trippie.Common.LoginSignup.RetaileStartupScreen;
import com.example.trippie.HelperClassses.HomeAdapter.FeaturedAdapter;
import com.example.trippie.HelperClassses.HomeAdapter.FeaturedHelperClass;
import com.example.trippie.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.trippie.R.*;

public class UserDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   //variables
   
    static final float END_SCALE=0.7f;
    LinearLayout contentView;
    RecyclerView featuredRecycler,mostViewedRecycler, categoriesRecycler;
    RecyclerView.Adapter adapter;
    private GradientDrawable gradient1,graident2,gradient3,gradient4;
    ImageView menuIcon;
    ImageView map;
    //drawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(layout.activity_user_dashboard);

        //Hooks
        featuredRecycler= findViewById(id.featured_recycler);
        menuIcon= findViewById(id.menu_icon);
        contentView = findViewById(id.content);
        map=findViewById(id.mapfragment);
        // menu hooks
        drawerLayout=findViewById(id.drawer_layout);
        navigationView=findViewById(id.navigation_view);
        navigationView.setCheckedItem(id.nav_home);



        navigationDrawer();


        //recycler function calls
        featuredRecycler();
    }
//Navigaton Drawers functions
    private void navigationDrawer() {
        //navigation drawer

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else  drawerLayout.openDrawer(GravityCompat.START);


            }
        });

        animateNavigationDrawer();
    }

    private void animateNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(color.app));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });    }
        public void callRetailerScreen(View view){

        startActivity(new Intent(getApplicationContext(), RetaileStartupScreen.class));

        }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else
          super.onBackPressed();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }

    private void featuredRecycler(){
        featuredRecycler.setHasFixedSize(true);
        featuredRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<FeaturedHelperClass> featuredLocations = new ArrayList<>();
        featuredLocations.add(new FeaturedHelperClass(R.drawable.mcdonald, "Mcdonald's","bwkebwcbwiecbiwecbwhebc wckjwecwi"));
        featuredLocations.add(new FeaturedHelperClass(drawable.mcdonald, "Mcdonald's","bwkebwcbwiecbiwecbwhebc wckjwecwi"));
        featuredLocations.add(new FeaturedHelperClass(drawable.mcdonald, "Mcdonald's","bwkebwcbwiecbiwecbwhebc wckjwecwi"));

        adapter = new FeaturedAdapter(featuredLocations);
        featuredRecycler.setAdapter(adapter);

        GradientDrawable geadient1 = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{0xffeff400,0xffaff600});
    }
    // database
    public void openmap(View view){
            Intent maps = new Intent(Intent.ACTION_VIEW);
            maps.setData(Uri.parse("geo:30.900965, 75.857277"));
            startActivity(maps);
    }
    // database
    public  void search(View view){
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        string query;
        intent.putExtra(SearchManager.QUERY, "himanshu"); // query contains search string
        startActivity(intent);
    }
    public void opencab(View view){
        Intent launchintent =getPackageManager().getLaunchIntentForPackage("io.github.project_travel_mate");
        startActivity(launchintent);
    }
    public void openflights(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        Intent chooser=Intent.createChooser(intent,"Map");

        startActivity(chooser);
    }



}