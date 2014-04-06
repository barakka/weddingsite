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
	
	// Stage 2 - participants
    int numParticipants;
    int numYoungChildren;
	int numMajorChildren;
	
	// Stage 3 - dinner requirements
	boolean vegetarian;
	boolean celiac;
	boolean allergies;
	String allergiesDetails;
	String otherDinnerRequirements;

    // Stage 4 - transportation
    String origin;
	String orginDetails;
	String transportation;
	
	// Stage 5 - Accommodation
    boolean needsAccommodation;
	String accommodationType;

    // Stage 6 - other
    boolean needsBeautyService;
	boolean needsIroning;
	boolean needsMakeUp;
	boolean needsFlowers;
	boolean needsSightseeing;
	String needsOther;

    // Stage 7 - participants details

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
				complete: complete, 
				stage: stage, 
				
				participationConfirmed: participationConfirmed, 
				
				numParticipants: numParticipants, 
				numYoungChildren: numYoungChildren,
				numMajorChildren: numMajorChildren,
				
				vegetarian: vegetarian,
				celiac: celiac,
				allergies: allergies,
				allergiesDetails: allergiesDetails,
				otherDinnerRequirements: otherDinnerRequirements,
				
				origin: origin,
				orginDetails: orginDetails,
				transportation: transportation, 
				
				needsAccommodation: needsAccommodation,
				accommodationType: accommodationType,				
				
				needsBeautyService: needsBeautyService,
				needsIroning: needsIroning,
				needsMakeUp: needsMakeUp,
				needsFlowers: needsFlowers,
				needsSightseeing: needsSightseeing,
				needsOther: needsOther
            ])
            return json
        }
    }


}
