package ca.gc.agr.mbb.androidmockws;

import static spark.Spark.*;
import spark.*;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import ca.gc.agr.mbb.androidmockws.seqdb.SpecimenReplicate;

public class AndroidMockWS {
    public static String thisRoute = null;
    public static final String RESPONSE_TYPE = "application/json";
    public static final String VERSION = "v1";
    public static final String COUNT = "count";
    public static final String SPECIMEN_REPLICATE = "specimenReplicate";
    public static final String SPECIMEN_REPLICATE_PATH = "/" + VERSION + "/" + SPECIMEN_REPLICATE;
    public static final String SPECIMEN_REPLICATE_ID = ":ID";
    public static final String VAR_PREFIX = ":";

    Map <String, SpecimenReplicate> specimenReplicates = new HashMap <String, SpecimenReplicate>();
    
    public static void main(String[] args) {
	AndroidMockWS service = new AndroidMockWS();
	service.start();
    }
    
    public void start(){
	populateReplicateData(specimenReplicates);

	/////////////
	// Specimen Replicates
	// get count
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + COUNT + "/";
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    // .. Show something ..
		    System.err.println("*GET: count" + request.pathInfo());
		    return "{ "
			+ "\"type\": \"specimenReplicates\""
			+ ",\n"
			+ "\"count\": " + specimenReplicates.size()
			+ "}";
		}
	    });

	// get all
	thisRoute = SPECIMEN_REPLICATE_PATH + "/";
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    // .. Show something ..
		    System.err.println("*GET: " + request.pathInfo());
		    StringBuilder sb = new StringBuilder();
		    sb.append("{\n");
		    sb.append("\"count\": " + specimenReplicates.size() + ",\n");
		    sb.append("\"specimenReplicates\":");
		    sb.append("[\n");
		    boolean first = true;
		    for (String key : specimenReplicates.keySet()) {
			if(first){
			    first = false;
			}else{			
			    sb.append(",\n");
			} 
			sb.append("    ");
			sb.append("\"" + SPECIMEN_REPLICATE_PATH + "/" + key + "\"");
		    }
		    sb.append("\n]");
		    sb.append("\n}");
		    return sb.toString();
		}
	    });

	// get by ID
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + SPECIMEN_REPLICATE_ID;
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    // .. Show something ..
		    String id = request.params(SPECIMEN_REPLICATE_ID);
		    System.err.println("*GET: " + request.pathInfo() + "   id=" + id);

		    if(! isInteger(id)) {
			response.status(401);
			return "{\"error\":\"Not an integer\", \"value\":\"" + id + "\"}";
		    }else{
			if(specimenReplicates.containsKey(id)){
			    return specimenReplicates.get(id).json();
			}else{
			    response.status(401);
			    return "{\"message\":\"Not found\", \"value\":\"" + id + "\"}";
			}
		    }
		}
	    });
       
	thisRoute = SPECIMEN_REPLICATE_PATH;
	post(new Route(SPECIMEN_REPLICATE_PATH) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    // .. Create something .. 
		    return SPECIMEN_REPLICATE_PATH;
		}
	    });
       
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + SPECIMEN_REPLICATE_ID;
	put(new Route(SPECIMEN_REPLICATE_PATH) {
		@Override
		public Object handle(Request request, Response response) {
		    // .. Update something ..
		    return SPECIMEN_REPLICATE_PATH;
		}
	    });
       
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + SPECIMEN_REPLICATE_ID;
	delete(new Route(SPECIMEN_REPLICATE_PATH) {
		@Override
		public Object handle(Request request, Response response) {
		    // .. annihilate something ..
		    return SPECIMEN_REPLICATE_PATH;
		}
	    });


	/////////////
	// Specimen Replicates
	get(new Route("/" + VERSION) {
		@Override
		public Object handle(Request request, Response response) {
		    // .. Show something ..
		    return SPECIMEN_REPLICATE_PATH;
		}
	    });
       
	post(new Route("/ + VERSION") {
		@Override
		public Object handle(Request request, Response response) {
		    // .. Create something .. 
		    return SPECIMEN_REPLICATE_PATH;
		}
	    });
       
	put(new Route("/ + VERSION") {
		@Override
		public Object handle(Request request, Response response) {
		    // .. Update something ..
		    return SPECIMEN_REPLICATE_PATH;
		}
	    });
       
	delete(new Route("/" + VERSION) {
		@Override
		public Object handle(Request request, Response response) {
		    // .. annihilate something ..
		    return SPECIMEN_REPLICATE_PATH;
		}
	    });
    }

    public static boolean isInteger(String s) {
	if (s == null){
	    return false;
	}
	try { 
	    Integer.parseInt(s); 
	} catch(NumberFormatException e) { 
	    return false; 
	}
	// only got here if we didn't return false
	return true;
    }
    
    static final Random r = new Random();
    private static void populateReplicateData(final Map <String, SpecimenReplicate> sReplicates){
	if (sReplicates == null){
	    throw new NullPointerException();
	}
	// SpecimenReplicate
	for(int i=1; i<(50 + r.nextInt(1000)); i++){
	    SpecimenReplicate sr = new SpecimenReplicate();
	    sr.PK = 1000 + r.nextInt(40000);
	    sr.specimenIdentifier = 1000 + r.nextInt(100000);
	    sr.version = 1 + r.nextInt(4);
	    sr.containerNumber = 1 + r.nextInt(36);
	    sr.storageUnit = 1 + r.nextInt(6);
	    sr.compartment = 1 + r.nextInt(2);
	    sr.shelf = 1 + r.nextInt(4);
	    sr.rack = 1 + r.nextInt(3);
	    
	    sReplicates.put(Integer.toString(sr.PK), sr);
	}
    }

}//
