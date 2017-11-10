package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import project.senior.hardhats.Documents.InvoiceLine;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLineFragment extends Fragment {


    public AddLineFragment() {
        // Required empty public constructor
    }


    InvoiceLine invoiceLine;
    EditText DescriptionEditText;
    EditText QuantityEditText;
    EditText UnitsEditText;
    EditText PriceEditText;
    Spinner UnitsSpinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_line, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // TODO Create edit texts, send data back to activity on click
            invoiceLine = new InvoiceLine();
        DescriptionEditText= (EditText) getView().findViewById(R.id.fragmentaddline_DescriptionEditText);
        QuantityEditText= (EditText) getView().findViewById(R.id.fragmentaddline_QuantityEditText);
        UnitsEditText= (EditText) getView().findViewById(R.id.fragmentaddline_UnitsEditText);

        UnitsEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        PriceEditText= (EditText) getView().findViewById(R.id.fragmentaddline_PriceEditText);
        UnitsSpinner=(Spinner) getView().findViewById(R.id.fragmentaddline_UnitsSpinner);
        UnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==5) {

                    UnitsEditText.setVisibility(View.VISIBLE);
                    UnitsEditText.setText("");
                }
                else
                {
                    UnitsEditText.setText("");
                    UnitsEditText.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        UnitsEditText.setVisibility(View.INVISIBLE);


        ArrayList<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("EA");
        spinnerArray.add("HR");
        spinnerArray.add("LB");
        spinnerArray.add("somethings");
        spinnerArray.add("somethings");
        spinnerArray.add("Custom...");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simplelistitem, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UnitsSpinner.setAdapter(adapter);



        //String selected = sItems.getSelectedItem().toString();
       // if (selected.equals("what ever the option was")) {
        //}



    }

    public InvoiceLine sendLineBack()
    {
        invoiceLine.setType(DescriptionEditText.getText().toString());
        invoiceLine.setQuantity(Integer.valueOf(QuantityEditText.getText().toString()));
        if (UnitsSpinner.getSelectedItem().toString().equals("Custom..."))
        {
            invoiceLine.setUnits(UnitsEditText.getText().toString());

        }
            else {
            invoiceLine.setUnits(UnitsSpinner.getSelectedItem().toString());
        }
        invoiceLine.setPrice(Double.valueOf(PriceEditText.getText().toString()));
        invoiceLine.setLineTotal();
        invoiceLine.Verify();
        return invoiceLine;
    }
}
