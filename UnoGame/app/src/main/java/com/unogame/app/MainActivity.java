package com.unogame.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Debug;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    Random rand = new Random();
    int randNum = rand.nextInt(36);
    int cardsInHand = 0;
    int whoseTurn = 0;
    public ArrayList<Integer> ids = new ArrayList<Integer>();
    List<String> allCardNames = new ArrayList<>();
    List<String> compHand = new ArrayList<>();
    public static final String MESSAGE="com.unogame.app.extra.MESSAGE";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=1;i<35;i++){
            allCardNames.add("blue"+i);
            allCardNames.add("green"+i);
            allCardNames.add("red"+i);
            allCardNames.add("yellow"+i);
        }

        generateIntIDs(ids);

        generateHand();

        generateCompHand();

        ImageView topCard = findViewById(R.id.topCard);

        topCard.setImageResource(ids.get(randNum));
        String topName = topCard.getResources().getResourceEntryName(ids.get(randNum));
        topCard.setTag(topName);

        final LinearLayout linLay = findViewById(R.id.linearLayout);
        final Button drawBtn = findViewById(R.id.drawBtn);
        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(whoseTurn == 0) {
                    addRandomCard();  // if it is player's turn
                    String cardsHand = Integer.toString(cardsInHand);
                    Log.d("CARDSINPLAYERHAND", cardsHand);
                    whoseTurn = 1;
                }
                delay(linLay,drawBtn);
            }
        });


    }

    public void generateHand(){
        addRandomCard();
        addRandomCard();
        addRandomCard();
        addRandomCard();
        addRandomCard();
        addRandomCard();
        addRandomCard();

    }

    public void whenClick(View v){
        final LinearLayout linLay = findViewById(R.id.linearLayout);
        Object obj = v.getTag();
        String idStr = obj.toString();
        String handColor = getColor(idStr);
        int handNumber = getNumber(idStr);

        int vId = getResources().getIdentifier(idStr, "drawable", getPackageName());

        ImageView topCard = findViewById(R.id.topCard);

        Object obj1 = topCard.getTag();
        String idTopCard =obj1.toString();

        //int winId = getResources().getIdentifier("winscreen", "drawable", getPackageName());
        final Button drawBtn = findViewById(R.id.drawBtn);

        String topCardColor = getColor(idTopCard);
        int topCardNumber = getNumber(idTopCard);


        if(handColor.equals(topCardColor) ||handNumber == topCardNumber){
            Intent intent = new Intent(this,EndActivity.class);
            topCard.setImageResource(vId);
            topCard.setTag(idStr);
            linLay.removeView(v);
            cardsInHand--;
            String cardsHand = Integer.toString(cardsInHand);
            Log.d("CARDSINPLAYERHAND", cardsHand);
            if(cardsInHand == 0){
                intent.putExtra(MESSAGE,"YOU WIN!!!");
                startActivity(intent);
                //topCard.setImageResource(winId);
            }
            //drawBtn.setEnabled(false); //stops from clicking drawBtn when not their turn
            whoseTurn = 1;

            delay(linLay,drawBtn);
/*
            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    drawBtn.setEnabled(false);
                    for(int i=0;i<linLay.getChildCount();i++){
                        ImageView iv = (ImageView) linLay.getChildAt(i);
                        iv.setClickable(false);
                    }
                }
                public void onFinish() {
                    // When timer is finished
                    // Execute your code here
                    computerTurn();
                    drawBtn.setEnabled(true);
                    for(int i=0;i<linLay.getChildCount();i++){
                        ImageView iv = (ImageView) linLay.getChildAt(i);
                        iv.setClickable(true);
                    }
                }
            }.start();
 */




        }

    }

    public void delay(final LinearLayout linLay, final Button drawBtn){

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                drawBtn.setEnabled(false);
                for(int i=0;i<linLay.getChildCount();i++){
                    ImageView iv = (ImageView) linLay.getChildAt(i);
                    iv.setClickable(false);
                }
            }
            public void onFinish() {
                // When timer is finished
                // Execute your code here
                computerTurn();
                drawBtn.setEnabled(true);
                for(int i=0;i<linLay.getChildCount();i++){
                    ImageView iv = (ImageView) linLay.getChildAt(i);
                    iv.setClickable(true);
                }
            }
        }.start();
    }
    public void computerTurn(){
        //whoseTurn = 1;
        printComputerHand();
        LinearLayout compLayout = findViewById(R.id.compLayout);
        ImageView topCard = findViewById(R.id.topCard);
        Object obj = topCard.getTag();
        String idTopCard =obj.toString();

        Intent intent = new Intent(this,EndActivity.class);

        String topCardColor = getColor(idTopCard);
        int topCardNumber = getNumber(idTopCard);
        Log.d("ID top card", idTopCard);
        //int loseId = getResources().getIdentifier("losescreen", "drawable", getPackageName());
        for(int i=0;i<compHand.size();i++){
            if(getColor(compHand.get(i)).equals(topCardColor)||getNumber(compHand.get(i)) == topCardNumber){
                int compCard = getResources().getIdentifier(getColor(compHand.get(i))+getNumber(compHand.get(i)),
                        "drawable", getPackageName());
                Log.d("VALIDCOMPCARD", getColor(compHand.get(i))+getNumber(compHand.get(i)));
                topCard.setImageResource(compCard);
                topCard.setTag(getColor(compHand.get(i))+getNumber(compHand.get(i)));
                compHand.remove(i);
                ImageView imageView = (ImageView) compLayout.getChildAt(0);
                compLayout.removeView(imageView);
                String str = Integer.toString(compHand.size());
                Log.d("CARDS IN COMPUTER HAND", str);
                if(compHand.size() == 0){
                    intent.putExtra(MESSAGE,"YOU LOSE!!!");
                    startActivity(intent);
                    //topCard.setImageResource(loseId);
                }
                whoseTurn = 0;
                break;
            }
        }
        if(whoseTurn == 1) {
            addCompRandomCard();
            String str = Integer.toString(compHand.size());
            whoseTurn = 0;
            Log.d("COMPUTER DREW", "computer drew a card");
            Log.d("CARDS IN COMPUTER HAND", str);
        }
    }
    public void generateCompHand(){
        addCompRandomCard();
        addCompRandomCard();
        addCompRandomCard();
        addCompRandomCard();
        addCompRandomCard();
        addCompRandomCard();
        addCompRandomCard();
    }

    public void printComputerHand(){
        for(int i=0;i<compHand.size();i++){
            Log.d("COMPUTER HAND:", compHand.get(i));
        }
    }

    public void addCompRandomCard(){
        Random rand = new Random();
        int randNum = rand.nextInt(36);
        compHand.add(allCardNames.get(randNum));
        int imageRes = getResources().getIdentifier("unoback", "drawable", getPackageName());
        final ImageView unoBack = new ImageView(this);
        unoBack.setImageResource(imageRes);
        LinearLayout linearLayout = findViewById(R.id.compLayout);
        linearLayout.addView(unoBack);
        unoBack.getLayoutParams().height = 230;
        unoBack.getLayoutParams().width = 83*2;
    }

    public void addRandomCard(){
        Random rand = new Random();
        int randNum = rand.nextInt(36);


        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(ids.get(randNum));

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setGravity(Gravity.BOTTOM);
        linearLayout.addView(imageView);
        imageView.getLayoutParams().height = 230;
        imageView.getLayoutParams().width = 83*2;

        String id = imageView.getResources().getResourceEntryName(ids.get(randNum));

        imageView.setTag(id);

        Object obj = imageView.getTag();

        String idStr = obj.toString();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whenClick(imageView);
            }
        });
        Log.d("YOUDREWCARD", idStr);
        cardsInHand++;
    }

    public String getColor(String id){
        int type = id.length();
        switch(type){
            case(6):
                return id.substring(0,5);
            case(5):
                return id.substring(0,4);
            case(7):
                return id.substring(0,6);
            case(4):
                return id.substring(0,3);
            default:return "none";
        }
    }
    public int getNumber(String id){
        String ret = id.substring(id.length() - 1);
        int returnNum = Integer.parseInt(ret);
        return returnNum;

    }

    public void generateIntIDs(ArrayList<Integer> ids) {
        int imageRes;
        List<String> color = new ArrayList<String>();
        color.add("blue");
        color.add("green");
        color.add("red");
        color.add("yellow");
        for (int i = 0; i < color.size(); i++) {
            for (int j = 1; j <= 9; j++) {
                imageRes = getResources().getIdentifier(color.get(i) + j, "drawable", getPackageName());
                ids.add(imageRes);

            }
        }
    }

}