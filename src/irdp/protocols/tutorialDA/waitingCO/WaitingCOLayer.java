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

package irdp.protocols.tutorialDA.waitingCO;

import irdp.protocols.tutorialDA.events.ProcessInitEvent;
import net.sf.appia.core.Layer;
import net.sf.appia.core.Session;
import net.sf.appia.core.events.SendableEvent;
import net.sf.appia.core.events.channel.ChannelInit;

/**
 * Reliable causal order broadcast waiting algorithm.
 * 
 * Note: waiting because it might occur that a msg must wait to be delivered
 * because the msg that causally precede haven't arrived yet. Each msg has a
 * vector clock associated in order to determine which msgs precede it.
 * 
 * July 2003
 * 
 * @author MJoaoMonteiro
 */
public class WaitingCOLayer extends Layer {

  /**
   * Standard constructor
   */
  public WaitingCOLayer() {

    evProvide = new Class[3];
    evProvide[0] = ChannelInit.class;
    evProvide[1] = SendableEvent.class;
    evProvide[2] = ProcessInitEvent.class;

    evRequire = new Class[1];
    evRequire[0] = ChannelInit.class;

    evAccept = new Class[3];
    evAccept[0] = ChannelInit.class;
    evAccept[1] = SendableEvent.class;
    evAccept[2] = ProcessInitEvent.class;

  }

  /**
   * @see appia.Layer#createSession()
   */
  public Session createSession() {
    return new WaitingCOSession(this);
  }
}
