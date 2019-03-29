package anandniketan.com.skool360teacher.AsyncTasks;

import android.os.AsyncTask;


import java.util.ArrayList;
import java.util.HashMap;

import anandniketan.com.skool360teacher.Models.HomeWorkResponse.HomeworkModel;
import anandniketan.com.skool360teacher.Utility.AppConfiguration;
import anandniketan.com.skool360teacher.Utility.ParseJSON;
import anandniketan.com.skool360teacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class GetTeacherDailyWorkAsyncTask extends AsyncTask<Void, Void, ArrayList<HomeworkModel>> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetTeacherDailyWorkAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<HomeworkModel> doInBackground(Void... params) {
        String responseString = null;
        ArrayList<HomeworkModel> result = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetTeacherDailyWork), param);
            result = ParseJSON.parseTeacherHomeworkJson(responseString);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<HomeworkModel> result) {
        super.onPostExecute(result);
    }
}