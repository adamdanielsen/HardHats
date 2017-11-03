package project.senior.hardhats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class InvoiceCreate extends AppCompatActivity implements AddLineFragment.AddLineButtonInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_create);

        // TODO create a list that holds invoicelines, setup fragment switching(AddLine to InvoicePreview), setup buttons, do most logic here

    }

    @Override
    public void AddLine() {
        //TODO Not Sure if needed
    }
}
