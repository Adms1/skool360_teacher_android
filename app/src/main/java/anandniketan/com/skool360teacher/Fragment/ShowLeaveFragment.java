package anandniketan.com.skool360teacher.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360teacher.Activities.LoginActivity;
import anandniketan.com.skool360teacher.Adapter.LeaveDetailAdapter;
import anandniketan.com.skool360teacher.Adapter.LeaveListAdapter;
import anandniketan.com.skool360teacher.AsyncTasks.DeleteStaffLeaveAsyncTask;
import anandniketan.com.skool360teacher.AsyncTasks.GetLeaveDataAsyncTask;
import anandniketan.com.skool360teacher.Interfacess.onDeleteButton;
import anandniketan.com.skool360teacher.Interfacess.onEditTest;
import anandniketan.com.skool360teacher.Models.LeaveModel.LeaveFinalArray;
import anandniketan.com.skool360teacher.Models.LeaveModel.LeaveMainModel;
import anandniketan.com.skool360teacher.R;
import anandniketan.com.skool360teacher.Utility.AppConfiguration;
import anandniketan.com.skool360teacher.Utility.Utility;


public class ShowLeaveFragment extends Fragment implements View.OnClickListener {
    Fragment fragment;
    FragmentManager fragmentManager;
    Context mContext;
    View line_view;
    LeaveDetailAdapter leaveDetailAdapter;
    LeaveListAdapter leaveListAdapter;
    List<String> listDataHeader = new ArrayList<>();
    HashMap<String, List<LeaveFinalArray>> listDataChild = new HashMap<>();
    List<LeaveFinalArray> listDataChildTemp = new ArrayList<LeaveFinalArray>();

    String deleteLeaveIdStr, leaveIdStr, editLeaveDaysStr, editLeaveStartDateStr, editLeaveEndDateStr, editLeaveReasonStr, editLeaveHeadStr;
    ArrayList<String> editLeaveDetailArray;
    private View rootView;
    private FloatingActionButton add_leave_fab_btn;
    private Button btnLogout, btnBacktest_homework;
    private TextView txtNoRecordsClasswork;
    private LinearLayout header_linear, header_leave;
    private RecyclerView listLeavedetail;
    private ExpandableListView listLeave;
    private ProgressDialog progressDialog = null;
    private GetLeaveDataAsyncTask getLeaveDataAsyncTask = null;
    private DeleteStaffLeaveAsyncTask deleteStaffLeaveAsyncTask = null;
    private LeaveMainModel leaveDataResponse;
    private int lastExpandedPosition = -1;

