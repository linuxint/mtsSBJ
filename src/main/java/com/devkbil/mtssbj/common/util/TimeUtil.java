package com.devkbil.mtssbj.common.util;


import java.util.Calendar;

public class TimeUtil {

    private static long startTime;
    private static long endTime;
    private static long diffTime;

    public static void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    public static long getStartTime() {
        return startTime;
    }

    public static void setEndTime() {
        endTime = System.currentTimeMillis();
    }

    public static long getEndTime() {
        return endTime;
    }

    public static long getDiffTime() {
        diffTime = getEndTime() - getStartTime();
        return diffTime;
    }

    public static String formatTime(long lTime) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(lTime);
        return String.format("%02d:%02d:%02d.%03d", c.get(Calendar.HOUR), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND));
    }

    /**
     diffTime
     <p>
     두 시각과의 차이를 구한다.
     @return 시간차이

     @throws
     @praam fromTime String - 시작시각
     @praam toTime String - 종료시각
     */
    public static long diffTime(String fromTime, String toTime) {

        long lDiff = 0;

        try {
            //Date fromDate = DateUtil.convDate(fromTime);
            //Date toDate   = DateUtil.convDate(toTime);

            long lFromTime = DateUtil.convDate(fromTime).getTime() / 1000;
            //logger.debug("lCurTime="+lFromTime);

            long lToTime = DateUtil.convDate(toTime).getTime() / 1000;
            //logger.debug("lTmpTime="+lToTime);

            lDiff = lToTime - lFromTime;
        } catch(Exception e) {
        }

        return lDiff;
    }

}
