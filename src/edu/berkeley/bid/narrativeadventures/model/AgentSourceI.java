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
public interface AgentSourceI 
{
    public interface AgentSourceFilterI {
        public ArrayList<Agent> ignoredAgents();
        public void setIgnoreAgent(Agent agent, boolean ignore);
    }
    public ArrayList<Agent> getAgents();
    public ArrayList<Agent> getAgents(AgentSourceFilterI agsf);
}

