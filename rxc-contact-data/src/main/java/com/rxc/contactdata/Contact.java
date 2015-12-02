package com.rxc.contactdata;


import java.util.UUID;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class Contact {
  public final UUID id;
  public final String firstName;
  public final String lastName;


  public Contact(final UUID id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static class Builder implements com.rxc.lang.Builder<Contact> {
    public String firstName;
    public String lastName;


    @Override
    public Contact build() {
      return new Contact(null, firstName, lastName);
    }
  }
}

