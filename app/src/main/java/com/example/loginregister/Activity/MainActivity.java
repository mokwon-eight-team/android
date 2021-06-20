package com.example.loginregister.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.loginregister.R;
import com.example.loginregister.adapter.ViewPagerAdapter;
import com.example.loginregister.fragment.CourierFragment;
import com.example.loginregister.fragment.CustomFragment;
import com.example.loginregister.fragment.MailFragment;
import com.example.loginregister.fragment.PostFragment;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private PostFragment postFragment;
    private CourierFragment courierFragment;
    private MailFragment mailFragment;
    private CustomFragment customFragment;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션바 설정
        //액션바 타이블 변경
        getSupportActionBar().setTitle("UCMD");
        //홈버튼 표시
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createFragment();
        createViewpager();
        settingTabLayout();


    }
    //액션바 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if ( id == R.id.action_btn1){
            Intent CCTVIntent = new Intent(this, Postcctv.class);
            startActivity(CCTVIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    //프래그먼트 생성
    public void createFragment()
    {
        postFragment = new PostFragment();
        courierFragment = new CourierFragment();
        mailFragment = new MailFragment();
        customFragment = new CustomFragment();
    }
    //뷰페이저 및 어댑터 생성
    public void createViewpager()
    {
        viewPager = (ViewPager2) findViewById(R.id.viewPager2);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPagerAdapter.addFragment(postFragment);
        viewPagerAdapter.addFragment(courierFragment);
        viewPagerAdapter.addFragment(mailFragment);
        viewPagerAdapter.addFragment(customFragment);

        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setUserInputEnabled(false);//터치 스크롤 막음
    }
    //탭레이아웃 - 뷰페이저 연결
    public void settingTabLayout()
    {
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();

                switch (pos)
                {
                    // 첫번째 화면 출력
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    // 두번째 화면 출력
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    // 세번째 화면 출력
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                    // 네번째 화면 출력
                    case 3:
                        viewPager.setCurrentItem(3);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


}