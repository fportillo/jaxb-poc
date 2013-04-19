package br.com.almana.converter;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLConverter implements Converter {

    private Reader reader;
    private Writer writer;

    private JAXBContext ctx;

    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public XMLConverter() {
        writer = new StringWriter();
    }

    @Override
    public String objectToText(Object object) throws JAXBException {
        makeMarshaller(object);
        marshal(object);
        return writer.toString();
    }

    private void makeMarshaller(Object object) throws JAXBException {
        ctx = JAXBContext.newInstance(object.getClass());
        marshaller = ctx.createMarshaller();
    }

    private void marshal(Object object) throws JAXBException {
        marshaller.marshal(object, writer);
    }

    public <T> T textToObject(Class<T> clazz, String xml) throws JAXBException {
        makeUnmarshaller(clazz);
        reader = new StringReader(xml);
        @SuppressWarnings("unchecked")
        T t = (T) unmarshaller.unmarshal(reader);
        return t;
    }

    private <T> void makeUnmarshaller(Class<T> clazz) throws JAXBException {
        ctx = JAXBContext.newInstance(clazz);
        unmarshaller = ctx.createUnmarshaller();
    }

    @Override
    public void setWriter(Writer writer) throws JAXBException {
        this.writer = writer;
    }

}
