package project.senior.hardhats;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project.senior.hardhats.Documents.Invoice;
import project.senior.hardhats.Documents.InvoiceLine;

/**
 * A simple {@link Fragment} subclass.
 */

public class InvoiceViewDetailFragment extends Fragment {

    TextView totalTextView;
    InvoiceAdapter invoiceAdapter;
    ArrayList<InvoiceLine> array;
    int previousPosition;
    private static final DecimalFormat df = new DecimalFormat("$0.00");
    double total;
    Invoice currentInvoice;
    TextView displayTextView;
    Button sendEmailButton;
    public InvoiceViewDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        total=0;
        try {
            String selectedInvoiceId = ((InvoiceFragment)getParentFragment()).getSelectedID();
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
        return inflater.inflate(R.layout.fragment_invoice_view_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels;
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        view.setMinimumHeight(screenHeight - actionBarHeight + 5);






        displayTextView = (TextView) view.findViewById(R.id.fragmentinvoiceviewdetail_displayTextView);
        displayTextView.setText(currentInvoice.createTxtString());
        sendEmailButton = (Button) view.findViewById(R.id.fragmentinvoiceviewdetail_sendEmail);
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundWorker sendEmailWorker = new BackgroundWorker();
                DataContainer dataContainer = new DataContainer();
                dataContainer.type="SendEmail";
                dataContainer.phpVariableNames.add("toAddress");
                dataContainer.phpVariableNames.add("invoicestring");
                dataContainer.dataPassedIn.add(currentInvoice.getCustomerAddress().getEmailAddress());
                dataContainer.dataPassedIn.add(currentInvoice.createTxtString());
                String s="";
                try {
                    sendEmailWorker.execute(dataContainer).get();
                    Toast.makeText(getContext(), "Allow a few minutes for the invoice to arrive", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                AlertDialog.Builder box = new AlertDialog.Builder(getContext());
                box.setMessage("s");
                box.show();

            }
        });

        //invoiceAdapter = new InvoiceAdapter(getContext(),array);
        //previewListView.setAdapter(invoiceAdapter);
        //Refresh();

    }


    public void Refresh()
    {
        invoiceAdapter.notifyDataSetChanged();
        total=0;
        for (InvoiceLine line : array)

        {

            total+=line.getLineTotal();

        }

        totalTextView.setText("WORKING TOTAL: "+df.format(total));
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
                //TextView line1 = (TextView) convertView.findViewById(R.id.invoicelistitem_line1TextView);
                //TextView line2 = (TextView) convertView.findViewById(R.id.invoicelistitem_line2TextView);
                //line1.setText(invoiceLines.get(position).getType());
                //line2.setText("$ "+ invoiceLines.get(position).getPrice()+"0 @ "+invoiceLines.get(position).getQuantity()+" "+invoiceLines.get(position).getUnits());
            }
            return convertView;
        }
    }
}
