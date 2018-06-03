package com.escapewall.verchere.escapewall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by VERCHERE on 11/09/2017.
 */

public class Mode extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle saveInstantState){
        super.onCreate(saveInstantState);
        setContentView(R.layout.modes_layout);
        Button inf=findViewById(R.id.infinity);
        inf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game=new Intent(Mode.this,Game.class);
                game.putExtra("mode","BEST");
                startActivity(game);
                finish();
            }
        });
        Button eas=findViewById(R.id.easy);
        eas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameEasy=new Intent(Mode.this,Game.class);
                gameEasy.putExtra("mode","EASY");
                startActivity(gameEasy);
                finish();
            }
        });
        Button diffi=findViewById(R.id.difficult);
        diffi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameDiff=new Intent(Mode.this,Game.class);
                gameDiff.putExtra("mode","DIFFICULT");
                startActivity(gameDiff);
                finish();
            }
        });
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
        Intent returnhome=new Intent(Mode.this,Accueil.class);
        startActivity(returnhome);
        finish();
    }
}
