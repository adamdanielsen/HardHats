package project.senior.hardhats;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {

    public EditText USERID;
    private Button add_customer;
    private String selectedID;

    public CustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getChildFragmentManager().beginTransaction().add(R.id.customer_FrameLayout, new CustomerPreviewListFragment(), "CUSTOMERPREVIEWLIST").commit();

        add_customer = getView().findViewById(R.id.add_customer_button);
        add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCustomer.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag("CUSTOMERPREVIEWLIST");
        ((CustomerPreviewListFragment) fragment).Setup();
    }

    public String getSelectedID() {
        return selectedID;
    }

    public void setSelectedID(String id) {
        selectedID = id;
    }

    public void switchToDetailView() {
        //   backToPreviewButton.setVisibility(View.VISIBLE);
        //  getChildFragmentManager().beginTransaction().replace(R.id.invoice_FrameLayout, new InvoiceViewDetailFragment(),"INVOICEPREVIEWDETAIL").commit();

    }
}
