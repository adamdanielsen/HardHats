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

import project.senior.hardhats.Documents.InvoiceLine;


/**
 * A simple {@link Fragment} subclass.
 */


public class InvoicePreviewFragment extends Fragment {

    ListView previewListView;

    public InvoicePreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice_preview, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO Get invoice data from InvoiceCreate to populate list.
        ArrayList<InvoiceLine> invoiceLines = getInvoiceLines();
        previewListView = (ListView) getView().findViewById(R.id.fragmentInvoicePreview_previewListView);



        ArrayList<InvoiceLine> array = ((InvoiceCreate)getActivity()).GetInvoiceLines();

        InvoiceAdapter invoiceAdapter = new InvoiceAdapter(getContext(),array);
        previewListView.setAdapter(invoiceAdapter);
        invoiceAdapter.notifyDataSetChanged();

    }





    private ArrayList<InvoiceLine> getInvoiceLines() {

        return ((InvoiceCreate) getActivity()).GetInvoiceLines();

    }

    private class InvoiceAdapter extends BaseAdapter
    {
        Context context;
        ArrayList<InvoiceLine> invoiceLines;

        public InvoiceAdapter(Context context, ArrayList<InvoiceLine> invoiceLines) {
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
            View v =null;
            if (convertView==null) {
                v = View.inflate(context, R.layout.invoicelistitem, null);
                //need to declare textviews and set them equal to variables in the invoiceline
                TextView line1 = (TextView) v.findViewById(R.id.invoicelistitem_line1TextView);
                TextView line2 = (TextView) v.findViewById(R.id.invoicelistitem_line2TextView);
                line1.setText(invoiceLines.get(position).getType());
                line2.setText("$ "+ invoiceLines.get(position).getPrice()+"0 @ "+invoiceLines.get(position).getQuantity()+" "+invoiceLines.get(position).getUnits());

            }
            return v;
        }
    }
}
