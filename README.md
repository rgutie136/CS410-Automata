# CS410 Automata Projects
This repository contains various projects related to automata, including DSFM simulators and a CFG simulator.

## DSFM Simulator - C++ (Deprecated)
**Note: This C++ version of the DSFM Simulator is deprecated. For the most recent and maintained version, please refer to the DSFM Simulator - Java.**

This project simulates a DSFM using an input/output file `dsfm.txt`. The file should be structured as follows:

1. **Alphabet Language**: The language of the automaton.
   - Example: `a,b`
2. **Number of States**: The total number of states in the automaton.
   - Example: `3`
3. **Accepting States**: The states that are accepting in the automaton.
   - Example: `0,1`
4. **List of Transitions**: The transitions between states. Each state should have its own line of transitions.
   - Example: `(0,a,1)(0,b,0)`

## CFG Simulator - Java
This project simulates a CFG (Context-Free Grammar) using an input/output file. The input file should contain the grammar G = (V, T, S, P) and the output file will contain the simplified grammar.

The input file should be structured as follows:

1. **Non-Terminals (V)**: The non-terminal symbols of the grammar.
   - Example: `V: S, A, B, C, D, E, F, a, b`
2. **Terminals (T)**: The terminal symbols of the grammar.
   - Example: `T: a, b`
3. **Start Symbol (S)**: The start symbol of the grammar.
   - Example: `S: S`
4. **Production Rules (P)**: The production rules of the grammar. Each rule should be on its own line.
   - Example: 
     ```
     P:
     S:: aA
     A:: aB
     A:: bC
     B:: aB
     B:: bD
     C:: a
     C:: b
     E:: aC
     F:: abE
     ```

The CFG simulator will then simplify the grammar by removing unproductive and unreachable non-terminals, and output the simplified grammar to the output file.


## DSFM Simulator - Java (Remade)
This is a remade version of the DSFM simulator, now implemented in Java. It uses the same `dsfm.txt` file structure as the C++ version.

1. **Alphabet Language**: The language of the automaton.
   - Example: `a,b`
2. **Number of States**: The total number of states in the automaton.
   - Example: `3`
3. **Accepting States**: The states that are accepting in the automaton.
   - Example: `0,1`
4. **List of Transitions**: The transitions between states. Each state should have its own line of transitions.
   - Example: `(0,a,1)(0,b,0)`