package model

import groovyx.gaelyk.datastore.Entity;
import groovyx.gaelyk.datastore.Key;

@Entity
class Group {
	@Key
	long id
	String name
	
	public Map asMap(boolean includeChilds=false){
		Map result = [ id: id, name: name];
		if (includeChilds){
			def parentKey = ["Group", id] as com.google.appengine.api.datastore.Key;
			result.putAll([users: User.findAll( { ancestor parentKey }).collect({ it.asMap()})]);
		}
		
		return result
	}
}
