import groovy.json.JsonSlurper;
import service.GroupsService

if (params["new"]){
	def result = new JsonSlurper().parse(request.reader);
	
	def group = new model.Group(id: result.id, name: result.name);
	group.save();
} else if (params["edit"]){
	def result = new JsonSlurper().parse(request.reader);

	def group = model.Group.get(params.groupId as long);
	
	if (group!=null){
		group.name = result.name;
		group.save();
	} else {
		response.sendError(response.SC_NOT_FOUND);
	}
} else if (params["delete"]){
	def group = model.Group.get(params.groupId as long);

	log.info("deleting: " + params.groupId)
	if (group!=null){		
		group.delete();
		print new GroupsService().listAll().toPrettyString();
	} else {
		response.sendError(response.SC_NOT_FOUND);
	}
} else {
	if (params.groupId){		
		print new GroupsService().findById(params.groupId as long).toPrettyString();
	} else {
		print new GroupsService().listAll().toPrettyString();
	}
}