/*******************************************************************************
 * Copyright (c) 2012 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * CONTRIBUTORS:
 * 		Henrik Rentz-Reichert (initial contribution)
 * 
 *******************************************************************************/

package org.eclipse.etrice.generator.generic

import com.google.inject.Inject
import java.util.ArrayList
import org.eclipse.etrice.core.room.ProtocolClass
import org.eclipse.etrice.core.room.Port
import org.eclipse.etrice.core.room.InterfaceItem
import org.eclipse.etrice.core.room.SAPRef
import org.eclipse.etrice.core.room.SPPRef
import org.eclipse.etrice.core.room.Message
import org.eclipse.etrice.core.room.MessageFromIf
import org.eclipse.etrice.generator.generic.RoomExtensions
import org.eclipse.xtext.util.Pair
import static org.eclipse.xtext.util.Tuples.*


class GenericProtocolClassGenerator {

	@Inject extension ILanguageExtension langExt
	@Inject extension RoomExtensions roomExt

	def genMessageIDs(ProtocolClass pc) {
		var offset = 0
		
		var list = new ArrayList<Pair<String, String>>()
		list.add(pair(memberInDeclaration(pc.name, "MSG_MIN"), offset.toString))
		for (msg : pc.allOutgoingMessages) {
			offset = offset+1
			list.add(pair(memberInDeclaration(pc.name, "OUT_"+msg.name), offset.toString))
		}
		for (msg : pc.allIncomingMessages) {
			offset = offset+1
			list.add(pair(memberInDeclaration(pc.name, "IN_"+msg.name), offset.toString))
		}
		offset = offset+1
		list.add(pair(memberInDeclaration(pc.name, "MSG_MAX"), offset.toString))
		
		return langExt.genEnumeration(langExt.memberInDeclaration(pc.name, "msg_ids"), list)
	}
	
	def getMessageID(MessageFromIf mif) {
		return getMessageID(mif.message, mif.from)
	}
	
	def getMessageID(Message msg, InterfaceItem item) {
		if (item instanceof Port) {
			var p = item as Port;
			var direction = if (p.isConjugated())"OUT_" else "IN_"
			return memberInUse(p.getProtocol().getName(), direction+msg.getName())
		}
		else if (item instanceof SAPRef) {
			var sap = item as SAPRef;
			return memberInUse(sap.getProtocol().getName(), "OUT_"+msg.getName())
		}
		else if (item instanceof SPPRef) {
			var spp = item as SPPRef;
			return memberInUse(spp.getProtocol().getName(), "IN_"+msg.getName())
		}

		return "unknown interface item";
	}
}