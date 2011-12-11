package betterwithage

import java.util.Date

class BusinessContact {

    String externalId
    String firstName
    String lastName
    Date createDate
    String address
    String address2
    String city
    String state
    String zip
    String country
    String email
    String website
    String company
    String title // 'position' reserved keyword in create table DDL
    String workPhone
    String cellPhone
    String frontImgUrl
    String note

    String ageRange

    static constraints = {
        // list properties in desired display order
        firstName(nullable: true)
        lastName(nullable: true)
        ageRange(nullable: true)
        address(nullable: true)
        address2(nullable: true)
        city(nullable: true)
        state(nullable: true)
        zip(nullable: true)
        country(nullable: true)
        email(nullable: true)
        website(nullable: true)
        company(nullable: true)
        title(nullable: true)
        workPhone(nullable: true)
        cellPhone(nullable: true)
        frontImgUrl(nullable: true)
        note(nullable: true)
        externalId(nullable: true)
        createDate(nullable: true)
    }
}
