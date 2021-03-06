/**
 * @author generated by eTrice
 *
 * Source File of ActorClass ATimingService
 * 
 */

#include "ATimingService.h"

#include "modelbase/etActor.h"
#include "debugging/etLogger.h"
#include "debugging/etMSCLogger.h"
#include "platform/etMemory.h"

#include "PTimer.h"

/*--------------------- begin user code ---------------------*/
//etTimerControlBlock tcbs[ET_NB_OF_TCBS];
/*--------------------- end user code ---------------------*/

/* interface item IDs */
enum interface_items {
	IFITEM_timer = 1
};


/* state IDs */
enum state_ids {
	NO_STATE = 0,
	STATE_TOP = 1,
	STATE_Operational = 2
};

/* transition chains */
enum chain_ids {
	CHAIN_TRANS_INITIAL_TO__Operational = 1,
	CHAIN_TRANS_tr1_FROM_Operational_TO_Operational_BY_startTimeouttimer_tr1 = 2,
	CHAIN_TRANS_tr3_FROM_Operational_TO_Operational_BY_startTimertimer_tr3 = 3,
	CHAIN_TRANS_tr4_FROM_Operational_TO_Operational_BY_killtimer_tr4 = 4
};

/* triggers */
enum triggers {
	POLLING = 0,
	TRIG_timer__startTimer = IFITEM_timer + EVT_SHIFT*PTimer_IN_startTimer,
	TRIG_timer__startTimeout = IFITEM_timer + EVT_SHIFT*PTimer_IN_startTimeout,
	TRIG_timer__kill = IFITEM_timer + EVT_SHIFT*PTimer_IN_kill
};


static void setState(ATimingService* self, int new_state) {
	self->state = new_state;
}

/* Entry and Exit Codes */
static void entry_Operational(ATimingService* self) {
	// prepare
}
static  void do_Operational(ATimingService* self) {
	// maintain timers
	etTimerControlBlock* temp;
	etTargetTime_t t;
	
	getTimeFromTarget(&t);
	while (self->usedTcbsRoot /* ORIG: usedTcbsRoot */ !=0 ){
		if (ATimingService_isTimeGreater(self, &t, &(self->usedTcbsRoot /* ORIG: usedTcbsRoot */->expTime)) /* ORIG: isTimeGreater(&t,&(usedTcbsRoot->expTime)) */){
			PTimerReplPort_timeout(&self->constData->timer, self->usedTcbsRoot /* ORIG: usedTcbsRoot */->portIdx) /* ORIG: timer[usedTcbsRoot->portIdx].timeout() */;
			temp=self->usedTcbsRoot /* ORIG: usedTcbsRoot */;
			self->usedTcbsRoot /* ORIG: usedTcbsRoot */=self->usedTcbsRoot /* ORIG: usedTcbsRoot */->next;
			if((temp->pTime.sec==0)&&(temp->pTime.nSec==0)){
				// single shot timer
				ATimingService_returnTcb(self, temp) /* ORIG: returnTcb(temp) */;
			}else{
				// periodic timer
				ATimingService_addTime(self, &temp->expTime, &temp->pTime) /* ORIG: addTime(&temp->expTime,&temp->pTime) */;
				ATimingService_putTcbToUsedList(self, temp) /* ORIG: putTcbToUsedList(temp) */;
				}
			}else{
				break;
				}
		}
}

