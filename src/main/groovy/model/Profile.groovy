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
	boolean participationConfirmed;
    int numParticipants;
    int numYoungChildren;
	int numMajorChildren;

    // Stage 2 - location
    String origin;
	String orginDetails;
	String transportation;
    boolean needsAccomodation;
	String accomodationType;

    // Stage 3 - other
    boolean needsBeautyService;
	boolean needsIroning;
	boolean needsMakeUp;
	boolean needsSightseeing;
	String needsOther;

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
            json.putAll([
				id: id, 
				complete: complete, 
				stage: stage, 
				
				participationConfirmed: participationConfirmed, 
				numParticipants: numParticipants, 
				numYoungChildren: numYoungChildren,
				numMajorChildren: numMajorChildren,
				
				origin: origin,
				orginDetails: orginDetails,
				transportation: transportation, 
				needsAccomodation: needsAccomodation,
				accomodationType: accomodationType,
				
				needsBeautyService: needsBeautyService,
				needsIroning: needsIroning,
				needsMakeUp: needsMakeUp,
				needsSightseeing: needsSightseeing,
				needsOther: needsOther
            ])
            return json
        }
    }


}
