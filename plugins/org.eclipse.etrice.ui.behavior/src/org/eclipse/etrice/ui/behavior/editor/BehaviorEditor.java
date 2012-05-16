/*******************************************************************************
 * Copyright (c) 2010 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.etrice.ui.behavior.editor;


import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.etrice.core.naming.RoomNameProvider;
import org.eclipse.etrice.core.room.ActorClass;
import org.eclipse.etrice.core.room.RefinedState;
import org.eclipse.etrice.core.room.RoomFactory;
import org.eclipse.etrice.core.room.State;
import org.eclipse.etrice.core.room.StateGraph;
import org.eclipse.etrice.core.room.util.RoomHelpers;
import org.eclipse.etrice.ui.behavior.Activator;
import org.eclipse.etrice.ui.behavior.support.ContextSwitcher;
import org.eclipse.etrice.ui.behavior.support.SupportUtil;
import org.eclipse.etrice.ui.common.editor.RoomDiagramEditor;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditorInput;
import org.eclipse.swt.graphics.Image;


public class BehaviorEditor extends RoomDiagramEditor {

	public static final String BEHAVIOR_EDITOR_ID = "org.eclipse.etrice.ui.behavior.editor.BehaviorEditor";
	
	public BehaviorEditor() {
		super();
	}
	
	@Override
	public Image getDefaultImage() {
		return Activator.getImage("icons/Behavior.gif");
	}

	@SuppressWarnings("restriction")
	@Override
	protected void initializeGraphicalViewer() {
		super.initializeGraphicalViewer();
		
		Command cmd = new RecordingCommand(getEditingDomain()) {
			@Override
			protected void doExecute() {
				ContextSwitcher.switchTop(getDiagramTypeProvider().getDiagram());
			}
		};
		getEditingDomain().getCommandStack().execute(cmd);
		getEditingDomain().getCommandStack().flush();
	}
	
	@SuppressWarnings("restriction")
	public boolean showStateGraph(StateGraph sg) {
		URI boUri = EcoreUtil.getURI(sg);
		final StateGraph mySG = (StateGraph) getEditingDomain().getResourceSet().getEObject(boUri, true);
		if (mySG==null)
			return false;
		
		Command cmd = new RecordingCommand(getEditingDomain()) {
			@Override
			protected void doExecute() {
				ContextSwitcher.switchTo(getDiagramTypeProvider().getDiagram(), mySG);
			}
		};
		getEditingDomain().getCommandStack().execute(cmd);
		getEditingDomain().getCommandStack().flush();
		
		return true;
	}

	/**
	 * @return the actor class of this editor
	 */
	public ActorClass getActorClass() {
		Diagram diagram = ((DiagramEditorInput)getEditorInput()).getDiagram();
		EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(diagram);
		if (bo instanceof ActorClass)
			return (ActorClass) bo;
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.ui.internal.editor.DiagramEditorInternal#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("restriction")
	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditingDomain().getCommandStack().execute(new RecordingCommand(getEditingDomain()) {
			protected void doExecute() {
				removeEmptySubgraphs();
				rebaseRefinedStates();
				removeUnusedRefinedStates();
			}
		});
		
		super.doSave(monitor);
	}

	/**
	 * 
	 */
	protected void removeUnusedRefinedStates() {
		@SuppressWarnings("restriction")
		Diagram diagram = getDiagramTypeProvider().getDiagram();
		ActorClass ac = SupportUtil.getActorClass(diagram);
		
		if (ac.getStateMachine()!=null) {
			ArrayList<RefinedState> toBeRemoved = new ArrayList<RefinedState>();
			for (State s : ac.getStateMachine().getStates()) {
				if (s instanceof RefinedState) {
					if (isUnused((RefinedState)s))
						toBeRemoved.add((RefinedState) s);
				}
			}
			
			ac.getStateMachine().getStates().removeAll(toBeRemoved);
		}
	}

	/**
	 * @param s
	 * @return
	 */
	private boolean isUnused(RefinedState s) {
		if (RoomHelpers.hasDirectSubStructure(s))
			return false;
		if (RoomHelpers.hasDetailCode(s.getEntryCode()))
			return false;
		if (RoomHelpers.hasDetailCode(s.getExitCode()))
			return false;
		
		return true;
	}

	protected void removeEmptySubgraphs() {
		@SuppressWarnings("restriction")
		Diagram diagram = getDiagramTypeProvider().getDiagram();

		// if our current context is an empty state graph we go one level up
		StateGraph current = ContextSwitcher.getCurrentStateGraph(diagram);
		if (current!=null && current.eContainer() instanceof State) {
			State s = (State) current.eContainer();
			if (!RoomHelpers.hasDirectSubStructure(s)) {
				ContextSwitcher.goUp(diagram, current);
			}
		}
		
		ArrayList<Shape> toBeRemoved = new ArrayList<Shape>();
		for (Shape ctxShape : diagram.getChildren()) {
			EObject bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(ctxShape);
			if (bo.eIsProxy()) {
				toBeRemoved.add(ctxShape);
				continue;
			}
			
			assert(bo instanceof StateGraph): "expected state graph";
			
			StateGraph sg = (StateGraph) bo;
			if (sg.eContainer() instanceof State) {
				State s = (State) sg.eContainer();
				if (!RoomHelpers.hasDirectSubStructure(s)) {
					EcoreUtil.delete(sg);
					toBeRemoved.add(ctxShape);
				}
			}
		}
		
		// need to recursively delete the shapes to avoid dangling HREFs
		for (Shape shape : toBeRemoved) {
			EcoreUtil.delete(shape, true);
		}
	}
	
	protected void rebaseRefinedStates() {
		@SuppressWarnings("restriction")
		Diagram diagram = getDiagramTypeProvider().getDiagram();
		ActorClass ac = SupportUtil.getActorClass(diagram);

		if (ac.getStateMachine()==null)
			return;
		
		ArrayList<RefinedState> refinedStates = new ArrayList<RefinedState>();
		ArrayList<String> paths = new ArrayList<String>();
		HashMap<String, RefinedState> path2rs = new HashMap<String, RefinedState>();
		TreeIterator<EObject> it = ac.getStateMachine().eAllContents();
		while (it.hasNext()) {
			EObject obj = it.next();
			if (obj instanceof RefinedState) {
				refinedStates.add((RefinedState) obj);
				String path = RoomNameProvider.getFullPath((RefinedState) obj);
				paths.add(path);
				path2rs.put(path, (RefinedState) obj);
			}
		}
		
		// we sort the paths to have paths with same beginning in descending length
		java.util.Collections.sort(paths, java.util.Collections.reverseOrder());
		
		// find the best matching context
		HashMap<RefinedState, RefinedState> rs2parent = new HashMap<RefinedState, RefinedState>();
		for (RefinedState rs : refinedStates) {
			String fullPath = RoomNameProvider.getFullPath(rs);
			for (String path : paths) {
				if (!fullPath.equals(path) && fullPath.startsWith(path) && fullPath.charAt(path.length())==RoomNameProvider.PATH_SEP.charAt(0)) {
					RefinedState parent = path2rs.get(path);
					if (!(parent.getSubgraph()!=null && parent.getSubgraph().getStates().contains(rs)))
						rs2parent.put(rs, parent);
					break;
				}
			}
		}
		
		// move all to the new context
		for (RefinedState rs : rs2parent.keySet()) {
			RefinedState parent = rs2parent.get(rs);
			if (parent.getSubgraph()==null)
				parent.setSubgraph(RoomFactory.eINSTANCE.createStateGraph());
			parent.getSubgraph().getStates().add(rs);
		}
	}
}
