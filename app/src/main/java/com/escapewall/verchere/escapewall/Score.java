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
 * Created by VERCHERE on 24/08/2017.
 */

public class Score extends AppCompatActivity {

    TextView scoreShow,commentshow,modeShow;
    ImageView picture;
    SharedPreferences preferences;//1ere ligne utile
    String mode;

    @Override
    protected void onCreate(Bundle saveInstantState) {
        super.onCreate(saveInstantState);
        mode=getIntent().getStringExtra("mode");
        setContentView(R.layout.score_layout);
        modeShow=findViewById(R.id.mode_score);
        switch (mode){
            case "BEST":
                modeShow.setText(getResources().getString(R.string.classic_mode));
                break;
            case "EASY":
                modeShow.setText(getResources().getString(R.string.easy_mode));
                break;
            case "DIFFICULT":
                modeShow.setText(getResources().getString(R.string.difficult_mode));
                break;

        }
        scoreShow=findViewById(R.id.affichage);
        commentshow=findViewById(R.id.comments);
        picture=findViewById(R.id.smile);
        preferences= PreferenceManager.getDefaultSharedPreferences(this);//init avec le contexte en parametre
        String strBest=preferences.getString(mode,"0.0");//récupération de la valeur (mode est le flag de ta mémoire)
        testScore(strBest);
        Button reset=findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=preferences.edit();//pour écrire appeler l'éditeur
                editor.putString(mode,"0.0");//"remplit" l'éditeur
                editor.apply();//écrit dans la mémoire en asynchrone
                testScore("0.0");
            }
        });
    }

    public void  testScore(String scoreTmp){
        scoreShow.setText(getResources().getString(R.string.score,scoreTmp));
        double best=Double.parseDouble(scoreTmp);
        if(best>=60.0){
            commentshow.setText(getResources().getString(R.string.state1));
            picture.setImageResource(R.drawable.king);
        }
        else if (best>=48.0){
            commentshow.setText(getResources().getString(R.string.state2));
            picture.setImageResource(R.drawable.gold);
        }
        else if (best>=36.0){
            commentshow.setText(getResources().getString(R.string.state3));
            picture.setImageResource(R.drawable.silver);
        }
        else if(best>=24.0){
            commentshow.setText(getResources().getString(R.string.state4));
            picture.setImageResource(R.drawable.bronze);
        }
        else if (best>=12.0){
            commentshow.setText(getResources().getString(R.string.state5));
            picture.setImageResource(R.drawable.chocolate);
        }
        else {
            commentshow.setText(getResources().getString(R.string.state6));
            picture.setImageResource(R.drawable.bonbon);
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
        Intent returnhome=new Intent(Score.this,Accueil.class);
        startActivity(returnhome);
        finish();
    }
}
