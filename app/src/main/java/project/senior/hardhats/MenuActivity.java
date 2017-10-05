package project.senior.hardhats;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction ft = getFragmentManager().beginTransaction();


            switch (item.getItemId()) {


                case R.id.navigation_Customers:
                    ft.replace(R.id.menu_FragmentFrameLayout,new CustomerFragment());
                    ft.commit();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    //ft.addToBackStack(null);
                    return true;
                case R.id.navigation_Invoices:
                    ft.replace(R.id.menu_FragmentFrameLayout,new InvoiceFragment());
                    ft.commit();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    //ft.addToBackStack(null);
                    return true;
                case R.id.navigation_Reports:
                    ft.replace(R.id.menu_FragmentFrameLayout,new ReportsFragment());
                    ft.commit();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                   // ft.addToBackStack(null);
                    return true;
                case R.id.navigation_Options:
                    ft.replace(R.id.menu_FragmentFrameLayout,new OptionsFragment());
                    ft.commit();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                  //  ft.addToBackStack(null);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        //FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.menu_FragmentFrameLayout,new InvoiceFragment());
        ft.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    protected void onResume() {
        super.onResume();

      //  TipDetailFragment frag = (TipDetailFragment) getFragmentManager().findFragmentById(R.id.detail_fragmenttipdetailholder);
      //  frag.populate(pos);


    }

    public void Logout()
    {
        Intent logoutIntent = new Intent(MenuActivity.this, LoginActivity.class);
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(logoutIntent);
    }
}
