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

import project.senior.hardhats.documents.InvoiceLine;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLineFragment extends Fragment {


    private InvoiceLine invoiceLine;
    private EditText DescriptionEditText;
    private EditText QuantityEditText;
    private EditText UnitsEditText;
    private EditText PriceEditText;
    private TextInputLayout DescriptionTextInputLayout;
    private TextInputLayout QuantityTextInputLayout;
    private TextInputLayout UnitsTextInputLayout;
    private TextInputLayout PriceTextInputLayout;
    private Spinner UnitsSpinner;
    public AddLineFragment() {
        // Required empty public constructor
    }

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

        DescriptionEditText = getView().findViewById(R.id.fragmentaddline_DescriptionEditText);
        QuantityEditText = getView().findViewById(R.id.fragmentaddline_QuantityEditText);
        UnitsEditText = getView().findViewById(R.id.fragmentaddline_UnitsEditText);
        UnitsEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        PriceEditText = getView().findViewById(R.id.fragmentaddline_PriceEditText);
        DescriptionTextInputLayout = getView().findViewById(R.id.fragmentaddline_DescriptionTextInputLayout);
        QuantityTextInputLayout = getView().findViewById(R.id.fragmentaddline_QuantityTextInputLayout);
        UnitsTextInputLayout = getView().findViewById(R.id.fragmentaddline_UnitsTextInputLayout);
        UnitsTextInputLayout.setHint("");

        //UnitsEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
        PriceTextInputLayout = getView().findViewById(R.id.fragmentaddline_PriceTextInputLayout);

        UnitsSpinner = getView().findViewById(R.id.fragmentaddline_UnitsSpinner);
        UnitsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    UnitsEditText.setVisibility(View.VISIBLE);
                    UnitsEditText.setText("");
                    UnitsTextInputLayout.setErrorEnabled(false);
                    UnitsTextInputLayout.setHint("Units");
                } else {
                    UnitsEditText.setText("");
                    UnitsEditText.setVisibility(View.INVISIBLE);
                    UnitsTextInputLayout.setErrorEnabled(false);
                    UnitsTextInputLayout.setHint("");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        UnitsEditText.setVisibility(View.INVISIBLE);


        ArrayList<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("EA");
        spinnerArray.add("HR");
        spinnerArray.add("LB");
        spinnerArray.add("Custom...");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.simplelistitem, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UnitsSpinner.setAdapter(adapter);

    }


    private boolean Validate() {

        if (DescriptionEditText.getText().toString().trim().isEmpty()) {
            DescriptionTextInputLayout.setError("Please enter a description");
            requestFocus(DescriptionEditText);
            QuantityTextInputLayout.setErrorEnabled(false);
            PriceTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
            return false;
        } else {
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
        } else {
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
        } else {
            DescriptionTextInputLayout.setErrorEnabled(false);
            QuantityTextInputLayout.setErrorEnabled(false);
            PriceTextInputLayout.setErrorEnabled(false);
            UnitsTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    public InvoiceLine sendLineBack() {

        if (!Validate()) {
            return null;
        }


        invoiceLine.setType(DescriptionEditText.getText().toString());
        invoiceLine.setQuantity(Integer.valueOf(QuantityEditText.getText().toString()));
        if (UnitsSpinner.getSelectedItem().toString().equals("Custom...")) {
            invoiceLine.setUnits(UnitsEditText.getText().toString());


        } else {
            invoiceLine.setUnits(UnitsSpinner.getSelectedItem().toString());
        }
        invoiceLine.setPrice(Double.valueOf(PriceEditText.getText().toString()));
        invoiceLine.setLineTotal();
        return invoiceLine;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
