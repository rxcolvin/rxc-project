package com.rxc.meta;

import com.rxc.lang.T2;

import static com.rxc.lang.Tuple.*;

//TODO: move to separate page
public class CommonDataDictionary {

  public FieldMeta<String> firstName = new FieldMeta<>(String.class, "firstName", this::validName);


  private T2<ValidState, String> validName(final String s) {

    final T2<ValidState, String> ret;
    if (s.length() == 0) {
      ret = $(ValidState.ERROR, "Must have at least one character"); //TODO whole validation mechanism msut allow xlation.
    } else if (!onlyHasChars(s))  {
      ret = $(ValidState.ERROR, "Must have chars");
    } else {
      ret = $(ValidState.OK, "");
    }
    return ret;
  }

  private static boolean  onlyHasChars(final String s) {
    for (int i = 0; i < s.length() ; i++) {
       if (!Character.isAlphabetic(s.charAt(i))) {
         return false;
       }
    }
    return true;
  }
}
