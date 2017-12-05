package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    TextInputLayout DescriptionTextInputLayout;
    TextInputLayout QuantityTextInputLayout;
    TextInputLayout UnitsTextInputLayout;
    TextInputLayout PriceTextInputLayout;

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
        DescriptionTextInputLayout= (TextInputLayout) getView().findViewById(R.id.fragmentaddline_DescriptionTextInputLayout);
        QuantityTextInputLayout= (TextInputLayout) getView().findViewById(R.id.fragmentaddline_QuantityTextInputLayout);
        UnitsTextInputLayout= (TextInputLayout) getView().findViewById(R.id.fragmentaddline_UnitsTextInputLayout);
        //UnitsEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        PriceTextInputLayout= (TextInputLayout) getView().findViewById(R.id.fragmentaddline_PriceTextInputLayout);




        UnitsSpinner=(Spinner) getView().findViewById(R.id.fragmentaddline_UnitsSpinner);
        UnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==3) {
                    UnitsEditText.setVisibility(View.VISIBLE);
                    UnitsEditText.setText("");
                    UnitsTextInputLayout.setErrorEnabled(false);
                    UnitsEditText.setHint("Units");
                }
                else
                {
                    UnitsEditText.setText("");
                    UnitsEditText.setVisibility(View.INVISIBLE);
                    UnitsTextInputLayout.setErrorEnabled(false);
                    UnitsEditText.setHint("");
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
        spinnerArray.add("Custom...");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.simplelistitem, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UnitsSpinner.setAdapter(adapter);
        //String selected = sItems.getSelectedItem().toString();
       // if (selected.equals("what ever the option was")) {
        //}
    }





    public boolean Validate()
    {

      //  TextInputLayout DescriptionTextInputLayout;
    //    TextInputLayout QuantityTextInputLayout;
  //      TextInputLayout UnitsTextInputLayout;
//        TextInputLayout PriceTextInputLayout;

        if (DescriptionEditText.getText().toString().trim().isEmpty()) {
            DescriptionTextInputLayout.setError("Please enter a description");
            requestFocus(DescriptionEditText);
            QuantityTextInputLayout.setErrorEnabled(false);
            PriceTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            DescriptionTextInputLayout.setErrorEnabled(false);
            QuantityTextInputLayout.setErrorEnabled(false);
            PriceTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
        }

        if (QuantityEditText.getText().toString().trim().isEmpty()) {
            QuantityTextInputLayout.setError("Please enter a quantity");
            requestFocus(QuantityEditText);

            DescriptionTextInputLayout.setErrorEnabled(false);
            PriceTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            DescriptionTextInputLayout.setErrorEnabled(false);
            QuantityTextInputLayout.setErrorEnabled(false);
            PriceTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
        }

        if (UnitsSpinner.getSelectedItem().toString().equals("Custom...")) {
            if (UnitsEditText.getText().toString().trim().isEmpty()) {
                UnitsTextInputLayout.setError("Please enter Units");
                requestFocus(DescriptionEditText);

                DescriptionTextInputLayout.setErrorEnabled(false);
                QuantityTextInputLayout.setErrorEnabled(false);
                PriceTextInputLayout.setErrorEnabled(false);


                return false;
            } else if (UnitsEditText.getText().toString().trim().length() == 1) {
                UnitsTextInputLayout.setError("Custom Unit should be use two characters");
                requestFocus(DescriptionEditText);

                DescriptionTextInputLayout.setErrorEnabled(false);
                QuantityTextInputLayout.setErrorEnabled(false);
                PriceTextInputLayout.setErrorEnabled(false);
                return false;
            } else {
                UnitsTextInputLayout.setErrorEnabled(false);
            }
        }

        if (PriceEditText.getText().toString().trim().isEmpty()) {
            PriceTextInputLayout.setError("Please enter a Price");
            requestFocus(PriceEditText);

            DescriptionTextInputLayout.setErrorEnabled(false);
            QuantityTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
            return false;
        }
        else {
            DescriptionTextInputLayout.setErrorEnabled(false);
            QuantityTextInputLayout.setErrorEnabled(false);
            PriceTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    public InvoiceLine sendLineBack()
    {

        if (!Validate())
        {
            return null;
        }


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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
