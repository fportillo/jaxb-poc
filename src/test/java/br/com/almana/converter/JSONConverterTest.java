package br.com.almana.converter;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import br.com.almana.converter.Converter;
import br.com.almana.converter.JSONConverter;
import br.com.almana.domain.User;

public class JSONConverterTest extends ConverterTest {

    private static final String JSONED_JOHN_DOE = "{\"user\":{\"name\":\"John Doe\",\"age\":33,\"birthDay\":\"1979-09-01T00:00:00-03:00\",\"address\":{\"street\":\"Wall Street\",\"zipCode\":10005,\"city\":{\"@fedUnit\":\"NY\",\"name\":\"New York\"}},\"dogs\":{\"dog\":[{\"name\":\"Marley\"},{\"name\":\"Lessie\"}]}}}";

    @Test
    public void testJsonToObject() throws JAXBException, JSONException,
            XMLStreamException {
        User actualUser = converter.textToObject(User.class, JSONED_JOHN_DOE);
        assertEquals("Objects are not equal", johnDoe.getName(),
                actualUser.getName());
    }

    @Test
    public void testObjectToJson() throws JAXBException {
        String marshalledJson = converter.objectToText(johnDoe);
        assertEquals("JSON is not well formed", JSONED_JOHN_DOE, marshalledJson);
    }

    @Override
    protected Converter initConverter() {
        return new JSONConverter();
    }

    @Test
    public void testObjectToJsonWithWriter() throws JAXBException {
        StringWriter writer = new StringWriter();
        converter.setWriter(writer);
        converter.objectToText(johnDoe);
        assertEquals("JSON is not well formed", JSONED_JOHN_DOE,
                writer.toString());
    }

}
