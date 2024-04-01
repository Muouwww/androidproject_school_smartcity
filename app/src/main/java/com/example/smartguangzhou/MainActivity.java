package com.example.smartguangzhou;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartguangzhou.Utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    public NavController navController;
    public BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static String Token = "";
    public static boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

    }


    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView() {
        sharedPreferences = getSharedPreferences("App",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Token = sharedPreferences.getString("token","");


        toolbar = findViewById(R.id.toolbar);
        navController = Navigation.findNavController(this,R.id.main_fragment);
        bottomNavigationView = findViewById(R.id.nav_menu);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.nav_item1){
                    navController.navigate(R.id.home1);
                    return true;
                } else if (item.getItemId() == R.id.nav_item2) {
                    navController.navigate(R.id.serive);
                    return true;
                } else if (item.getItemId() == R.id.nav_item3) {
                    navController.navigate(R.id.underground);
                    item.setIcon(R.drawable.underground_s);
                    return true;
                } else if (item.getItemId() == R.id.nav_item4) {
                    navController.navigate(R.id.news);
                    return true;
                } else if (item.getItemId() == R.id.nav_item5) {
                    navController.navigate(R.id.personal);
                    return true;
                }
                return false;
            }
        });

    }
    public void updagePage(int item){
        bottomNavigationView.setSelectedItemId(item);
    }
}