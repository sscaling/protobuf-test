package com.shaunscaling.protobuftest;

import com.shaunscaling.protobuftest.protos.AddressBookProtos;
import com.shaunscaling.protobuftest.protos.AddressBookProtos.AddressBook;
import com.shaunscaling.protobuftest.protos.AddressBookProtos.Person;

import java.io.*;
import java.util.Random;

public class Generate {

    public static Random random = new Random();

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Specify number of people to generate and output filename\n  java <class> <n> <filename>");
            return;
        }

        int people = Integer.parseInt(args[0]);
        String filename = args[1];

        random.setSeed(System.currentTimeMillis());

        AddressBook addressBook = randomAddressBook(people);

        System.out.println(addressBook.toString());

        try (FileOutputStream fos = new FileOutputStream(new File(filename))) {
            addressBook.writeTo(fos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
    }

    public static AddressBook randomAddressBook(int peopleCount) {
        AddressBook.Builder addressBook = AddressBook.newBuilder();

        // Add required amount of addresses
        for (int i = 0; i < peopleCount; ++i) {
            addressBook.addPeople(randomPerson());
        }

//         Write the new address book back to disk.
//        FileOutputStream output = new FileOutputStream(args[0]);
        return addressBook.build();
//                .writeTo(output);
//        output.close();

    }

    public static AddressBookProtos.Person randomPerson() {
        Person.Builder person = Person.newBuilder();

        person.setId(random.nextInt());
        person.setName(randomAlphaNumeric(5));

        if (random.nextBoolean()) {
            person.setEmail(randomAlphaNumeric(4) + "@" + randomAlphaNumeric(6) + ".com");
        }

        while (random.nextBoolean()) {
            Person.PhoneNumber.Builder phoneNumber =
                    Person.PhoneNumber.newBuilder().setNumber(randomNumeric(7));

            switch (random.nextInt(3)) {
                case 0:
                    phoneNumber.setType(Person.PhoneType.MOBILE);
                    break;
                case 1:
                    phoneNumber.setType(Person.PhoneType.HOME);
                    break;
                case 2:
                    phoneNumber.setType(Person.PhoneType.WORK);
                    break;
            }

            person.addPhones(phoneNumber);
        }

        return person.build();

    }

    private static String data = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String randomAlphaNumeric(int size) {
        char buf[] = new char[size];
        for (int ch = 0; ch < size; ch++) {
            buf[ch] = data.charAt(random.nextInt(data.length()));
        }
        return new String(buf);
    }

    private static String randomNumeric(int size) {
        char buf[] = new char[size];
        for (int ch = 0; ch < size; ch++) {
            buf[ch] = data.charAt(random.nextInt(10)); // only use first 10 digits of 0-indexed array
        }
        return new String(buf);
    }
}
