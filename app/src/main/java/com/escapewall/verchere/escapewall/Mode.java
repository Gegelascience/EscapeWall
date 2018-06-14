package com.escapewall.verchere.escapewall;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.setting:
                Intent param=new Intent(Mode.this,Param.class);
                startActivity(param);
                finish();
                return true;
            case R.id.bscore:
                Intent score=new Intent(Mode.this,Score.class);
                score.putExtra("mode","BEST");
                startActivity(score);
                finish();
                return true;
            case R.id.bscore_easy:
                Intent scoreEasy=new Intent(Mode.this,Score.class);
                scoreEasy.putExtra("mode","EASY");
                startActivity(scoreEasy);
                finish();
                return true;
            case R.id.bscore_difficult:
                Intent scoreDifficult=new Intent(Mode.this,Score.class);
                scoreDifficult.putExtra("mode","DIFFICULT");
                startActivity(scoreDifficult);
                finish();
                return true;
            case R.id.about:
                AlertDialog.Builder about = new AlertDialog.Builder(Mode.this,R.style.DialogTheme);
                about.setCancelable(false);
                about.setTitle(getResources().getString(R.string.about));
                about.setMessage(getResources().getString(R.string.info));
                about.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Action for "OK"
                    }
                })
                ;
                final AlertDialog alertAbout = about.create();
                alertAbout.show();
                return true;


        }

        return super.onOptionsItemSelected(item);

    }

}
