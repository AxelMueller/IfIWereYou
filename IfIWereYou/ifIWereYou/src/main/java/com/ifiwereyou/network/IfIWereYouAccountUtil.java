package com.ifiwereyou.network;

import android.accounts.Account;
import android.accounts.AccountManager;

import com.ifiwereyou.IfIWereYouApplication;

/**
 * Created by D060670 on 01.04.2015.
 */
public class IfIWereYouAccountUtil {

    /*
    Brauchen wir, falls wir nen SyncAdapter laufen lassen wollen.
     */

    public static final String ACCOUNT_TYPE = "com.ifiwereyou";

    public static Account addAccount(String email, String password) {
        removeAccounts();
        Account newAccount = new Account(email, ACCOUNT_TYPE);
        AccountManager accountManager = AccountManager.get(IfIWereYouApplication.getContext());
        accountManager.addAccountExplicitly(newAccount, password, null);
        return null;
    }

    public static Account getAccount() {
        Account[] accounts = AccountManager.get(IfIWereYouApplication.getContext()).getAccountsByType(ACCOUNT_TYPE);
        return (accounts.length == 1) ? accounts[0] : null;
    }

    private static void removeAccounts() {
        AccountManager accountManager = AccountManager.get(IfIWereYouApplication.getContext());
        for (Account account : accountManager.getAccountsByType(ACCOUNT_TYPE)) {
            accountManager.removeAccount(account, null, null);
        }
    }

}