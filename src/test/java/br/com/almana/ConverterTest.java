package br.com.almana;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

	private User johnDoe;
	private Address address;
	private City newYork;
	private List<Dog> dogs;
	private Converter conversor;

	@Before
	public void setup() throws ParseException {
		conversor = new Converter();
		setupCity();
		setupAddress();
		setupDogList();
		setupUser();
	}

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

	@Test
	public void testObjectToXml() throws JAXBException {
		String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><name>John Doe</name><age>33</age><birthDay>1979-09-01T00:00:00-03:00</birthDay><address><street>Wall Street</street><zipCode>10005</zipCode><city fedUnit=\"NY\"><name>New York</name></city></address><dogs><dog><name>Marley</name></dog><dog><name>Lessie</name></dog></dogs></user>";
		String marshalledXML = conversor.toXml(johnDoe);
		assertEquals("XML is not well formed", expectedXml, marshalledXML);
	}

	@Test
	public void testObjectToJson() throws JAXBException {
		String expectedJson = "{\"user\":{\"name\":\"John Doe\",\"age\":33,\"birthDay\":\"1979-09-01T00:00:00-03:00\",\"address\":{\"street\":\"Wall Street\",\"zipCode\":10005,\"city\":{\"@fedUnit\":\"NY\",\"name\":\"New York\"}},\"dogs\":{\"dog\":[{\"name\":\"Marley\"},{\"name\":\"Lessie\"}]}}}";
		String marshalledJson = conversor.toJson(johnDoe);
		assertEquals("JSON is not well formed", expectedJson, marshalledJson);
	}

	@Test
	public void testXMLToObject() throws JAXBException {
		User expectedUser = johnDoe;
		String marshalledJohnDoe = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><name>John Doe</name><age>33</age><birthDay>1979-09-01T00:00:00-03:00</birthDay><address><street>Wall Street</street><zipCode>10005</zipCode><city fedUnit=\"NY\"><name>New York</name></city></address><dogs><dog><name>Marley</name></dog><dog><name>Lessie</name></dog></dogs></user>";
		User actualUser = conversor.xmlToObject(User.class, marshalledJohnDoe);
		assertEquals("Objects are not equal", expectedUser, actualUser);
	}

	@Test
	public void testJsonToObject() throws JAXBException, JSONException,
			XMLStreamException {
		User expectedUser = johnDoe;
		String marshalledJohnDoe = "{\"user\":{\"name\":\"John Doe\",\"age\":33,\"birthDay\":\"1979-09-01T22:59:28.732-03:00\",\"address\":{\"street\":\"Wall Street\",\"zipCode\":10005,\"city\":{\"@fedUnit\":\"NY\",\"name\":\"New York\"}},\"dogs\":[{\"name\":\"Marley\"},{\"name\":\"Lessie\"}]}}";
		User actualUser = conversor.jsonToObject(User.class, marshalledJohnDoe);
		assertEquals("Objects are not equal", expectedUser.getName(),
				actualUser.getName());
	}

}
