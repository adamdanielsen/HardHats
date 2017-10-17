package project.senior.hardhats;
import java.util.regex.Pattern;

//This class is untested.

public class HardHatsFunctions {


    private String regexUsername = "^[a-zA-Z0-9]{5,20}+$";
    //Only A to Z upper or lower case and numbers 5 to 20 characters. I think this one works

    private String otherRegexUsername = "^\\w{5,20}";
    //Allegedly this one blocks special characters, 5-20 characters

    private String regexPassword = "^[a-zA-Z0-9!$_]+$";
    //I made this one up it probably doesn't work

    private String otherRegexPassword = "^[a-zA-Z0-9!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~]+$";
    //Made this one up too, should allow most characters for passwords.

    private String regexName = "^[A-Za-z][1,100]+$";

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



