package com.rgsc.rgscaibot.ui;

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
 *    date   : 2020/11/20  15:00
 *    desc   : 日历控件监听事件
 *    version: 1.0
 *    hope   :  code without BUG 
 */
public interface IMyCalendarLister {
    void onDateValueChanged(String value);

    void onTitleClick(int year, int month);

    void onStartToEndDataValueChange(String startDate,String endDate);
}
