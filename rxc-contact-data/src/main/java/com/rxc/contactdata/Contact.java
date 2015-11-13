package com.rxc.contactdata;


import com.rxc.lang.UUID;

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
}

