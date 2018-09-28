package com.example.sonliu.drinkingalert.ui.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.sonliu.drinkingalert.R;
import com.example.sonliu.drinkingalert.User.UserManager;
import com.example.sonliu.drinkingalert.ui.adapter.ViewPagerAdapter;
import com.example.sonliu.drinkingalert.ui.fragment.MeFragment;
import com.example.sonliu.drinkingalert.ui.fragment.PlanFragment;
import com.example.sonliu.drinkingalert.ui.fragment.ReportFragment;
import com.example.sonliu.drinkingalert.ui.fragment.TodayFragment;

public class MainActivity extends AppCompatActivity {
    private ViewPager mVpContent;
    private MenuItem menuItem;
    private Toolbar mToolbar;
    private BottomNavigationView mBottomTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserManager.getInstance().initUsers();
        initView();
    }

    /* 初始化View */
    private void initView() {
        mVpContent = findViewById(R.id.vp_content);
        mBottomTab = findViewById(R.id.bottom_tab);
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("喝水提醒");
        setSupportActionBar(mToolbar);
        mBottomTab.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_tab_bottom_today:
                        mVpContent.setCurrentItem(0);
                        break;
                    case R.id.item_tab_bottom_plan:
                        mVpContent.setCurrentItem(1);
                        break;
                    case R.id.item_tab_bottom_report:
                        mVpContent.setCurrentItem(2);
                        break;
                    case R.id.item_tab_bottom_me:
                        mVpContent.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) { }

            @Override
            public void onPageSelected(int i) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    mBottomTab.getMenu().getItem(0).setChecked(false);
                }
                menuItem = mBottomTab.getMenu().getItem(i);
                menuItem.setChecked(true);

                switch (i) {
                    case 0:
                        mToolbar.setTitle("喝水提醒");
                        break;
                    case 1:
                        mToolbar.setTitle("喝水计划");
                        break;
                    case 2:
                        mToolbar.setTitle("成绩单");
                        break;
                    case 3:
                        mToolbar.setTitle("我");
                        break;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) { }
        });
        setupViewPager(mVpContent);
    }

    /**
     * 填充ViewPager
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TodayFragment.newInstance());
        adapter.addFragment(PlanFragment.newInstance());
        adapter.addFragment(ReportFragment.newInstance());
        adapter.addFragment(MeFragment.newInstance());
        viewPager.setAdapter(adapter);
    }
}
