package project.senior.hardhats;

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
import android.widget.TextView;

import org.json.JSONArray;
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


        //invoiceAdapter = new InvoiceAdapter(getContext(),array);
        //previewListView.setAdapter(invoiceAdapter);
        //Refresh();

    }

    private void Setup() throws JSONException {

        String invoiceID= ((InvoiceFragment) getParentFragment()).getSelectedID();

        DataContainer dataContainer = new DataContainer();
        //todo write that code and it should work?
        dataContainer.type="getinvoicelines";

        dataContainer.phpVariableNames.add("InvoiceID");

        dataContainer.phpVariableNames.add(invoiceID);

        BackgroundWorkerJSONArray getInvoiceLines = new BackgroundWorkerJSONArray();

        JSONArray jsonarray = getInvoiceLines.doInBackground(dataContainer);
        for (int i = 0 ; i<jsonarray.length(); i++)
        {
            InvoiceLine listItem = new InvoiceLine();
            listItem.setInvoiceFK(Integer.parseInt(invoiceID));
            listItem.setUnits(jsonarray.getJSONObject(i).getString("Units"));
            listItem.setType(jsonarray.getJSONObject(i).getString("Type"));
            listItem.setQuantity(jsonarray.getJSONObject(i).getInt("Quantity"));
            listItem.setUnits(jsonarray.getJSONObject(i).getString("Units"));
            array.add(listItem);
        }
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
