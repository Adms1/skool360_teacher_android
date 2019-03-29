package anandniketan.com.skool360teacher.Models.HomeWorkResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admsandroid on 11/8/2017.
 */

public class HomeworkStatusInsertUpdateModel {
    @SerializedName("Success")
    @Expose
    private String success;
    @SerializedName("FinalArray")
    @Expose
    private List<Object> finalArray = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<Object> getFinalArray() {
        return finalArray;
    }

    public void setFinalArray(List<Object> finalArray) {
        this.finalArray = finalArray;
    }
}
