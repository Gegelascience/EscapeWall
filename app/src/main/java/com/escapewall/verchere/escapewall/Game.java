package com.escapewall.verchere.escapewall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;

public class Game extends AppCompatActivity implements SensorEventListener {

    Viewgame myview;
    long starttime,startscore;
    Sensor s;
    SensorManager sm;
    int tau=1000;
    int untilWin=10;//80
    double delta=1;
    boolean isEnd=false;
    String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode=getIntent().getStringExtra("mode");
        if (mode.equals("DIFFICULT")){
            tau=500;
            delta=0.5;
        }
        myview=new Viewgame(this);
        setContentView(myview);
        sm=(SensorManager)getSystemService(SENSOR_SERVICE);
        s=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        starttime=System.currentTimeMillis();
        startscore=starttime;
    }

    public void onSensorChanged(SensorEvent e){
        if (!isEnd){
            myview.x-=e.values[0];
        }
        long timeout=System.currentTimeMillis()-starttime;
        long timescore=System.currentTimeMillis()-startscore;

        //gestion des collisions dimensions phone
        if(myview.x<=0){
            myview.x=0;
        }
        if(myview.x+myview.rayon>=myview.getWidth()){
         myview.x=myview.getWidth()-myview.rayon;
        }

        //gestion score
        if(timescore>=100){
            if (!isEnd){
                myview.timeWin=myview.timeWin+0.1;
                startscore=timescore+startscore;
            }
        }
        //gestion murs
        if(timeout>=tau) {
            if(isEnd){
                myview.timeWin=myview.timeWin+0.1;
                lose(mode);
            }
            starttime = timeout + starttime;
            //gestion vitese des murs
            if(untilWin!=0){
                untilWin--;
            }
            else{
                if(tau==100){
                    untilWin=90;
                }else if (tau==500){
                    if (mode.equals("EASY")){
                        untilWin=90;
                    }else {
                        untilWin=10;
                        tau=tau-100;
                        delta=delta-0.1;
                    }
                }else {
                    untilWin=10;
                    tau=tau-100;
                    delta=delta-0.1;
                }
            }

            Random r = new Random();
            int t = r.nextInt(11);
            for (int i = 0; i < 11; i++) {
                double yrect = myview.walls.get(i).y;

                //activer la descente des murs
                if (i == t & myview.walls.get(t).depart) {
                    myview.walls.get(t).setDepart(false);
                }
                //descente des murs
                if (!myview.walls.get(i).depart) {
                    if (yrect == myview.ystart + 8*(myview.dimWall-10)) {
                        yrect = myview.ystart;
                        myview.walls.get(i).setDepart(true);
                    } else {
                        yrect = yrect + (myview.dimWall-10);
                    }
                    myview.walls.get(i).y=yrect;
                }

            }
        }

        //collision mur/module
        for (int i = 0; i < 11; i++) {
                float cote = myview.walls.get(i).dim;
                float xrect = myview.walls.get(i).x;
                double yrect = myview.walls.get(i).y;

                if(xrect>myview.x & xrect<(myview.x+myview.rayon)){
                    if(yrect>=myview.y & yrect<myview.y+myview.rayon){
                        isEnd=true;
                    }
                    else if(yrect+cote>myview.y & yrect+cote<myview.y+myview.rayon){
                        isEnd=true;

                    }
                }
                else if(xrect+cote>myview.x & xrect+cote<(myview.x+myview.rayon)){
                    if(yrect>=myview.y & yrect<myview.y+myview.rayon){
                        isEnd=true;
                    }
                    else if(yrect+cote>myview.y & yrect+cote<myview.y+myview.rayon){
                        isEnd=true;
                    }
                }

        }
        myview.invalidate();
    }


    public void onAccuracyChanged(Sensor sens,int accuracy){

    }


    public void lose(String modeloc){
        sm.unregisterListener(this, s);
        setContentView(R.layout.activity_main);
        //affichage score
        TextView score=findViewById(R.id.score);
        double nbscore=Math.round(myview.timeWin * (1.0/myview.precision) ) / (1.0/myview.precision);
        score.setText(getResources().getString(R.string.result,String.valueOf(nbscore)));
        //test meilleur score
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(this);
        String strBest=preferences.getString(modeloc,"0.0");
        double best=Double.parseDouble(strBest);
        best=Math.round(Math.max(best,nbscore)*(1.0/myview.precision) ) / (1.0/myview.precision);
        //sauvegarde best score
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString(modeloc,String.valueOf(best));
        editor.apply();
        //affichage score
        TextView besttxt=findViewById(R.id.best);
        //besttxt.setText("best : "+best+"s");
        besttxt.setText(getResources().getString(R.string.best,String.valueOf(best)));


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
                home();
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
        Intent retourmode=new Intent(Game.this,Mode.class);
        startActivity(retourmode);
        finish();
    }

    public void home(){
        Intent returnhome=new Intent(Game.this,Accueil.class);
        startActivity(returnhome);
        finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (!isEnd) {
            sm.unregisterListener(this, s);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (!isEnd) {
            sm.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }
}


