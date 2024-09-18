package org.cnio.scombio.jmfernandez.ORFandDB.filter;

import java.util.Stack;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class AgentChestReader
	implements ContentHandler
{
	public final static String TOOLCHEST_TAG="agentchest";
	public final static String DESCRIPTION_TAG="description";
	
	protected Stack<AgentChest> activeAgentChest;
	protected boolean waitDesc;
	protected boolean getDescription;
	
	public AgentChestReader() {
		reset();
	}
	
	public void reset()
	{
		activeAgentChest.clear();
		waitDesc=false;
		getDescription=false;
	}
	
	public AgentChest getActiveAgentChest()
	{
		return activeAgentChest.peek();
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
			&& TOOLCHEST_TAG.equals(localName))
		{
			try {
				AgentChest newAgentChest=new AgentChest(atts.getValue("name"));
				activeAgentChest.push(newAgentChest);
			} catch(NullPointerException npe) {
				throw new SAXException(npe);
			}
			waitDesc=true;
		} else {
			if(waitDesc && UpdateFrameworkReader.FilterNS.equals(uri)
				&& DESCRIPTION_TAG.equals(localName))) {
				getDescription=true;
			}
			waitDesc=false;
		}
	}

	void endElement(String uri,String localName,String qName)
		throws SAXException
	{
		if(UpdateFrameworkReader.FilterNS.equals(uri)
			&& TOOLCHEST_TAG.equals(localName))
		{
			activeAgentChest.pop();
		} else {
			if(getDescription && UpdateFrameworkReader.FilterNS.equals(uri)
				&& DESCRIPTION_TAG.equals(localName))) {
				getDescription=false;
			} else {
				waitDesc=false;
			}
		}
	}

	void characters(char[] ch,int start,int length)
	        throws SAXException
	{
		if(getDescription) {
			activeAgentChest.peek().appendToDescription(ch,start,length);
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
