package anandniketan.com.skool360teacher.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anandniketan.com.skool360teacher.Interfacess.onStudentHomeWorkStatus;
import anandniketan.com.skool360teacher.Interfacess.onWorkStatus;
import anandniketan.com.skool360teacher.Models.HomeWorkResponse.HomeworkModel;
import anandniketan.com.skool360teacher.R;

/**
 * Created by admsandroid on 10/13/2017.
 */

public class ExpandableListAdapterHomeWork extends BaseExpandableListAdapter {

    boolean visible = true;
    String FontStyle, splitFont1, splitFont2, splitFont3, splitFont4;
    TextView homwork_name_txt, chapter_name_txt, objective_txt;
    Button StudentHomeWorkStatus_btn, add_hw_cw_btn;
    onStudentHomeWorkStatus _onStudentHomeWorkStatus;
    ArrayList<String> getId = new ArrayList<>();
    ArrayList<String> daybookId;
    SpannableStringBuilder chapterSpanned, homeworkSpanned, objectiveSpanned;
    String chapterStr, homeworkStr, objectiveStr;
    onWorkStatus onWorkStatus;
    LinearLayout classwork_linear, homework_linear;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    private HashMap<String, ArrayList<HomeworkModel.HomeworkData>> _listDataChild;
    private String date = new String();

