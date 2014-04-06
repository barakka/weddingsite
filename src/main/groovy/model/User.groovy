package model

import com.google.appengine.api.datastore.PhoneNumber
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Parent;

import com.google.appengine.api.datastore.Email
import com.google.appengine.api.datastore.Key
import groovyx.gaelyk.logging.GroovyLogger
import utils.JSON

@Entity
class User {
	@groovyx.gaelyk.datastore.Key
	long id;
	String name;
    Email email;
    PhoneNumber phoneNumber;
    int shoeSize;

	@Parent Key parent;
	    
    boolean predefined;

    public void update(Map properties){
        properties.each {
            this.setProperty(it.key,it.value);
        }
    }

    @Override
    public Object asType(Class type){
        if (type == Map){
            def json = [:]
            json.putAll( [
                    id: id, 
					name: name, 
					email: getEmail(), 
					phoneNumber: getPhoneNumber(), 
					shoeSize: shoeSize,
                    parent: parent as String, 
                    predefined: predefined
            ])
            
            return json
        }
    }

    void setPhoneNumber(String number){
        this.phoneNumber = number as PhoneNumber;
    }

    void setPhoneNumber(PhoneNumber number){
        this.phoneNumber = number;
    }

    String getPhoneNumber(){
        new GroovyLogger("test").info("Phone number ${this.phoneNumber?.number}")
        return this.phoneNumber?.number;
    }

    void setEmail(String mail){
        this.email = mail as Email;
    }

    void setEmail(Email mail){
        this.email = mail;
    }

    String getEmail(){
        return this.email?.email;
    }
}