/* Action Codes */
static void action_TRANS_INITIAL_TO__Operational(ATimingService* self) {
	int i;
	self->usedTcbsRoot /* ORIG: usedTcbsRoot */=0;
	self->freeTcbsRoot /* ORIG: freeTcbsRoot */=&self->tcbs[0] /* ORIG: tcbs[0] */;
	self->tcbs[ET_NB_OF_TCBS-1] /* ORIG: tcbs[ET_NB_OF_TCBS-1] */.next=0;
	for (i=0;i<ET_NB_OF_TCBS-1;i++){
		self->tcbs[i] /* ORIG: tcbs[i] */.next=&self->tcbs[i+1] /* ORIG: tcbs[i+1] */;
		}
}
static void action_TRANS_tr1_FROM_Operational_TO_Operational_BY_startTimeouttimer_tr1(ATimingService* self, InterfaceItemBase ifitem, uint32 time) {
	etTimerControlBlock* timer = ATimingService_getTcb(self) /* ORIG: getTcb() */;
	etTargetTime_t t;
	if (timer!= 0){
		t.sec=time/1000;
		t.nSec=(time%1000)*1000000L;
		timer->pTime.sec = 0;
		timer->pTime.nSec = 0;
		timer->portIdx=((etReplSubPort*)ifitem)->index;
		getTimeFromTarget(&(timer->expTime));
		ATimingService_addTime(self, &(timer->expTime), &t) /* ORIG: addTime(&(timer->expTime),&t) */;
		ATimingService_putTcbToUsedList(self, timer) /* ORIG: putTcbToUsedList(timer) */;
		}
}
static void action_TRANS_tr3_FROM_Operational_TO_Operational_BY_startTimertimer_tr3(ATimingService* self, InterfaceItemBase ifitem, uint32 time) {
	etTimerControlBlock* timer = ATimingService_getTcb(self) /* ORIG: getTcb() */;
	etTargetTime_t t;
	if (timer!= 0){
		t.sec=time/1000;
		t.nSec=(time%1000)*1000000L;
		timer->pTime = t;
		timer->portIdx=((etReplSubPort*)ifitem)->index;
		getTimeFromTarget(&(timer->expTime));
		ATimingService_addTime(self, &(timer->expTime), &t) /* ORIG: addTime(&(timer->expTime),&t) */;
		ATimingService_putTcbToUsedList(self, timer) /* ORIG: putTcbToUsedList(timer) */;
		}
}
static void action_TRANS_tr4_FROM_Operational_TO_Operational_BY_killtimer_tr4(ATimingService* self, InterfaceItemBase ifitem) {
	ATimingService_removeTcbFromUsedList(self, ((etReplSubPort*)ifitem)->index) /* ORIG: removeTcbFromUsedList(((etReplSubPort*)ifitem)->index) */;
}

/**
 * calls exit codes while exiting from the current state to one of its
 * parent states while remembering the history
 * @param current - the current state
 * @param to - the final parent state
 * @param handler - entry and exit codes are called only if not handler (for handler TransitionPoints)
 */
static void exitTo(ATimingService* self, int current, int to, boolean handler) {
	while (current!=to) {
		switch (current) {
			case STATE_Operational:
				self->history[STATE_TOP] = STATE_Operational;
				current = STATE_TOP;
				break;
		}
	}
}

/**
 * calls action, entry and exit codes along a transition chain. The generic data are cast to typed data
 * matching the trigger of this chain. The ID of the final state is returned
 * @param chain - the chain ID
 * @param generic_data - the generic data pointer
 * @return the ID of the final state
 */
static int executeTransitionChain(ATimingService* self, int chain, InterfaceItemBase ifitem, void* generic_data) {
	switch (chain) {
		case CHAIN_TRANS_INITIAL_TO__Operational:
		{
			action_TRANS_INITIAL_TO__Operational(self);
			return STATE_Operational;
		}
		case CHAIN_TRANS_tr1_FROM_Operational_TO_Operational_BY_startTimeouttimer_tr1:
		{
			uint32 time = *((uint32*) generic_data);
			action_TRANS_tr1_FROM_Operational_TO_Operational_BY_startTimeouttimer_tr1(self, ifitem, time);
			return STATE_Operational;
		}
		case CHAIN_TRANS_tr3_FROM_Operational_TO_Operational_BY_startTimertimer_tr3:
		{
			uint32 time = *((uint32*) generic_data);
			action_TRANS_tr3_FROM_Operational_TO_Operational_BY_startTimertimer_tr3(self, ifitem, time);
			return STATE_Operational;
		}
		case CHAIN_TRANS_tr4_FROM_Operational_TO_Operational_BY_killtimer_tr4:
		{
			action_TRANS_tr4_FROM_Operational_TO_Operational_BY_killtimer_tr4(self, ifitem);
			return STATE_Operational;
		}
	}
	return NO_STATE;
}

