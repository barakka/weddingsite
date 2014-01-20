package model

import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Parent;

import com.google.appengine.api.datastore.Email
import com.google.appengine.api.datastore.Key

@Entity
class User {
	@groovyx.gaelyk.datastore.Key
	long id;
	String name;
	Email email;
	@Parent Key parent;
	boolean male;
	
	public Map asMap(){
		return [ id: id, name: name, email: email?.email, parent: parent as String, male: male];
	} 
}
