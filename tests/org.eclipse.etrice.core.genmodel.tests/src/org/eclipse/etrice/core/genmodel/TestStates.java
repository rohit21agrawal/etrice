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

package org.eclipse.etrice.core.genmodel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.etrice.core.genmodel.etricegen.ExpandedActorClass;
import org.eclipse.etrice.core.genmodel.etricegen.Root;
import org.eclipse.etrice.core.genmodel.etricegen.TransitionChain;
import org.eclipse.etrice.core.naming.RoomNameProvider;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.etrice.core.room.ChoicePoint;
import org.eclipse.etrice.core.room.ContinuationTransition;
import org.eclipse.etrice.core.room.RoomPackage;
import org.eclipse.etrice.core.room.State;
import org.eclipse.etrice.core.room.Transition;

public class TestStates extends TestInstanceModelBuilderBase {
	
	private Root root;
	private HashMap<EClass, ArrayList<EObject>> instances;

	@Before
	public void setUp() {
		prepare();
		
		root = buildInstanceModel("states.room");
		instances = collectInstances(root.getXpActorClasses().get(0));
	}
	
	@Test
	public void testNumbers() {
		assertEquals("number of states", 7, instances.get(RoomPackage.eINSTANCE.getSimpleState()).size());
		assertEquals("number of choice points", 1, instances.get(RoomPackage.eINSTANCE.getChoicePoint()).size());
		assertEquals("number of transition points", 2, instances.get(RoomPackage.eINSTANCE.getTransitionPoint()).size());
		assertEquals("number of entry points", 1, instances.get(RoomPackage.eINSTANCE.getEntryPoint()).size());
		assertEquals("number of exit points", 1, instances.get(RoomPackage.eINSTANCE.getExitPoint()).size());
		assertEquals("number of initial transitions", 2, instances.get(RoomPackage.eINSTANCE.getInitialTransition()).size());
		assertEquals("number of triggered transitions", 9, instances.get(RoomPackage.eINSTANCE.getTriggeredTransition()).size());
		assertEquals("number of continuation transitions", 3, instances.get(RoomPackage.eINSTANCE.getContinuationTransition()).size());
		assertEquals("number of branch transitions", 2, instances.get(RoomPackage.eINSTANCE.getCPBranchTransition()).size());
	}
	
	@Test
	public void testXPACGeneral() {
		ExpandedActorClass xpac = root.getXpActorClasses().get(0);
		assertEquals("actor class", xpac.getActorClass().getName(), "ATest");
		EList<TransitionChain> chains = xpac.getTransitionChains();
		assertEquals("number of chains", 11, chains.size());
		assertEquals("numer of triggers", 3, xpac.getTriggers().size());
	}
	
	@Test
	public void testGraph() {
		ArrayList<EObject> states = instances.get(RoomPackage.eINSTANCE.getSimpleState());
//		for (EObject obj : states) {
//			LogicalSystem.out.println("state "+RoomNameProvider.getFullPath((StateGraphItem) obj));
//		}

		ExpandedActorClass xpac = root.getXpActorClasses().get(0);
		
		State s = (State) getStateGraphItem(states, "State3_State3");
		assertNotNull("state exists", s);
		assertEquals("number of incoming transitions", 3, xpac.getIncomingTransitions(s).size());
		assertEquals("number of outgoing transitions", 2, xpac.getOutgoingTransitions(s).size());
		
		assertEquals("active triggers", 3, xpac.getActiveTriggers(s).size());
		
		s = (State) getStateGraphItem(states, "State2");
		assertNotNull("state exists", s);
		assertEquals("number of incoming transitions", 3, xpac.getIncomingTransitions(s).size());
		assertEquals("number of outgoing transitions", 1, xpac.getOutgoingTransitions(s).size());
		
		assertEquals("active triggers", 2, xpac.getActiveTriggers(s).size());

		ArrayList<EObject> cps = instances.get(RoomPackage.eINSTANCE.getChoicePoint());
		
		ChoicePoint cp = (ChoicePoint) cps.get(0);
		assertEquals("choicepoint name", "CP1", RoomNameProvider.getFullPath(cp));
		assertEquals("number of incoming transitions", 1, xpac.getIncomingTransitions(cp).size());
		assertEquals("number of outgoing transitions", 3, xpac.getOutgoingTransitions(cp).size());
		
		ContinuationTransition dflt = xpac.getDefaultBranch(xpac.getOutgoingTransitions(cp));
		assertNotNull("default branch", dflt);
	}
	
	@Test
	public void testChains() {
		ArrayList<EObject> cts = instances.get(RoomPackage.eINSTANCE.getContinuationTransition());
//		for (EObject obj : cts) {
//			LogicalSystem.out.println("ct "+RoomNameProvider.getFullPath((StateGraphItem) obj));
//		}
		
		Transition t = (Transition) getStateGraphItem(cts, "State3_TRANS_tr1_FROM_tp1_TO_State3");
		assertNotNull("transition", t);

		ExpandedActorClass xpac = root.getXpActorClasses().get(0);
		TransitionChain chain = xpac.getChain(t);
		assertNotNull("chain", chain);
		assertEquals("chain name", "TRANS_tr5_FROM_tp0_TO_State3_tp1_BY_afct", RoomNameProvider.getFullPath(chain.getTransition()));
	}
}
