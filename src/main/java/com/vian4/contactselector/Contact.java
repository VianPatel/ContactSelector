package com.vian4.contactselector;

enum School {
    LAHS,
    MVHS,
    AVHS,
    FOOTHILLMIDDLECOLLEGE,
    NONPUBLICHS,
    DISTRICTORSTAFF,
    OTHER
}

enum Role {
    ADMIN,
    STUDENT,
    COUNSELOR,
    TEACHER,
    SUBSTITUTE,
    
    OTHER
}

public class Contact {
    private final String name;
    private final School school;
    private final Role role;
    private final String email;
    private final String phone;

    static School parseSchool(String school) {
        if (!school.contains(",")) {
            return School.OTHER;
        }
        String schoolStr = school.split(",")[1].substring(1); //removing leading space
        switch (schoolStr) {
            case "Los Altos High School":
                return School.LAHS;
            case "Mountain View High School":
                return School.MVHS;
            case "Alta Vista High School":
                return School.AVHS;
            case "Foot Hill Middle College":
                return School.FOOTHILLMIDDLECOLLEGE;
            case "MVLA Non Public HighSchool":
                return School.NONPUBLICHS;
            case "Mountain View Los Altos HSD":
                return School.DISTRICTORSTAFF;
            default:
                return School.OTHER;
        }
    }

    static Role parseRole(String role) {
        if (!role.contains(",")) {
            return Role.OTHER;
        }
        String roleStr = role.split(",")[0];
        if (roleStr.contains("Admin")) {
            return Role.ADMIN;
        }
        switch (roleStr) {
            case "Student":
                return Role.STUDENT;
            case "Counselor":
                return Role.COUNSELOR;
            case "Teacher":
                return Role.TEACHER;
            case "Substitute":
                return Role.SUBSTITUTE;
            default:
                return Role.OTHER;
        }
    }

    public Contact(String name, School school, Role role, String email, String phone) {
        this.name = name;
        this.school = school;
        this.role = role;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public School getSchool() {
        return school;
    }

    public Role getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}