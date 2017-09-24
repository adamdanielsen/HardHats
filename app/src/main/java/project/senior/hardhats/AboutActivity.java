package project.senior.hardhats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by theev on 9/24/2017.
 */

public class AboutActivity extends AppCompatActivity {
    ListView authorListView;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String Author1="Johnathan Cucuzza";
        String Author2="Evan Esatto";
        String Author3="Adam Danielsen";
        String Author4="Michael Kruger";
        String Author5="Oscar Dass";
        ArrayList<String> authorList= new ArrayList<String>();
        authorListView=(ListView) findViewById(R.id.about_authorListView);
        authorList.add(Author1);
        authorList.add(Author2);
        authorList.add(Author3);
        authorList.add(Author4);
        authorList.add(Author5);
        Collections.shuffle(authorList);

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,R.layout.simplelistitem,authorList);
        authorListView.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

    }
}