    public ShowLeaveFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_show_leave, container, false);
        mContext = getActivity();

        initViews();
        setListners();

        return rootView;
    }

    public void initViews() {
        AppConfiguration.firsttimeback = true;
        AppConfiguration.position = 9;
        add_leave_fab_btn = (FloatingActionButton) rootView.findViewById(R.id.add_leave_fab_btn);
        btnLogout = (Button) rootView.findViewById(R.id.btnLogout);
        btnBacktest_homework = (Button) rootView.findViewById(R.id.btnBacktest_homework);
        txtNoRecordsClasswork = (TextView) rootView.findViewById(R.id.txtNoRecordsClasswork);
        header_linear = (LinearLayout) rootView.findViewById(R.id.header_linear);
        listLeave = (ExpandableListView) rootView.findViewById(R.id.listLeave);
        header_leave = (LinearLayout) rootView.findViewById(R.id.header_leave);
        listLeavedetail = (RecyclerView) rootView.findViewById(R.id.listLeavedetail);
        getLeaveDetail();
    }


    public void setListners() {
        add_leave_fab_btn.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
        btnBacktest_homework.setOnClickListener(this);
        listLeave.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
                    listLeave.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_leave_fab_btn:
                AppConfiguration.firsttimeback = true;
                AppConfiguration.position = 12;
                fragment = new LeaveFragment();
                Bundle args = new Bundle();
                args.putString("LeaveId", "");
                args.putString("LeaveDay", "");
                args.putString("LeaveStartDate", "");
                args.putString("LeaveEndDate", "");
                args.putString("LeaveReason", "");
                args.putString("LeaveHead", "");
                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
                break;
            case R.id.btnLogout:
                new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                        .setCancelable(false)
                        .setTitle("Logout")
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("Are you sure you want to logout? ")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Utility.setPref(mContext, "StaffID", "");
                                Utility.setPref(mContext, "Emp_Code", "");
                                Utility.setPref(mContext, "Emp_Name", "");
                                Utility.setPref(mContext, "DepratmentID", "");
                                Utility.setPref(mContext, "DesignationID", "");
                                Utility.setPref(mContext, "DeviceId", "");
                                Utility.setPref(mContext, "unm", "");
                                Utility.setPref(mContext, "pwd", "");
                                Intent i = new Intent(getActivity(),LoginActivity.class);
                                getActivity().startActivity(i);
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();
                break;
            case R.id.btnBacktest_homework:
                fragment = new HomeFragment();
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
                break;
        }
    }

    public void getLeaveDetail() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("StaffID", Utility.getPref(mContext, "StaffID"));
                        params.put("LocationID", Utility.getPref(mContext, "LocationId"));

                        getLeaveDataAsyncTask = new GetLeaveDataAsyncTask(params);
                        leaveDataResponse = getLeaveDataAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (leaveDataResponse.getSuccess().equalsIgnoreCase("True")) {
                                    if (leaveDataResponse.getFinalArray().size() > 0) {
                                        header_linear.setVisibility(View.VISIBLE);
                                        LeaveDetailList();
                                    } else {
                                        header_linear.setVisibility(View.GONE);

                                    }
                                    if (leaveDataResponse.getLeaveDetails().size() > 0) {
                                        header_leave.setVisibility(View.VISIBLE);
                                        leaveDetailAdapter = new LeaveDetailAdapter(mContext, leaveDataResponse);
                                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                                        listLeavedetail.setLayoutManager(mLayoutManager);
                                        listLeavedetail.setItemAnimator(new DefaultItemAnimator());
                                        listLeavedetail.setAdapter(leaveDetailAdapter);

                                    } else {
                                        header_leave.setVisibility(View.GONE);
                                        listLeavedetail.setVisibility(View.GONE);
                                    }
                                } else {
                                    progressDialog.dismiss();

                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

    public void LeaveDetailList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<LeaveFinalArray>>();

        for (int j = 0; j < leaveDataResponse.getFinalArray().size(); j++) {
            listDataHeader.add(leaveDataResponse.getFinalArray().get(j).getCreateDate() + "|" +
                    leaveDataResponse.getFinalArray().get(j).getLeaveDays() + "|" +
                    leaveDataResponse.getFinalArray().get(j).getStatus());

            ArrayList<LeaveFinalArray> rows = new ArrayList<LeaveFinalArray>();
            rows.add(leaveDataResponse.getFinalArray().get(j));
            listDataChild.put(listDataHeader.get(j), rows);
            listDataChildTemp.addAll(rows);
        }




        leaveListAdapter = new LeaveListAdapter(mContext, listDataHeader, listDataChild, new onEditTest() {
            @Override
            public void getEditTest() {
                editLeaveDetailArray = new ArrayList<>();
                editLeaveDetailArray = leaveListAdapter.getAllId();
                for (int i = 0; i < editLeaveDetailArray.size(); i++) {
                    String[] spilt = editLeaveDetailArray.get(i).split("\\|");
                    leaveIdStr = spilt[0];
                    editLeaveDaysStr = spilt[1];
                    editLeaveStartDateStr = spilt[2];
                    editLeaveEndDateStr = spilt[3];
                    editLeaveReasonStr = spilt[4];
                    editLeaveHeadStr = spilt[5];
                }
                fragment = new LeaveFragment();
                Bundle args = new Bundle();
                args.putString("LeaveId", leaveIdStr);
                args.putString("LeaveDay", editLeaveDaysStr);
                args.putString("LeaveStartDate", editLeaveStartDateStr);
                args.putString("LeaveEndDate", editLeaveEndDateStr);
                args.putString("LeaveReason", editLeaveReasonStr);
                args.putString("LeaveHead", editLeaveHeadStr);
                args.putString("update_flag","1");

                fragment.setArguments(args);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(0, 0)
                        .replace(R.id.frame_container, fragment).commit();
            }
        }, new onDeleteButton() {
            @Override
            public void deleteSentMessage() {
                deleteLeaveIdStr = leaveListAdapter.deleteId;
                new android.app.AlertDialog.Builder(new android.view.ContextThemeWrapper(getActivity(), R.style.AppTheme))
                        .setCancelable(false)
                        .setIcon(mContext.getResources().getDrawable(R.drawable.ic_launcher))
                        .setMessage("Are you sure you want to delete leave? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                deleteLeaveDetail();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.ic_launcher)
                        .show();


            }
        });
        listLeave.setAdapter(leaveListAdapter);
        leaveListAdapter.notifyDataSetChanged();
        listLeave.deferNotifyDataSetChanged();

        try {
            for (int pos = 0; pos < listDataChildTemp.size(); pos++) {

                String showListItem = Utility.getPref(getActivity(), "Push_Notification_message");

                String[] splitedArray = showListItem.split("-");

                String startDate = splitedArray[1];
                String endDate = splitedArray[2];

                String finalDate = startDate+"-"+endDate;
                String matchDate  = listDataChildTemp.get(pos).getLeaveStartDate()+"-"+listDataChildTemp.get(pos).getLeaveEndDate();

                if (finalDate.trim().equalsIgnoreCase(matchDate)) {
                    listLeave.expandGroup(pos);
                    listLeave.smoothScrollToPositionFromTop(pos,pos);
                    Utility.setPref(getActivity(), "Push_Notification_message", null);
                    Utility.setPref(getActivity(), "notification_type", null);
                    break;
                }else{
                    if(pos == listDataChildTemp.size() - 1){
                        Utility.setPref(getActivity(), "Push_Notification_message", null);
                        Utility.setPref(getActivity(), "notification_type", null);
                        break;
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void deleteLeaveDetail() {
        if (Utility.isNetworkConnected(mContext)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("LeaveID", deleteLeaveIdStr);
                        params.put("LocationID", Utility.getPref(mContext, "LocationId"));
                        deleteStaffLeaveAsyncTask = new DeleteStaffLeaveAsyncTask(params);
                        leaveDataResponse = deleteStaffLeaveAsyncTask.execute().get();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (leaveDataResponse.getSuccess().equalsIgnoreCase("True")) {
                                    Utility.ping(mContext, "Leave deleted successfully");
                                    getLeaveDetail();
                                } else {
                                    progressDialog.dismiss();

                                }
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } else {
            Utility.ping(mContext, "Network not available");
        }
    }

}
