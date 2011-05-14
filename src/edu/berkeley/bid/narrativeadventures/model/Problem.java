package edu.berkeley.bid.narrativeadventures.model;


public class Problem implements Ided
{
    String id;
    public String situation;
    public String description;
    public String place;
    public String people;
    public int difficulty;
    public Narrative narrative;
    
    public Problem()
    {
        id = java.util.UUID.randomUUID().toString();
        situation = "situationfoo";
        description = "descriptionfoo";
        place = "placefoo";
        people = "peoplefoo";
        difficulty = 0;
        Narrative nar = new Narrative();
    }
    
    public String getId()
    {
        return id;
    }
    
}
