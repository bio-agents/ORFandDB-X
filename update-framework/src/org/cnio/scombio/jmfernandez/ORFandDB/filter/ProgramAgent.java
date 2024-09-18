package org.cnio.scombio.jmfernandez.ORFandDB.filter;

import java.util.HashMap;

public class ProgramAgent
	extends Agent
{
	private String programPath;
	private HashMap<ProgramOptionSet> optionSets;
	
	public ProgramAgent(String agentName,String path)
	{
		super(agentName);
		
		programPath=path;
		optionSets=new HashMap<ProgramOptionSet>();
	}
	
	public String getProgramPath()
	{
		return programPath;
	}
	
	public ProgramOptionSet getProgramOptionSet(String optionSetName)
	{
		return optionSets.get(optionSetName);
	}
	
	public void addProgramOptionSet(ProgramOptionSet newPOS)
	{
		optionSets.put(newPOS.getName(),newPOS);
	}
}
