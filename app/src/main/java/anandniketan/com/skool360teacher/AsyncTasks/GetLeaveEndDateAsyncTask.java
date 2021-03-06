package anandniketan.com.skool360teacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.skool360teacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.skool360teacher.Utility.AppConfiguration;
import anandniketan.com.skool360teacher.WebServicesCall.WebServicesCall;

public class GetLeaveEndDateAsyncTask extends AsyncTask<Void, Void, LeaveMainModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public GetLeaveEndDateAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LeaveMainModel doInBackground(Void... params) {
        String responseString = null;
        LeaveMainModel getLeaveHead = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.GetLeaveEndDate), param);
            Gson gson = new Gson();
            getLeaveHead = gson.fromJson(responseString, LeaveMainModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return getLeaveHead;
    }

    @Override
    protected void onPostExecute(LeaveMainModel result) {
        super.onPostExecute(result);
    }
}


