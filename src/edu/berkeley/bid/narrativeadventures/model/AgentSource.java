package edu.berkeley.bid.narrativeadventures.model;

import java.util.ArrayList;
/** 
 * Returns a list of possible agents.
 * A possible source might be a contact list, 
 * a facebook profile, or a JSON file.
 * 
 * @author javirosa
 *
 */
public interface AgentSource 
{
    public ArrayList<Agent> getAgents();
}

