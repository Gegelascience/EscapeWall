package com.escapewall.verchere.escapewall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by VERCHERE on 08/11/2017.
 */

public class Param extends AppCompatActivity implements View.OnClickListener {

    TextView theme;
    Button clas,butt;
    SharedPreferences preferences;
    ImageView themeImg;

    @Override
    protected void onCreate(Bundle saveInstantState){
        super.onCreate(saveInstantState);
        setContentView(R.layout.param_layout);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String themeChoice=preferences.getString("theme","classic");
        theme=findViewById(R.id.theme);
        themeImg=findViewById(R.id.theme_img);
        if (themeChoice.equals("butterfly")){
            theme.setText(getResources().getString(R.string.theme,getResources().getString(R.string.btn_bu_th)));
        }else{
            theme.setText(getResources().getString(R.string.theme,getResources().getString(R.string.classic_mode)));
        }
        if (themeChoice.equals("classic")){
            themeImg.setImageResource(R.drawable.module);
        }
        else if (themeChoice.equals("butterfly")){
            themeImg.setImageResource(R.drawable.butterfly);
        }
        clas=findViewById(R.id.button2);
        butt=findViewById(R.id.button3);
        clas.setOnClickListener(this);
        butt.setOnClickListener(this);

    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.button2:
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("theme","classic");
                editor.apply();
                theme.setText(getResources().getString(R.string.theme,getResources().getString(R.string.classic_mode)));
                themeImg.setImageResource(R.drawable.module);
                break;
            case R.id.button3:
                SharedPreferences.Editor editor2=preferences.edit();
                editor2.putString("theme","butterfly");
                editor2.apply();
                theme.setText(getResources().getString(R.string.theme,getResources().getString(R.string.btn_bu_th)));
                themeImg.setImageResource(R.drawable.butterfly);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_retour, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.home:
                retour();
                return true;
            case R.id.return_img:
                retour();
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            retour();
            return true;
        }
        return false;

    }

    public void retour(){
        Intent returnhome=new Intent(Param.this,Mode.class);
        startActivity(returnhome);
        finish();
    }

}
