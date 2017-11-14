package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment extends Fragment {
    Button generateInvoiceButton;
    ListView listView;

    public InvoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invoice, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        generateInvoiceButton=(Button) getView().findViewById(R.id.fragmentInvoice_GenerateInvoiceButton);
        listView = (ListView) getView().findViewById(R.id.fragmentInvoice_ListView);
        ArrayList<String> list = new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.simplelistitem,list);

        list.add("");

        list.add("Tom                                          6/05/16");

        list.add("Bill                                            4/25/17");


        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        generateInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MenuActivity)getActivity()).SwitchToGenerateInvoiceActivity();


            }
        });

    }
}
