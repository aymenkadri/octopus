package com.ensi.octopus.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Patient.
 */
@Document(collection = "patients")
public class Patient extends AbstractAuditingEntity implements Serializable {

    @NotNull
    @Size(min = 0, max = 50)
    private Long cin;

    @Size(min = 0, max = 100)
    private String name;

    @Size(min = 0, max = 100)
    private Integer age;

    public Long getCin() {
        return cin;
    }

    public void setCin(Long cin) {
        this.cin = cin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;

        Patient patient = (Patient) o;

        return !(age != null ? !age.equals(patient.age) : patient.age != null) &&
                cin.equals(patient.cin) &&
                !(name != null ? !name.equals(patient.name) : patient.name != null);

    }

    @Override
    public int hashCode() {
        int result = cin.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "cin=" + cin +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
