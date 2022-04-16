package models;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@EqualsAndHashCode
@ToString
@Jacksonized
public class RegistrationModel {

    private String zipCode;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmPassword;
}
