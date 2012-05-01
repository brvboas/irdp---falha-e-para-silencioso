/*
 *
 * Hands-On code of the book Introduction to Reliable Distributed Programming
 * by Christian Cachin, Rachid Guerraoui and Luis Rodrigues
 * Copyright (C) 2005-2011 Luis Rodrigues
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA
 *
 * Contact
 * 	Address:
 *		Rua Alves Redol 9, Office 605
 *		1000-029 Lisboa
 *		PORTUGAL
 * 	Email:
 * 		ler@ist.utl.pt
 * 	Web:
 *		http://homepages.gsd.inesc-id.pt/~ler/
 * 
 */

package irdp.protocols.tutorialDA.print;

import net.sf.appia.core.AppiaEventException;
import net.sf.appia.core.Direction;
import net.sf.appia.core.Event;
import net.sf.appia.core.Layer;
import net.sf.appia.core.Session;
import net.sf.appia.core.events.channel.ChannelInit;

/**
 * Session implementing the Bounded Print protocol.
 * <br>
 * After a defined number of print requests, an alarm
 * is sent and further request are denied.
 * 
 * @author alexp
 */
public class UpperPrintSession extends Session {
  
  /** Creates a new instance of BoundedPrintSession */
  public UpperPrintSession(Layer layer) {
    super(layer);
  }

  public void handle(Event event) {
    if (event instanceof ChannelInit) {
      handleChannelInit((ChannelInit) event);
    } else if (event instanceof PrintRequestEvent) {
      handlePrintRequest((PrintRequestEvent) event);
    } else if (event instanceof PrintConfirmEvent) {
      handlePrintConfirm((PrintConfirmEvent) event);
    }
  }

  private void handleChannelInit(ChannelInit init) {
    try {
      init.go();
    } catch (AppiaEventException e) {
      e.printStackTrace();
    }
  }

  private void handlePrintRequest(PrintRequestEvent request) {
         
	  String stringUpper;
	  
	  stringUpper = request.getString();
	  
	  try{	  
		  
		  stringUpper = stringUpper.toUpperCase();		    
		   
		  //stringUpper = null;
		  
		  stringUpper.toUpperCase();
		  
		  request.setString(stringUpper); 
		  
		  try {	  
        	  
	           request.go();
	           }catch (AppiaEventException e) {
	            
	              	   
	          	   e.printStackTrace();
	          }  
		  
	     }catch(NullPointerException e){
	    	  
	    	 // e.printStackTrace();
	    	  
	    	  try {
	    	         PrintConfirmEvent conf = new PrintConfirmEvent();
	    	    
	    	        	    	         
	                 conf.setId(request.getId());
	                 conf.setChannel(request.getChannel());
	    	 
			    	 PrintStatusEvent status = new PrintStatusEvent();
		             status.setId(conf.getId());
		             status.setStatus(Status.NOK); 
		             status.setChannel(conf.getChannel());
		             status.setDir(Direction.UP);
		             status.setSourceSession(this);
		             status.init();
                     status.go();
                     } catch (AppiaEventException e1) {         	   	
                                                       e1.printStackTrace();
                                                      }
	               } 
	  
	  
	  
          
 }

  private void handlePrintConfirm(PrintConfirmEvent conf) {
	  
    PrintStatusEvent status = new PrintStatusEvent();
    status.setId(conf.getId());
    status.setStatus(Status.OK);

    try {
      status.setChannel(conf.getChannel());
      status.setDir(Direction.UP);
      status.setSourceSession(this);
      status.init();
      status.go();
    } catch (AppiaEventException e) {
    	   	
      e.printStackTrace();
    }
  }
}
