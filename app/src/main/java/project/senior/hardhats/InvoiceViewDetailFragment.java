package project.senior.hardhats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project.senior.hardhats.documents.Invoice;
import project.senior.hardhats.documents.InvoiceLine;

/**
 * A simple {@link Fragment} subclass.
 */

public class InvoiceViewDetailFragment extends Fragment {

    private static final DecimalFormat df = new DecimalFormat("$0.00");
    int previousPosition;
    private TextView totalTextView;
    private InvoiceAdapter invoiceAdapter;
    private ArrayList<InvoiceLine> array;
    private double total;
    private Invoice currentInvoice;
    private TextView displayTextView;
    private Button invoiceActionsButton;

    public InvoiceViewDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_invoice_view_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        displayTextView = view.findViewById(R.id.fragmentinvoiceviewdetail_displayTextView);
        invoiceActionsButton = view.findViewById(R.id.fragmentinvoiceviewdetail_invoiceActions);
        invoiceActionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInvoiceActionsClick();
            }
        });

    }

    public void onResume() {
        super.onResume();

        Setup();
    }


    private void onInvoiceActionsClick() {
        Intent startActions = new Intent(getActivity(), InvoiceActionsActivity.class);
        startActions.putExtra("currentinvoice", currentInvoice.getId());
        startActivityForResult(startActions, 1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == 1) {
            ((InvoiceFragment) getParentFragment()).onDeleteInvoice();
            return;
        }
        Setup();
    }

    public void Setup() {

        total = 0;
        try {
            String selectedInvoiceId = ((InvoiceFragment) getParentFragment()).getSelectedID();
            currentInvoice = new Invoice(selectedInvoiceId);
        } catch (InterruptedException e) {
            Log.d("InterruptedException", "onCreateView: Invoice failed to be retrieved");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.d("ExecutionException", "onCreateView: Invoice failed to be retrieved");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("JSONException", "onCreateView: Invoice failed to be retrieved");
            e.printStackTrace();
        }
        displayTextView.setText(currentInvoice.createPreviewString());

        //invoiceAdapter.notifyDataSetChanged();
        //total=0;
        //for (InvoiceLine line : array)

        //{

        //    total+=line.getLineTotal();

        //}

        //totalTextView.setText(getString(R.string.invoiceviewdetailfragment_total)+df.format(total));
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
                //TextView line1 = (TextView) convertView.findViewById(R.id.invoicelistitem_line1TextView);
                //TextView line2 = (TextView) convertView.findViewById(R.id.invoicelistitem_line2TextView);
                //line1.setText(invoiceLines.get(position).getType());
                //line2.setText("$ "+ invoiceLines.get(position).getPrice()+"0 @ "+invoiceLines.get(position).getQuantity()+" "+invoiceLines.get(position).getUnits());
            }
            return convertView;
        }
    }
}
