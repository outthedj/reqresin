package testdata;

import models.RegistrationModel;
import utilities.GenerateFakeMessage;

public class GetRegistrationModel {

    public static RegistrationModel getRegistrationWithAllFieldsBuilder() {
        String password = GenerateFakeMessage.getPassword();
        RegistrationModel registrationModel = RegistrationModel
                .builder()
                .zipCode(GenerateFakeMessage.getValidZipcode())
                .firstName(GenerateFakeMessage.getFirstName())
                .lastName(GenerateFakeMessage.getLastName())
                .email(GenerateFakeMessage.getEmail())
                .password(password)
                .confirmPassword(password)
                .build();
        return registrationModel;
    }

}
