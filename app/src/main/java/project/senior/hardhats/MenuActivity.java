package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MenuActivity extends AppCompatActivity{

    class InvoiceForPreview
    {
        String id;
        String name;
        String date;
        String total;

        public InvoiceForPreview() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    class CustomerForPreview{
        String first;
        String last;
        String customerID;

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }

        public String getCustomerID() {
            return customerID;
        }

        public void setCustomerID(String customerID) {
            this.customerID = customerID;
        }

        @Override
        public String toString(){return super.toString();}
    }



    private ViewPager viewPager;
    MenuItem prevMenuItem;
    BottomNavigationView bottomNavigationView;
    ArrayList<InvoiceForPreview> invoicesList;
    ArrayList<CustomerForPreview> customerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_menu);
        swipeBottomNavigation();

        try {
            Setup();
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();

        }




    }

    protected void Setup() throws ExecutionException, InterruptedException, JSONException {
       DataContainer invoiceDataContainer = new DataContainer();
       invoiceDataContainer.type="invoicelistformenupreview";
       invoiceDataContainer.phpVariableNames.add("user_id");
       invoiceDataContainer.dataPassedIn.add(SessionData.getInstance().getUserID());
       BackgroundWorkerJSONArray getInvoices = new BackgroundWorkerJSONArray();
       JSONArray invoiceJSONArray = getInvoices.execute(invoiceDataContainer).get();

        DataContainer customerDataContainer = new DataContainer();
        customerDataContainer.type="customerlistformenupreview";
        customerDataContainer.phpVariableNames.add("user_id");
        customerDataContainer.dataPassedIn.add(SessionData.getInstance().getUserID());
        BackgroundWorkerJSONArray getCustomers = new BackgroundWorkerJSONArray();
        JSONArray customerJSONArray = getCustomers.execute(customerDataContainer).get();

       invoicesList = new ArrayList<InvoiceForPreview>();

       customerList = new ArrayList<CustomerForPreview>();


       for (int i = 0 ; i<invoiceJSONArray.length(); i++)
       {
           InvoiceForPreview listItem = new InvoiceForPreview();

           listItem.setId(invoiceJSONArray.getJSONObject(i).getString("InvoiceID"));

           listItem.setDate(invoiceJSONArray.getJSONObject(i).getString("InvoiceDate"));

           String name=invoiceJSONArray.getJSONObject(i).getString("FirstName")+" "+invoiceJSONArray.getJSONObject(i).getString("LastName");

           if (name.length()>16)
           {
               name=name.substring(0,16);
               name+="...";
           }

           listItem.setName(name);

           listItem.setTotal(invoiceJSONArray.getJSONObject(i).getString("Total"));


           invoicesList.add(listItem);

       }

/*
       for(int i = 0; i<customerJSONArray.length(); i++)
       {
          CustomerForPreview listItem = new CustomerForPreview();
          listItem.setCustomerID(customerJSONArray.getJSONObject(i).getString("CustomerID"));
          listItem.setFirst(customerJSONArray.getJSONObject(i).getString("FirstName"));
          listItem.setLast(customerJSONArray.getJSONObject(i).getString("LastName"));
          customerList.add(listItem);
       }
*/

   }

    public  ArrayList<InvoiceForPreview> getInvoicesList()
    {
        return invoicesList;

    }
    public  ArrayList<CustomerForPreview> getCustomerList()
    {
        return customerList;

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
//        pagerAdapter.addFragment(new ReportsFragment());
        pagerAdapter.addFragment(new OptionsFragment());
        viewPager.setAdapter(pagerAdapter);
    }


    public void SwitchToGenerateInvoiceActivity() {

        Intent generateInvoiceIntent = new Intent(MenuActivity.this, GenerateInvoiceActivity.class);
        startActivity(generateInvoiceIntent);

    }

    public void swipeBottomNavigation(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);


            bottomNavigationView.setOnNavigationItemSelectedListener(
                    new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.navigation_Invoices:
                                    viewPager.setCurrentItem(0);
                                    break;
                                case R.id.navigation_Customers:
                                    viewPager.setCurrentItem(1);
                                    break;
                               case R.id.navigation_Options:
                                    viewPager.setCurrentItem(2);
                                    break;
                            }
                            return false;
                        }

                    }
            );


            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (prevMenuItem != null) {
                        prevMenuItem.setChecked(false);
                    }
                    else {
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
}
