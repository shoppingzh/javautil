package com.xpzheng.lang;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalenderUtils {

    /**
     * 获取指定月份的月视图<br>
     * 如2019年10月的月视图为：
     * <br><br>
     * <table border="1px solid" cellspacing="0" cellpadding="5px">
     *      <tr><td>日</td><td>一</td><td>二</td><td>三</td><td>四</td><td>五</td><td>六</td></tr>
     *      <tr><td>29</td><td>30</td><td>1</td><td>2</td><td>3</td><td>4</td><td>5</td></tr>
     *      <tr><td>6</td><td>7</td><td>8</td><td>9</td><td>10</td><td>11</td><td>12</td></tr>
     *      <tr><td>13</td><td>14</td><td>15</td><td>16</td><td>17</td><td>18</td><td>19</td></tr>
     *      <tr><td>20</td><td>21</td><td>22</td><td>23</td><td>24</td><td>25</td><td>26</td></tr>
     *      <tr><td>27</td><td>28</td><td>29</td><td>30</td><td>31</td><td>1</td><td>2</td></tr>
     * </table>
     * <br>
     * 当alignment为false时，月份日历的非当月数据不会展示出来，对以上日历，09/29、09/30、11/01、11/02不会展示
     * 
     * @param monthDate    带有年/月信息的Date对象
     * @param alignment    是否对齐所有日期
     * @return
     */
    public static List<Date> monthView(Date monthDate, boolean alignment) {
        if (monthDate == null) {
            return null;
        }
        Calendar month = Calendar.getInstance();
        month.setTime(monthDate);
        int minDay = month.getActualMinimum(Calendar.DAY_OF_MONTH);
        int maxDay = month.getActualMaximum(Calendar.DAY_OF_MONTH);
        List<Calendar> days = new ArrayList<Calendar>();
        for (int i = minDay; i <= maxDay; i++) {
            Calendar day = Calendar.getInstance();
            day.set(Calendar.YEAR, month.get(Calendar.YEAR));
            day.set(Calendar.MONTH, month.get(Calendar.MONTH));
            day.set(Calendar.DATE, i);
            day.set(Calendar.HOUR_OF_DAY, 0);
            day.set(Calendar.MINUTE, 0);
            day.set(Calendar.SECOND, 0);
            day.set(Calendar.MILLISECOND, 0);
            days.add(day);
        }
        // 两端补齐
        if (alignment) {
            Calendar startDay = (Calendar) days.get(0).clone();
            Calendar endDay = (Calendar) days.get(days.size() - 1).clone();
            int startWeekday = startDay.get(Calendar.DAY_OF_WEEK);
            int endWeekday = endDay.get(Calendar.DAY_OF_WEEK);
            int beforeOffset = startWeekday - Calendar.SUNDAY;
            int afterOffset = Calendar.SATURDAY - endWeekday;
            while (beforeOffset > 0) {
                startDay.roll(Calendar.DAY_OF_YEAR, false);
                days.add(0, (Calendar) startDay.clone());
                beforeOffset--;
            }
            while (afterOffset > 0) {
                endDay.roll(Calendar.DAY_OF_YEAR, true);
                days.add((Calendar) endDay.clone());
                afterOffset--;
            }
        }
        List<Date> result = new ArrayList<Date>();
        for (Calendar day : days) {
            result.add(day.getTime());
        }
        return result;
    }

}
