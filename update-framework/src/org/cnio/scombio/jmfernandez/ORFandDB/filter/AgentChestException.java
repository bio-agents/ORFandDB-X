package org.cnio.scombio.jmfernandez.ORFandDB.filter;

import java.lang.Exception;

public class AgentChestException
	extends Exception
{
	public AgentChestException()
	{
		super();
	}
	
	public AgentChestException(String message)
	{
		super(message);
	}
	
	public AgentChestException(String message, Throwable cause)
	{
		super(message,cause);
	}
	
	public AgentChestException(Throwable cause)
	{
		super(cause);
	}
}
