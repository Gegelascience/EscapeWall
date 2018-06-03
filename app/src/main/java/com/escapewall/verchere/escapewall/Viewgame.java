package com.escapewall.verchere.escapewall;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VERCHERE on 21/07/2017.
 */

public class Viewgame extends View {

    public float x,y,rayon,dimWall;
    public double ystart;
    public List<Mur> walls=new ArrayList<>(11);
    public boolean first;
    public double timeWin=0;
    double precision=0.01;
    String theme;

    public Viewgame(Context context){
        super(context);
        first=true;
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(context);
        theme=pref.getString("theme","classic");
        ystart=200;
        for(int i=0;i<11;i++){
            walls.add(new Mur(i*50,ystart));
        }
    }

    public void onDraw(Canvas canvas){
        canvas.drawRGB(0,0,0);
        if(first){
            first=false;
            x=canvas.getWidth()/2;
            y=canvas.getHeight()/2;
            dimWall=canvas.getWidth()/11;
            rayon=(dimWall-10);
            ystart=y-(4*(dimWall-10));
            for(int i=0;i<11;i++){
                walls.get(i).x=i*dimWall;
                walls.get(i).y=ystart;
                walls.get(i).dim=dimWall-10;
            }

        }
        Paint P=new Paint();
        P.setARGB(255,255,255,255);
        P.setTextSize(40);
        timeWin=Math.round( timeWin * (1.0/precision) ) / (1.0/precision);
        canvas.drawText(getResources().getString(R.string.tmp_game,String.valueOf(timeWin)),20,60,P);

        //dessin du module
        Rect dstmod=new Rect((int)(x),(int)(y),(int)(x+rayon),(int)(y+rayon));
        Bitmap mod;
        if (theme.equals("butterfly")){
            mod=BitmapFactory.decodeResource(getResources(),R.drawable.butterfly);
        }
        else {
            mod = BitmapFactory.decodeResource(getResources(), R.drawable.module);
        }
        canvas.drawBitmap(mod,null,dstmod,null);

        //dessin des murs
        Bitmap mur;
        if (theme.equals("butterfly")){
            mur=BitmapFactory.decodeResource(getResources(),R.drawable.flower);
        }
        else {
            mur = BitmapFactory.decodeResource(getResources(), R.drawable.murtest);
        }
        for (int i=0;i<11;i++){
            Rect dst=new Rect((int)walls.get(i).x,(int)walls.get(i).y,(int)(walls.get(i).x+walls.get(i).dim),(int)(walls.get(i).y+walls.get(i).dim));
            canvas.drawBitmap(mur,null,dst,null);
            dst=null;
        }


    }

    protected class Mur{
        float x;
        double y;
        float dim;
        boolean depart;

        public Mur(float w,double h){
            x=w;
            y=h;
            dim=40;
            depart=true;
        }

        public void setDepart(boolean de){
            depart=de;
        }
    }
}
