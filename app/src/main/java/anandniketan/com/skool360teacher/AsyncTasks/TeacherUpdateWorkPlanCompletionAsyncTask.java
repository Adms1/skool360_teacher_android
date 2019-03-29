package anandniketan.com.skool360teacher.AsyncTasks;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.HashMap;

import anandniketan.com.skool360teacher.Models.WorkPlanResponse.UpdateWorkStatusModel;
import anandniketan.com.skool360teacher.Utility.AppConfiguration;
import anandniketan.com.skool360teacher.WebServicesCall.WebServicesCall;

/**
 * Created by admsandroid on 11/13/2017.
 */

public class TeacherUpdateWorkPlanCompletionAsyncTask  extends AsyncTask<Void, Void,UpdateWorkStatusModel> {
    HashMap<String, String> param = new HashMap<String, String>();

    public TeacherUpdateWorkPlanCompletionAsyncTask(HashMap<String, String> param) {
        this.param = param;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected UpdateWorkStatusModel doInBackground(Void... params) {
        String responseString = null;
        UpdateWorkStatusModel updateWorkStatusModel =null;
        try {
            responseString = WebServicesCall.RunScript(AppConfiguration.getUrl(AppConfiguration.TeacherUpdateWorkPlanCompletion), param);
            Gson gson = new Gson();
            updateWorkStatusModel = gson.fromJson(responseString, UpdateWorkStatusModel.class);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return updateWorkStatusModel;
    }

    @Override
    protected void onPostExecute(UpdateWorkStatusModel result) {
        super.onPostExecute(result);
    }
}
