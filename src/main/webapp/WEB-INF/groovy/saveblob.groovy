def blobs = blobstore.getUploads(request)
def blob = blobs["image"].get(0)

def keys = "key=" + blobs["image"].collect( {it.keyString}).join("&key=");

log.warning keys

response.status = 302
 
if (blob) {	
	redirect "/blobkey.groovy?" + keys
} else {
	redirect "/blobkey.groovy?key=failure"
}