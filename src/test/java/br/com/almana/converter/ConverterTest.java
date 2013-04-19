package br.com.almana.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;

import br.com.almana.converter.Converter;
import br.com.almana.domain.Address;
import br.com.almana.domain.City;
import br.com.almana.domain.Dog;
import br.com.almana.domain.FederationUnit;
import br.com.almana.domain.User;
import static org.junit.Assert.*;

public abstract class ConverterTest {

    protected User johnDoe;
    private Address address;
    private City newYork;
    private List<Dog> dogs;
    protected Converter converter;

    @Before
    public void setup() throws ParseException {
        converter = initConverter();
        if (converter == null)
            fail("converter cannot be null");
        setupCity();
        setupAddress();
        setupDogList();
        setupUser();
    }

    protected abstract Converter initConverter();

    private void setupUser() throws ParseException {
        johnDoe = new User();
        johnDoe.setAddress(address);
        johnDoe.setAge(33);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        johnDoe.setBirthDay(simpleDateFormat.parse("01/09/1979"));
        johnDoe.setName("John Doe");
        johnDoe.setDogs(dogs);
    }

    private void setupDogList() {
        Dog marley = new Dog();
        marley.setName("Marley");
        Dog lessie = new Dog();
        lessie.setName("Lessie");
        dogs = new ArrayList<>();
        dogs.add(marley);
        dogs.add(lessie);
    }

    private void setupAddress() {
        address = new Address();
        address.setCity(newYork);
        address.setStreet("Wall Street");
        address.setZipCode("10005");
    }

    private void setupCity() {
        newYork = new City();
        newYork.setName("New York");
        newYork.setFedUnit(FederationUnit.NY);
    }

}