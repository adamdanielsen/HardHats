package project.senior.hardhats;
 	import java.util.regex.Pattern;



//This class is untested.

public class HardHatsFunctions {

    private String regexUsername = "^[a-zA-Z0-9]{5,20}+$";
    //Only A to Z upper or lower case and numbers 5 to 20 characters. I think this one works

    private String otherRegexUsername = "^\\w{5,20}";
    //Allegedly this one blocks special characters, 5-250 characters

    private String regexPassword = "^[a-zA-Z0-9!$_]+$";
    //I made this one up it probably doesn't work
    //todo fix
    private String otherRegexPassword = "^[a-zA-Z0-9!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~]+$";
    //Made this one up too, should
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



