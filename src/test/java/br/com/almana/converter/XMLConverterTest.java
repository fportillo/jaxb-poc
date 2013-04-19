package br.com.almana.converter;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import javax.xml.bind.JAXBException;

import org.junit.Test;

import br.com.almana.converter.Converter;
import br.com.almana.converter.XMLConverter;
import br.com.almana.domain.User;

public class XMLConverterTest extends ConverterTest {

    private static final String MARSHALED_JOHN_DOE = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><user><name>John Doe</name><age>33</age><birthDay>1979-09-01T00:00:00-03:00</birthDay><address><street>Wall Street</street><zipCode>10005</zipCode><city fedUnit=\"NY\"><name>New York</name></city></address><dogs><dog><name>Marley</name></dog><dog><name>Lessie</name></dog></dogs></user>";

    @Test
    public void testObjectToXml() throws JAXBException {
        String marshalledXML = converter.objectToText(johnDoe);
        assertEquals("XML is not well formed", MARSHALED_JOHN_DOE,
                marshalledXML);
    }

    @Test
    public void testXMLToObject() throws JAXBException {
        User actualUser = converter
                .textToObject(User.class, MARSHALED_JOHN_DOE);
        assertEquals("Objects are not equal", johnDoe, actualUser);
    }

    @Test
    public void testObjectToXmlWithWriter() throws JAXBException {
        StringWriter writer = new StringWriter();
        converter.setWriter(writer);
        converter.objectToText(johnDoe);
        assertEquals("XML is not well formed", MARSHALED_JOHN_DOE,
                writer.toString());
    }

    @Override
    protected Converter initConverter() {
        return new XMLConverter();
    }

}
