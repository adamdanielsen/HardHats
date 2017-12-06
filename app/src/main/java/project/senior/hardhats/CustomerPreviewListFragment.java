package project.senior.hardhats;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonathan.Cucuzza on 12/5/2017.
 */

public class CustomerPreviewListFragment extends Fragment{

    private ListView customerListView;
    public CustomerPreviewListFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_preview_list, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        customerListView = (ListView) getView().findViewById(R.id.fragmentcustomerpreviewlist_customerListView);

        final ArrayList<MenuActivity.CustomerForPreview> customerList = ((MenuActivity)getActivity()).getCustomerList();

        CustomerPreviewListFragment.CustomerForMenuAdapter adapter = new CustomerPreviewListFragment.CustomerForMenuAdapter(getContext(),customerList);
        customerListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
/*
        customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((CustomerFragment)getParentFragment()).setSelectedID(((MenuActivity.CustomerForPreview)parent.getItemAtPosition(position)).getCustomerID());

                //((CustomerFragment)getParentFragment()).switchToDetailView();
            }
        });
*/
    }
    private class CustomerForMenuAdapter extends BaseAdapter
    {
        final Context context;

        final ArrayList<MenuActivity.CustomerForPreview> customerLines;

        CustomerForMenuAdapter(Context context, ArrayList<MenuActivity.CustomerForPreview> customerLines) {
            this.context = context;
            this.customerLines = customerLines;
        }

        @Override
        public int getCount() {
            return customerLines.size();
        }

        @Override
        public Object getItem(int position) {
            return customerLines.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView==null) {
                convertView = View.inflate(context, R.layout.menucustomerlistitem, null);
                TextView left = (TextView) convertView.findViewById(R.id.menucustomerlistitem_leftTextView);
                TextView middle= (TextView) convertView.findViewById(R.id.menucustomerlistitem_middleTextView);
                //TextView right = (TextView) convertView.findViewById(R.id.menucustomerlistitem_rightTextView);



                left.setText(customerLines.get(position).getFirst());
                middle.setText(customerLines.get(position).getLast());
                //right.setText(customerLines.get(position).getCustomerID());
            }
            return convertView;
        }
    }

}
