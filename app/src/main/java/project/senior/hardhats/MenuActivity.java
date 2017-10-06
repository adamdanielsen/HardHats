package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


public class MenuActivity extends AppCompatActivity{

    // For Scrolling Tabs (using ViewPagerAdapter Class
    private ViewPager viewPager;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch(item.getItemId()){
                            case R.id.navigation_Invoices:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.navigation_Customers:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.navigation_Reports:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.navigation_Options:
                                viewPager.setCurrentItem(3);
                                break;
                        }
                        return false;
                    }
                }
        );
        viewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
   }

    protected void onResume() {
        super.onResume();
    }

    public void Logout()
    {
        Intent logoutIntent = new Intent(MenuActivity.this, LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logoutIntent);
    }

    //For ViewPagerAdapter
    public void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(new InvoiceFragment());
        pagerAdapter.addFragment(new CustomerFragment());
        pagerAdapter.addFragment(new ReportsFragment());
        pagerAdapter.addFragment(new OptionsFragment());
        viewPager.setAdapter(pagerAdapter);
    }
}
