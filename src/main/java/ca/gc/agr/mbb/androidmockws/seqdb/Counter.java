package ca.gc.agr.mbb.androidmockws.seqdb;

import java.util.List;

public class Counter{
    int total;
    int limit;
    int offset;
    String type;
    String previousPageUrl;
    String nextPageUrl;
    String[] uris;


    public Counter(final int total, final int offset, final int limit, final String type, final String[] uris, final String previousPageUrl, final String nextPageUrl){
	this.total = total;
	this.offset = offset;
	this.limit = limit;
	this.type = type;
	this.uris = uris;
	this.previousPageUrl = previousPageUrl;
	this.nextPageUrl = nextPageUrl;
    }

}
