package com.example.project;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.List;

public class ConnectionAsyncTask extends AsyncTask<String, String, String> {
    Activity activity;
    DatabaseHelper dbHelper;

    public ConnectionAsyncTask(Activity activity, DatabaseHelper dbHelper) {
        this.activity = activity;
        this.dbHelper = dbHelper;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setProgress(true);
        } else if (activity instanceof LoginActivity) {
            ((LoginActivity) activity).setProgress(true);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        return HttpManager.getData(params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (activity instanceof MainActivity) {
            ((MainActivity) activity).setProgress(false);
        } else if (activity instanceof LoginActivity) {
            ((LoginActivity) activity).setProgress(false);
        }

        if (s == null) {
            // If data is null, redirect to the FailedLoginActivity
            Intent intent = new Intent(activity, FailedLoginActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } else {
            List<Flight> flights = PlaneJsonParser.getObjectFromJson(s);
            if (flights == null) {
                // parsing errors
                Intent intent = new Intent(activity, FailedLoginActivity.class);
                activity.startActivity(intent);
                activity.finish();
            } else {
                // Insert flights into the database
                for (Flight flight : flights) {
                    dbHelper.insertFlight(flight);
                }
//                ((MainActivity) activity).fillFlights(flights);
            }
        }
    }
}
