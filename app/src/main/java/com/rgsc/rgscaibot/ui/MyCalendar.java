package com.rgsc.rgscaibot.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.rgsc.rgscaibot.R;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/***
 *                    .::::.
 *                  .::::::::.
 *                 :::::::::::  GOOD LUCK
 *             ..:::::::::::'
 *           '::::::::::::'
 *             .::::::::::
 *        '::::::::::::::..
 *             ..::::::::::::.
 *           ``::::::::::::::::
 *            ::::``:::::::::'        .:::.
 *           ::::'   ':::::'       .::::::::.
 *         .::::'      ::::     .:::::::'::::.
 *        .:::'       :::::  .:::::::::' ':::::.
 *       .::'        :::::.:::::::::'      ':::::.
 *      .::'         ::::::::::::::'         ``::::.
 *  ...:::           ::::::::::::'              ``::.
 * ```` ':.          ':::::::::'                  ::::..
 *                    '.:::::'                    ':'````..
 *
 *
 *    author : DongHao
 *    e-mail : donghao@chiot.com.cn
 *    date   : 2020/11/19  10:23
 *    desc   : 自定义日历（后期想拓展成 SufaceView）
 *    version: 1.0
 *    hope   :  code without BUG 
 */
public class MyCalendar extends View {
    public static final int SIMPLE_DATE = 0;  //单选时间
    public static final int RANGE_DATE = 1;   //选择区间
    private static final String OPTION_TYPE_TITLE = "option_title";
    private static final String OPTION_TYPE_YEAR_LEFT = "option_year_left";
    private static final String OPTION_TYPE_YEAR_RIGHT = "option_year_right";
    private static final String OPTION_TYPE_MONTH_LEFT = "option_month_left";
    private static final String OPTION_TYPE_MONTH_RIGHT = "option_month_right";
    private IMyCalendarLister mIMyCalendarLister;
    private String[] week = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    private int OFFSET = 30;     //操作按钮偏移量
    private String title;
    private int selectMode;
    private int nomorCollor;
    private int backgroundColor;
    private int selectColor;
    private int sectionColor;
    private List<ItemDay> mItemRectFList;
    /**
     * 最小可选日期
     */
    private String minDate;
    /**
     * 最大可选日期
     */
    private String maxDate;
    /**
     * 选择起始日期
     */
    private String startDate;
    /**
     * 选择终止日期
     */
    private String endDate;
    /**
     * 当前显示年
     */
    private int year;
    /**
     * 当前显示月
     */
    private int month;
    /**
     * 最小日期对应的最小年份
     */
    private int minYear;
    /**
     * 最小月份
     */
    private int minMonth;
    /**
     * 最大年份
     */
    private int maxYear;
    /**
     * 最大月份
     */
    private int maxMonth;
    /**
     * 布局的宽
     */
    private int width;
    /**
     * 布局的高
     */
    private int hight;
    /**
     * 日期文字大小
     */
    private int dayTextSize;
    /**
     * 标题文字大小
     */
    private int titleTextSize;
    /**
     * 底部描述文字大小
     */
    private int tipTextSize;
    /**
     * 屏幕高度
     */
    private int clientHight;
    /**
     * 操作区域
     */
    private Map<String, Region> optionMap;
    private Paint mPaint;
    /**
     * 当前日期生成多少行
     */
    private int currentLine;
    /**
     * 开始时间底部描述
     */
    private String startDateTip;
    /**
     * 结束时间底部描述
     */
    private String endDateTip;

    public MyCalendar(Context context) {
        this(context, null, 0);
    }

    public MyCalendar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public String getCurrentDate() {
        return year + "年" + month + "月";
    }


