package com.vian4.contactselector;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

public class ContactDB {
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> selectedContacts;
    
    public ContactDB() throws FileNotFoundException {
        String resource = "contacts.json";
        InputStream is = ContactDB.class.getResourceAsStream("/" + resource);
        if (is == null) {
            throw new FileNotFoundException("Unable to find " + resource);
        }
        JSONTokener tokener = new JSONTokener(is);
        JSONArray contactsArray = new JSONArray(tokener);
        int numContacts = contactsArray.length();
        contacts = new ArrayList<>(numContacts);
        for (int i = 0; i < numContacts; i++) {
            JSONObject contact = contactsArray.getJSONObject(i);
            String nameField = contact.getString("name");
            String jobField = contact.getString("job");
            String emailField = contact.getString("email");
            String phoneField = contact.getString("phone");
            contacts.add(new Contact(nameField, Contact.parseSchool(jobField), Contact.parseRole(jobField), emailField, phoneField));
        }

        resetSelected();
    }

    public int size() {
        return contacts.size();
    }

    public int selectedSize() {
        return selectedContacts.size();
    }

    public void resetSelected() {
        selectedContacts = new ArrayList<>(contacts);
    }

    public void removeSelectedFromContacts() {
        for (Contact contact: selectedContacts) {
            boolean rem = contacts.remove(contact);
            if (!rem) {
                return;
            }
        }
    }

    public void randomlySelect(int number) {
        ArrayList<Contact> randomlySelectedContacts = new ArrayList<>(number);
        for (int i = 0; i < number; i++) {
            int randomSelection = randFunction(0, selectedContacts.size());
            randomlySelectedContacts.add(selectedContacts.get(randomSelection));
            selectedContacts.remove(randomSelection);
        }
        selectedContacts = randomlySelectedContacts;
    }

    enum SaveType {
        PHONE,
        EMAIL,
        NAME
    }
    public String saveSelectedToDisk(SaveType saveType) throws Exception {
        String fileName = (new SimpleDateFormat("yyyy-MM-dd HH-mm-ss")).format(new Date()) + ".txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            String contactString = null;
            for (Contact contact: selectedContacts) {
                switch (saveType) {
                    case PHONE: {
                        contactString = contact.getPhone();
                        if (contactString.equals("")) {
                            contactString = "[No Phone]";
                        }
                        break;
                    }
                    case EMAIL: {
                        contactString = contact.getEmail();
                        if (contactString.equals("")) {
                            contactString = "[No Email]";
                        }
                        break;
                    }
                    case NAME: {
                        contactString = contact.getName();
                        if (contactString.equals("")) {
                            contactString = "[No Name]";
                        }
                        break;
                    }
                }
                writer.write(contactString + "\n");
            }
            writer.close();
        } catch (IOException ignored) {
            throw new Exception("Unable to save data to " + fileName);
        }
        return fileName;
    }

    public int randFunction(int lowerInclusive, int upper) {
        return (int) (Math.random() * (upper - lowerInclusive) + lowerInclusive);
    }

    public void removeNotAt(School school) {
        for (int i = 0; i < selectedContacts.size(); i++) {
            if (selectedContacts.get(i).getSchool() != school) {
                selectedContacts.remove(selectedContacts.get(i));
                i--;
            }
        }
    }

    public void removeNotAt(Role role) {
        for (int i = 0; i < selectedContacts.size(); i++) {
            if (selectedContacts.get(i).getRole() != role) {
                selectedContacts.remove(selectedContacts.get(i));
                i--;
            }
        }
    }
}
