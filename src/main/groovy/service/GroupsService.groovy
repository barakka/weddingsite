package service

import model.Group;
import groovy.json.JsonBuilder
import utils.FullJSON
import utils.JSON;

class GroupsService {
	public JsonBuilder listAll(){
		def groups = Group.findAll();
		
		return new JsonBuilder(groups.collect {it as JSON});
	}
	
	public JsonBuilder findById(long id){
		def group = Group.get(id);
		
		if (group!=null){
			return new JsonBuilder(group as FullJSON);
		} else {
			return new JsonBuilder([]);
		}
	}
}