    public MyCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Calendar calendar = Calendar.getInstance();
        //初始化属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyCalendar, defStyleAttr, 0);
        selectMode = typedArray.getInteger(R.styleable.MyCalendar_mode, 0);
        title = typedArray.getString(R.styleable.MyCalendar_titleTip);
        title = StringUtils.isNotBlank(title) ? title : "选择日期";
        minDate = typedArray.getString(R.styleable.MyCalendar_minDate);
        minDate = StringUtils.isNotBlank(minDate) ? minDate : "1970-01-01";
        maxDate = typedArray.getString(R.styleable.MyCalendar_maxDate);
        maxDate = StringUtils.isNotBlank(maxDate) ? maxDate : DateUtils.formatDate(DateUtils.getCurrentCommonDate(), DateUtils.FORMAT_COMMON_DAY);
        calendar.setTime(DateUtils.parseString(minDate, DateUtils.FORMAT_COMMON_DAY));
        minYear = calendar.get(Calendar.YEAR);
        minMonth = calendar.get(Calendar.MONTH) + 1;
        calendar.setTime(DateUtils.parseString(maxDate, DateUtils.FORMAT_COMMON_DAY));
        maxYear = calendar.get(Calendar.YEAR);
        maxMonth = calendar.get(Calendar.MONTH) + 1;
        startDate = typedArray.getString(R.styleable.MyCalendar_startDate);//默认选中时间
        startDate = StringUtils.isNotBlank(startDate) ? startDate : DateUtils.formatDate(DateUtils.getCurrentCommonDate(), DateUtils.FORMAT_COMMON_DAY);
        endDate = typedArray.getString(R.styleable.MyCalendar_endDate);
        endDate = StringUtils.isNotBlank(endDate) ? endDate : "";
        calendar.setTime(DateUtils.parseString(startDate, DateUtils.FORMAT_COMMON_DAY));
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        startDateTip = typedArray.getString(R.styleable.MyCalendar_startDateTip);
        startDateTip = StringUtils.isNotBlank(startDateTip) ? startDateTip : "开始";
        endDateTip = typedArray.getString(R.styleable.MyCalendar_endDateTip);
        endDateTip = StringUtils.isNotBlank(endDateTip) ? endDateTip : "结束";
        nomorCollor = typedArray.getColor(R.styleable.MyCalendar_nomorCollor, Color.parseColor("#8e8e8e"));
        backgroundColor = typedArray.getColor(R.styleable.MyCalendar_backgroundColor, Color.parseColor("#F3F9FF"));
        selectColor = typedArray.getColor(R.styleable.MyCalendar_selectColor, Color.parseColor("#19be6b"));
        sectionColor = typedArray.getColor(R.styleable.MyCalendar_dateSectionColor, Color.parseColor("#B8F1CF"));
        dayTextSize = (int) typedArray.getDimension(R.styleable.MyCalendar_dayTextSize, px2dip(45));
        titleTextSize = (int) typedArray.getDimension(R.styleable.MyCalendar_titleTextSize, px2dip(42));
        tipTextSize = (int) typedArray.getDimension(R.styleable.MyCalendar_tipTextSize, px2dip(35));
        typedArray.recycle();
        setWillNotDraw(false);
        OFFSET = px2dip(30);
        mItemRectFList = new ArrayList<>();
        clientHight = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        optionMap = new Hashtable<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
//        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingLeft() - getPaddingRight();
//        if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
//            height = clientHight;
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//        } else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY ) {
//            // 表示高度确定，要测量宽度
//            width = clientWidth;
//            widthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
//        }
        float days = new Date(year, month, 0).getDate(); //这个月多少天
        int space = new Date(year, month - 1, 1).getDay(); //在第几个格 0代表周六
        float residueDays = days; //剩余的天数
        if (space == 0) {
            residueDays = residueDays - 1;
        } else {
            residueDays = residueDays - (7 - space) - 1;
        }
        currentLine = (int) Math.ceil(residueDays / 7) + 1;
        int heightSpec = clientHight / 2;
        int result = currentLine == 5 ? heightSpec : (int) (heightSpec + heightSpec * (0.44 - 0.3));
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(result, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void lineChanged() {
        float days = new Date(year, month, 0).getDate(); //这个月多少天
        int space = new Date(year, month - 1, 1).getDay(); //在第几个格 0代表周六
        float residueDays = days; //剩余的天数
        if (space == 0) {
            residueDays = residueDays - 1;
        } else {
            residueDays = residueDays - (7 - space) - 1;
        }
        int nextLine = (int) Math.ceil(residueDays / 7) + 1;
        if (currentLine != nextLine) {
            currentLine = nextLine;
            requestLayout();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mItemRectFList.clear();
        optionMap.clear();
        mPaint = new Paint();
        Matrix mMatrix = new Matrix();
        mPaint.setStrokeWidth(10);
        mPaint.setColor(nomorCollor);
        mPaint.setTextSize(titleTextSize);
        mPaint.setFakeBoldText(true);
        mPaint.setAntiAlias(true);
        width = getMeasuredWidth();
        hight = clientHight / 2;
        Path mPath = new Path();
        canvas.drawColor(backgroundColor);
        drawOption(canvas, mPaint, mPath, mMatrix);
        drawDate(canvas, mPaint);
    }

    private void drawOption(Canvas canvas, Paint mPaint, Path mPath, Matrix mMatrix) {
        //画标题
        if (StringUtils.isNotBlank(startDate)) {
            if (selectModeIsSimpleDate()) {
                title = startDate;
                if (mIMyCalendarLister != null) {
                    mIMyCalendarLister.onDateValueChanged(startDate);
                }
            } else {
                title = startDate + " 至 " + endDate;
                if (mIMyCalendarLister != null) {
                    mIMyCalendarLister.onStartToEndDataValueChange(startDate, endDate);
                }
            }
        }
        canvas.drawText(title, width / 2 - getTextXCenterPoint(title), hight * 0.06f, mPaint);
        mPaint.setTextSize(dayTextSize + 2);
        //画周文字
        for (int i = 0; i < week.length; i++) {
            canvas.drawText(week[i], (width / week.length) * i + width / week.length / 3, hight * 0.28f, mPaint);
        }
        mPaint.setColor(Color.BLACK);
        float weekHight = hight * 0.16f;
        String currentDate = getCurrentDate();
        canvas.drawText(currentDate, width / 2 - getTextXCenterPoint(currentDate), weekHight, mPaint);
        //保存显示年月日区域
        Rect titleOption = new Rect((int) (width / 2 - getTextXCenterPoint(currentDate)), (int) weekHight, (int) (width / 2 + getTextXCenterPoint(currentDate)), (int) (weekHight + getTextYCenterPoint(currentDate) * 2));
        Region titleOptionRegion = new Region(titleOption);
        optionMap.put(OPTION_TYPE_TITLE, titleOptionRegion);
        //绘制切换月图标
        mPaint.setColor(nomorCollor);
        mPaint.setStrokeWidth(6);
        mPaint.setStyle(Paint.Style.STROKE);
        float centerX = width / 3.5f;
        float centerY = weekHight - getTextYCenterPoint(currentDate) / 2.8f;
        mPath.moveTo(centerX + OFFSET, weekHight);
        mPath.lineTo(centerX, centerY);
        mPath.lineTo(centerX + OFFSET, centerY * 2 - weekHight);
        canvas.drawPath(mPath, mPaint);
        //保存左侧月操作区域
        int EXPAND_SIZE = (int) (hight * 0.06f);//操作区域放大处理
        Rect leftMonthOption = new Rect((int) centerX - EXPAND_SIZE, (int) (centerY * 2 - weekHight) - EXPAND_SIZE, (int) (centerX + OFFSET) + EXPAND_SIZE, (int) weekHight + EXPAND_SIZE);
        Region leftMonthOptionRegion = new Region(leftMonthOption);
        optionMap.put(OPTION_TYPE_MONTH_LEFT, leftMonthOptionRegion);
        //绘制切换年份
        mPaint.setColor(Color.parseColor("#666666"));
        mMatrix.postTranslate(-width / 7, 0);
        mPath.transform(mMatrix);
        canvas.drawPath(mPath, mPaint);
        mMatrix.reset();
        mMatrix.postTranslate(OFFSET, 0);
        mPath.transform(mMatrix);
        canvas.drawPath(mPath, mPaint);
        //保存左侧年操作区域
        Rect leftYearOption = new Rect((int) (centerX - width / 7) - EXPAND_SIZE, (int) (centerY * 2 - weekHight) - EXPAND_SIZE, (int) (centerX - width / 7 + 2 * OFFSET) + EXPAND_SIZE, (int) weekHight + EXPAND_SIZE);
        Region leftYearOptionRegion = new Region(leftYearOption);
        optionMap.put(OPTION_TYPE_YEAR_LEFT, leftYearOptionRegion);
        //绘制右边切换图标
        mMatrix.reset();
        mMatrix.postRotate(180, width / 2, centerY);
        mPath.transform(mMatrix);
        canvas.drawPath(mPath, mPaint);
        mMatrix.reset();
        mMatrix.postTranslate(OFFSET, 0);
        mPath.transform(mMatrix);
        canvas.drawPath(mPath, mPaint);
        //保存右侧年操作区域
        Rect rightYearOption = new Rect(width - leftYearOption.left - 2 * OFFSET - EXPAND_SIZE, (int) (centerY * 2 - weekHight) - EXPAND_SIZE, width - leftYearOption.left + EXPAND_SIZE, (int) weekHight + EXPAND_SIZE);
        Region rightYearOptionRegion = new Region(rightYearOption);
        optionMap.put(OPTION_TYPE_YEAR_RIGHT, rightYearOptionRegion);
        //切换月
        mPaint.setColor(nomorCollor);
        mMatrix.reset();
        mMatrix.postTranslate(-width / 7, 0);
        mPath.transform(mMatrix);
        canvas.drawPath(mPath, mPaint);
        //保存右侧月操作区域
        Rect rightMonthOption = new Rect(width - leftMonthOption.left - OFFSET - EXPAND_SIZE, (int) (centerY * 2 - weekHight) - EXPAND_SIZE, width - leftMonthOption.left + EXPAND_SIZE, (int) weekHight + EXPAND_SIZE);
        Region rightMonthOptionRegion = new Region(rightMonthOption);
        optionMap.put(OPTION_TYPE_MONTH_RIGHT, rightMonthOptionRegion);
    }

    /**
     * 绘制日期
     */
    private void drawDate(Canvas canvas, Paint mPaint) {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(backgroundColor);
        float days = new Date(year, month, 0).getDate(); //这个月多少天
        int space = new Date(year, month - 1, 1).getDay(); //在第几个格 0代表周六
        int itemDayReftHight = (int) (hight * 0.44f);
        int startTop = (int) (hight * 0.3f);
        int itemHightDistance = itemDayReftHight - startTop;
        float residueDays = days; //剩余的天数
        if (space == 0) {
            residueDays = residueDays - 1;
        } else {
            residueDays = residueDays - (7 - space) - 1;
        }
        currentLine = (int) Math.ceil(residueDays / 7) + 1;
        if (space == 0) {
            Rect itemRef = new Rect();
            itemRef.left = 6 * (width / 7);
            itemRef.right = 7 * (width / 7);
            itemRef.top = startTop;
            itemRef.bottom = itemDayReftHight;
            Region region = new Region(itemRef);
            ItemDay itemDay = renderItemDay(new ItemDay(), region, "01");
            mPaint.setColor(itemDay.getBackageColor());
            drawRegion(canvas, region, mPaint, itemDay);
            mPaint.setTextSize(dayTextSize);
            mPaint.setStrokeWidth(10);
            mPaint.setColor(itemDay.getColor());
            canvas.drawText("1", 6 * width / 7 + (itemRef.right - itemRef.left) / 2 - getTextXCenterPoint("1"), itemRef.top + (itemRef.bottom - itemRef.top) / 2 + getTextYCenterPoint("1") / 2, mPaint);
            mItemRectFList.add(itemDay);
        } else {
            int dayTip = 1;
            for (int i = space - 1; i < 7; i++) {
                Rect itemRef = new Rect();
                itemRef.left = i * (width / 7);
                itemRef.right = (i + 1) * (width / 7);
                itemRef.top = startTop;
                itemRef.bottom = itemDayReftHight;
                Region region = new Region(itemRef);
                String day = dayTip <= 9 ? "0" + dayTip : dayTip + "";
                ItemDay itemDay = renderItemDay(new ItemDay(), region, day);
                mPaint.setColor(itemDay.getBackageColor());
                drawRegion(canvas, region, mPaint, itemDay);
                mPaint.setTextSize(dayTextSize);
                mPaint.setStrokeWidth(10);
                mPaint.setColor(itemDay.getColor());
                String text = String.valueOf(dayTip);
                canvas.drawText(text, i * width / 7 + (itemRef.right - itemRef.left) / 2 - getTextXCenterPoint(text), itemRef.top + (itemRef.bottom - itemRef.top) / 2 + getTextYCenterPoint(text) / 2, mPaint);
                dayTip++;
                mItemRectFList.add(itemDay);
            }
        }
        mPaint.reset();
        mPaint.setColor(backgroundColor);
        mPaint.setAntiAlias(true);
        //绘制剩下的每一个日期框
        int itemIndex = 0;
        int lineIndex = 1;
        for (int i = (int) (days - residueDays + 1); i < days + 1; i++) {
            Rect itemRef = new Rect();
            itemRef.left = itemIndex * width / 7;
            itemRef.right = (itemIndex + 1) * width / 7;
            itemRef.top = startTop + lineIndex * itemHightDistance;
            itemRef.bottom = startTop + (lineIndex + 1) * itemHightDistance;
            Region region = new Region(itemRef);
            String day = i <= 9 ? "0" + i : i + "";
            ItemDay itemDay = renderItemDay(new ItemDay(), region, day);
            mPaint.setColor(itemDay.getBackageColor());
            drawRegion(canvas, region, mPaint, itemDay);
            mItemRectFList.add(itemDay);
            mPaint.setTextSize(dayTextSize);
            mPaint.setStrokeWidth(10);
            mPaint.setColor(itemDay.getColor());
            String text = String.valueOf(i);
            canvas.drawText(text, itemIndex * width / 7 + (itemRef.right - itemRef.left) / 2 - getTextXCenterPoint(text), itemRef.top + (itemRef.bottom - itemRef.top) / 2 + getTextYCenterPoint(text) / 2, mPaint);
            itemIndex++;
            if (itemIndex % 7 == 0) {
                lineIndex++;
                itemIndex = 0;
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        hight = h;
    }

    private float getTextXCenterPoint(String text) {
        return mPaint.measureText(text) / 2;
    }

    private int getTextYCenterPoint(String text) {
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ItemDay renderItemDay(ItemDay itemDay, Region region, String dayTip) {
        String mon = month <= 9 ? "0" + month : month + "";
        itemDay.setValue(year + "-" + mon + "-" + dayTip);
        itemDay.setRegion(region);
        itemDay.setCanSelect(currentDateCanSelect(itemDay.getValue()));
        if (selectModeIsSimpleDate()) {
            if (StringUtils.isNotBlank(startDate) && startDate.equals(itemDay.getValue())) {
                itemDay.setSelect(true);
                itemDay.setBackageColor(selectColor);
                itemDay.setColor(Color.WHITE);
            } else {
                itemDay.setSelect(false);
                itemDay.setBackageColor(backgroundColor);
            }
        } else {
            if ((StringUtils.isNotBlank(startDate) && startDate.equals(itemDay.getValue())) || (StringUtils.isNotBlank(endDate) && endDate.equals(itemDay.getValue()))) {
                if (itemDay.getValue().equals(startDate) && itemDay.getValue().equals(endDate)) {
                    itemDay.setTip(endDateTip);
                } else {
                    if (itemDay.getValue().equals(startDate)) {
                        itemDay.setTip(startDateTip);
                    } else if (itemDay.getValue().equals(endDate)) {
                        itemDay.setTip(endDateTip);
                    }
                }
                itemDay.setBackageColor(selectColor);
                itemDay.setColor(Color.WHITE);
                itemDay.setSelect(true);
            } else {
                itemDay.setSelect(false);
                //判断是否在时间范围内
                if (inStartAndEndSection(startDate, endDate, itemDay.getValue())) {
                    itemDay.setColor(selectColor);
                    itemDay.setBackageColor(sectionColor);
                } else {
                    itemDay.setBackageColor(backgroundColor);
                }
            }
        }
        return itemDay;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    private boolean selectModeIsSimpleDate() {
        return selectMode == SIMPLE_DATE;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            if (inOptionArea(x, y)) {
                return true;
            }
            for (ItemDay itemDay : mItemRectFList) {
                if (itemDay.getRegion().contains(x, y)) {
                    if (itemDay.isCanSelect()) {
                        if (selectModeIsSimpleDate()) {
                            itemDay.setSelect(!itemDay.isSelect());
                            startDate = itemDay.isSelect() ? itemDay.getValue() : "";
                            invalidate();
                            if (mIMyCalendarLister != null) {
                                mIMyCalendarLister.onDateValueChanged(startDate);
                            }
                        } else {   //区间模式
                            itemDay.setSelect(true);
                            if (StringUtils.isBlank(startDate)) {
                                startDate = itemDay.getValue();
                            } else {
                                if (DateUtils.getTimeDifference(DateUtils.FORMAT_COMMON_DAY, startDate, itemDay.getValue()) < 0) {    //判断选择结束时间是否小于起始时间
                                    startDate = itemDay.getValue();
                                    endDate = "";
                                } else {
                                    if (StringUtils.isBlank(endDate)) {
                                        endDate = itemDay.getValue();
                                    } else {
                                        startDate = itemDay.getValue();
                                        endDate = "";
                                    }
                                }
                            }
                            invalidate();
                            if (mIMyCalendarLister != null) {
                                mIMyCalendarLister.onStartToEndDataValueChange(startDate, endDate);
                            }
                        }
                    }
                    break;
                }
            }
        }
        return true;
    }

    /**
     * 是否点击操作区域
     *
     * @return
     */
    private boolean inOptionArea(int x, int y) {
        boolean isIn = false;
        int mon = month;
        int yar = year;
        for (Map.Entry<String, Region> entry : optionMap.entrySet()) {
            if (entry.getValue().contains(x, y)) {
                String key = entry.getKey();
                if (key.equals(OPTION_TYPE_TITLE)) {
                    if (mIMyCalendarLister != null) {
                        mIMyCalendarLister.onTitleClick(year, month);
                    }
                } else if (key.equals(OPTION_TYPE_MONTH_LEFT)) {
                    if (mon - 1 > 0) {
                        mon = mon - 1;
                    } else {
                        yar = yar - 1;
                        mon = 12;
                    }
                    if (yar > minYear) {
                        month = mon;
                        year = yar;
                        lineChanged();
                        invalidate();
                    } else if (yar == minYear) {
                        if (mon >= minMonth) {
                            month = mon;
                            year = yar;
                            lineChanged();
                            invalidate();
                        }
                    }
                } else if (key.equals(OPTION_TYPE_YEAR_LEFT)) {
                    if (yar - 1 > minYear) {
                        year = yar - 1;
                        lineChanged();
                        invalidate();
                    } else if (yar - 1 == minYear) {
                        year = yar - 1;
                        if (mon < minMonth) {
                            month = minMonth;
                        }
                        lineChanged();
                        invalidate();
                    }
                } else if (key.equals(OPTION_TYPE_MONTH_RIGHT)) {
                    if (mon + 1 <= 12) {
                        mon = mon + 1;
                    } else {
                        yar = yar + 1;
                        mon = 1;
                    }
                    if (yar < maxYear) {
                        month = mon;
                        year = yar;
                        lineChanged();
                        invalidate();
                    } else if (yar == maxYear) {
                        if (mon <= maxMonth) {
                            month = mon;
                            year = yar;
                            lineChanged();
                            invalidate();
                        }
                    }
                } else if (key.equals(OPTION_TYPE_YEAR_RIGHT)) {
                    if (yar + 1 < maxYear) {
                        year = yar + 1;
                        lineChanged();
                        invalidate();
                    } else if (yar + 1 == maxYear) {
                        year = yar + 1;
                        if (mon > maxMonth) {
                            month = maxMonth;
                        }
                        lineChanged();
                        invalidate();
                    }
                }
                isIn = true;
                break;
            }
        }
        return isIn;
    }

    /**
     * 绘制区域
     *
     * @param canvas
     * @param rgn
     * @param paint
     * @param itemDay 是否绘制成圆角
     */
    @SuppressLint("NewApi")
    private void drawRegion(Canvas canvas, Region rgn, Paint paint, ItemDay itemDay) {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();
        while (iter.next(r)) {
            if (itemDay.isSelect()) {
                if (selectModeIsSimpleDate()) {
                    canvas.drawRoundRect(r.left, r.top, r.right, r.bottom, 20, 20, paint);
                } else {
                    canvas.drawRect(r, paint);
                    //绘制底部描述
                    mPaint.setColor(Color.WHITE);
                    mPaint.setTextSize(tipTextSize);
                    canvas.drawText(itemDay.getTip(), r.left + (r.right - r.left) / 2 - getTextXCenterPoint(itemDay.getTip()), r.bottom - getTextYCenterPoint(itemDay.getTip()) / 2, mPaint);
                }
            } else {
                canvas.drawRect(r, paint);
            }
        }
    }

    /**
     * 判断当前日期是否可选
     *
     * @param currentDate
     * @return
     */
    private boolean currentDateCanSelect(String currentDate) {
        if (StringUtils.isNotBlank(minDate) && StringUtils.isNotBlank(maxDate)) {
            long min = DateUtils.getTimeDifference(DateUtils.FORMAT_COMMON_DAY, currentDate, minDate);
            long max = DateUtils.getTimeDifference(DateUtils.FORMAT_COMMON_DAY, currentDate, maxDate);
            return min <= 0 && max >= 0;
        }
        if (StringUtils.isNotBlank(minDate)) {
            long min = DateUtils.getTimeDifference(DateUtils.FORMAT_COMMON_DAY, currentDate, minDate);
            return min <= 0;
        }
        if (StringUtils.isNotBlank(maxDate)) {
            long max = DateUtils.getTimeDifference(DateUtils.FORMAT_COMMON_DAY, currentDate, maxDate);
            return max >= 0;
        }
        return true;
    }


    private boolean inStartAndEndSection(String startDate, String endDate, String value) {
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            long min = DateUtils.getTimeDifference(DateUtils.FORMAT_COMMON_DAY, value, startDate);
            long max = DateUtils.getTimeDifference(DateUtils.FORMAT_COMMON_DAY, value, endDate);
            return min <= 0 && max >= 0;
        }
        return false;
    }

    public void setIMyCalendarLister(IMyCalendarLister IMyCalendarLister) {
        mIMyCalendarLister = IMyCalendarLister;
    }

    public MyCalendar setSelectMode(int selectMode) {
        this.selectMode = selectMode;
        return this;
    }

    public MyCalendar setNomorCollor(int nomorCollor) {
        this.nomorCollor = nomorCollor;
        return this;
    }


    public MyCalendar setCalendarBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public MyCalendar setSelectColor(int selectColor) {
        this.selectColor = selectColor;
        return this;
    }

    public MyCalendar setSectionColor(int sectionColor) {
        this.sectionColor = sectionColor;
        return this;
    }

    public MyCalendar setMinDate(String minDate) {
        this.minDate = minDate;
        return this;
    }

    public MyCalendar setMaxDate(String maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public MyCalendar setStartDateTip(String startDateTip) {
        this.startDateTip = startDateTip;
        return this;
    }

    public MyCalendar setEndDateTip(String endDateTip) {
        this.endDateTip = endDateTip;
        return this;
    }

    /**
     * dp 转成px
     */
    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转成dp
     */
    public int px2dip(float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mIMyCalendarLister = null;
        week = null;
        mItemRectFList = null;
        optionMap = null;
        minDate = null;
        maxDate = null;
        startDate = null;
        endDate = null;
    }
}
