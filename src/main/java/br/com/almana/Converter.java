package br.com.almana;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

public class Converter {

	private StringReader stringReader;
	private StringWriter stringWriter;
	
	private JAXBContext ctx;
	private JSONObject jsonObject;

	private Marshaller marshaller;
	private Unmarshaller unmarshaller;

	private Configuration config;
	private MappedNamespaceConvention mappedNamespaceConvention;

	private XMLStreamWriter xmlStreamWriter;
	private XMLStreamReader xmlStreamReader;

	public String toXml(Object object) throws JAXBException {
		makeMarshaller(object);
		marshal(object);
		return stringWriter.toString();
	}

	private void makeMarshaller(Object object) throws JAXBException {
		ctx = JAXBContext.newInstance(object.getClass());
		marshaller = ctx.createMarshaller();
	}

	private void marshal(Object object) throws JAXBException {
		stringWriter = new StringWriter();
		marshaller.marshal(object, stringWriter);
	}

	public String toJson(Object object) throws JAXBException {
		makeMarshaller(object);
		setupForJson();
		stringWriter = new StringWriter();
		xmlStreamWriter = new MappedXMLStreamWriter(mappedNamespaceConvention,
				stringWriter);
		marshaller.marshal(object, xmlStreamWriter);
		return stringWriter.toString();
	}

	private void setupForJson() {
		config = new Configuration();
		mappedNamespaceConvention = new MappedNamespaceConvention(config);
	}

	public <T> T xmlToObject(Class<T> clazz, String xml) throws JAXBException {
		makeUnmarshaller(clazz);
		stringReader = new StringReader(xml);
		@SuppressWarnings("unchecked")
		T t = (T) unmarshaller.unmarshal(stringReader);
		return t;
	}

	public <T> T jsonToObject(Class<T> clazz, String json)
			throws JAXBException, JSONException, XMLStreamException {
		makeUnmarshaller(clazz);
		jsonObject = new JSONObject(json);
		setupForJson();
		xmlStreamReader = new MappedXMLStreamReader(jsonObject,
				mappedNamespaceConvention);
		@SuppressWarnings("unchecked")
		T t = (T) unmarshaller.unmarshal(xmlStreamReader);
		return t;
	}

	private <T> void makeUnmarshaller(Class<T> clazz) throws JAXBException {
		ctx = JAXBContext.newInstance(clazz);
		unmarshaller = ctx.createUnmarshaller();
	}

}
