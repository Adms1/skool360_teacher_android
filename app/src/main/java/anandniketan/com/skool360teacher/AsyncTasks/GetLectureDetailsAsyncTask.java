package anandniketan.com.skool360teacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.skool360teacher.Models.TimeTable.GetTimeTableSubjectModel;
import anandniketan.com.skool360teacher.Utility.AppConfiguration;
import anandniketan.com.skool360teacher.WebServicesCall.WebServicesCall;

public class GetLectureDetailsAsyncTask extends AsyncTask<Void, Void, GetTimeTableSubjectModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetLectureDetailsAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected GetTimeTableSubjectModel doInBackground(Void... params) {
        String responseString = null;
        GetTimeTableSubjectModel timeTableSubjectModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetLectureDetails), param);
            Gson gson = new Gson();
            timeTableSubjectModel = gson.fromJson(responseString, GetTimeTableSubjectModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return timeTableSubjectModel;
    }

    @Override
    protected void onPostExecute(GetTimeTableSubjectModel result) {
        super.onPostExecute(result);
    }
}
