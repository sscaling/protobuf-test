package com.shaunscaling.protobuftest;

import com.shaunscaling.protobuftest.protos.AddressBookProtos.AddressBook;
import com.shaunscaling.protobuftest.protos.AddressBookProtos.Person;

import java.io.FileInputStream;
import java.io.IOException;

public class Read {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage:  java com.shaunscaling.protobuftest.Read <file>");
            System.exit(-1);
        }

        // Read the existing address book.
        AddressBook addressBook =
                AddressBook.parseFrom(new FileInputStream(args[0]));

        Print(addressBook);

        System.out.println("Done");
    }

    static void Print(AddressBook addressBook) {
        for (Person person: addressBook.getPeopleList()) {
            System.out.println("Person ID: " + person.getId());
            System.out.println("  Name: " + person.getName());
            if (person.hasEmail()) {
                System.out.println("  E-mail address: " + person.getEmail());
            }

            for (Person.PhoneNumber phoneNumber : person.getPhonesList()) {
                switch (phoneNumber.getType()) {
                    case MOBILE:
                        System.out.print("  Mobile phone #: ");
                        break;
                    case HOME:
                        System.out.print("  Home phone #: ");
                        break;
                    case WORK:
                        System.out.print("  Work phone #: ");
                        break;
                }
                System.out.println(phoneNumber.getNumber());
            }
        }
    }
}
