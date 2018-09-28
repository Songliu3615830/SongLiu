package com.example.sonliu.drinkingalert;

/**
 * 常量类 默认设置
 */
public class DrinkAlertDefaultConstant {
    public static final int MAX_USER = 3;//最大用户量
    public static final int DEFAULT_MAN_DRINK = 1800;//默认成人喝水目标
    public static final int DEFAULT_BOY_DRINK = 1400;//默认孩子喝水目标
    //默认闹钟时间（分钟数）
    public static final int[] DEFALUT_ALARM = new int[]{500, 600, 680, 850, 950, 1010, 1075, 1110};
    //默认水杯图片序号
    public static final int[] DEFALUT_CUP_IMG = new int[]{0, 1, 2, 3};
    //默认水杯容量
    public static final int[] DEFALUT_CUP_NUM = new int[]{150, 250, 400, 0};
    //默认水杯
    public static final int[] IMGS_CUP = {R.drawable.img_cup_small, R.drawable.img_cup_middle, R.drawable.img_cup_big,
            R.drawable.img_cup_empty, R.drawable.img_cup1_bear_b, R.drawable.img_cup1_bear_gr,
            R.drawable.img_cup1_bear_p, R.drawable.img_cup1_fish_b, R.drawable.img_cup1_fish_gr,
            R.drawable.img_cup1_fish_p, R.drawable.img_cup1_flower_b, R.drawable.img_cup1_flower_gr,
            R.drawable.img_cup1_flower_p, R.drawable.img_cup1_rabit_b, R.drawable.img_cup1_rabit_gr,
            R.drawable.img_cup1_rabit_p, R.drawable.img_cup1_star_b, R.drawable.img_cup1_star_gr,
            R.drawable.img_cup1_star_p};
    //默认头像集合
    public static final int[] IMGS_PERSON = {R.drawable.img_avatar_g, R.drawable.img_avatar_girl1, R.drawable.img_avatar_boy1,
            R.drawable.img_avatar_girl2, R.drawable.img_avatar_boy2};
    public static final int IMGS_DEFALUT_MAN = 0;//默认主用户头像序号
    public static final int SKIP_MAINACTIVITY_CODE = 0x120;//跳转到mainActivity的requestCode
    public static final int DEFALUT_ALERT_TYPE = 3;//默认提醒方式
    public static final boolean DEFALUT_IS_ALERT = true; //默认是否提醒
    public static final String DEAFLUT_MAIN_NAME = "";//默认用户名字
}

