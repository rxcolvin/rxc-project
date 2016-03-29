package com.rxc.meta;

import com.rxc.lang.T2;

import static com.rxc.lang.Tuple.$;

//TODO: move to separate package
public class CommonDataDictionary {
    interface $ {
        String firstName = "firstName";
        String lastName = "lastName";
    }

    private static final T2<ValidState, String> VALID = $(ValidState.OK, null);

    public final FieldDef<String> firstName = new FieldDef<>(String.class, $.firstName, this::validName);
    public final FieldDef<String> lastName = new FieldDef<>(String.class, $.lastName, this::validName);


    private T2<ValidState, String> validName(final String s) {

        final T2<ValidState, String> ret;
        if (s.length() == 0) {
            ret = $(ValidState.ERROR, "Must have at least one character"); //TODO whole validation mechanism msut allow xlation.
        } else if (!onlyHasChars(s)) {
            ret = $(ValidState.ERROR, "Must have chars");
        } else {
            ret = VALID;
        }
        return ret;
    }

    private static boolean onlyHasChars(final String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isAlphabetic(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
