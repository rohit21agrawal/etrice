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
import org.eclipse.etrice.core.room.Message
import org.eclipse.etrice.generator.base.ILogger
import org.eclipse.etrice.generator.etricegen.ExpandedActorClass
import org.eclipse.etrice.generator.etricegen.Root
import org.eclipse.xtext.generator.JavaIoFileSystemAccess

import org.eclipse.etrice.generator.extensions.RoomExtensions
import org.eclipse.etrice.generator.generic.ProcedureHelpers
import org.eclipse.etrice.generator.generic.TypeHelpers


@Singleton
class ActorClassGen {
	
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

			// header file
			logger.logInfo("generating ActorClass header '"+xpac.actorClass.getCSourceFileName +"' in '"+path+"'")
			fileAccess.setOutputPath(path)
			fileAccess.generateFile(xpac.actorClass.getCSourceFileName , root.generateSourceFile(xpac, xpac.actorClass))
		}
	}
	
	def generateHeaderFile(Root root, ExpandedActorClass xpac, ActorClass ac) {'''
		#ifndef _�xpac.name�_H_
		#define _�xpac.name�_H_
		
		#include "etDatatypes.h"
		
		package �ac.getPackage�;
		
���		import org.eclipse.etrice.runtime.java.messaging.Address;
���		import org.eclipse.etrice.runtime.java.messaging.IRTObject;
���		import org.eclipse.etrice.runtime.java.messaging.IMessageReceiver;
���		import org.eclipse.etrice.runtime.java.modelbase.ActorClassBase;
���		import org.eclipse.etrice.runtime.java.modelbase.SubSystemClassBase;
���		import org.eclipse.etrice.runtime.java.modelbase.InterfaceItemBase;
���		import org.eclipse.etrice.runtime.java.debugging.DebuggingService;
		
		
		�FOR dataClass : root.getReferencedDataClasses(ac)�#include "�dataClass.name�.h"
		�ENDFOR�
		
		�FOR pc : root.getReferencedProtocolClasses(ac)�#include "�pc.name�.h"
		�ENDFOR�
		
		�helpers.UserCode(ac.userCode1)�
		
		
		public �IF ac.abstract�abstract �ENDIF�class �ac.name� extends �IF ac.base!=null��ac.base.name��ELSE�ActorClassBase�ENDIF� {
		
			�helpers.UserCode(ac.userCode2)�
			
			//--------------------- ports
			�FOR ep : ac.getEndPorts()�
				protected �ep.getPortClassName()� �ep.name� = null;
			�ENDFOR�
			//--------------------- saps
			�FOR sap : ac.strSAPs�
				protected �sap.getPortClassName()� �sap.name� = null;
			�ENDFOR�
			//--------------------- services
			�FOR svc : ac.serviceImplementations�
				protected �svc.getPortClassName()� �svc.spp.name� = null;
			�ENDFOR�
		
			//--------------------- interface item IDs
			�FOR ep : ac.getEndPorts()�
				protected static final int IFITEM_�ep.name� = �xpac.getInterfaceItemLocalId(ep)+1�;
			�ENDFOR�
			�FOR sap : ac.strSAPs�
				protected static final int IFITEM_�sap.name� = �xpac.getInterfaceItemLocalId(sap)+1�;
			�ENDFOR�
			�FOR svc : ac.serviceImplementations�
				protected static final int IFITEM_�svc.spp.name� = �xpac.getInterfaceItemLocalId(svc.spp)+1�;
			�ENDFOR�
			
			�helpers.Attributes(ac.attributes)�
			�helpers.OperationsDeclaration(ac.operations, ac.name)�
		
			//--------------------- construction
			public �ac.name�(IRTObject parent, String name, Address[][] port_addr, Address[][] peer_addr){
				�IF ac.base==null�
					super(parent, name, port_addr[0][0], peer_addr[0][0]);
				�ELSE�
					super(parent, name, port_addr, peer_addr);
				�ENDIF�
				setClassName("�ac.name�");
				
				�ac.attributes.attributeInitialization�
		
				// own ports
				�FOR ep : ac.getEndPorts()�
					�ep.name� = new �ep.getPortClassName()�(this, "�ep.name�", IFITEM_�ep.name�, �IF ep.multiplicity==1�0, �ENDIF�port_addr[IFITEM_�ep.name�]�IF ep.multiplicity==1�[0]�ENDIF�, peer_addr[IFITEM_�ep.name�]�IF ep.multiplicity==1�[0]�ENDIF�); 
				�ENDFOR�
				// own saps
				�FOR sap : ac.strSAPs�
					�sap.name� = new �sap.getPortClassName()�(this, "�sap.name�", IFITEM_�sap.name�, 0, port_addr[IFITEM_�sap.name�][0], peer_addr[IFITEM_�sap.name�][0]); 
				�ENDFOR�
				// own service implementations
				�FOR svc : ac.serviceImplementations�
					�svc.spp.name� = new �svc.getPortClassName()�(this, "�svc.spp.name�", IFITEM_�svc.spp.name�, port_addr[IFITEM_�svc.spp.name�], peer_addr[IFITEM_�svc.spp.name�]); 
				�ENDFOR�
			}
			
		
			//--------------------- lifecycle functions
			public void init(){
				initUser();
			}
		
			public void start(){
				startUser();
			}
		
			�IF !ac.overridesStop()�
			public void stop(){
				stopUser();
			}
			�ENDIF�
			
			public void destroy(){
				destroyUser();
			}	
		
			�IF ac.stateMachine != null�
				�stateMachineGen.genStateMachine(xpac, ac)�
			�ELSEIF !xpac.hasStateMachine()�
				//--------------------- no state machine
				@Override
				public void receiveEvent(InterfaceItemBase ifitem, int evt, Object data) {
				handleSystemEvent(ifitem, evt, data);
				}
				
				@Override
				public void executeInitTransition(){
				}
			�ENDIF�
		};
		
		#endif /* _�xpac.name�_H_ */
		
	'''
	}
	
	def generateSourceFile(Root root, ExpandedActorClass xpac, ActorClass ac) {'''
	#include "�xpac.getCHeaderFileName�"
	
	'''
	}
	
	def msgArgs(Message msg) {
		'''�IF msg.data!=null��msg.data.defaultValue()��ENDIF�'''
	}
}