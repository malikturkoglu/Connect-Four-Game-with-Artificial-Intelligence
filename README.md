# ConnectFourGamewithAi
This document explains how Connect-Four game is implemented. Methods and 
classes will be laid out concisely. 
This document is strictly bonded to the project document. There may be parts 
without explanation, which are coming directly from the project document. α-β 
pruning is not implemented. There was a class AI, which is not included here since 
we have used it for testing purposes. 
The maximum ply number is 8, minimax expands 8 level down. The time required for 
expanding is around 5-5.5 seconds. All are presented with screenshots. 
 Heuristics
- H1: This heuristic is the simple one. It uses minimax but its approach is chancebased. Nevertheless, it wins rarely. 
- H2: This heuristic has an approach such that having a line of pieces brings points. 
For example, if two pieces are side by side, player earns 20 points; if three, then 
50 points; if 4, game-winning condition, then it earns 1000 points. Additionally, 
blocking other player from winning brings 90 points. 
- H3: This heuristic is improved version of H2. It does the same thing but 
additionally it checks for solution-producibility potential. For example, say there 
is a double along with fifth row but AI cannot add two more to it to win. Then the 
AI ignores this line and looks for another place to play since this double does not 
have solution-producibility potential- it is a dead end. If there is no place that can 
produce a solution, then H3 acts as H2. 
 Classes and Methods 
1. ConnectFour Class 
This is the class where the game is implemented. Methods and attributes are 
explained below. 
Attributes - static int[][] gameTable: This is the game table. It holds the current status 
of the game.
- int rowAmount: number of columns.
- int columnAmount: number of rows.
Methods 
- startGame: It prints the menu and prompts the user to interact with the 
system.
- playHumanToHuman: It enables two human players play with each other. 
It works simple; take input and update the game table.
- playHumanToAi: It enables the user to challenge our best AI player which 
uses H3. 
- playAiToAi13: This method lets the AI player with H1 competes with the 
AI player with H3. 
- playAiToAi23: This method lets the AI player with H2 competes with the 
AI player with H3. 
- playAiToAi12: This method lets the AI player with H1 competes with the 
AI player with H2. 
- checkCol: Checks the location if it is valid or not e.g., inside the borders of 
the game table.
- validCols: Returns all valid locations’ column numbers.
- initializeTable: Builds the game table with all empty locations.
- findAvaliableLocation: Returns the empty place at the lowest level of the 
column.
- playThePiece: Puts the piece on the table.
- printTable: Prints the current status of the table.
- checkWinning: Checks if there is winner.
- evalH1: Implements H1 which is about playing randomly.
- evalH2: Implements H2. The implementation is done by searching. The 
method traverses whole game table and try to finds followings; 1) 
Winning condition, 2) the other player’s winning condition, 3) line with 
three/two pieces side by side, on top of each other or diagonal. According to the result of searching, it assigns necessary points to each case and 
returns the score.
- evalH3: It implements H3. As an addition to H2, it also checks whether a 
move can produce a solution. It finds that by simply checking the rest of 
the line. For example, if there is a line with two pieces, this method looks 
further two locations and checks whether they are empty or not. If 
locations are not empty, then moves to another location to check. If they 
are empty, then it chooses that location immediately. In the case of not 
finding any such location, then it simply implements evalH2 to behave as 
H2.
- minimaxH1: It implements minimax algorithm with H1 as an evaluation 
function. It punishes by 500000 in the case of losing and rewards with 
5000000 in the case of winning.
- minimaxH2: It implements minimax algorithm with H2 as an evaluation 
function. Punishing and rewarding policies are the same as minimaxH1.
- minimaxH3: It implements minimax algorithm with H3 as an evaluation 
function. Punishing and rewarding policies are the same as minimaxH1.
2. PlayConnectFourGame Class 
It is a very simple class that includes main method to start the game. 
The class diagram is given below.
