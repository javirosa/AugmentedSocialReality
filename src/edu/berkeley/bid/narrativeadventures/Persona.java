package edu.berkeley.bid.narrativeadventures;

@Deprecated
public class Persona {

    public String name;
 //   public String resourceId;
    public Persona()
    {
        name = "";
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
