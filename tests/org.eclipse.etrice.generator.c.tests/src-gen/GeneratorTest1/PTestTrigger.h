/**
 * @author generated by eTrice
 *
 * Header File of ProtocolClass PTestTrigger
 * 
 */

#ifndef _PTESTTRIGGER_H_
#define _PTESTTRIGGER_H_

#include "etDatatypes.h"
#include "modelbase/etPort.h"




/* message IDs */
enum PTestTrigger_msg_ids {
	PTestTrigger_MSG_MIN = 0,
	PTestTrigger_OUT_done = 1,
	PTestTrigger_IN_trigger = 2,
	PTestTrigger_MSG_MAX = 3
};

/*--------------------- port structs and methods */

	typedef etPort PTestTriggerPort;
	typedef etReplPort PTestTriggerReplPort;
	
	void PTestTriggerPort_done(const PTestTriggerPort* self);
	void PTestTriggerReplPort_done_broadcast(const PTestTriggerReplPort* self);
	void PTestTriggerReplPort_done(const PTestTriggerReplPort* self, int idx);

	typedef etPort PTestTriggerConjPort;
	typedef etReplPort PTestTriggerConjReplPort;
	
	void PTestTriggerConjPort_trigger(const PTestTriggerConjPort* self);
	void PTestTriggerConjReplPort_trigger_broadcast(const PTestTriggerConjReplPort* self);
	void PTestTriggerConjReplPort_trigger(const PTestTriggerConjReplPort* self, int idx);

/*--------------------- debug helpers */

/* get message string for message id */
const char* PTestTrigger_getMessageString(int msg_id);


#endif /* _PTESTTRIGGER_H_ */

