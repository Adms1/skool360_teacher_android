package anandniketan.com.skool360teacher.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import anandniketan.com.skool360teacher.Models.Attendance.StaffNewAttendenceModel;
import anandniketan.com.skool360teacher.Models.Attendance.StudentDetailAttedance;
import anandniketan.com.skool360teacher.R;
import de.hdodenhof.circleimageview.CircleImageView;


public class AttendanceListAdapter extends BaseAdapter {
    private Context mContext;
    private StaffNewAttendenceModel staffNewAttendenceModel;
    ImageLoader imageLoader;

    // Constructor
    public AttendanceListAdapter(Context c, StaffNewAttendenceModel staffNewAttendenceModel) {
        mContext = c;
        this.staffNewAttendenceModel = staffNewAttendenceModel;

    }

    private class ViewHolder {
        CircleImageView profile_image;
        TextView student_name_txt;
        RadioGroup attendance_group;
        //        CheckBox present_chk, absent_chk, leave_chk;
        RadioButton present_chk, absent_chk, leave_chk;

    }

    @Override
    public int getCount() {
        return staffNewAttendenceModel.getFinalArray().get(0).getStudentDetail().size();
    }

    @Override
    public Object getItem(int position) {
        return staffNewAttendenceModel.getFinalArray().get(0).getStudentDetail().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        View view = null;
        convertView = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_row_student_attendance, null);
            viewHolder.profile_image = (CircleImageView) convertView.findViewById(R.id.profile_image);
            viewHolder.student_name_txt = (TextView) convertView.findViewById(R.id.student_name_txt);
            viewHolder.present_chk = (RadioButton) convertView.findViewById(R.id.present_chk);
            viewHolder.absent_chk = (RadioButton) convertView.findViewById(R.id.absent_chk);
            viewHolder.leave_chk = (RadioButton) convertView.findViewById(R.id.leave_chk);
            viewHolder.attendance_group = (RadioGroup) convertView.findViewById(R.id.attendance_group);

            final StudentDetailAttedance detail = staffNewAttendenceModel.getFinalArray().get(0).getStudentDetail().get(position);
            try {

                if(detail.getRowenable().equalsIgnoreCase("true")){
                    viewHolder.present_chk.setClickable(true);
                    viewHolder.absent_chk.setClickable(true);
                    viewHolder.leave_chk.setClickable(true);
                }else {
                    viewHolder.present_chk.setClickable(false);
                    viewHolder.absent_chk.setClickable(false);
                    viewHolder.leave_chk.setClickable(false);
                }

//                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                        .cacheInMemory(true)
//                        .cacheOnDisk(true)
//                        .imageScaleType(ImageScaleType.EXACTLY)
//                        .displayer(new FadeInBitmapDisplayer(300))
//                        .build();
//                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                        mContext)
//                        .threadPriority(Thread.MAX_PRIORITY)
//                        .defaultDisplayImageOptions(defaultOptions)
//                        .memoryCache(new WeakMemoryCache())
//                        .denyCacheImageMultipleSizesInMemory()
//                        .tasksProcessingOrder(QueueProcessingType.LIFO)// .enableLogging()
//                        .build();
//                imageLoader.init(config.createDefault(mContext));
//                imageLoader.displayImage(detail.getStudentImage(), viewHolder.profile_image);

                if(!TextUtils.isEmpty(detail.getStudentImage())) {
                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.placeholder(R.drawable.profile_pic_holder);
                    requestOptions.error(R.drawable.profile_pic_holder);
                    Glide.with(mContext).setDefaultRequestOptions(requestOptions).load(detail.getStudentImage()).into(viewHolder.profile_image);
                }else{
                    Glide.with(mContext).load(R.drawable.profile_pic_holder).into(viewHolder.profile_image);

                }

                viewHolder.student_name_txt.setText(detail.getStudentName().trim());


                viewHolder.attendance_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        RadioButton rb = (RadioButton) group.findViewById(checkedId);
                        if (null != rb && checkedId > -1) {
                            // checkedId is the RadioButton selected
                            switch (checkedId) {
                                case R.id.present_chk:
                                    detail.setAttendenceStatus("1");
                                    break;

                                case R.id.absent_chk:
                                    detail.setAttendenceStatus("0");
                                    break;

                                case R.id.leave_chk:
                                    detail.setAttendenceStatus("-1");
                                    break;
                            }

                        }
                    }
                });


                switch (Integer.parseInt(detail.getAttendenceStatus())) {
                    case 0:
                        viewHolder.absent_chk.setChecked(true);
                        break;
                    case 1:
                        viewHolder.present_chk.setChecked(true);
                        break;
                    case -1:
                        viewHolder.leave_chk.setChecked(true);
                        break;
                    case -2:
                        viewHolder.present_chk.setChecked(true);
                        viewHolder.present_chk.setClickable(false);
                    default:
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}


