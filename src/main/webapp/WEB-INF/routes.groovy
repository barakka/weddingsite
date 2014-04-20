get "/backend/@groupId/newblob", forward: "/newblob.groovy?groupId=@groupId"
get "/backend/blob/@blobKey", forward: "/blob.groovy?blobKey=@blobKey"
get "/backend/blobkey/@blobKey", forward: "/blobkey.groovy?blobKey=@blobKey"