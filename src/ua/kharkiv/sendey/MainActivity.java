package ua.kharkiv.sendey;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

/*
 * ����������� ������� ����������, ��������� �������
 */
public class MainActivity extends FragmentActivity
                          implements ActionBar.TabListener {
	
    public static final int TAB_COUNT = 2; // ���������� �������
	
	private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    
    /* �������� ������� */
    private int[] tabs = {R.string.tab_select, R.string.tab_insert};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);       
        
        /* ��������� ������� */
        for (int tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                     .setTabListener(this));
        }
        
        /* �������� ��������������� ������� ��� ������������� */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()  {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
            
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }
    
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
    
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    	
        /* ���������� ��������������� �������� */
        viewPager.setCurrentItem(tab.getPosition());
    }
    
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
    
    /* ������� ������� ��� �������*/
    public class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        @Override
        public Fragment getItem(int index) {
        
            switch (index) {
            case 0:
                /* ������ ��������� */
                return new SelectFragment();
            case 1:
                /* ���������� �������� */
                return new InsertFragment();
            }
            return null;
        }
     
        @Override
        public int getCount() {
            return TAB_COUNT;
        }
    }
}