package model

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;

import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Parent;
import groovyx.gaelyk.logging.GroovyLogger
import utils.JSON

/**
 * Created by rserafin on 22/01/2014.
 */
@Entity
class Comment {
    @groovyx.gaelyk.datastore.Key
    long id;
	@Parent Key parent;
	
	String to;
	Text message;
	Date date;
	
	
    public void update(Map properties){
        properties.each {
            this.setProperty(it.key,it.value);
        }
    }

    @Override
    public Object asType(Class type) {
        if (type == Map) {
            def json = [:]
            json.putAll([
				id: id, 
				parent: parent as String,
				to: to,
				message: getMessage(),
				date: date
            ])
            return json
        }
    }

	void setMessage(String message){
		this.message = message as Text;
	}
	
	void setMessage(Text message){
		this.message = message;
	}
	
	String getMessage(){
		return this.message?.value;
	}
}
