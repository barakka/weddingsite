import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import model.User

import com.google.appengine.api.datastore.Email
import com.google.appengine.api.datastore.Key
import utils.JSON


def String list(parentKey){
	def users = User.findAll {
		ancestor parentKey
	}
	
	return new JsonBuilder(users.collect({it as JSON})).toPrettyString();
}

def json = new JsonBuilder();
def parentKey = ["Group", params.groupId as long] as Key;


if (params["new"]){
	def result = new JsonSlurper().parse(request.reader);
	
	def user = new User(name: result.name, email: result.email as Email, parent: parentKey,
            male: (result.male ? true : false), predefined: true );
	user.save();
	
	print list(parentKey);
} else if (params["edit"]){
	def result = new JsonSlurper().parse(request.reader);

	def user = User.get(parentKey, params.userId as long);
	
	if (user!=null){
		user.name = result.name;
		user.email = result.email as Email;
		user.male = result.male;
		user.save();
		
		print list(parentKey);
	} else {
		response.sendError(response.SC_NOT_FOUND);
	}
} else if (params["delete"]){
	def user = User.get(parentKey, params.userId as long);

	if (user!=null){		
		user.delete();
		print list(parentKey);
	} else {
		response.sendError(response.SC_NOT_FOUND);
	}
} else {
	if (params.userId){		
		def user = User.get(parentKey,params.userId as long);
		
		if (user!=null){
			json = new JsonBuilder(user as JSON);
		} 
		
		print json.toPrettyString();
	} else {
		print list(parentKey);
	}
}