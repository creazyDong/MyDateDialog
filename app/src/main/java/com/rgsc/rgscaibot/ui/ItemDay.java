package com.rgsc.rgscaibot.ui;

import android.graphics.Color;
import android.graphics.Region;

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
 *    date   : 2020/11/20  10:33
 *    desc   : 单个日期实体
 *    version: 1.0
 *    hope   :  code without BUG 
 */
public class ItemDay {
    /**
     * 日期
     */
    private String value;
    /**
     * 是否选中
     */
    private boolean isSelect;
    /**
     * 对应区域
     */
    private Region mRegion;
    /**
     * 是否可选
     */
    private boolean canSelect;
    /**
     * 文字颜色
     */
    private int color;
    /**
     * 背景颜色
     */
    private int backageColor;
    /**
     * 底部描述
     */
    private String tip;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Region getRegion() {
        return mRegion;
    }

    public void setRegion(Region region) {
        mRegion = region;
    }

    public boolean isCanSelect() {
        return canSelect;
    }

    public void setCanSelect(boolean canSelect) {
        this.canSelect = canSelect;
        if (canSelect) {
            color = Color.BLACK;
        } else {
            color = Color.parseColor("#8e8e8e");
        }
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBackageColor() {
        return backageColor;
    }

    public void setBackageColor(int backageColor) {
        this.backageColor = backageColor;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
