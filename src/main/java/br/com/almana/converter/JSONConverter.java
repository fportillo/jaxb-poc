package br.com.almana.converter;

import java.io.StringWriter;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

public class JSONConverter extends SuperConverter implements Converter {

    private XMLStreamWriter xmlStreamWriter;
    private MappedNamespaceConvention mappedNamespaceConvention;
    private Configuration config;
    private JSONObject jsonObject;
    private XMLStreamReader xmlStreamReader;

    public JSONConverter() {
        writer = new StringWriter();
    }

    @Override
    public String objectToText(Object object) throws JAXBException {
        makeMarshaller(object);
        setupForJson();
        xmlStreamWriter = new MappedXMLStreamWriter(mappedNamespaceConvention,
                writer);
        marshaller.marshal(object, xmlStreamWriter);
        return writer.toString();
    }

    private void setupForJson() {
        config = new Configuration();
        mappedNamespaceConvention = new MappedNamespaceConvention(config);
    }

    @Override
    public <T> T textToObject(Class<T> clazz, String json) throws JAXBException {
        makeUnmarshaller(clazz);
        setupForJson();
        try {
            jsonObject = new JSONObject(json);
            xmlStreamReader = new MappedXMLStreamReader(jsonObject,
                    mappedNamespaceConvention);
        } catch (JSONException e) {
            throw new JAXBException(e);
        } catch (XMLStreamException e) {
            throw new JAXBException(e);
        }
        @SuppressWarnings("unchecked")
        T t = (T) unmarshaller.unmarshal(xmlStreamReader);
        return t;
    }

}
