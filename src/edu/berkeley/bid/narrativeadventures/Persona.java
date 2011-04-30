package edu.berkeley.bid.narrativeadventures;

public class Persona {

    public String name;
 //   public String resourceId;
    public Persona()
    {
        // TODO Auto-generated constructor stub
    }
    public Persona(String name) 
            //, String resourceFilePath)
    {
        this.name = name;
//        this.resourceId = resourceFilePath;
    }
    @Override
    public String toString()
    {
        return this.name;
    }
}
