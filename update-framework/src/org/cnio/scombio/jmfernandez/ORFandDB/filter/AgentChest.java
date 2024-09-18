package org.cnio.scombio.jmfernandez.ORFandDB.filter;

import java.util.HashMap;

public class AgentChest
{
	private String name;
	private StringBuilder description;
	private HashMap<String,Agent> agentChest;
	
	public AgentChest(String name)
	{
		if(name==null)
			throw new NullPointerException("AgentChest constructor needs a non-null name!!!");
		this.name=name;
		agentChest=new HashMap<String,Agent>();
		clearDescription();
	}
	
	public AgentChest(String name, AgentChest base)
	{
		this(name);
		addAgentChest(base);
	}
	
	public AgentChest(String name,Agent[] base)
	{
		this(name);
		
		for(Agent agent: base) {
			if(agent!=null) {
				addAgent(agent);
			}
		}
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description.toString();
	}
	
	public void appendToDescription(String newDescription)
	{
		description.append(newDescription);
	}
	
	public void appendToDescription(char[] newDescription)
	{
		description.append(newDescription);
	}
	
	public void appendToDescription(char[] newDescription,int offset,int len)
	{
		description.append(newDescription,offset,len);
	}
	
	public void setDescription(String newDescription)
	{
		description=new StringBuilder(newDescription);
	}
	
	public void clearDescription()
	{
		description=new StringBuilder();
	}
	
	/**
		This method adds the contents of an
		existing agentchest to the current one
	*/
	public void addAgentChest(AgentChest base)
		throws NullPointerException
	{
		if(base==null)
			throw new NullPointerException("addAgentChest needs a base non-null AgentChest!!!");
		
		agentChest.putAll(base.agentChest);
	}
	
	/**
		A agent is added to the agentchest.
		If a agent with the same name is already inside,
		an exception is fired.
	*/
	public void addAgent(Agent t)
		throws NullPointerException,AgentChestException
	{
		if(t==null)
			throw new NullPointerException("addAgent needs a non-null Agent!!!");
		
		String agentName=t.getName();
		
		if(agentName==null)
			throw new AgentChestException("addAgent needs that the input Agent has a non-null name!!!");
		
		if(agentChest.containsKey(agentName))
			throw new AgentChestException("This agentchest already contains a agent with the name '"+agentName+"'");
		
		agentChest.put(agentName,agent);
	}
	
	/**
		A agent is added to the agentchest.
		If a agent with the same name is already inside,
		the agent is replaced. The replaced agent is
		returned, when available.
	*/
	public Agent addOrReplaceAgent(Agent t)
		throws NullPointerException,AgentChestException
	{
		if(t==null)
			throw new NullPointerException("addOrReplaceAgent needs a non-null Agent!!!");
		
		String agentName=t.getName();
		
		if(agentName==null)
			throw new AgentChestException("addOrReplaceAgent needs that the input Agent has a non-null name!!!");
		
		return agentChest.put(agentName,agent);
	}
	
	/**
		A agent is removed from the agentchest.
		The removed agent is returned.
	*/
	public Agent removeAgent(String agentName)
		throws NullPointerException
	{
		if(agentName==null)
			throw new NullPointerException("removeAgent needs that the input Agent has a non-null name!!!");
		
		return agentChest.remove(agentName);
	}
	
	/**
		All the agents inside the agentchest
		are discarded.
	*/
	public boolean clear()
	{
		agentChest.clear()
	}
}
