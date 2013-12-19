package ca.gc.agr.mbb.androidmockws;


public class Service{
    public String description;
    volatile public String path;
    public String url=null;

    public Service(final String description, final String path){
	this.description = description;
	this.path = path;
    }

    public Service(final Service service){
	this.description = service.description;
	this.url = service.url;
    }
}
