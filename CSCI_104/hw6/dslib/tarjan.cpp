#include "tarjan.h"

#include <algorithm>

Tarjan::Tarjan(int argc, char** argv)
{
	// if (argc != 3)
	// {
	// 	throw invalid_argument("you did not provide enough arguments when running your program.");
	// }
	//gets name of output file
	outputFilename = argv[2];
    //instantiates twitter engine for use in program
    twitterEngine engine;
   	//initializes users
   	engine.getLines(argc, argv);
   	users = engine.getUsers();
   	engine.getFollowing();
    //initializes depth index to 0
    depthIndex = 0;
    //initializes indices with names and indices of -1, to indicate undefined
    for (std::vector<User*>::iterator i = users.begin(); i != users.end(); i++)
    {
    	indices[(*i)->name()] = -1;
    }
    //initializes low link with names and low links of -1, to indicate undefined
    for (std::vector<User*>::iterator i = users.begin(); i != users.end(); i++)
    {
    	lowlinks[(*i)->name()] = -1;
    }
}

Tarjan::~Tarjan()
{}

void Tarjan::algorithm()
{
	//for every User object
    for (std::vector<User*>::iterator i = users.begin(); i != users.end(); i++)
    {
    	//if the depth index of that user is undefined
    	if(indices[(*i)->name()] == -1)
    	{
    		//search for strongly connected subgraph starting with this user
    		//passes User*
    		strongconnect(*i);
    	}
    }
}

void Tarjan::strongconnect(User*& user)
{
	indices[user->name()] = depthIndex;
	lowlinks[user->name()] = depthIndex;

	depthIndex++;
	
	treeStack.push(user);
	treeStackSet.insert(user);
	
	Set<User*> following = user->following();
	for (Set<User*>::iterator i = following.begin(); i != following.end(); i++) 
	{
		User* followedUser = *i;
		if (indices[followedUser->name()] == -1)
		{
			strongconnect(followedUser);
			lowlinks[user->name()] = std::min(lowlinks[user->name()], lowlinks[followedUser->name()]);

		}
		else if (treeStackSet.find(followedUser) != treeStackSet.end())
		{
			lowlinks[user->name()] = std::min(lowlinks[user->name()], lowlinks[followedUser->name()]);
		}
	}

	if (lowlinks[user->name()] == indices[user->name()])
	{
		vector<string> SCC;
		User* topUser;
		do
		{
			topUser = treeStack.top();
			treeStack.pop();
			treeStackSet.erase(topUser);
			SCC.push_back(topUser->name());
		}
		while(topUser != user);

		SCCs.push_back(SCC);
	}
}

void Tarjan::output()
{
	ofstream ofile(outputFilename);
	int componentNum = 1;
	for (vector< vector<string> >::iterator scc = SCCs.begin(); scc != SCCs.end(); scc++)
	{
		ofile << "Component " << componentNum << endl;
		vector<string> component = *scc;
		for (vector<string>::iterator name = component.begin(); name != component.end(); name++)
		{
			string username = *name;
			ofile << username << endl;
		}
		ofile << endl;
		componentNum++;
	}
}