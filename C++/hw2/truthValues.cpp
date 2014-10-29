#include "truthValues.h"

truthValues::truthValues(int numVariables, int numClauses) {
	truthOfVariables = new int[numVariables];
	for (int i = 1; i <= numVariables; i++) {
		this->set_variable(i, -1);
	}
	truthOfClauses = new int[numClauses];
	for (int i = 1; i <= numClauses; i++) {
		this->set_clause(i, -1);
	}
}

int truthValues::get_variable(int varNum) {
	return truthOfVariables[varNum-1];
}

int truthValues::get_clause(int clNum) {
	return truthOfClauses[clNum-1];
}

void truthValues::set_variable(int varNum, int truthVal) {
	truthOfVariables[varNum-1] = truthVal;
}

void truthValues::set_clause(int clNum, int truthVal) {
	truthOfClauses[clNum-1] = truthVal;
}

truthValues::~truthValues() {}
