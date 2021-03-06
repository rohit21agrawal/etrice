/**
 * @author generated by eTrice
 *
 * Source File of SubSystemClass SS
 * 
 */

#include "SS.h"

/* include instances for all classes */
#include "SS_Inst.h"
#include "SS_Disp.h"

#include "debugging/etLogger.h"
#include "debugging/etMSCLogger.h"

#include "platform/etTimer.h"
#include "etGlobalFlags.h"


/* data for SubSysten SS */
typedef struct SS {
	char *name;
} SS;

static SS SSInst = {"SS"};

void SS_initActorInstances(void);

void SS_init(void){
	ET_MSC_LOGGER_SYNC_ENTRY("SubSys", "init")
	etLogger_logInfoF("%s_init", SSInst.name);
	
	/* initialization of all message services */
	etMessageService_init(&msgService_Thread1, msgBuffer_Thread1, MESSAGE_POOL_MAX, MESSAGE_BLOCK_SIZE, MsgDispatcher_Thread1_receiveMessage);
	
	/* init all actors */
	SS_initActorInstances();
	
	ET_MSC_LOGGER_SYNC_EXIT
}

void SS_start(void){
	ET_MSC_LOGGER_SYNC_ENTRY("SubSys", "start")
	etLogger_logInfoF("%s_start", SSInst.name);
	ET_MSC_LOGGER_SYNC_EXIT
}

void SS_run(void){
	ET_MSC_LOGGER_SYNC_ENTRY("SubSys", "run")
	
	#ifdef ET_RUNTIME_ENDLESS
		while(TRUE){
			if (etTimer_executeNeeded()){
				etMessageService_execute(&msgService_Thread1);
			}
		}
	#else
		uint32 loopCounter = 0;
		while(TRUE){
			if (etTimer_executeNeeded()){
				etMessageService_execute(&msgService_Thread1);
				etLogger_logInfo("Execute");
				if (loopCounter++ > ET_RUNTIME_MAXLOOP){
					break;
				}
			}
		}
	#endif
	
	ET_MSC_LOGGER_SYNC_EXIT
}

void SS_stop(void){
	ET_MSC_LOGGER_SYNC_ENTRY("SubSys", "stop")
	etLogger_logInfoF("%s_stop", SSInst.name);
	ET_MSC_LOGGER_SYNC_EXIT
}

void SS_destroy(void){
	ET_MSC_LOGGER_SYNC_ENTRY("SubSys", "destroy")
	etLogger_logInfoF("%s_destroy", SSInst.name);
	ET_MSC_LOGGER_SYNC_EXIT
}

void SS_initActorInstances(void){
	ET_MSC_LOGGER_SYNC_ENTRY("SS", "initActorInstances")
	Tester_init(&_SS_tester);
	Testee_init(&_SS_testee);
	ET_MSC_LOGGER_SYNC_EXIT
}

