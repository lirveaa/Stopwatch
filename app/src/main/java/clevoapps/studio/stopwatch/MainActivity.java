package clevoapps.studio.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Keep running and save the state when the screen is rotated
        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    // Run timer when Start button is clicked
    public void onClickStart(View view){
        running = true;
    }

    // Stop timer when Stop button is clicked
    public void onClickStop(View view){
        running = false;
    }

    // Reset timer when Reset button is clicked
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    private void runTimer (){
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler(Looper.myLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds /3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%02d:%02d:%02d",
                        hours, minutes,secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstance){
        savedInstance.putInt("seconds", seconds);
        savedInstance.putBoolean("running", running);
        savedInstance.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning){
            running = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning){
            running = true;
        }
    }


}