/**
 * calls entry codes while entering a state's history. The ID of the final leaf state is returned
 * @param state - the state which is entered
 * @param handler - entry code is executed if not handler
 * @return - the ID of the final leaf state
 */
static int enterHistory(ATimingService* self, int state, boolean handler, boolean skip_entry) {
	while (TRUE) {
		switch (state) {
			case STATE_Operational:
				if (!(skip_entry || handler)) entry_Operational(self);
				// in leaf state: return state id
				return STATE_Operational;
			case STATE_TOP:
				state = self->history[STATE_TOP];
				break;
		}
		skip_entry = FALSE;
	}
	//return NO_STATE; // required by CDT but detected as unreachable by JDT because of while (true)
}

static void executeInitTransition(ATimingService* self) {
	int chain = CHAIN_TRANS_INITIAL_TO__Operational;
	int next = executeTransitionChain(self, chain, NULL, NULL);
	next = enterHistory(self, next, FALSE, FALSE);
	setState(self, next);
}

/* receiveEvent contains the main implementation of the FSM */
static void receiveEvent(ATimingService* self, InterfaceItemBase ifitem, int evt, void* generic_data) {
	int trigger = (ifitem==NULL)? POLLING : ifitem->localId + EVT_SHIFT*evt;
	int chain = NOT_CAUGHT;
	int catching_state = NO_STATE;
	boolean is_handler = FALSE;
	boolean skip_entry = FALSE;
	
	if (!handleSystemEvent(ifitem, evt, generic_data)) {
		switch (self->state) {
			case STATE_Operational:
				switch(trigger) {
				case POLLING:
					do_Operational(self);
					break;
					case TRIG_timer__startTimeout:
						{
							chain = CHAIN_TRANS_tr1_FROM_Operational_TO_Operational_BY_startTimeouttimer_tr1;
							catching_state = STATE_TOP;
						}
					break;
					case TRIG_timer__startTimer:
						{
							chain = CHAIN_TRANS_tr3_FROM_Operational_TO_Operational_BY_startTimertimer_tr3;
							catching_state = STATE_TOP;
						}
					break;
					case TRIG_timer__kill:
						{
							chain = CHAIN_TRANS_tr4_FROM_Operational_TO_Operational_BY_killtimer_tr4;
							catching_state = STATE_TOP;
						}
					break;
				}
				break;
		}
	}
	if (chain != NOT_CAUGHT) {
		exitTo(self, self->state, catching_state, is_handler);
		int next = executeTransitionChain(self, chain, ifitem, generic_data);
		next = enterHistory(self, next, is_handler, skip_entry);
		setState(self, next);
	}
}
	 
//******************************************
// END of generated code for FSM
//******************************************

void ATimingService_init(ATimingService* self){
	ET_MSC_LOGGER_SYNC_ENTRY("ATimingService", "init")
	self->state = STATE_TOP;
	{
		int i;
		for (i=0; i<ATIMINGSERVICE_HISTORY_SIZE; ++i)
			self->history[i] = NO_STATE;
	}
	executeInitTransition(self);
	ET_MSC_LOGGER_SYNC_EXIT
}


void ATimingService_receiveMessage(void* self, void* ifitem, const etMessage* msg){
	ET_MSC_LOGGER_SYNC_ENTRY("ATimingService", "_receiveMessage")
	
	receiveEvent(self, (etPort*)ifitem, msg->evtID, (void*)(((char*)msg)+MEM_CEIL(sizeof(etMessage))));
	
	ET_MSC_LOGGER_SYNC_EXIT
}

void ATimingService_execute(ATimingService* self) {
	ET_MSC_LOGGER_SYNC_ENTRY("ATimingService", "_execute")
	
	receiveEvent(self, NULL, 0, NULL);
	
	ET_MSC_LOGGER_SYNC_EXIT
}

