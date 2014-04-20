

def uri = new URI(blobstore.createUploadUrl("/saveblob.groovy?groupId=" + params.groupId));

print uri.path;