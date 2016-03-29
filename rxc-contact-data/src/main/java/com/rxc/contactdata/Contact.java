package com.rxc.contactdata;


import java.util.UUID;
import java.util.function.Supplier;

/**
 * Created by richard.colvin on 13/11/2015.
 */
public class Contact {
  public final static String $name = "Contact";

  public final UUID id;
  public final String firstName;
  public final String lastName;


  public Contact(final UUID id, final String firstName, final String lastName) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public static class Builder implements Supplier<Contact> {
    public String firstName;
    public String lastName;


    @Override
    public Contact get() {
      return new Contact(null, firstName, lastName);
    }
  }
}

