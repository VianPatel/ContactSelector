package com.vian4.contactselector;

import java.io.FileNotFoundException;
import java.util.Scanner;

import com.vian4.contactselector.ContactDB.SaveType;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Loading contacts" );
        long start = System.currentTimeMillis();
        ContactDB contactDB = null;
        try {
            contactDB = new ContactDB();
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
            System.exit(1);
        }
        System.out.println("Loaded " + contactDB.size() + " contacts in " + (System.currentTimeMillis() - start) + " milliseconds");

        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("Stratify by location (school) or role:");
            System.out.println("1) Location\n2) Role\n3) No stratification");
            switch (getInt(scanner, 3)) {
                case 1: {
                    // location
                    locationStratify(scanner, contactDB);
                    System.out.println("Would you also like to stratify by role:");
                    System.out.println("1) Yes\n2) No");
                    if (getInt(scanner, 2) == 1) {
                        roleStratify(scanner, contactDB);
                    }
                    break;
                }
                case 2: {
                    // role
                    roleStratify(scanner, contactDB);
                    System.out.println("Would you also like to stratify by location:");
                    System.out.println("1) Yes\n2) No");
                    if (getInt(scanner, 2) == 1) {
                        locationStratify(scanner, contactDB);
                    }
                    break;
                }
            }

            System.out.println("Would you like to randomly select a sample of this population?");
            System.out.println("1) Yes\n2) No");
            if (getInt(scanner, 2) == 1) {
                System.out.println("How many people would you like to select? Enter a number between 1 and " + contactDB.selectedSize());
                contactDB.randomlySelect(getInt(scanner, contactDB.selectedSize()));
            }

            System.out.println("Would you like to save this selection?");
            System.out.println("1) Yes\n2) No");
            if (getInt(scanner, 2) == 1) {
                boolean doSave = true;
                while (doSave) {
                    System.out.println("What would you like to save in this file?");
                    System.out.println("1) Email\n2) Phone\n3) Name");
                    SaveType saveType = SaveType.EMAIL;
                    switch (getInt(scanner, 3)) {
                        case 1: {
                            saveType = SaveType.EMAIL;
                            break;
                        }
                        case 2: {
                            saveType = SaveType.PHONE;
                            break;
                        }
                        case 3: {
                            saveType = SaveType.NAME;
                            break;
                        }
                    }
                    try {
                        System.out.println("Saved file to " + contactDB.saveSelectedToDisk(saveType));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("What would you like to save another file?");
                    System.out.println("1) Yes\n2) No");
                    if (getInt(scanner, 2) == 2) {
                        doSave = false;
                    }
                }
            }

            System.out.println("What would you like to do");
            System.out.println("1) Remove the current selection from contacts list and restart (ensures that multiple selections will not have duplicates)\n2) Restart\n3) Exit");
            switch (getInt(scanner, 3)) {
                case 1: {
                    contactDB.removeSelectedFromContacts();
                    break;
                }
                case 3: {
                    System.exit(0);
                    break;
                }
            }

            contactDB.resetSelected();
        }
    }

    public static void locationStratify(Scanner scanner, ContactDB contactDB) {
        System.out.println("Select location to stratify by (All teachers are in the District/Staff catagory):");
        System.out.println(
                "1) Los Altos High School\n2) Mountain View High School\n3) Alta Vista High School\n4) Foot Hill Middle College\n5) MVLA Non Public HighSchool\n6) District/Staff");
        switch (getInt(scanner, 6)) {
            case 1: {
                contactDB.removeNotAt(School.LAHS);
                break;
            }
            case 2: {
                contactDB.removeNotAt(School.MVHS);
                break;
            }
            case 3: {
                contactDB.removeNotAt(School.AVHS);
                break;
            }
            case 4: {
                contactDB.removeNotAt(School.FOOTHILLMIDDLECOLLEGE);
                break;
            }
            case 5: {
                contactDB.removeNotAt(School.NONPUBLICHS);
                break;
            }
            case 6: {
                contactDB.removeNotAt(School.DISTRICTORSTAFF);
                break;
            }
        }
        System.out.println("Found " + contactDB.selectedSize() + " contacts");
    }

    public static void roleStratify(Scanner scanner, ContactDB contactDB) {
        System.out.println("Select role to stratify by:");
        System.out.println("1) Student\n2) Course Counselor\n3) Teacher\n4) Substitute\n5) Other");
        switch (getInt(scanner, 5)) {
            case 1: {
                contactDB.removeNotAt(Role.STUDENT);
                break;
            }
            case 2: {
                contactDB.removeNotAt(Role.COUNSELOR);
                break;
            }
            case 3: {
                contactDB.removeNotAt(Role.TEACHER);
                break;
            }
            case 4: {
                contactDB.removeNotAt(Role.SUBSTITUTE);
                break;
            }
            case 5: {
                contactDB.removeNotAt(Role.OTHER);
                break;
            }
        }
        System.out.println("Found " + contactDB.selectedSize() + " contacts");
    }

    public static int getInt(Scanner scanner, int numOptions) {
        while (true) {
            try {
                int num = Integer.parseInt(scanner.next());
                if (num > 0 && num <= numOptions) {
                    System.out.println("");
                    return num;
                }
            } catch (Exception ignored) {}
            System.out.println("Enter a valid number selection between 1 and " + numOptions);
        }
    }
}
