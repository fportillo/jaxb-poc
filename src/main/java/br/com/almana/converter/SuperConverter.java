package br.com.almana.converter;

import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class SuperConverter {

    protected Marshaller marshaller;
    protected Unmarshaller unmarshaller;
    private JAXBContext ctx;
    protected Writer writer;

    protected void makeMarshaller(Object object) throws JAXBException {
        ctx = JAXBContext.newInstance(object.getClass());
        marshaller = ctx.createMarshaller();
    }

    protected <T> void makeUnmarshaller(Class<T> clazz) throws JAXBException {
        ctx = JAXBContext.newInstance(clazz);
        unmarshaller = ctx.createUnmarshaller();
    }

    public void setWriter(Writer writer) throws JAXBException {
        this.writer = writer;
    }

}
