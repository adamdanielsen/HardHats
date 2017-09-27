package project.senior.hardhats;
 	import java.util.regex.Pattern;



//This class is untested.

public class HardHatsFunctions {

    private String regexUsername = "^[a-zA-Z0-9]+$";
    //Only A to Z upper or lower case and numbers\

    private String otherRegexUsername = "\\w{5,255}";
    //Allegedly this one blocks special characters

    private String regexPassword = "^[a-zA-Z0-9!$_]+$";
    //I made this one up it probably doesn't work

    public boolean ValidateUsername (String username)
    {

        if (!Pattern.matches(regexUsername,username))
        {
            return false;
        }
        return true;
    }


    public boolean ValidatePassword (String password)
    {

        if (!Pattern.matches(regexPassword,password))
        {
            return false;
        }
        return true;
    }

}



