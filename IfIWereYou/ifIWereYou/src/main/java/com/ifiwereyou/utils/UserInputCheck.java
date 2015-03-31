package com.ifiwereyou.utils;

import android.text.TextUtils;

public class UserInputCheck {

	/**
	 * Checks whether the given email address complies with the pattern for
	 * email addresses.
	 * 
	 * @param mailAddress
	 *            the email address to check
	 * @return true if the email address is valid
	 */
	public final static boolean isValidEmail(String mailAddress) {
		return !TextUtils.isEmpty(mailAddress)
				&& android.util.Patterns.EMAIL_ADDRESS.matcher(mailAddress)
						.matches();
	}

}