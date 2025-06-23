package pe.edu.upc.center.agecare.users.interfaces.rest.resources;

public record AddressResource(
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {}
