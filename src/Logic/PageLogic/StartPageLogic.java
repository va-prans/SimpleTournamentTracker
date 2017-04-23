package Logic.PageLogic;

import DatabaseWrapper.DatabaseCalls;

/**
 * Created by NSA on 22-04-2017.
 */
public class StartPageLogic {

    public boolean adminSuccessfulLogin(String username, String password) {

        boolean loginSuccess = false;
        DatabaseCalls databaseCalls = new DatabaseCalls();
        String realPassword = databaseCalls.getPassword(username);
        if (password.equals(realPassword)) {loginSuccess = true;}
        return loginSuccess;

    }


}