    public ExpandableListAdapterHomeWork(Context context, List<String> listDataHeader,
                                         HashMap<String, ArrayList<HomeworkModel.HomeworkData>> listChildData,
                                         onWorkStatus onWorkStatus, onStudentHomeWorkStatus onStudentHomeWorkStatus) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._onStudentHomeWorkStatus = onStudentHomeWorkStatus;
        this.onWorkStatus = onWorkStatus;
    }

    @Override
    public ArrayList<HomeworkModel.HomeworkData> getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             final boolean isLastChild, View convertView, ViewGroup parent) {

        final ArrayList<HomeworkModel.HomeworkData> childData = getChild(groupPosition, 0);


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_home_work, null);
        }

        homwork_name_txt = (TextView) convertView.findViewById(R.id.homwork_name_txt);
        chapter_name_txt = (TextView) convertView.findViewById(R.id.chapter_name_txt);
        objective_txt = (TextView) convertView.findViewById(R.id.objective_txt);
        StudentHomeWorkStatus_btn = (Button) convertView.findViewById(R.id.StudentHomeWorkStatus_btn);
        add_hw_cw_btn = (Button) convertView.findViewById(R.id.add_hw_cw_btn);

        classwork_linear = (LinearLayout) convertView.findViewById(R.id.classwork_linear);
        homework_linear = (LinearLayout) convertView.findViewById(R.id.homework_linear);

        FontStyle = "";
        splitFont1 = "";
        splitFont2 = "";
        splitFont3 = "";
        FontStyle = childData.get(childPosition).getFont();

        chapterStr = childData.get(childPosition).getWorkPlan().replaceAll("\\n", "").trim();
        homeworkStr = childData.get(childPosition).getHomeWork().replaceAll("\\n", "").trim();
        objectiveStr = childData.get(childPosition).getClassWork().replaceAll("\\n", "").trim();

        setText(chapterStr, homeworkStr, objectiveStr);

        if (childData.get(childPosition).getBtnStatus().equalsIgnoreCase("true")) {
            add_hw_cw_btn.setVisibility(View.VISIBLE);
        } else {
            add_hw_cw_btn.setVisibility(View.GONE);
        }

        if (childData.get(childPosition).getHomeWork().equalsIgnoreCase("")) {
            StudentHomeWorkStatus_btn.setVisibility(View.GONE);
            homework_linear.setVisibility(View.GONE);
        } else {
            StudentHomeWorkStatus_btn.setVisibility(View.VISIBLE);
            homework_linear.setVisibility(View.VISIBLE);
        }

        if (childData.get(childPosition).getClassWork().equalsIgnoreCase("")) {
            classwork_linear.setVisibility(View.GONE);
        } else {
            classwork_linear.setVisibility(View.VISIBLE);
        }
        StudentHomeWorkStatus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getId.add(childData.get(childPosition).getStandardID() + "|" + childData.get(childPosition).getClassID() +
                        "|" + childData.get(childPosition).getSubjectID() + "|" + childData.get(childPosition).getTermID() +
                        "|" + childData.get(childPosition).getStandard() + "|" + childData.get(childPosition).getClassName() +
                        "|" + childData.get(childPosition).getSubject());
                _onStudentHomeWorkStatus.getStudentHomeWorkStatus();
            }
        });


        add_hw_cw_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daybookId = new ArrayList<>();
                if (childData.get(childPosition).getClassWork().equalsIgnoreCase("") && childData.get(childPosition).getHomeWork().equalsIgnoreCase("")) {
                    daybookId.add(childData.get(childPosition).getDayBookId() +
                            "|" + childData.get(childPosition).getStandard() +
                            "|" + childData.get(childPosition).getClassName() +
                            "|" + childData.get(childPosition).getSubject() +
                            "|" + "1" +
                            "|" + "1");
                } else if (childData.get(childPosition).getClassWork().equalsIgnoreCase("")) {
                    daybookId.add(childData.get(childPosition).getDayBookId() +
                            "|" + childData.get(childPosition).getStandard() +
                            "|" + childData.get(childPosition).getClassName() +
                            "|" + childData.get(childPosition).getSubject() +
                            "|" + "1" +
                            "|" + homwork_name_txt.getText().toString());
                } else if (childData.get(childPosition).getHomeWork().equalsIgnoreCase("")) {
                    daybookId.add(childData.get(childPosition).getDayBookId() +
                            "|" + childData.get(childPosition).getStandard() +
                            "|" + childData.get(childPosition).getClassName() +
                            "|" + childData.get(childPosition).getSubject() +
                            "|" + objective_txt.getText().toString() +
                            "|" + "1");
                } else {
                    daybookId.add(childData.get(childPosition).getDayBookId() +
                            "|" + childData.get(childPosition).getStandard() +
                            "|" + childData.get(childPosition).getClassName() +
                            "|" + childData.get(childPosition).getSubject() +
                            "|" + objective_txt.getText().toString() +
                            "|" +  homwork_name_txt.getText().toString()+"|" +"1");
                }

                onWorkStatus.onWorkStatus();
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String[] headerTitle = getGroup(groupPosition).toString().split("\\|");

        String headerTitle1 = headerTitle[0];
        String headerTitle2 = headerTitle[1];
        String headerTitle3 = headerTitle[2];
        String headerTitle4 = headerTitle[3];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_homework, null);
        }
        TextView Date_txt, Standard_txt, Class_txt, Subject_txt;
        ImageView arrow_txt;

        Date_txt = (TextView) convertView.findViewById(R.id.Date_txt);
        Standard_txt = (TextView) convertView.findViewById(R.id.Standard_txt);
        Class_txt = (TextView) convertView.findViewById(R.id.Class_txt);
        Subject_txt = (TextView) convertView.findViewById(R.id.Subject_txt);
        arrow_txt = (ImageView) convertView.findViewById(R.id.arrow_txt);

        Date_txt.setText(headerTitle1);
        Standard_txt.setText(headerTitle2);
        Class_txt.setText(headerTitle3);
        Subject_txt.setText(headerTitle4);

        if (isExpanded) {
            arrow_txt.setBackgroundResource(R.drawable.arrow_1_42_down);
            date = Date_txt.getText().toString();
        } else {
            arrow_txt.setBackgroundResource(R.drawable.arrow_1_42);
        }


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void setText(String html, String html1, String html2) {

        chapterSpanned = (SpannableStringBuilder) Html.fromHtml(html);
        homeworkSpanned = (SpannableStringBuilder) Html.fromHtml(html1);
        objectiveSpanned = (SpannableStringBuilder) Html.fromHtml(html2);

        chapterSpanned = trimSpannable(chapterSpanned);
        homeworkSpanned = trimSpannable(homeworkSpanned);
        objectiveSpanned = trimSpannable(objectiveSpanned);

        homwork_name_txt.setText(homeworkSpanned, TextView.BufferType.SPANNABLE);
        chapter_name_txt.setText(chapterSpanned, TextView.BufferType.SPANNABLE);
        objective_txt.setText(objectiveSpanned, TextView.BufferType.SPANNABLE);
    }

    private SpannableStringBuilder trimSpannable(SpannableStringBuilder spannable) {
        int trimStart = 0;
        int trimEnd = 0;
        String text = spannable.toString();

        while (text.length() > 0 && text.startsWith("\n")) {
            text = text.substring(1);
            trimStart += 1;
        }
        while (text.length() > 0 && text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
            trimEnd += 1;
        }
        return spannable.delete(0, trimStart).delete(spannable.length() - trimEnd, spannable.length());
    }

    public ArrayList<String> getAllId() {
        return getId;
    }

    public ArrayList<String> getdaybookId() {
        return daybookId;
    }

    public String getDate() {
        return date;
    }
}


