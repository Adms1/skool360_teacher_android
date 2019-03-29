package anandniketan.com.skool360teacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.skool360teacher.Models.UserProfileModel;
import anandniketan.com.skool360teacher.Utility.AppConfiguration;
import anandniketan.com.skool360teacher.WebServicesCall.WebServicesCall;

public class StaffChangePasswordAsyncTask extends AsyncTask<Void, Void, UserProfileModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public StaffChangePasswordAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected UserProfileModel doInBackground(Void... params) {
        String responseString = null;
        UserProfileModel userProfileModel = null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.StaffChangePassword), param);
            Gson gson = new Gson();
            userProfileModel = gson.fromJson(responseString, UserProfileModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return userProfileModel;
    }

    @Override
    protected void onPostExecute(UserProfileModel result) {
        super.onPostExecute(result);
    }
}
