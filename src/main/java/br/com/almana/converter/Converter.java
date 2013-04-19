package br.com.almana.converter;

import java.io.Writer;

import javax.xml.bind.JAXBException;

/**
 * @email francisco.portillo@gmail.com
 * 
 */
public interface Converter {

    /**
     * Converts annotated JAXB classes to text
     * 
     * @param object
     *            - the object you want to convert to text
     * @return the plain text representation of the object
     * @throws JAXBException
     */
    String objectToText(Object object) throws JAXBException;

    /**
     * Converts marshaled object in plain text to object
     * 
     * @param clazz
     *            - The type of the object (same as the output)
     * @param marsalledObject
     *            - the marshaled object in plain text
     * @return
     * @throws JAXBException
     */
    <T> T textToObject(Class<T> clazz, String marsalledObject)
            throws JAXBException;

    /**
     * Sets the writer where the text representation of the object is supposed
     * to be sent to
     * 
     * @param writer
     *            - the writer that's going to be written the text
     * @throws JAXBException
     */
    void setWriter(Writer writer) throws JAXBException;

}
