package service

import model.Group;
import groovy.json.JsonBuilder;

class GroupsService {
	public JsonBuilder listAll(){
		def groups = Group.findAll();
		
		return new JsonBuilder(groups.collect {it.asMap()});
	}
	
	public JsonBuilder findById(long id){
		def group = Group.get(id);
		
		if (group!=null){
			return new JsonBuilder(group.asMap(true));
		} else {
			return new JsonBuilder([]);
		}
	}
}
