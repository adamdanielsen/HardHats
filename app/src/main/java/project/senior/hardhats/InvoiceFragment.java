package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment extends Fragment {
    Button generateInvoiceButton;


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
        generateInvoiceButton=(Button) getView().findViewById(R.id.fragmentOptions_GenerateInvoiceButton);
        generateInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MenuActivity)getActivity()).SwitchToGenerateInvoiceActivity();


            }
        });

    }
}
