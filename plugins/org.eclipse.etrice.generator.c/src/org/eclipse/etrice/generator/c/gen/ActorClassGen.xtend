/*******************************************************************************
 * Copyright (c) 2011 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * CONTRIBUTORS:
 * 		Henrik Rentz-Reichert (initial contribution)
 * 		Thomas Schuetz (changed for C code generator)
 * 
 *******************************************************************************/

package org.eclipse.etrice.generator.c.gen

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.etrice.core.room.ActorClass
import org.eclipse.etrice.generator.base.ILogger
import org.eclipse.etrice.generator.etricegen.ExpandedActorClass
import org.eclipse.etrice.generator.etricegen.Root
import org.eclipse.xtext.generator.JavaIoFileSystemAccess

import org.eclipse.etrice.generator.extensions.RoomExtensions
import org.eclipse.etrice.generator.generic.ProcedureHelpers
import org.eclipse.etrice.generator.generic.TypeHelpers
import org.eclipse.etrice.generator.generic.GenericActorClassGenerator


@Singleton
class ActorClassGen extends GenericActorClassGenerator {
	
	@Inject extension JavaIoFileSystemAccess fileAccess
	@Inject extension CExtensions stdExt
	@Inject extension RoomExtensions roomExt
	
	@Inject extension ProcedureHelpers helpers
	@Inject extension TypeHelpers
	@Inject extension StateMachineGen stateMachineGen
	@Inject ILogger logger
	
	def doGenerate(Root root) {
		for (xpac: root.xpActorClasses) {
			var path = xpac.actorClass.generationTargetPath+xpac.actorClass.getPath
			
			// header file
			logger.logInfo("generating ActorClass header '"+xpac.actorClass.getCHeaderFileName+"' in '"+path+"'")
			fileAccess.setOutputPath(path)
			fileAccess.generateFile(xpac.actorClass.getCHeaderFileName, root.generateHeaderFile(xpac, xpac.actorClass))

			// source file
			if (hasBehaviorAnnotation(xpac, "BehaviorManual")) {
				logger.logInfo("omitting ActorClass source for '"+xpac.actorClass.name+"' since @BehaviorManual is specified")
			}
			else {
				logger.logInfo("generating ActorClass source '"+xpac.actorClass.getCSourceFileName +"' in '"+path+"'")
				fileAccess.setOutputPath(path)
				fileAccess.generateFile(xpac.actorClass.getCSourceFileName , root.generateSourceFile(xpac, xpac.actorClass))
			}
		}
	}
	
	def hasBehaviorAnnotation(ExpandedActorClass xpac, String annotation) {
		if (xpac.actorClass.annotations != null){
			if(xpac.actorClass.annotations.findFirst(e|e.name == annotation) != null){
				return true;
			}
		}
		return false;		
	}
	
	def generateHeaderFile(Root root, ExpandedActorClass xpac, ActorClass ac) {'''
		/**
		 * @author generated by eTrice
		 *
		 * Header File of ActorClass �xpac.name�
		 * 
		 */

		�generateIncludeGuardBegin(xpac.name)�
		
		#include "etDatatypes.h"
		#include "etMessage.h"
		
		�FOR dataClass : root.getReferencedDataClasses(ac)�
			#include "�dataClass.name�.h"
		�ENDFOR�
		�FOR pc : root.getReferencedProtocolClasses(ac)�
			#include "�pc.name�.h"
		�ENDFOR�
		
		�helpers.userCode(ac.userCode1)�
		
		typedef struct �xpac.name� �xpac.name�;
		
		/* const part of ActorClass (ROM) */
		typedef struct �xpac.name�_const {
��� TODO: needed?			const �xpac.name�* actor;
			/* Ports */
			�FOR ep : ac.getEndPorts()�
				const �ep.getPortClassName()� �ep.name�;
			�ENDFOR�
		} �xpac.name�_const;
		
		/* variable part of ActorClass (RAM) */
		struct �xpac.name� {
			const �xpac.name�_const* constData;
			�helpers.attributes(ac.allAttributes)�
			�IF xpac.hasNonEmptyStateMachine�
				�stateMachineGen.genDataMembers(xpac, ac)�
			�ENDIF�
		};

		void �xpac.name�_init(�xpac.name�* self);

		void �xpac.name�_ReceiveMessage(void* self, void* ifitem, const etMessage* msg);
		
		�helpers.userCode(ac.userCode2)�
		
		�generateIncludeGuardEnd(xpac.name)�
		
	'''
	}
	
	def generateSourceFile(Root root, ExpandedActorClass xpac, ActorClass ac) {'''
		/**
		 * @author generated by eTrice
		 *
		 * Source File of ActorClass �xpac.name�
		 * 
		 */

		#include "�xpac.getCHeaderFileName�"
		
		#include "etActor.h"
		#include "etLogger.h"
		#include "etMSCLogger.h"

		�FOR pc : root.getReferencedProtocolClasses(ac)�
			#include "�pc.getCHeaderFileName�"
		�ENDFOR�
		
		�helpers.userCode(xpac.userCode3)�

		/* interface item IDs */
		�genInterfaceItemConstants(xpac, ac)�

		�IF xpac.hasNonEmptyStateMachine�
			�stateMachineGen.genStateMachine(xpac, ac)�
		�ENDIF�
		
		void �xpac.name�_init(�xpac.name�* self){
			ET_MSC_LOGGER_SYNC_ENTRY("�xpac.name�", "init")
			�IF xpac.hasNonEmptyStateMachine�
				�stateMachineGen.genInitialization(xpac, ac)�
			�ENDIF�
			ET_MSC_LOGGER_SYNC_EXIT
		}
		
		
		void �xpac.name�_ReceiveMessage(void* self, void* ifitem, const etMessage* msg){
			ET_MSC_LOGGER_SYNC_ENTRY("�xpac.name�", "ReceiveMessage")
			�IF xpac.hasNonEmptyStateMachine�
				
				receiveEvent(self, (etPort*)ifitem, msg->evtID, (void*)(&msg[1]));
			�ENDIF�
			
			ET_MSC_LOGGER_SYNC_EXIT
		}
		
		'''
	}
}
