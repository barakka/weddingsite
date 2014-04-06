import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import model.Comment;
import model.User

import com.google.appengine.api.datastore.Email
import com.google.appengine.api.datastore.Key
import com.google.appengine.api.datastore.Text;

import utils.JSON


def String list(parentKey){
	def comments = Comment.findAll {
		ancestor parentKey
	}
	
	return new JsonBuilder(comments.collect({it as Map})).toPrettyString();
}

def json = new JsonBuilder();
def parentKey = ["Group", params.groupId as long] as Key;


if (params["new"]){
	def result = new JsonSlurper().parse(request.reader);
	
	def comment = new Comment(parent: parentKey, to: result.to, message: result.message, 
		date: Date.parse("yyyy-MM-dd'T'HH:mm:ss.S'Z'",result.date));
	comment.save();
	
	print list(parentKey);
} else if (params["delete"]){
	def comment = Comment.get(parentKey, params.commentId as long);

	if (comment!=null){		
		comment.delete();
		print list(parentKey);
	} else {
		response.sendError(response.SC_NOT_FOUND);
	}
} else {
	if (params.commentId){		
		def comment = Comment.get(parentKey,params.commentId as long);
		
		if (comment!=null){
			json = new JsonBuilder(comment as Map);
		} 
		
		print json.toPrettyString();
	} else {
		
		print list(parentKey);
	}
}