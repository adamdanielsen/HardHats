package project.senior.hardhats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class BackupAddressHandlerActivity extends AppCompatActivity {

    EditText streetEditText;
    EditText cityEditText;
    EditText zipcodeEditText;
    String customer_state;
    String addressToSendBack;
    Spinner statespinner;
    Button continueButton;
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_address_handler);
        streetEditText=(EditText) findViewById(R.id.backup_streetEditText);
        cityEditText=(EditText) findViewById(R.id.backup_cityEditText);
        zipcodeEditText=(EditText) findViewById(R.id.backup_zipCodeEditText);
        statespinner = (Spinner) findViewById(R.id.backup_stateSpinner);
        continueButton=(Button) findViewById(R.id.backup_continue);
        cancelButton=(Button) findViewById(R.id.backup_cancel);
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
        if (Verify())
        {
         return;
        }
        String street= streetEditText.getText().toString();
        String city= cityEditText.getText().toString();
        String state=statespinner.getSelectedItem().toString();
        String zipcode=zipcodeEditText.getText().toString();
        String country="USA";
        StringBuilder addressBuild= new StringBuilder();
        addressBuild.append(street);
        addressBuild.append(", ");
        addressBuild.append(city);
        addressBuild.append(", ");
        addressBuild.append(state);
        addressBuild.append(" ");
        addressBuild.append(zipcode);
        addressBuild.append(", ");
        addressBuild.append(country);
        addressToSendBack=addressBuild.toString();

        Intent data = new Intent();
        data.putExtra("address",addressToSendBack);
        setResult(RESULT_OK,data);
        finish();
    }

    private boolean Verify() {
        //todo finish this
        return true;
    }


    private void onCancel() {

        Intent data = new Intent();
        setResult(RESULT_CANCELED,data);
        finish();
    }

    private AdapterView.OnItemSelectedListener state_listener = new AdapterView.OnItemSelectedListener() {
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


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
