package org.cnio.scombio.jmfernandez.ORFandDB.filter;

import java.util.Stack;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class ClassAgentReader
	implements ContentHandler
{
	public final static String CLASS_TAG="class";
	public final static String LIBRARY_TAG="library";
	
	protected ClassAgent activeClassAgent;
	protected boolean waitLibrary;
	protected boolean getLibrary;
	protected StringBuilder libraryName;
	protected AgentReader tr;
	
	public ClassAgentReader(AgentReader tr) {
		this.tr=tr;
		reset();
	}
	
	public void reset()
	{
		activeClassAgent=null;
		getLibrary=false;
		waitLibrary=false;
		libraryName=null;
	}
	
	public String getActiveClassAgent()
	{
		return activeClassAgent;
	}
	
	void setDocumentLocator(Locator locator)
	{
		// DoNothing(R)
	}

	void startDocument()
		throws SAXException
	{
		// DoNothing(R)
	}

	void endDocument()
		throws SAXException
	{
		// DoNothing(R)
	}

	void startPrefixMapping(String prefix,String uri)
		throws SAXException
	{
		// DoNothing(R)
	}

	void endPrefixMapping(String prefix)
		throws SAXException
	{
		// DoNothing(R)
	}

	void startElement(String uri,String localName,String qName,Attributes atts)
		throws SAXException
	{
		if(UpdateFrameworkReader.FilterNS.equals(uri)
			&& CLASS_TAG.equals(localName))
		{
			activeClassAgent=new ClassAgent(tr.getActiveAgent(),atts.getValue("import"));
			activeClassAgent.setDescription(tr.getActiveDescription());
			waitLibrary=true;
		} else {
			if(waitLibrary && UpdateFrameworkReader.FilterNS.equals(uri)
				&& LIBRARY_TAG.equals(localName))) {
				getLibrary=true;
				libraryName=new StringBuilder();
			}
			waitDesc=false;
		}
	}

	void endElement(String uri,String localName,String qName)
		throws SAXException
	{
		if(UpdateFrameworkReader.FilterNS.equals(uri)
			&& CLASS_TAG.equals(localName))
		{
			activeClassAgent=null;
			libraryName=null;
			waitLibrary=false;
		} else {
			if(getLibrary && UpdateFrameworkReader.FilterNS.equals(uri)
				&& LIBRARY_TAG.equals(localName))) {
				activeClassAgent.addLibrary(libraryName.toString());
				getLibrary=false;
			}
		}
	}

	void characters(char[] ch,int start,int length)
	        throws SAXException
	{
		if(getLibrary) {
			libraryName.append(ch,start,length);
		}
	}

	void ignorableWhitespace(char[] ch,int start,int length)
		throws SAXException
	{
		// DoNothing(R)
	}

	void processingInstruction(String target,String data)
		throws SAXException
	{
		// DoNothing(R)
	}

	void skippedEntity(String name)
		throws SAXException
	{
		// DoNothing(R)
	}
}
