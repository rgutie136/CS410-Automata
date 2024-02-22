/*
  Course: CS 41000
  Project: DSFM Project
  Name:Romulo Gutierrez
  Email:gutie136@pnw.edu
  
  C++
  Compiler Used: MinGW GCC
  IDE Used: Programmer's Notepad + OnlineGDB
*/

// IMPORTANT NOTE:
// This program was compiled using gcc compiler with Programmer's Notepad on Windows 10.
// Works properly when using OnlineGDB with testRun.txt file
// (Depricated) Newer Java version working properly

#include <iostream>
#include <cstring>
#include <string>
#include <cstdlib>
#include <fstream>
#include <algorithm>

using namespace std;

string removeSeperators(string x)
{ // remove the seperators
	x.erase(remove(x.begin(), x.end(), ','), x.end());
	x.erase(remove(x.begin(), x.end(), ' '), x.end());
	x.erase(remove(x.begin(), x.end(), '('), x.end());
	x.erase(remove(x.begin(), x.end(), ')'), x.end());
	return x;
}
int findIndex(string input, char c)
{ // finds index of strings
	int index = input.find(c);
	if (index != string::npos)
	{return index;}
	else {return -1;}
}

int main()
{
	ifstream file("testRun.txt");
	if (file.fail())
	{
		cout << "Failed to retrieve file. Try again (Make sure file is \"testRun.txt\")\nPress any key to exit..." << endl;
		cin.get();											 
		exit(0);
	}
	// Grab Alphabet language
	string alphabet;
	getline(file, alphabet);
	alphabet = removeSeperators(alphabet);
	int alphabetSize = alphabet.size();
	char alphabetArray[alphabetSize];
	strcpy(alphabetArray, alphabet.c_str());
	int numLang = sizeof(alphabetArray) / sizeof(alphabetArray[0]);
	
	// Get num states
	string numStatesChar;
	getline(file, numStatesChar);
	int numStates = atoi(numStatesChar.c_str());

	// Get accepting states -- Good
	string strStateAccept;
	getline(file, strStateAccept);
	strStateAccept = removeSeperators(strStateAccept);
	int sizeAccept = strStateAccept.size();
	bool acceptStates[10];
	fill_n(acceptStates, 10, false);
	for (int i = 0; i < sizeAccept; i++)
	{
		char k = strStateAccept.at(i);
		int indexAccept = strStateAccept.find(k);
		if (static_cast<int>(string::npos) != indexAccept)
		{
			acceptStates[i] = true;
		}
	}

	// Transitions
	char transitionMatrix[numStates][alphabetSize];
	string strTransition;
	int currentState = 0;
	int currentLang = 0;
	char nextState;
	int point = 2;
	for (int i = 0; i < numStates; i++)
	{
		point = 2;
		getline(file, strTransition);
		strTransition = removeSeperators(strTransition);
		// cout <<  "Transition: " << strTransition << endl;
		for (int j = 0; j < numLang; j++)
		{
			nextState = strTransition.at(point);
			transitionMatrix[currentState][currentLang] = nextState;
			// cout << "Transit to: "<< transitionMatrix[currentState][currentLang] << endl;
			point = point + 3;
			currentLang++;
		}
		currentLang = 0;
		currentState++;
		strTransition.erase();
	}
	file.close();

	// User interaction
	char retry;
	do
	{
		string userInput;
		int sizeInput;
		int currentState = 0;
		bool isValid = true;
		bool isAccepted = false;
		int index;
		cout << "Enter a string using alphabet" << endl;
		cin >> userInput;
		int userInputSize = userInput.size();
		sizeInput = sizeof(userInput) / sizeof(userInput[0]);
		for (int i = 0; i < userInputSize; i++)
		{
			char currentChar = userInput.at(i);
			index = findIndex(alphabet, currentChar);
			if (index != -1)
			{
				currentState = transitionMatrix[currentState][index] - '0';
				cout << endl
					 << "Step: " << currentState;
			}
			else
			{
				cout << endl
					 << "Error certain characters not in Language";
				isValid = false;
				break;
			}
		}
		isAccepted = acceptStates[currentState];
	
		if (isAccepted == true && isValid == true)
		{
			cout << endl
				 << "Your string was accepted!" << endl;
		}
		else
		{
			cout << endl
				 << "Your string was NOT accepted!" << endl;
		}
		// Prompt user to repeat program
		cout << "Do you want to try another string? (Y/N)" << endl;
		cin >> retry;
		cout << endl
			 << endl
			 << endl;
	} while (retry == 'y' || retry == 'Y');
	return 0;
	}