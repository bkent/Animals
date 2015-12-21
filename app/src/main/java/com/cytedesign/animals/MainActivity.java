package com.cytedesign.animals;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	int i;
	View view;
	private static final int SWIPE_MIN_DISTANCE = 120;
//    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 50;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    TextView tv;
    ImageView iv;
    String[] arrayOfAnimals = {"lion","cow","duck","elephant","frog","horse","ladybird","owl","pig","sheep"};
    Button bSimple, bAdvanced, bChoice1, bChoice2, bChoice3;
    Boolean choicesEnabled;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		view = this.getWindow().getDecorView();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		i = 0;
		
		bSimple = (Button) findViewById(R.id.bSimpleGame);
		bSimple.setOnClickListener(bSimpleListener);
	    bAdvanced = (Button) findViewById(R.id.bAdvancedGame);
	    bAdvanced.setOnClickListener(bAdvancedListener);
	    bChoice1 = (Button) findViewById(R.id.bChoice1);
	    bChoice1.setOnClickListener(bChoice1Listener);
	    bChoice1.setVisibility(View.INVISIBLE);
	    bChoice2 = (Button) findViewById(R.id.bChoice2);
	    bChoice2.setOnClickListener(bChoice2Listener);
	    bChoice2.setVisibility(View.INVISIBLE);
	    bChoice3 = (Button) findViewById(R.id.bChoice3);
	    bChoice3.setOnClickListener(bChoice3Listener);
	    bChoice3.setVisibility(View.INVISIBLE);
	    
	    iv = (ImageView)findViewById(R.id.imageView);
	    iv.setVisibility(View.INVISIBLE);
	    
	    tv = (TextView)findViewById(R.id.textView);
	    tv.setVisibility(View.INVISIBLE);
		
	    gestureDetector = new GestureDetector(this, new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };
	}

	public void enableSwipes(){
		view.setOnTouchListener(gestureListener);
	}
	
    public OnClickListener bSimpleListener = new OnClickListener() {
        public void onClick(View v) {
        	enableSwipes();
        	bSimple.setVisibility(View.INVISIBLE);
        	bAdvanced.setVisibility(View.INVISIBLE);
        	iv.setVisibility(View.VISIBLE);    	
        	tv.setVisibility(View.VISIBLE);
        }
    };
    
    public OnClickListener bAdvancedListener = new OnClickListener() {
        public void onClick(View v) {
        	bSimple.setVisibility(View.INVISIBLE);
        	bAdvanced.setVisibility(View.INVISIBLE);
        	iv.setVisibility(View.VISIBLE);
        	bChoice1.setVisibility(View.VISIBLE);
        	bChoice2.setVisibility(View.VISIBLE);
        	bChoice3.setVisibility(View.VISIBLE);
        	choicesEnabled = true;
        	setMultipleChoices();
        }
    };
    
    public OnClickListener bChoice1Listener = new OnClickListener() {
        public void onClick(View v) {
        	String buttonStr = (String) (bChoice1.getText());

        	if(arrayOfAnimals[i].equalsIgnoreCase(buttonStr))
        		changeAnimal(1);
        	else{ 
        		bChoice1.setEnabled(false);
        		bChoice1.setText("Try again!");
        	}
        }
        	
    };
    
    public OnClickListener bChoice2Listener = new OnClickListener() {
        public void onClick(View v) {
        	String buttonStr = (String)(bChoice2.getText());

        	if(arrayOfAnimals[i].equalsIgnoreCase(buttonStr))
        		changeAnimal(1);
        	else{ 
        		bChoice2.setEnabled(false);
        		bChoice2.setText("Try again!");
        	}
        }
    };
    
    public OnClickListener bChoice3Listener = new OnClickListener() {
        public void onClick(View v) {
        	String buttonStr = (String) (bChoice3.getText());

        		if(arrayOfAnimals[i].equalsIgnoreCase(buttonStr))
            		changeAnimal(1);
        		else{ 
            		bChoice3.setEnabled(false);
            		bChoice3.setText("Try again!");
        		}
        }

    };
	
    public void setMultipleChoices(){
    	int correct, wrong1, wrong2, resStrC, resStrW1, resStrW2;
    	//assign the currently pictured animal to a random button (1,2,3)
    	//then assign two other random wrong choices to the remaining 2 buttons
    	correct = (int) (Math.random() * 3);
    	wrong1 = (int) (Math.random() * arrayOfAnimals.length);
    	wrong2 = (int) (Math.random() * arrayOfAnimals.length);
    	
    	bChoice1.setEnabled(true);
    	bChoice2.setEnabled(true);
    	bChoice3.setEnabled(true);
    	
    	//check the randomly selected wrong answers is not the correct one
    	while(true){
    	if (i == wrong1)
    		wrong1 = (int) (Math.random() * arrayOfAnimals.length);
    	else 
    		break;
    	}
    	
    	while(true){
    	if ((i == wrong2)||(wrong1 == wrong2))
    		wrong2 = (int) (Math.random() * arrayOfAnimals.length);
    	else
    		break;
    	}
    	
    	resStrC = getResources().getIdentifier(arrayOfAnimals[i], "string", "com.cytedesign.animals");
    	resStrW1 = getResources().getIdentifier(arrayOfAnimals[wrong1], "string", "com.cytedesign.animals");
    	resStrW2 = getResources().getIdentifier(arrayOfAnimals[wrong2], "string", "com.cytedesign.animals");
    	
    	switch(correct){
    	case 0:
    		bChoice1.setText(resStrC);
    		bChoice2.setText(resStrW1);
    		bChoice3.setText(resStrW2);	
    	break;
    	case 1:
    		bChoice1.setText(resStrW1);
    		bChoice2.setText(resStrC);
    		bChoice3.setText(resStrW2);	
    	break;
    	case 2:
    		bChoice1.setText(resStrW1);
    		bChoice2.setText(resStrW2);
    		bChoice3.setText(resStrC);	
    	break;
    	}
    	
    }
    
    public void checkIfChoiceCorrect(int j){
    	//check if the text of the pressed button is equal to the currently selected animal
    	
    	//if correct, congratulate the user, and then change choice1 to < Previous and choice2 to Next >
    	
    	//if wrong, disable that button
    	
    }
    
	public void changeAnimal(int j){
		int resImgID;
		int resStrID;
		
		if (j == 1)
			i++;
		else
			i--;
		
		if (i < 0)
			i = 9;
		if(i > 9)
			i = 0;

		resImgID = getResources().getIdentifier(arrayOfAnimals[i], "drawable", "com.cytedesign.animals");
		resStrID = getResources().getIdentifier(arrayOfAnimals[i], "string", "com.cytedesign.animals");
		iv.setImageResource(resImgID);
		tv.setText(resStrID);
		setMultipleChoices();
	}
	
	class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
//                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
//                    return false;
            	if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) 
                	changeAnimal(0);
                  else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY)
                	changeAnimal(1);
            	
                if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
                	changeAnimal(1);
                  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)
                	changeAnimal(0);
            } catch (Exception e) {
                // nothing
            }
            return false;
        }
	}
	
	@Override
	 public boolean onKeyDown(int keyCode, KeyEvent event)  {
	     if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
	      	 bSimple.setVisibility(View.VISIBLE);
	 	     bAdvanced.setVisibility(View.VISIBLE);
	 	     bChoice1.setVisibility(View.INVISIBLE);
	 	     bChoice2.setVisibility(View.INVISIBLE);
	 	     bChoice3.setVisibility(View.INVISIBLE);
	 	     iv.setVisibility(View.INVISIBLE);    	
        	 tv.setVisibility(View.INVISIBLE);
        	 view.setOnTouchListener(null);
	         return true;
	     }

	     return super.onKeyDown(keyCode, event);
	 }
}
