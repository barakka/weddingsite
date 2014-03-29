package model

import groovyx.gaelyk.datastore.Entity;
import groovyx.gaelyk.datastore.Key
import groovyx.gaelyk.logging.GroovyLogger
import utils.FullJSON
import utils.JSON;

@Entity
class Group {
	@Key
	long id
	String name

    com.google.appengine.api.datastore.Key profileKey;

    @Override
    public Object asType(Class type){
        if (type == FullJSON){
            def json = new FullJSON()

            json.putAll([ id: id, name: name]);

            json["users"] = users.collect({ it as JSON})

            json["profile"] =  profile as JSON

            return json
        }

        if (type == JSON){
            def json = new JSON();
            json.putAll([ id: id, name: name]);
            return json;
        }
    }

    private Profile getProfile() {
        if (!profileKey){
            def profile = new Profile()
            profile.save()

            profileKey = ["Profile",profile.id] as com.google.appengine.api.datastore.Key
            save()

            return profile;
        } else {
            return profileKey.get() as Profile
        }
    }

    private List<User> getUsers(){
        def parentKey = ["Group", id] as com.google.appengine.api.datastore.Key
        return User.findAll( { ancestor parentKey });
    }
}
