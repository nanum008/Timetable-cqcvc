package com.evercocer.educationhelper.activitys;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.evercocer.educationhelper.model.CourseInfo;
import com.evercocer.educationhelper.model.DateInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;


public class TimetableViewModel extends ViewModel {
    private MutableLiveData<String[]> chapterDateInfo;
    private MutableLiveData<String> weekTh;
    private MutableLiveData<ArrayList<DateInfo>> dateInfo;
    private MutableLiveData<String> json;

    private Calendar calendar;
    private OkHttpClient okHttpClient;
    private ArrayList<CourseInfo> courseInfos;
    private ArrayList<int[]> colors;
    private Random random;
    private ArrayList<DateInfo> dateInfos;
    private Date time;

    private int currentWeek;

    public ArrayList<CourseInfo> getCourseInfos() {
        if (courseInfos == null) {
            courseInfos = new ArrayList<>();
        }
        return courseInfos;
    }

    public void setCourseInfos(ArrayList<CourseInfo> courseInfos) {
        this.courseInfos = courseInfos;
    }

    public MutableLiveData<String> getJson() {
        if (json == null) {
            json = new MutableLiveData<>();
        }
        return json;
    }
    public MutableLiveData<ArrayList<DateInfo>> getDateInfo() {
        if (dateInfo == null) {
            dateInfo = new MutableLiveData<>();
            if (calendar == null) {
                calendar = Calendar.getInstance();
            }
            dateInfos = new ArrayList<>();
            int beginMonth = calendar.get(Calendar.MONTH) + 1;
            int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
            System.out.println(beginDay);
            dateInfos.add(new DateInfo(beginMonth, beginDay));
            for (int i = 0; i < 6; i++) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                dateInfos.add(new DateInfo(month, day));
            }

            //????????????????????????
            calendar.set(Calendar.MONTH, beginMonth - 1);
            calendar.set(Calendar.DAY_OF_MONTH, beginDay);
            dateInfo.setValue(dateInfos);
        }
        return dateInfo;
    }

    /**
     * ??????????????????
     * ?????????????????????????????????????????????????????????
     * @param oldWeek
     * @param newWeek
     */
    public void flushDateInfo(int oldWeek, int newWeek) {
        //??????dteInfos??????
        dateInfos.clear();
        int change = newWeek - oldWeek;
        if (change == 0) return;
        //??????
        calendar.add(Calendar.DAY_OF_MONTH, 7 * change);
        calendar.set(Calendar.DAY_OF_WEEK, 2);
        int beginMonth = calendar.get(Calendar.MONTH) + 1;
        int beginDay = calendar.get(Calendar.DAY_OF_MONTH);
        dateInfos.add(new DateInfo(beginMonth, beginDay));
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dateInfos.add(new DateInfo(month, day));
        }


        //??????dateInfo
        dateInfo.setValue(dateInfos);
    }

    public MutableLiveData<String> getWeekTh() {
        if (weekTh == null) {
            weekTh = new MutableLiveData<>();
            weekTh.setValue(String.valueOf(getCurrentWeek()));
        }
        return weekTh;
    }


    public int getCurrentWeek() {
        if (calendar == null) {
            calendar = Calendar.getInstance();
            time = calendar.getTime();
            //????????????????????????????????????
            calendar.setFirstDayOfWeek(Calendar.MONDAY);

            //?????????????????????????????????
            calendar.set(2021, 8, 10);
            //?????????????????????????????????????????????
            int weekBegin = calendar.get(Calendar.WEEK_OF_YEAR);

            //????????????????????????????????????
            calendar.setTime(time);
            calendar.set(Calendar.DAY_OF_WEEK, 2);

            //???????????????????????????????????????
            int weekNow = calendar.get(Calendar.WEEK_OF_YEAR);
            //???????????????????????????????????????
            currentWeek = weekNow - weekBegin;
            System.out.println("currentWeek = " + currentWeek);
        }
        return currentWeek;
    }

    public MutableLiveData<String[]> getChapterDateInfo() {
        if (chapterDateInfo == null) {
            chapterDateInfo = new MutableLiveData<>();
            chapterDateInfo.setValue(new String[]{"08:45", "09:40", "10:35", "11:30", "14:55", "15:50", "16:45", "17:40", "19:30", "20:25"});
        }
        return chapterDateInfo;
    }

    //?????????colors
    private void initColor(ArrayList<int[]> colors_list) {
        int[] color1 = {25, 202, 173};
        int[] color2 = {236, 173, 158};
        int[] color3 = {190, 231, 233};
        int[] color4 = {214, 213, 183};
        int[] color5 = {244, 96, 108};
        int[] color6 = {209, 186, 116};
        int[] color7 = {190, 237, 199};
        int[] color8 = {230, 206, 172};
        int[] color9 = {140, 199, 181};
        int[] color10 = {160, 238, 225};

        colors_list.add(color1);
        colors_list.add(color2);
        colors_list.add(color3);
        colors_list.add(color4);
        colors_list.add(color5);
        colors_list.add(color6);
        colors_list.add(color7);
        colors_list.add(color8);
        colors_list.add(color9);
        colors_list.add(color10);
    }

    //?????????????????????
    public int[] getRandomColor() {
        if (random == null)
            random = new Random();

        if (colors == null) {
            colors = new ArrayList<>();
            initColor(colors);
        }
        int i = random.nextInt(10);
        switch (i) {
            case 0:
                return colors.get(0);
            case 1:
                return colors.get(1);
            case 2:
                return colors.get(2);
            case 3:
                return colors.get(3);
            case 4:
                return colors.get(4);
            case 5:
                return colors.get(5);
            case 6:
                return colors.get(6);
            case 7:
                return colors.get(7);
            case 8:
                return colors.get(8);
            case 9:
                return colors.get(9);
            default:
                return colors.get(0);
        }
    }


    //???????????????????????????CourseView
    public void parseCourseInfo(String data) {
        if (courseInfos == null) {
            courseInfos = new ArrayList<>();
        }
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                CourseInfo courseInfo = new CourseInfo();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //????????????
                String course = jsonObject.getString("kcmc");
                courseInfo.setCourse(course);
                //????????????
                String teacher = "@" + jsonObject.getString("jsxm");
                courseInfo.setTeacher(teacher);
                //????????????
                String classRoom = jsonObject.getString("jsmc");
                courseInfo.setClassRoom(classRoom);
                //????????????:?????????
                String dateInfo_str = jsonObject.getString("kcsj");
                int day = Integer.parseInt(dateInfo_str.substring(0, 1));
                int times = (dateInfo_str.length() / 2) + 1;
                int[] chapters = new int[times];
                chapters[0] = day;
                StringBuffer stringBuffer = new StringBuffer(dateInfo_str.substring(1, dateInfo_str.length()));
                for (int j = 1; j < times; j++) {
                    int chapter = Integer.parseInt(stringBuffer.substring(0, 2));
                    chapters[j] = chapter;
                    stringBuffer.delete(0, 2);
                }
                courseInfo.setChapterInfo(chapters);

                //rgb??????
                courseInfo.setRgbColor(getRandomColor());
                courseInfos.add(courseInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
