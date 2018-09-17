package qahwakhana.liedetector;

import java.util.Random;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;



public class MainActivity extends Activity {
	
	ImageView finger_scanner, TrueFalse_light, ivTrueFalseText, scanning_bar;
	
	private Handler handler;

	private InterstitialAd interstitial;
	MediaPlayer mp;
	Animation anim, anim_up;
	int num;
	int random_true_false_counter = 0;
	
	/* array of true and false images from drawable */
	int [] array_true_false = {R.drawable.true_light, R.drawable.false_light};
	
	/* array of true and false text images from drawable */
	int [] array_true_false_text = {R.drawable.true_text, R.drawable.false_text};
	
	/* array of true false sounds */
	int [] array_sound = {R.raw.clap, R.raw.fail};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		interstitial = new InterstitialAd(this);
	    interstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
	    interstitial.loadAd(new AdRequest.Builder().build());
	    interstitial.setAdListener(new AdListener() {
	        @Override
	        public void onAdClosed() {
	            // Load the next interstitial.
	        	interstitial.loadAd(new AdRequest.Builder().build());
	        }

	    });
		
		 interstitial = new InterstitialAd(this);
		    interstitial.setAdUnitId(getString(R.string.ad_unit_id));
		    // Create ad request.
		    AdRequest adRequesti = new AdRequest.Builder().build();

		    // Begin loading your interstitial.
		    interstitial.loadAd(adRequesti);
		    interstitial.setAdListener(new AdListener() {
		         public void onAdLoaded() {
		           
		          displayInterstitial();
		           // Change the button text and enable the button.
		           
		         }
		    });
	   

		finger_scanner = (ImageView) findViewById (R.id.imageView_finger);
		scanning_bar = (ImageView) findViewById (R.id.red_line);
		TrueFalse_light = (ImageView) findViewById (R.id.light_off);
		ivTrueFalseText = (ImageView) findViewById (R.id.scan_text);
		
		
		anim = AnimationUtils.loadAnimation(this, R.anim.move);
		
		handler = new Handler();
		
		finger_scanner.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				
				/* scanning text become visible */
				ivTrueFalseText.setVisibility(View.VISIBLE);
				ivTrueFalseText.setImageResource(R.drawable.scan_text);
				

				/* change image of the finger scanner */
				finger_scanner.setImageResource(R.drawable.thumb_scanner_pressed);
				
				/* Random number generation for Random image display */
				Random number = new Random();
				 num = number.nextInt(2);
				 
				/* thread to handle the whole process */
				Thread u = new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
					
				runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						mp = MediaPlayer.create(MainActivity.this, R.raw.scanner);
						mp.start();
						
						scanning_bar.setVisibility(View.VISIBLE);
						scanning_bar.startAnimation(anim);
						 

						finger_scanner.setEnabled(false);
						
						handler.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
								if(mp != null){
									mp.stop();
									mp.release();
								}
								
								scanning_bar.setVisibility(View.INVISIBLE);
								finger_scanner.setImageResource(R.drawable.thumb_scanner_normal);
								
								}
						},6000);
						
						handler.postDelayed(new Runnable(){

							@Override
							public void run() {
								// TODO Auto-generated method stub
							
								ivTrueFalseText.setImageResource(array_true_false_text[num]);
								ivTrueFalseText.setVisibility(View.VISIBLE);
								
								TrueFalse_light.setImageResource(array_true_false[num]);
								
								mp = MediaPlayer.create(MainActivity.this, array_sound[num]);
								mp.start();
								
								mp.setOnCompletionListener(new OnCompletionListener() {
									
									public void onCompletion(MediaPlayer arg0) {
										// TODO Auto-generated method stub
										arg0.stop();
										arg0.release();
									
										TrueFalse_light.setImageResource(R.drawable.light_off);
										ivTrueFalseText.setVisibility(View.INVISIBLE);
										finger_scanner.setEnabled(true);
									}
								});
								
								
								}
						}, 6500);
					
					}
				});
				}
					
				}	
				); u.start();
			
				
				return false;
				
			}
		});
		
	
	}
	

	
	public void displayInterstitial() {
		//	Toast.makeText(this, "Normalized sized screen" , Toast.LENGTH_LONG).show();
		if (interstitial.isLoaded()) {
			// Toast.makeText(this, "Normal sized screen" , Toast.LENGTH_LONG).show();
			interstitial.show();
		}
	}
	

	public void onStop()
	{
		super.onStop();
		try{
		if(mp != null){
			mp.stop();
			mp.release();
			
		}
		
	}
	catch(Exception e){
		String tag = null;
		Log.d(tag, "onstope");
	}
	}

	public void onPause()
	{
		super.onPause();
		try{
			if(mp != null){
		
			mp.stop();
			mp.release();
		}
		}
		catch(Exception e){
			String tag = null;
			Log.d(tag, "onPause");
		}
	}
	public void onDestroy()
	{
		super.onDestroy();
		try{
		if(mp != null){
			mp.stop();
			mp.release();
			mp.reset();
					
		}
		}
		catch(Exception e){
	String tag = null;
	Log.d(tag, "onDestroy");
}
	
	
	}
	}

