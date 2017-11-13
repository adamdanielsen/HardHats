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
import android.widget.Toast;

import java.util.ArrayList;

import project.senior.hardhats.Documents.InvoiceLine;


/**
 * A simple {@link Fragment} subclass.
 */


public class InvoicePreviewFragment extends Fragment {

    ListView previewListView;
    InvoiceAdapter invoiceAdapter;
    ArrayList<InvoiceLine> array;
    boolean warning;
    public InvoicePreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        warning=true;
        return inflater.inflate(R.layout.fragment_invoice_preview, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO Get invoice data from InvoiceCreateActivity to populate list.
        previewListView = (ListView) getView().findViewById(R.id.fragmentInvoicePreview_previewListView);

        previewListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub



                if (warning)
                {
                    Toast.makeText(getContext(), "Hold again to delete line!", Toast.LENGTH_SHORT).show();
                    warning=false;
                    return true;
                }
                else {
                    ((InvoiceCreateActivity) getActivity()).RemoveInvoiceLine(pos);
                    array = ((InvoiceCreateActivity) getActivity()).GetInvoiceLines();
                    invoiceAdapter = new InvoiceAdapter(getContext(), array);
                    previewListView.setAdapter(invoiceAdapter);
                    Refresh();
                    return true;
                }
            }
        });

        array = ((InvoiceCreateActivity)getActivity()).GetInvoiceLines();

        invoiceAdapter = new InvoiceAdapter(getContext(),array);
        previewListView.setAdapter(invoiceAdapter);
        Refresh();

    }





    private ArrayList<InvoiceLine> getInvoiceLines() {

        return ((InvoiceCreateActivity) getActivity()).GetInvoiceLines();

    }

    public void Refresh() {
        invoiceAdapter.notifyDataSetChanged();
    }

    private class InvoiceAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<InvoiceLine> invoiceLines;

        InvoiceAdapter(Context context, ArrayList<InvoiceLine> invoiceLines) {
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
                convertView = View.inflate(context, R.layout.invoicelistitem, null);
                TextView line1 = (TextView) convertView.findViewById(R.id.invoicelistitem_line1TextView);
                TextView line2 = (TextView) convertView.findViewById(R.id.invoicelistitem_line2TextView);
                line1.setText(invoiceLines.get(position).getType());
                line2.setText("$ "+ invoiceLines.get(position).getPrice()+"0 @ "+invoiceLines.get(position).getQuantity()+" "+invoiceLines.get(position).getUnits());

            }
            return convertView;
        }
    }
}
