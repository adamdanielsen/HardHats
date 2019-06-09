package project.senior.hardhats;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

//import android.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment extends Fragment {
    ListView listView;
    private Button generateInvoiceButton;
    private Button backToPreviewButton;
    private String selectedID;

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

        getChildFragmentManager().beginTransaction().replace(R.id.invoice_FrameLayout, new InvoicePreviewListFragment(), "INVOICEPREVIEWLIST").commit();
        generateInvoiceButton = getView().findViewById(R.id.fragmentInvoice_GenerateInvoiceButton);
        backToPreviewButton = getView().findViewById(R.id.fragmentInvoice_backToPreviewButton);
        backToPreviewButton.setVisibility(View.INVISIBLE);
        backToPreviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getChildFragmentManager().beginTransaction().replace(R.id.invoice_FrameLayout, new InvoicePreviewListFragment(), "INVOICEPREVIEWLIST").commit();
                backToPreviewButton.setVisibility(View.INVISIBLE);

            }
        });


        generateInvoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MenuActivity) getActivity()).SwitchToGenerateInvoiceActivity();

            }
        });

    }

    public void onDeleteInvoice() {


        try {
            ((MenuActivity) getActivity()).Setup();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getChildFragmentManager().beginTransaction().replace(R.id.invoice_FrameLayout, new InvoicePreviewListFragment(), "INVOICEPREVIEWLIST").commitAllowingStateLoss();
        backToPreviewButton.setVisibility(View.INVISIBLE);
    }

    public String getSelectedID() {
        return selectedID;
    }

    public void setSelectedID(String id) {


        selectedID = id;

    }

    public void switchToDetailView() {
        backToPreviewButton.setVisibility(View.VISIBLE);
        getChildFragmentManager().beginTransaction().replace(R.id.invoice_FrameLayout, new InvoiceViewDetailFragment(), "INVOICEPREVIEWDETAIL").commit();

    }
}
