package ua.kharkiv.sendey;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

/*
 * Отображение заставки при старте приложения
 */
public class SplashActivity extends Activity {
    private ProgressBar pbSmall;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        
        pbSmall = (ProgressBar) findViewById(R.id.pBarSmall);
        
        new LoadViewTask().execute();
    }
    
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        /* Готовим ProgressBar */
        @Override
        protected void onPreExecute() {
            pbSmall.setMax(100);
            pbSmall.setProgress(0);
        }  
        
        /* Ждем 2 секунды (в фоновом потоке) */
        @Override  
        protected Void doInBackground(Void... params) {
            try {
                synchronized (this) {
                    int counter = 0;  
                    while(counter <= 10) {
                        this.wait(200);
                        counter++;
                        publishProgress(counter*10);
                    }  
                }  
            }  
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
        
        /* Обновляем ProgressBar */
        @Override
        protected void onProgressUpdate(Integer... values) {
        	pbSmall.setProgress(values[0]);
        }
        
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            StartMainActivity();
        }
    }
    
    private void StartMainActivity() {
       Intent intent = new Intent(SplashActivity.this, MainActivity.class);
       startActivity(intent);
       finish();
    }
}