package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.rubys.android.jokedisplaylibrary.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.backend.myApi.model.MyBean;

import java.io.IOException;

import static com.rubys.android.jokedisplaylibrary.JokeDisplayActivity.INTENT_JOKE;

public class EndPointsAsyncTask extends AsyncTask<Pair<Context,String>, Void, String>{

    private static final String TAG = "EndPointsAsyncTask";

    private static MyApi apiService = null;
    private Context mContext;
    private ProgressBar mProgressBar;

    private InterstitialAd interstitialAd;
    private String joke;

    public EndPointsAsyncTask(Context mContext,ProgressBar mProgressBar) {
        this.mContext = mContext;
        this.mProgressBar = mProgressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);

        interstitialAd = new InterstitialAd(mContext);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                jokeDisplayActivity();
            }
        });
    }

    @Override
    protected String doInBackground(Pair<Context, String>[] pairs) {

        if (apiService == null){
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),null)
                    .setApplicationName(String.valueOf(R.string.app_name))
                    .setRootUrl("https://builditbigger-ramji.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });;

            apiService = builder.build();
        }
        try {
            return apiService.joke().execute().getJoke();
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String s) {

        joke = s;

        if (mProgressBar != null)
            mProgressBar.setVisibility(View.INVISIBLE);

        if (interstitialAd.isLoaded()){
            interstitialAd.show();
        }else {

            jokeDisplayActivity();

            Log.i(TAG, "Joke: " + s);
        }
    }

    private void jokeDisplayActivity() {

        Intent intent = new Intent(mContext, JokeDisplayActivity.class);
        intent.putExtra(INTENT_JOKE, joke);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
