get "/admin", forward: "/admin/admin.html"
get "/a/@groupId", forward: "/index.html#/@groupId/home"
get "/", forward: "/index.html"
get "/favicon.ico", forward: "/img/favicon.ico"

get "/groups/@groupId/users/@userId?", forward: "/users.groovy?groupId=@groupId"
put "/groups/@groupId/users/@userId", forward: "/users.groovy?edit=true&groupId=@groupId&userId=@userId"
delete "/groups/@groupId/users/@userId", forward: "/users.groovy?delete=true&groupId=@groupId&userId=@userId"
post "/groups/@groupId/users", forward: "/users.groovy?groupId=@groupId&new=true"

get "/groups/@groupId/comments/@commentId?", forward: "/comments.groovy?groupId=@groupId"
put "/groups/@groupId/comments/@commentId", forward: "/comments.groovy?edit=true&groupId=@groupId&commentId=@commentId"
delete "/groups/@groupId/commentss/@commentId", forward: "/comments.groovy?delete=true&groupId=@groupId&commentId=@commentId"
post "/groups/@groupId/comments", forward: "/comments.groovy?groupId=@groupId&new=true"


get "/groups/@groupId?", forward: "/groups.groovy"
put "/groups/@groupId", forward: "/groups.groovy?edit=true&groupId=@groupId"
delete "/groups/@groupId", forward: "/groups.groovy?delete=true&groupId=@groupId"
post "/groups", forward: "/groups.groovy?new=true"
