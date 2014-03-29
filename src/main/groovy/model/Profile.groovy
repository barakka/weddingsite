package model

import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.logging.GroovyLogger
import utils.JSON

/**
 * Created by rserafin on 22/01/2014.
 */
@Entity
class Profile {
    @groovyx.gaelyk.datastore.Key
    long id;
    boolean complete;
    int stage;

    // Stage 1 - participation
    int numParticipants;
    int numChildren;

    // Stage 2 - location
    String origin;
    boolean needsAccomodation;

    // Stage 3 - other
    boolean needsBeautyService

    // Stage 4 - participants

    public void update(Map properties){
        properties.each {
            this.setProperty(it.key,it.value);
        }
    }

    @Override
    public Object asType(Class type) {
        if (type == JSON) {
            def json = new JSON()
            json.putAll([id: id, complete: complete, stage: stage, numParticipants: numParticipants,
                    numChildren: numChildren, origin: origin, needsAccomodation: needsAccomodation,
                    needsBeautyService: needsBeautyService
            ])
            return json
        }
    }


}
