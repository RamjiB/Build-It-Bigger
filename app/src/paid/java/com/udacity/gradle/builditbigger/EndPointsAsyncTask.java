package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.rubys.android.jokedisplaylibrary.JokeDisplayActivity;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.backend.myApi.model.MyBean;

import java.io.IOException;

import static com.rubys.android.jokedisplaylibrary.JokeDisplayActivity.INTENT_JOKE;

@SuppressLint("StaticFieldLeak")
class EndPointsAsyncTask extends AsyncTask<Pair<Context,String>, Void, String>{

    private static final String TAG = "EndPointsAsyncTask";

    private static MyApi apiService = null;
    private final Context mContext;
    private final ProgressBar mProgressBar;

    public EndPointsAsyncTask(Context mContext,ProgressBar mProgressBar) {
        this.mContext = mContext;
        this.mProgressBar = mProgressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);
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
                    });

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

        if (mProgressBar != null)
            mProgressBar.setVisibility(View.INVISIBLE);


        Log.i(TAG,"Joke: "+ s);
        Intent intent = new Intent(mContext, JokeDisplayActivity.class);
        intent.putExtra(INTENT_JOKE,s);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }
}
