package com.finlingua.xbrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.xbrl._2003.instance.NormalizedStringItemType;
import org.xbrl._2003.instance.Xbrl;

/**
 * A program that reads an SEC XBRL filing document and prints out the TradingSymbol.
 * @author Todd Schiller
 */
public class FilingDriver {

	/**
	 * Types for the JAXBContext. Need a type from each package for the elements / types
	 * to be loaded into the context. See the repository README for a description of how
	 * the XSD files were modified to get them to work with JAXB.
	 */
	private static final Class<?>[] FILING_TYPES = new Class[] { 
		Xbrl.class, 
		org.xbrl._2003.linkbase.Linkbase.class,
		org.xbrl._2005.xbrldt.ObjectFactory.class,
		org.fasb.us_gaap._2013_01_31.ObjectFactory.class,
		gov.sec.xbrl.dei._2013_01_31.ObjectFactory.class,
	};
	
	public static Xbrl readFiling(InputStream inputStream) throws JAXBException {
		StreamSource src = new StreamSource(inputStream);
		JAXBContext jc = JAXBContext.newInstance(FILING_TYPES);
		Unmarshaller u = jc.createUnmarshaller();
		JAXBElement<Xbrl> doc = u.unmarshal(src, Xbrl.class);
		return doc.getValue();
	}
	
	public static void main(String[] args) throws FileNotFoundException, JAXBException {
		File file = new File(args[0]);
		Xbrl xbrl = readFiling(new FileInputStream(file));
		
		for (Object content : xbrl.getItemOrTupleOrContext()){
			if (content instanceof JAXBElement){
				JAXBElement<?> elt = (JAXBElement<?>) content;
		
				if (elt.getName().getLocalPart().equals("TradingSymbol")){
					System.out.println("Trading Symbol: " + ((NormalizedStringItemType) elt.getValue()).getValue());
				}
			}
		}
	}
}
