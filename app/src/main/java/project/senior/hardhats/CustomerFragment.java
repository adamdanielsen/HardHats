
package project.senior.hardhats;


import android.content.Intent;
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
public class CustomerFragment extends Fragment {

    Button add_customer;
    ListView listView;
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
        add_customer = (Button) getView().findViewById(R.id.add_customer_button);

        listView = (ListView) getView().findViewById(R.id.fragmentCustomer_ListView);
        ArrayList<String> list = new ArrayList<>();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.simplelistitem,list);

        list.add("Harry                                        9/15/15");

        list.add("Tom                                          6/05/16");

        list.add("Bill                                            4/25/17");


        listView.setAdapter(adapter);
        add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCustomer.class);
                startActivity(intent);

            }
        });
    //change stuff here to give logic to layout

    }
}
