package anandniketan.com.skool360teacher.Utility;

import java.util.Map;

import anandniketan.com.skool360teacher.Models.NewResponse.MainResponse;
import anandniketan.com.skool360teacher.Models.TermModel;
import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by admsandroid on 11/20/2017.
 */

public interface WebServices {

    @FormUrlEncoded
    @POST("/TeacherGetTestMarks")
    public void GetTeacherTestMarks(@FieldMap Map<String,String> map,Callback<MainResponse> callback);


    @FormUrlEncoded
    @POST("/GetTerm")
    public void getTerm(@FieldMap Map<String, String> map, Callback<TermModel> callback);




}
