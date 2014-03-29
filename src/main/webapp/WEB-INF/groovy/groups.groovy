import com.google.appengine.api.datastore.Key
import groovy.json.JsonSlurper
import model.Profile
import model.User;
import service.GroupsService

def Long parseId(String groupId){
    if (groupId){
        if (groupId.endsWith("/")){
            return groupId.substring(0,groupId.length()-1) as long;
        } else {
            return groupId as long;
        }
    } else {
        return null;
    }
}

if (params["new"]){
	def result = new JsonSlurper().parse(request.reader);
	
	def group = new model.Group(id: result.id, name: result.name);
	group.save();
} else if (params["edit"]){
	def result = new JsonSlurper().parse(request.reader);

	def group = model.Group.get(parseId(params.groupId));
	
	if (group!=null){
		group.name = result.name;
		group.save();

        if (result.profile){
            Profile profile = group.profile;
            profile.update(result.profile);
            profile.save();
        }

        if (result.users){
            List users= result.users;
            Key parentKey = ["Group", group.id as long] as Key
            users.each {
                if (it.id){
                    def user = User.get(parentKey,it.id);
                    if (user){
                        it.parent = parentKey;
                        user.update(it);
                        user.save();
                        log.info("Existing user saved: ${user.id}, ${user.name}, ${user.parent}")
                    }
                } else {
                    def user = new User(it);
                    user.parent = parentKey;
                    user.save();
                    log.info("New user saved: ${user.id}, ${user.name}, ${user.parent}")
                }
            }
        }
	} else {
		response.sendError(response.SC_NOT_FOUND);
	}
} else if (params["delete"]){
	def group = model.Group.get(parseId(params.groupId));

	if (group!=null){
        group.profile.delete();
        group.users.each { it.delete()}
		group.delete();
		print new GroupsService().listAll().toPrettyString();
	} else {
		response.sendError(response.SC_NOT_FOUND);
	}
} else {
	if (params.groupId){		
		print new GroupsService().findById(parseId(params.groupId)).toPrettyString();
	} else {
		print new GroupsService().listAll().toPrettyString();
	}
}