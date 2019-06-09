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

import java.text.DecimalFormat;
import java.util.ArrayList;

import project.senior.hardhats.documents.InvoiceLine;


/**
 * A simple {@link Fragment} subclass.
 */


public class InvoiceLinePreviewFragment extends Fragment {

    private static final DecimalFormat df = new DecimalFormat("$0.00");
    private ListView previewListView;
    private TextView totalTextView;
    private InvoiceAdapter invoiceAdapter;
    private ArrayList<InvoiceLine> array;
    private int previousPosition;
    private double total;


    public InvoiceLinePreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        previousPosition = -1;
        total = 0;
        return inflater.inflate(R.layout.fragment_invoice_line_preview, container, false);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO Get invoice data from InvoiceCreateActivity to populate list.
        previewListView = getView().findViewById(R.id.fragmentInvoiceLinePreview_previewListView);
        totalTextView = getView().findViewById(R.id.fragmentInvoiceLinePreview_totalTextView);
        previewListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub
                if (previousPosition != pos) {
                    Toast.makeText(getContext(), "Hold again to delete line!", Toast.LENGTH_SHORT).show();
                    previousPosition = pos;
                    return true;
                } else {
                    ((InvoiceCreateActivity) getActivity()).RemoveInvoiceLine(pos);
                    array = ((InvoiceCreateActivity) getActivity()).GetInvoiceLines();
                    invoiceAdapter = new InvoiceAdapter(getContext(), array);
                    previewListView.setAdapter(invoiceAdapter);
                    previousPosition = -1;
                    Refresh();
                    return true;
                }
            }
        });

        array = ((InvoiceCreateActivity) getActivity()).GetInvoiceLines();

        invoiceAdapter = new InvoiceAdapter(getContext(), array);
        previewListView.setAdapter(invoiceAdapter);
        Refresh();

    }


    private void Refresh() {
        invoiceAdapter.notifyDataSetChanged();
        total = 0;
        for (InvoiceLine line : array) {

            total += line.getLineTotal();

        }

        totalTextView.setText("WORKING TOTAL: " + df.format(total));
    }

    private class InvoiceAdapter extends BaseAdapter {
        final Context context;
        final ArrayList<InvoiceLine> invoiceLines;

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

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.invoicelistitem, null);
            }
            TextView line1 = convertView.findViewById(R.id.invoicelistitem_line1TextView);
            TextView line2Left = convertView.findViewById(R.id.invoicelistitem_line2LeftTextView);
            TextView line2Right = convertView.findViewById(R.id.invoicelistitem_line2RightTextView);

            String description = invoiceLines.get(position).getType();
            line1.setText(description);
            line2Left.setText(df.format(invoiceLines.get(position).getPrice()) + " @ " + invoiceLines.get(position).getQuantity() + " " + invoiceLines.get(position).getUnits());
            line2Right.setText(df.format(invoiceLines.get(position).getLineTotal()));

            return convertView;
        }
    }
}
