package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class BackupAddressHandlerActivity extends AppCompatActivity {

    private EditText streetEditText;
    private EditText cityEditText;
    private EditText zipCodeEditText;
    private TextInputLayout streetTextInputLayout;
    private TextInputLayout cityTextInputLayout;
    private TextInputLayout zipCodeTextInputLayout;

    private String customer_state;
    private String addressToSendBack;
    private Spinner statespinner;
    private final AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position > 0) {
                customer_state = (String) statespinner.getItemAtPosition(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private Button continueButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_address_handler);
        streetEditText = findViewById(R.id.backup_streetEditText);
        cityEditText = findViewById(R.id.backup_cityEditText);
        zipCodeEditText = findViewById(R.id.backup_zipCodeEditText);
        streetTextInputLayout = findViewById(R.id.backup_streetTextInputLayout);
        cityTextInputLayout = findViewById(R.id.backup_cityTextInputLayout);
        zipCodeTextInputLayout = findViewById(R.id.backup_zipCodeTextInputLayout);


        statespinner = findViewById(R.id.backup_stateSpinner);
        continueButton = findViewById(R.id.backup_continue);
        cancelButton = findViewById(R.id.backup_cancel);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.states, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statespinner.setAdapter(adapter);
        statespinner.setOnItemSelectedListener(state_listener);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinue();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });
    }

    private void onContinue() {
        if (!Validate()) {
            return;
        }
        String street = streetEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String state = statespinner.getSelectedItem().toString();
        String zipcode = zipCodeEditText.getText().toString();
        String country = "USA";
        addressToSendBack = street +
                ", " +
                city +
                ", " +
                state +
                " " +
                zipcode +
                ", " +
                country;

        Intent data = new Intent();
        data.putExtra("address", addressToSendBack);
        setResult(RESULT_OK, data);
        finish();
    }

    private boolean Validate() {

        boolean validationFlag = true;
        if (streetEditText.getText().toString().trim().isEmpty()) {
            streetTextInputLayout.setError("Please fill out the street");
            requestFocus(streetEditText);
            validationFlag = false;
        } else {
            streetTextInputLayout.setErrorEnabled(false);
        }

        if (cityEditText.getText().toString().trim().isEmpty()) {
            cityTextInputLayout.setError("Please fill out the City");
            requestFocus(cityEditText);
            validationFlag = false;
        } else {
            cityTextInputLayout.setErrorEnabled(false);
        }

        if (zipCodeEditText.getText().toString().trim().isEmpty()) {
            zipCodeTextInputLayout.setError("Please fill out the Zip Code");
            requestFocus(zipCodeEditText);
            validationFlag = false;
        } else {
            zipCodeTextInputLayout.setErrorEnabled(false);
        }

        return validationFlag;
    }

    private void onCancel() {

        Intent data = new Intent();
        setResult(RESULT_CANCELED, data);
        finish();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
