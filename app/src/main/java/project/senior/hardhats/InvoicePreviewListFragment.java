package project.senior.hardhats;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvoicePreviewListFragment extends Fragment {
    private static final DecimalFormat df = new DecimalFormat("$0.00");

    ListView invoicesListView;
    public InvoicePreviewListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice_preview_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        invoicesListView = (ListView) getView().findViewById(R.id.fragmentinvoicepreviewlist_invoicesListView);






        final ArrayList<MenuActivity.InvoiceForPreview> invoicelist = ((MenuActivity)getActivity()).getInvoicesList();



        InvoiceForMenuAdapter adapter = new InvoiceForMenuAdapter(getContext(),invoicelist);
        invoicesListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        invoicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((InvoiceFragment)getParentFragment()).setSelectedID(((MenuActivity.InvoiceForPreview)parent.getItemAtPosition(position)).getId());

                ((InvoiceFragment)getParentFragment()).switchToDetailView();
            }
        });

    }

    private class InvoiceForMenuAdapter extends BaseAdapter
    {
        Context context;

        ArrayList<MenuActivity.InvoiceForPreview> invoiceLines;

        InvoiceForMenuAdapter(Context context, ArrayList<MenuActivity.InvoiceForPreview> invoiceLines) {
            this.context = context;
            this.invoiceLines = invoiceLines;
        }

        @Override
        public int getCount() {
            return invoiceLines.size();
        }

        @Override
        public Object getItem(int position) {
            return invoiceLines.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView==null) {
                convertView = View.inflate(context, R.layout.menuinvoicelistitem, null);
                TextView left = (TextView) convertView.findViewById(R.id.menuinvoicelistitem_leftTextView);
                TextView middle= (TextView) convertView.findViewById(R.id.menuinvoicelistitem_middleTextView);
                TextView right = (TextView) convertView.findViewById(R.id.menuinvoicelistitem_rightTextView);



                left.setText(invoiceLines.get(position).getName());
                middle.setText(df.format(Double.valueOf(invoiceLines.get(position).getTotal())));
                right.setText(invoiceLines.get(position).getDate());
            }
            return convertView;
        }
    }

}
