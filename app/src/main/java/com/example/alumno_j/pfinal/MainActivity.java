package com.example.alumno_j.pfinal;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private FragmentLeft mFragmentLeft;
   // private BottomFragment mBottomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentLeft = (FragmentLeft) Fragment.instantiate(MainActivity.this, FragmentLeft.class.getName());

        getFragmentManager().beginTransaction().add(R.id.frameleft,mFragmentLeft).commit();
    }
}
