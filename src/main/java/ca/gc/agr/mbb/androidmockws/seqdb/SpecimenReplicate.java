package ca.gc.agr.mbb.androidmockws.seqdb;

public class SpecimenReplicate {

    public int PK = -1;
    public int specimenIdentifier = -1;
    public int version = -1;
    public int containerNumber = -1;
    public int storageUnit = -1;
    public int compartment = -1;
    public int shelf = -1;
    public int rack = -1;

    public String json(){
	return "{" 
	    + "\n\"PK\": " + PK
	    + ","
	    + "\n\"specimenIdentifier\":" + specimenIdentifier
	    + ","
	    + "\n\"version\": " + version
	    + ","
	    + "\n\"containerNumber\": " + containerNumber
	    + ","
	    + "\n\"storageUnit\": " + storageUnit
	    + ","
	    + "\n\"compartment\": " + compartment
	    + ","
	    + "\n\"shelf\": " + shelf
	    + ","
	    + "\n\"rack\": " + rack
	    + "\n}";
    }
}
