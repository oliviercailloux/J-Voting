package io.github.oliviercailloux.y2018.j_voting;

import java.util.*;
import java.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SWFCommander{
    private SocialWelfareFunction swf ; 
	private static Logger LOGGER = LoggerFactory.getLogger(SWFCommander.class.getName());
    
	
	public SWFCommander(SocialWelfareFunction s) {
		swf = s;
	}
	
    /**
     * Asks the user to enter a StrictPreference 
     * @return the entered StrictPreference
     * @throws IOException when the entered preference is empty.
     */
    public static StrictPreference askPreference() {
    	LOGGER.debug("askPreference");
        System.out.println("Enter a StrictPreference complete");
        try(Scanner scan = new Scanner(System.in)){
        	LOGGER.debug("Scanner OK");
        	
        	String vote = scan.nextLine();
        	return new ReadProfile().getPreferences(vote);
        }
    }   
    
    /**
     * Asks the user to enter StrictPreferences while he doesn't say no to the question "continue?".
     * Each time the user enters a StrictPreference, it displays the current state of the StrictProfile (the winning StrictPreference).
     * @throws IOException when the entered preference is empty.
     */
    public void createProfileIncrementally() throws IOException{
    	LOGGER.debug("createProfileIncrementally:");
    	StrictProfileBuilder prof = new StrictProfileBuilder();
        boolean keepGoing = true;
        int voterId = 1;

        while(keepGoing){
        	LOGGER.debug("new voter id  : {}", voterId);
            Voter v = new Voter(voterId);
            StrictPreference strictPreference = askPreference();
            LOGGER.debug("strictPreference :{}",strictPreference);
            prof.addProfile(v, strictPreference);
            
            System.out.println("Continue ? (yes/no)");
            try(Scanner scn = new Scanner(System.in)){
            	String answer = scn.nextLine();
            	if(answer.trim().toLowerCase() != ("yes")) {
            		LOGGER.debug("answered no to continue.");
                	keepGoing = false;
            	}
            }
            voterId++;
            System.out.println(swf.getSocietyStrictPreference(prof));
        }
    }
    
}