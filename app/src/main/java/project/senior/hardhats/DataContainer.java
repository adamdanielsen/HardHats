package project.senior.hardhats;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by on 10/10/2017.
 */

public class DataContainer {

    public String type;
    public ArrayList<String> phpVariableNames;
    public ArrayList<String> dataPassedIn;

    public DataContainer()
    {
        phpVariableNames = new ArrayList<>();
        dataPassedIn = new ArrayList<>();
    }

    DataContainer(ArrayList<String> temp_phpVariableNames,ArrayList<String> temp_dataPassedIn )
    {
        phpVariableNames = new ArrayList<>();
        dataPassedIn = new ArrayList<>();
        phpVariableNames=temp_phpVariableNames;
        dataPassedIn = temp_dataPassedIn;
    }

    DataContainer(String[] temp_phpVariableNames,String[] temp_dataPassedIn )
    {
        phpVariableNames = new ArrayList<>(Arrays.asList(temp_phpVariableNames));
        dataPassedIn = new ArrayList<>(Arrays.asList(temp_dataPassedIn));
    }

}
