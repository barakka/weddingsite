import com.google.appengine.api.blobstore.BlobKey;

def blob = new BlobKey(params.blobKey);

response.contentType = blob.contentType
blobstore.serve(blob, response)