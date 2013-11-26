package ca.gc.agr.mbb.androidmockws;

import static spark.Spark.*;
import spark.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import com.google.gson.Gson;

import ca.gc.agr.mbb.androidmockws.seqdb.SpecimenReplicate;
import ca.gc.agr.mbb.androidmockws.seqdb.Counter;

public class AndroidMockWS {
    public static final int HTTP_200_OK = 200;
    public static final int HTTP_204_No_Content = 204; // returned by successful delete
    public static final int HTTP_400_Bad_Request = 400;
    public static final int HTTP_404_Not_Found = 404;
    public static final int HTTP_405_Method_Not_Allowed = 405;
    public static String thisRoute = null;
    public static final String RESPONSE_TYPE = "application/json";
    public static final String VERSION = "v1";
    public static final String COUNT = "count";
    public static final String SPECIMEN_REPLICATE = "specimenReplicate";
    public static final String SPECIMEN_REPLICATE_PATH = "/" + VERSION + "/" + SPECIMEN_REPLICATE;
    public static final String SPECIMEN_REPLICATE_ID = ":ID";
    public static final String VAR_PREFIX = ":";

    public static final String PAGING_OFFSET="offset";
    public static final String PAGING_LIMIT="limit";
    public static final int PAGING_LIMIT_LIMIT=500;

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
	thisRoute = SPECIMEN_REPLICATE_PATH;
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    Gson gson = new Gson();
		    // .. Show something ..
		    System.err.println("*GET: " + request.pathInfo());
		    StringBuilder sb = new StringBuilder();
		    final int size = specimenReplicates.size();
		    
		    int offset = 0;
		    int limit = 100;

		    String pagingOffset = request.queryParams(PAGING_OFFSET);
		    if(pagingOffset != null){
			if(! isInteger(pagingOffset)) {
			    response.status(400);
			    return error(PAGING_OFFSET + ": Not an integer: " + pagingOffset, gson);
			}
			offset = Integer.parseInt(pagingOffset);
		    }
		    String pagingLimit = request.queryParams(PAGING_LIMIT);
		    if(pagingLimit != null){
			if(! isInteger(pagingLimit)) {
			    response.status(400);
			    return error(PAGING_LIMIT + ": Not an integer: " + pagingLimit, gson);
			}
			limit = Integer.parseInt(pagingLimit);
		    }

		    if(offset < 0 || limit < 1){
			response.status(406);
			return error("offset <0 or limit < 1", gson);
		    }

		    if(limit >PAGING_LIMIT_LIMIT){
			response.status(406);
			return error("offset > MAX=" + PAGING_LIMIT_LIMIT, gson);
		    }

		    String baseUrl = request.scheme() + "://" + request.host() + request.pathInfo();
		    int previous = offset-limit;
		    String[] uris = null;
		    if(offset < size){
			if(offset+limit > size){
			    limit = size - offset;
			}
			uris = new String[limit];
			int i=0;
			int end = limit+offset;
			for (String key : specimenReplicates.keySet()) {
			    if(i > end){
				break;
			    }
			    if(i >= offset && i < offset + limit){
				uris[i-offset] = makeUrl(request, SPECIMEN_REPLICATE_PATH) + "/" + key;
			    }
			    ++i;
			}
			if(previous < 0){
			    previous = 0;
			}
		    }else{
			previous = size - limit;
		    }
		    Counter counter = new Counter(size, offset, limit, SPECIMEN_REPLICATE, uris, 
						  makePreviousUrl(size, offset, limit, baseUrl),
						  makeNextUrl(size, offset, limit, baseUrl));
		    return gson.toJson(counter);
		}
	    });

	// get by ID
	thisRoute = SPECIMEN_REPLICATE_PATH + "/" + SPECIMEN_REPLICATE_ID;
	get(new Route(thisRoute) {
		@Override
		public Object handle(Request request, Response response) {
		    response.type(RESPONSE_TYPE);
		    // .. Show something ..
		    Gson gson = new Gson();
		    String id = request.params(SPECIMEN_REPLICATE_ID);
		    System.err.println("*GET: " + request.pathInfo() + "   id=" + id);
		    
		    StringBuilder errorString = new StringBuilder();
		    MutableInt errorCode = new MutableInt(HTTP_200_OK);
		    if(!integerAndFound(request, SPECIMEN_REPLICATE_ID, specimenReplicates, errorCode, errorString)){
			response.status(400);
			return error(errorString.toString(), gson);
		    }

		    if(! isInteger(id)) {
			response.status(400);
			return error("Not an integer: " + id, gson);
		    }else{
			if(specimenReplicates.containsKey(id)){
			    response.status(errorCode.value);
			    SpecimenReplicate sr = specimenReplicates.get(id);
			    sr.makeParent(makeUrl(request, SPECIMEN_REPLICATE_PATH));
			    return gson.toJson(sr);
			}else{
			    response.status(404);
			    return error("Not found: " + id, gson); 
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
		    //if(!integerAndFound(request, SPECIMEN_REPLICATE_ID, specimenReplicates, errorString)){
		    //response.status(400);
		    //return error(errorString.toString(), gson);
		    //}
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

    ////////////////////////////////////////////////
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
	for(int i=1; i<(10000 + r.nextInt(1000)); i++){
	    SpecimenReplicate sr = new SpecimenReplicate();
	    sReplicates.put(Integer.toString(sr.primaryKey), sr);
	}
	
	List<String> keysAsArray = new ArrayList<String>(sReplicates.keySet());
	for (String key: keysAsArray){
	    SpecimenReplicate sr = sReplicates.get(key);
	    if(r.nextInt(100)>80){
		sr.parentId = keysAsArray.get(r.nextInt(keysAsArray.size()));
	    }
	}

    }

    public final static String jsonWrap(final String s){
	return "{\n" + s + "\n}";
    }


    private static final String makePreviousUrl(final int size, final int offset, int limit, final String baseUrl){
	int prevOffset = offset - limit;
	if(prevOffset > size | prevOffset < 0){
	    return null;
	}
	if(prevOffset + limit > size){
	    limit = size -prevOffset;
	}
	return baseUrl + "?" + PAGING_OFFSET+ "=" + prevOffset + "&" + PAGING_LIMIT + "=" + limit;
    }

    private static final String makeNextUrl(final int size, final int offset, final int limit, final String baseUrl){
	int nextOffset = offset + limit;
	if(nextOffset > size){
	    return null;
	}
	return baseUrl + "?" + PAGING_OFFSET+ "=" + nextOffset + "&" + PAGING_LIMIT + "=" + limit;
    }


    private static final String error(String s, final Gson gson){
	Error e = new Error(s);
	return gson.toJson(e);
    }

    private static boolean integerAndFound(Request request, String paramKey, Map <String, SpecimenReplicate> repMap, MutableInt errorCode, StringBuilder errorString){
	System.err.println("Looking up paramKey: " + paramKey);
	String param = request.params(paramKey);
	if(param == null){
	    errorString.append(paramKey + " is null");
	    System.err.println(paramKey + " is null");
	    errorCode.value = HTTP_400_Bad_Request;
	}else{
	    if(!isInteger(param)){
		errorString.append(paramKey + "=" + param + ";  is not an integer");
		errorCode.value = HTTP_400_Bad_Request;
	    }else{
		if(!repMap.containsKey(param)){
		    errorString.append(paramKey + " is not found");
		    errorCode.value = HTTP_400_Bad_Request;
		}else{
		    return true;
		}
	    }
	}
	return false;
    }

    public String makeUrl(Request request, String path){
	return request.scheme() + "://" + request.host() + path;
    }

}//
