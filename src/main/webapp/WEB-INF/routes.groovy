get "/admin", forward: "/admin/admin.html"

get "/groups/@groupId/users/@userId?", forward: "/users.groovy?groupId=@groupId"
put "/groups/@groupId/users/@userId", forward: "/users.groovy?edit=true&groupId=@groupId&userId=@userId"
delete "/groups/@groupId/users/@userId", forward: "/users.groovy?delete=true&groupId=@groupId&userId=@userId"
post "/groups/@groupId/users", forward: "/users.groovy?groupId=@groupId&new=true"

get "/groups/@groupId?", forward: "/groups.groovy"
put "/groups/@groupId", forward: "/groups.groovy?edit=true&groupId=@groupId"
delete "/groups/@groupId", forward: "/groups.groovy?delete=true&groupId=@groupId"
post "/groups", forward: "/groups.groovy?new=true"
