package org.cnio.scombio.jmfernandez.ORFandDB.filter;

public class UpdateFrameworkReader
	implements ContentHandler
{
	public final static String FilterNS="http://www.cnio.es/scombio/jmfernandez/ORFandDB/4.0/filter/1.0";
	
	private AgentChestReader tsr;
	private AgentReader tr;
	private ClassAgentReader ctr;
	private ProgramAgentReader ptr;
	private WorkflowAgentReader wtr;
	
	public UpdateFrameworkReader()
	{
		tsr=new AgentChestReader();
		tr=new AgentReader(tsr);
		ctr=new ClassAgentReader(tr);
		ptr=new ProgramAgentReader(tr);
		wtr=new WorkflowAgentReader(tr);
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
		// DoNothing(R)
	}

	void endElement(String uri,String localName,String qName)
		throws SAXException
	{
		// DoNothing(R)
	}

	void characters(char[] ch,int start,int length)
	        throws SAXException
	{
		// DoNothing(R)
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
