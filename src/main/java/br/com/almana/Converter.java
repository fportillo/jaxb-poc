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
	
	public String toXml(Object object) throws JAXBException {
		StringWriter stringWriter = new StringWriter();
		JAXBContext ctx = JAXBContext.newInstance(object.getClass());
		Marshaller marshaller = ctx.createMarshaller();
		marshaller.marshal(object, stringWriter);
		return stringWriter.toString();
	}

	public String toJson(Object object) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(object.getClass());
		Configuration config = new Configuration();
		MappedNamespaceConvention mappedNamespaceConvention = new MappedNamespaceConvention(config);
		StringWriter stringWriter = new StringWriter();
		XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(mappedNamespaceConvention, stringWriter);
		Marshaller marshaller = ctx.createMarshaller();
		marshaller.marshal(object, xmlStreamWriter);
		return stringWriter.toString();
	}

	public <T> T xmlToObject(Class<T> clazz, String xml) throws JAXBException {
		JAXBContext ctx = JAXBContext.newInstance(clazz);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		StringReader stringReader = new StringReader(xml);
		@SuppressWarnings("unchecked")
		T t = (T) unmarshaller.unmarshal(stringReader);
		return t;
	}

	public <T> T jsonToObject(Class<T> clazz, String json) throws JAXBException, JSONException, XMLStreamException {
		JAXBContext ctx = JAXBContext.newInstance(clazz);
		JSONObject jsonObject = new JSONObject(json);
		Configuration config = new Configuration();
		MappedNamespaceConvention mappedNamespaceConvention = new MappedNamespaceConvention(config);		
		XMLStreamReader xmlStreamReader = new MappedXMLStreamReader(jsonObject, mappedNamespaceConvention);
		Unmarshaller unmarshaller = ctx.createUnmarshaller();
		@SuppressWarnings("unchecked")
		T t = (T) unmarshaller.unmarshal(xmlStreamReader);
		return t;
	}

}
