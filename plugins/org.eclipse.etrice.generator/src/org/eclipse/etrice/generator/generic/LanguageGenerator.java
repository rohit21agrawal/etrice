/*******************************************************************************
 * Copyright (c) 2010 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * CONTRIBUTORS:
 * 		Thomas Schuetz and Henrik Rentz-Reichert (initial contribution)
 * 
 *******************************************************************************/

package org.eclipse.etrice.generator.generic;

import org.eclipse.etrice.core.room.InitialTransition;
import org.eclipse.etrice.core.room.Message;
import org.eclipse.etrice.core.room.MessageFromIf;
import org.eclipse.etrice.core.room.PrimitiveType;
import org.eclipse.etrice.core.room.Transition;
import org.eclipse.etrice.core.room.Trigger;
import org.eclipse.etrice.core.room.TriggeredTransition;
import org.eclipse.etrice.core.room.VarDecl;
import org.eclipse.etrice.generator.etricegen.ExpandedActorClass;
import org.eclipse.etrice.generator.etricegen.TransitionChain;

public class LanguageGenerator {

	public String getExecuteChain(ExpandedActorClass ac, TransitionChain tc) {
		LanguageTransitionChainVisitor tcv = new LanguageTransitionChainVisitor(ac, tc);
		return tc.genExecuteChain(tcv);
	}

	public String getArgumentList(ExpandedActorClass xpac, Transition t) {
		if (t instanceof InitialTransition)
			// actually is InitialTransition
			return "";
		
		TransitionChain chain = xpac.getChain(t);
		if (!(chain.getTransition() instanceof TriggeredTransition))
			return "";
		
		Trigger trigger = ((TriggeredTransition)chain.getTransition()).getTriggers().get(0);
		MessageFromIf mif = trigger.getMsgFromIfPairs().get(0);
		
		return getTypedArgumentList(mif.getMessage());
	}

	public String getArgumentList(Message m) {
		return getArglistAndTypedData(m.getData())[0];
	}

	public String getTypedData(Message m) {
		return getArglistAndTypedData(m.getData())[1];
	}

	public String getTypedArgumentList(Message m) {
		return getArglistAndTypedData(m.getData())[2];
	}
	
	public String[] getArglistAndTypedData(VarDecl data) {
		if (data==null)
			return new String[] {"", "", ""};
		
		String typeName = data.getType().getName();
		String castTypeName = typeName;
		if (data.getType() instanceof PrimitiveType) {
			String ct = ((PrimitiveType)data.getType()).getCastName();
			if (ct!=null && !ct.isEmpty())
				castTypeName = ct;
		}

		String typedData = typeName+" "+data.getName() + " = ("+castTypeName+") generic_data;\n";
		String dataArg = ", "+data.getName();
		String typedArgList = ", "+typeName+" "+data.getName();
		
		return new String[]{dataArg, typedData, typedArgList};
	}
}
