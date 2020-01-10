package user.jepsen;

import java.util.Map;
import java.util.HashMap;

public class Operation {
    public static final String OP_NAME = "opName";
    public static final String OP_VALUE = "opValue";
    public static final String INVOKE = "invoke";
    public static final String INFO = "info";

    public enum OperationType {
        CLIENT_OP,
	NEMESIS_OP
    }

    private Map<String, String> opMap;
    private OperationType opType;
    private boolean hasBeenSet;

    public Operation() {
        opMap = new HashMap<>();
	opType = null;
	hasBeenSet = false;
    }

    public Operation setOpType(OperationType type) {
	if (hasBeenSet)
	    return this;
	this.opType = type;
	this.hasBeenSet = true;
	if (type == OperationType.CLIENT_OP)
	    opMap.put("invoke", null);
 	else
	    opMap.put("info", null);
	return this;
    }

    private boolean isCorrectCategory(String str) {
	if (!hasBeenSet) return false;
	switch (str) {
	    case "opName": return opType == OperationType.CLIENT_OP;
	    case "opValue": return opType == OperationType.CLIENT_OP;
	    case "invoke": return opType == OperationType.CLIENT_OP;
	    default:
		return opType == OperationType.NEMESIS_OP;
	}	
    }

    public Operation setOpName(String opName) {
    	if (!isCorrectCategory(OP_NAME)) return this;
	opMap.put("opName", opName);
	return this;
    }

    public Operation setOpValue(String opValue) {
 	if (!isCorrectCategory(OP_VALUE)) return this;
	opMap.put("opValue", opValue);
	return this;	
    }

    public Operation setNemesisOp(String op) {
        if (!isCorrectCategory(INFO)) return this;
	opMap.put(op, null);
	return this;
    }

    public Map<String, String> getMap() {
    	return opMap;
    } 
}
