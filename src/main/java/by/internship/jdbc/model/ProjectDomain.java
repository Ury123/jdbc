package by.internship.jdbc.model;


import lombok.Getter;

@Getter
public enum ProjectDomain {
    FINTECH("fintech"),
    MEDTECH("medtech"),
    RETAIL("retail"),
    EDTECH("edtech"),
    ENTERPRISE("enterprise");

    private final String value;

    ProjectDomain(String value) {
        this.value = value;
    }
}
