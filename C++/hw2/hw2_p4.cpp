#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cstdlib>
#include "truthValues.h"
//test for makefile
using namespace std;

//reads number of variables, number of clauses, and clauses from the input file
int readFiles(int&, int&, string*&, ifstream&, ofstream&, int&, char**&);

//checks if any clauses are false
bool noClausesFalse(truthValues&, string*&, int&, int, stringstream&);

//checks true/false values of all clauses, and stores them in the truthOfClauses array in the truthValues class
void checkClauses(truthValues&, string*&, int&, stringstream&);

//checks if a solution exists
bool solutionExists(truthValues&, string*&, int&, int&, int, stringstream&); 

int main(int argc, char** argv) {

	//initializes i/o objects
	ifstream ifile;
	ofstream ofile;

	//initializes storage for pertinent information from input file
	int numVariables;
	int numClauses;
	string* clauses;
	stringstream ss;

	readFiles(numVariables, numClauses, clauses, ifile, ofile, argc, argv);

	//instantiates truth, which stores the truth values of all variables and clauses, and can be used to 
	//change these truth variables
	truthValues truth(numVariables, numClauses);
	
	//if there is a solution, store this fact
	bool solution = solutionExists(truth,clauses,numVariables,numClauses,1,ss);
	
	//only include the variables that are present in a clause, and therefore have a bearing on the true/false value of the 
	//expression
	stringstream ff;
	char var;
	string varsInFile;
	for (int i = 1; i <= numVariables; i++) {
		ff << i;
		ff >> var;
		for (int j = 1; j <= numClauses; j++) {	
				for (unsigned int k = 0; k < clauses[j-1].length(); k++) {
					if (clauses[j-1][k] == (var)) {
						if (varsInFile.find(var, 0) == string::npos) {
							varsInFile += var;
						}
					} 
				}
		}
		ff.clear();
		var = ' ';
	}

	int var2;
	
	//if a solution exists, send the solution (only including the variables in the clauses) to the output file
	if (solution) {
		for (int i = 0; i < numVariables; i++) {
			ff << varsInFile[i];
			ff >> var2;
			ofile << varsInFile[i] << " = " << truth.get_variable(var2) << '\n';
			ff.clear();
			var = ' ';
		}
	//if a solution does not exist, send "no solution" to the output file.
	} else {
		ofile << "no solution";
	}	

}


int readFiles(int& numVariables, int& numClauses, string*& clauses, ifstream& ifile, ofstream& ofile, int& argc, char**& argv) {

	////checks whether the user gave the correct number of arguments
	
	if (argc != 3) {
		cout << "you did not give the correct number of arguments" << endl;
		cout << "quitting program..." << endl;
		return 0;
	}


	////checks whether all files can be opened
	
	int fileCount = 0;
	
	ifile.open(argv[1]);
	ofile.open(argv[2]);

	if (ifile.fail()){
		cout << "could not open your input file." << endl;
	} else { fileCount++; } 


	if (ofile.fail()) {
		cout << "could not open your output file." << endl;
	} else { fileCount ++; }


	if (fileCount != 2) {
		cout << "missing files." << endl;
		cout << "quitting program..." << endl;
	} else {
		cout << "input and output files successfully opened." << endl;		
	}	


	////gets number of variables and number of clauses from file
	
	string line = "";
	while (line[0] != 'p') {
		getline(ifile, line);
	}	

	string chunk;	
	stringstream s1;
	stringstream s2;

	s1 << line;

	for (int i = 0; i < 3; i++) {
		s1 >> chunk;
	}

	s2 << chunk;
	s2 >> numVariables;
	s2.clear(); 

	s1 >> chunk;
	s2 << chunk;
	s2 >> numClauses;
	s2.clear();

	s1.clear();
	
	////stores clauses for access later

	clauses = new string[numClauses];
	int i = 0;
	while (getline(ifile, line)) {
		clauses[i] = line;
		i++;	
	}
	
	return 0;
	
}

void checkClauses(truthValues& truth, string*& clauses, int& numClauses, stringstream& ss) {
	
	////sets up storage necessary for parsing formatting of clauses, and determining whether clauses are false

	int chunk;
	bool clauseTruthValue;
	int clauseLen;
	int numFalse;
	
	////goes through all clauses, and checks what their truth values are with a given assignment of variables
	////then stores those truth values. 

	for (int i = 1; i <= numClauses; i++) {
		
		//initializes all variables to what they should be at the start of a clause check
		clauseTruthValue = true;
		chunk = 0;
		clauseLen = 0;
		numFalse = 0;	
		ss.clear();

		//stores the clause that is being checked
		ss << clauses[i-1];
		ss >> chunk;
	
		//gets the clause length
		while (chunk != 0) {
			ss >> chunk;
			clauseLen += 1;	
		}

		//restores the stringstream and 'chunk' which is used to store each number in a clause line from the cnf file
		ss.clear();
		ss << clauses[i-1];
		chunk = 1;

		//checks if the clause in question is true or false	
		while (chunk != 0) {
			ss >> chunk;
			//if the variable is not negated in the cnf file
			if (chunk > 0) {
				//then a truth value of 0 means it is false
				if (truth.get_variable(abs(chunk)) == 0) {
					numFalse += 1;
					//if all the variables in a clause are false, the clause is false
					if (numFalse == clauseLen) {
						clauseTruthValue = false;
					}
				}
			//if the variable is negated in the cnf file
			} else if (chunk < 0) {	
				//then a truth value of 1 means it is false
				if (truth.get_variable(abs(chunk)) == 1) {
					numFalse += 1;
					//if all the variables in a clause are false, the clause is false
					if (numFalse == clauseLen) {
						clauseTruthValue = false;
					}
				}
			}
		}
		//if the clause is true
		if (clauseTruthValue) {
			//store that
			truth.set_clause(i, 1);
		//if the clause is false
		} else if (!clauseTruthValue) {
			//store that
			truth.set_clause(i, 0);
		}
	}
}
	
bool noClausesFalse(truthValues& truth, string*& clauses, int& numClauses, int clauseNum, stringstream& ss) {
	
	checkClauses(truth, clauses, numClauses, ss);
	
	////if any clauses are false, "no clauses are false" is false
	
	for (int i = 1; i <= numClauses; i++) {
		if (truth.get_clause(i) == 0) {
			return false;
		}
	}
	
	////if no clauses are false, "no clauses are false" is true
	
	return true;
}

bool solutionExists(truthValues& truth, string*& clauses, int& numVariables, int& numClauses, int varNum, stringstream& ss) { 

	if (varNum < numVariables) {
		for (int i = 0; i < 2; i++) {
			truth.set_variable(varNum, i);
			if (solutionExists(truth, clauses, numVariables, numClauses, varNum+1, ss)) {
				return true;
			}
		}

		return false;
	} else {
		for (int i = 0; i < 2; i ++) {	
			truth.set_variable(varNum, i);
			if (noClausesFalse(truth, clauses, numClauses, 1, ss)) 
				return true;
		}

		return false;
	}


	return false;
} 