/*--------------------- operations ---------------------*/
etTimerControlBlock* ATimingService_getTcb(ATimingService* self) {
	
				etTimerControlBlock* temp = self->freeTcbsRoot /* ORIG: freeTcbsRoot */;
				
				if(self->freeTcbsRoot /* ORIG: freeTcbsRoot */!=0) {
					self->freeTcbsRoot /* ORIG: freeTcbsRoot */=self->freeTcbsRoot /* ORIG: freeTcbsRoot */->next;
					temp->next=0;
					}
				return temp;
}
void ATimingService_returnTcb(ATimingService* self, etTimerControlBlock* block) {
	
				block->next=self->freeTcbsRoot /* ORIG: freeTcbsRoot */;
				self->freeTcbsRoot /* ORIG: freeTcbsRoot */=block;
}
void ATimingService_removeTcbFromUsedList(ATimingService* self, int32 idx) {
	
				etTimerControlBlock* temp=self->usedTcbsRoot /* ORIG: usedTcbsRoot */;
				etTimerControlBlock* temp2=self->usedTcbsRoot /* ORIG: usedTcbsRoot */;
				
				if (temp==0) return;
	
				if (self->usedTcbsRoot /* ORIG: usedTcbsRoot */->portIdx == idx){
					// element found, the first one
					self->usedTcbsRoot /* ORIG: usedTcbsRoot */ = self->usedTcbsRoot /* ORIG: usedTcbsRoot */->next;
					ATimingService_returnTcb(self, temp) /* ORIG: returnTcb(temp) */;
					return;
					}
	
				temp=temp->next;
				while(temp!=0){
					if(temp->portIdx==idx){
						temp2->next=temp->next;
						ATimingService_returnTcb(self, temp) /* ORIG: returnTcb(temp) */;
						return;			
					}else{
						// try next
						temp2=temp;
						temp=temp->next;
						}
					}
}
void ATimingService_putTcbToUsedList(ATimingService* self, etTimerControlBlock* block) {
	
				etTimerControlBlock* temp=self->usedTcbsRoot /* ORIG: usedTcbsRoot */;
				etTimerControlBlock* temp2=self->usedTcbsRoot /* ORIG: usedTcbsRoot */;
	
				if (temp==0){
					// list empty put new block to root
					block->next=0;
					self->usedTcbsRoot /* ORIG: usedTcbsRoot */=block;
					return;
					}
				
				while(1){
					if (temp != 0){
						if (ATimingService_isTimeGreater(self, &block->expTime, &temp->expTime) /* ORIG: isTimeGreater(&block->expTime,&temp->expTime) */){
							//try next position
							temp2=temp;	
							temp=temp->next;
							}else{
							// right position found
							block->next=temp;
							if(temp==self->usedTcbsRoot /* ORIG: usedTcbsRoot */){
								self->usedTcbsRoot /* ORIG: usedTcbsRoot */=block;
								}else{
								temp2->next=block;
								}
							return;
							}
						}else{
						// end of list reached
						block->next=0;
						temp2->next=block;
						return;
					}
				}
}
boolean ATimingService_isTimeGreater(ATimingService* self, etTargetTime_t* t1, etTargetTime_t* t2) {
	
					if (t1->sec > t2->sec) return TRUE;
					if (t1->sec < t2->sec) return FALSE;
					if (t1->nSec > t2->nSec) return TRUE;
					return FALSE;
}
void ATimingService_addTime(ATimingService* self, etTargetTime_t* t1, etTargetTime_t* t2) {
	
					t1->sec += t2->sec;
					t1->nSec += t2->nSec;
					while(t1->nSec >= 1000000000L){
						t1->sec++;
						t1->nSec-=1000000000L;
						}
}
void ATimingService_printList(ATimingService* self) {
	
				etTimerControlBlock* temp=self->usedTcbsRoot /* ORIG: usedTcbsRoot */;
					printf("list: ");
					while (temp!=0){
						printf("(%d,%d),",temp->expTime.sec,temp->expTime.nSec);
						temp=temp->next;
					}
					printf("\n");
}

