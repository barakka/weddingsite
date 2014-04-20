import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import groovy.json.JsonBuilder;

def keys = params.key;


if (keys instanceof String){	
	if (keys !='failure'){
		print new JsonBuilder([keys]).toString();
		return;
	}
} else {
	print new JsonBuilder(keys).toString();
	return;
}
	
response.status = 404