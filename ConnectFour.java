import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;
public class ConnectFour {


    static int[][] gameTable = new int[6][7];
    int rowAmount=7;
    int columnAmount=6;


    public static void playHumanToHuman(){
        initializeTable(gameTable);
        Scanner systemInput = new Scanner(System.in);
        while(true){
            printTable(gameTable);
            System.out.println("Player1 choice row: ");
            systemInput = new Scanner(System.in);
            int row = systemInput.nextInt();
            if(checkCol(gameTable,row)){
                int getCol = findAvailableLocation(gameTable,row);
                playThePiece(gameTable,getCol,row,1);
            }

            if(checkWinning(gameTable,1)){
                System.out.println("player 1 won");
                printTable(gameTable);
                break;
            }

            printTable(gameTable);
            System.out.println("Player2 choice row: ");
            systemInput = new Scanner(System.in);
            int row2 = systemInput.nextInt();
            if(checkCol(gameTable,row2)){
                int getCol = findAvailableLocation(gameTable,row2);
                playThePiece(gameTable,getCol,row2,2);
            }
            if(checkWinning(gameTable,2)){
                System.out.println("player 2 won");
                printTable(gameTable);
                break;
            }



        }
    }

    public static void startGame(){

        System.out.println("Welcome! please select the operation:");
        System.out.println("1. Human to Human");
        System.out.println("2. Human to Ai(H3)");
        System.out.println("3. Ai(h1) to Ai(h3)");
        System.out.println("4. Ai(h2) to Ai(h3)");
        System.out.println("5. Ai(h1) to Ai(h2)");
        Scanner systemInput = new Scanner(System.in);
        System.out.print("Your Choice: ");
        int userInput = systemInput.nextInt();

        if(userInput==1)
            playHumanToHuman();
        if(userInput==2)
            playHumanToAi();
        if(userInput==3)
            playAiToAi13();
        if(userInput==4)
            playAiToAi23();
        if(userInput==5)
            playAiToAi12();

    }

    public static void playHumanToAi(){
        System.out.println("------------------------------");
        initializeTable(gameTable);
        boolean checker = terminalState(gameTable);
        Scanner systemInput = new Scanner(System.in);
        System.out.print("Please write depth of minmax(1-8): ");
        int depth = systemInput.nextInt();
        while(true){
            System.out.println("------------------------------");
            printTable(gameTable);
            System.out.println("Player1 choice row: ");
            systemInput = new Scanner(System.in);
            int row = systemInput.nextInt();
            if(checkCol(gameTable,row)){
                int getCol = findAvailableLocation(gameTable,row);
                playThePiece(gameTable,getCol,row,1);
            }

            if(checkWinning(gameTable,1)){
                System.out.println("Human won");
                printTable(gameTable);
                break;
            }

            printTable(gameTable);

            int temp[][] = new int[6][7];
            for(int i=0; i<gameTable.length; i++)
                for(int j=0; j<gameTable[i].length; j++)
                    temp[i][j]=gameTable[i][j];

            int row2;

            Instant start = Instant.now();
            int scoreTaken = minimaxH3(temp,depth,1); // maximizing
            Instant finish = Instant.now();
            System.out.println("Required time of H3 for depth " + depth + ": "+ Duration.between(start, finish).toMillis() + " milliseconds");
            row2 = Math.abs(scoreTaken) % 10;

            if(checkCol(gameTable,row2)){
                int getCol = findAvailableLocation(gameTable,row2);
                playThePiece(gameTable,getCol,row2,2);
            }
            if(checkWinning(gameTable,2)){
                System.out.println("AI player with H3 won");
                printTable(gameTable);
                System.out.println("------------------------------");
                break;
            }



        }
    }

    public static void playAiToAi13(){ //h1 vs h3
        System.out.println("------------------------------");
        initializeTable(gameTable);
        boolean startingMove = true;
        int counter = 0;
        boolean checker = terminalState(gameTable);
        Scanner systemInput = new Scanner(System.in);
        System.out.print("Please write depth of minmax(1-8): ");
        int depth = systemInput.nextInt();
        while(true){
            printTable(gameTable);
            int temp[][] = new int[6][7];
            for(int i=0; i<gameTable.length; i++)
                for(int j=0; j<gameTable[i].length; j++)
                    temp[i][j]=gameTable[i][j];

            int row2;
            Instant start = Instant.now();
            int deneme1 = minimaxH3(temp,depth,1); // maximizing
            Instant finish = Instant.now();
            System.out.println("Required time of H3 for depth " + depth + ": "+ Duration.between(start, finish).toMillis() + " milliseconds");
            row2 = Math.abs(deneme1) % 10;

            if(startingMove == true){
                row2= (int)(Math.random() * 7);
                startingMove = false;
            }
            if(checkCol(gameTable,row2)){
                int getCol = findAvailableLocation(gameTable,row2);
                playThePiece(gameTable,getCol,row2,2);
            }
            if(checkWinning(gameTable,2)){
                System.out.println("AI player with H3 won");
                checker = terminalState(gameTable);
                printTable(gameTable);
                System.out.println("------------------------------");
                break;
            }




            int tempSecondPlayer[][] = new int[6][7];
            for(int i=0; i<gameTable.length; i++){
                for(int j=0; j<gameTable[i].length; j++){
                    tempSecondPlayer[i][j]=gameTable[i][j];

                    if(gameTable[i][j] == 2){
                        tempSecondPlayer[i][j]=1;
                    }
                    else if(gameTable[i][j] == 1){
                        tempSecondPlayer[i][j]=2;
                    }
                    else{
                        tempSecondPlayer[i][j]=0;
                    }

                }
            }

            System.out.println("------------------------------");
            printTable(gameTable);
            start = Instant.now();
            int deneme = minimaxH1(tempSecondPlayer,depth,1);
            finish = Instant.now();
            System.out.println("Required time of H1 for depth " + depth + ": "+Duration.between(start, finish).toMillis() + " milliseconds");

            int rowSecondPlayer =Math.abs(deneme) % 10;
            if(checkCol(gameTable,rowSecondPlayer)){
                int getCol = findAvailableLocation(gameTable,rowSecondPlayer);
                playThePiece(gameTable,getCol,rowSecondPlayer,1);
            }

            if(checkWinning(gameTable,1)){
                System.out.println("AI player with H1 won");
                printTable(gameTable);
                break;
            }


            counter++;
            if(counter == 22){
                System.out.println("Game is over. Drawn.");
                break;
            }


        }
    }

    public static void playAiToAi23(){ // h2 vs h3
        System.out.println("------------------------------");
        initializeTable(gameTable);
        boolean startingMove = true;
        int counter = 0;
        boolean checker = terminalState(gameTable);
        Scanner systemInput = new Scanner(System.in);
        System.out.print("Please write depth of minmax(1-8): ");
        int depth = systemInput.nextInt();
        while(true){

            printTable(gameTable);
            int temp[][] = new int[6][7];
            for(int i=0; i<gameTable.length; i++)
                for(int j=0; j<gameTable[i].length; j++)
                    temp[i][j]=gameTable[i][j];
            int row2;
            Instant start = Instant.now();
            int deneme1 = minimaxH2(temp,depth,1); // maximizing
            Instant finish = Instant.now();
            System.out.println("Required time of H2 for depth " + depth + ": "+Duration.between(start, finish).toMillis() + " milliseconds");
            row2 = Math.abs(deneme1) % 10;

            if(startingMove == true){
                row2= (int)(Math.random() * 7);
                startingMove = false;
            }
            if(checkCol(gameTable,row2)){
                int getCol = findAvailableLocation(gameTable,row2);
                playThePiece(gameTable,getCol,row2,2);
            }
            if(checkWinning(gameTable,2)){
                System.out.println("AI player with H2 won");
                checker = terminalState(gameTable);
                printTable(gameTable);
                System.out.println("------------------------------");
                break;
            }




            int tempSecondPlayer[][] = new int[6][7];
            for(int i=0; i<gameTable.length; i++){
                for(int j=0; j<gameTable[i].length; j++){
                    tempSecondPlayer[i][j]=gameTable[i][j];

                    if(gameTable[i][j] == 2){
                        tempSecondPlayer[i][j]=1;
                    }
                    else if(gameTable[i][j] == 1){
                        tempSecondPlayer[i][j]=2;
                    }
                    else{
                        tempSecondPlayer[i][j]=0;
                    }

                }
            }

            System.out.println("------------------------------");
            printTable(gameTable);
            start = Instant.now();
            int deneme = minimaxH3(tempSecondPlayer,depth,1);
            finish = Instant.now();
            System.out.println("Required time of H3 for depth " + depth + ": "+Duration.between(start, finish).toMillis() + " milliseconds");

            int rowSecondPlayer =Math.abs(deneme) % 10;
            if(checkCol(gameTable,rowSecondPlayer)){
                int getCol = findAvailableLocation(gameTable,rowSecondPlayer);
                playThePiece(gameTable,getCol,rowSecondPlayer,1);
            }

            if(checkWinning(gameTable,1)){
                System.out.println("player with H3 won");
                printTable(gameTable);
                break;
            }


            counter++;
            if(counter == 22){
                System.out.println("Game is over. Drawn.");
                break;
            }


        }
    }

    public static void playAiToAi12(){
        System.out.println("------------------------------");
        initializeTable(gameTable);
        boolean startingMove = true;
        int counter = 0;
        boolean checker = terminalState(gameTable);
        Scanner systemInput = new Scanner(System.in);
        System.out.print("Please write depth of minmax(1-8): ");
        int depth = systemInput.nextInt();
        while(true){

            printTable(gameTable);
            int temp[][] = new int[6][7];
            for(int i=0; i<gameTable.length; i++)
                for(int j=0; j<gameTable[i].length; j++)
                    temp[i][j]=gameTable[i][j];
            int row2;
            Instant start = Instant.now();
            int deneme1 = minimaxH2(temp,depth,1); // maximizing
            Instant finish = Instant.now();
            System.out.println("Required time of H2 for depth " + depth + ": "+Duration.between(start, finish).toMillis() + " milliseconds");
            row2 = Math.abs(deneme1) % 10;

            if(startingMove == true){
                row2= (int)(Math.random() * 7);
                startingMove = false;
            }
            if(checkCol(gameTable,row2)){
                int getCol = findAvailableLocation(gameTable,row2);
                playThePiece(gameTable,getCol,row2,2);
            }
            if(checkWinning(gameTable,2)){
                System.out.println("AI player with H2 won");
                checker = terminalState(gameTable);
                printTable(gameTable);
                System.out.println("------------------------------");
                break;
            }




            int tempSecondPlayer[][] = new int[6][7];
            for(int i=0; i<gameTable.length; i++){
                for(int j=0; j<gameTable[i].length; j++){
                    tempSecondPlayer[i][j]=gameTable[i][j];

                    if(gameTable[i][j] == 2){
                        tempSecondPlayer[i][j]=1;
                    }
                    else if(gameTable[i][j] == 1){
                        tempSecondPlayer[i][j]=2;
                    }
                    else{
                        tempSecondPlayer[i][j]=0;
                    }

                }
            }

            System.out.println("------------------------------");
            printTable(gameTable);
            start = Instant.now();
            int deneme = minimaxH1(tempSecondPlayer,depth,1);
            finish = Instant.now();
            System.out.println("Required time of H1 for depth " + depth + ": "+Duration.between(start, finish).toMillis() + " milliseconds");

            int rowSecondPlayer =Math.abs(deneme) % 10;
            if(checkCol(gameTable,rowSecondPlayer)){
                int getCol = findAvailableLocation(gameTable,rowSecondPlayer);
                playThePiece(gameTable,getCol,rowSecondPlayer,1);
            }

            if(checkWinning(gameTable,1)){
                System.out.println("AI player with H1 won");
                printTable(gameTable);
                break;
            }


            counter++;
            if(counter == 22){
                System.out.println("Game is over. Drawn.");
                break;
            }


        }
    }


    public static boolean checkCol(int[][] gameTable, int row){  // burada ??st k??s??m dolmu?? mu diye bak??caz, o s??t??n da yer var m?? koymak i??in
        return gameTable[5][row]==0;
    }

    public static int[] validCols(int[][] gameTable){
        int[] allValidLocation = new int[7];
        int i;
        for(i=0; i<allValidLocation.length;i++){

            if(gameTable[5][i] == 0){
                allValidLocation[i]=1;
            }
        }
        return allValidLocation;

    }

    public static int bestMove(int[][] gameTable, int playerPiece){
        int[] validLocations= validCols(gameTable);
        int bestScore = 0;
        int bestMove = 0;
        int k,chance;
        for(k=0;k<100; k++){
            chance = (int)(Math.random() * 7);
            if(validLocations[chance] == 1){
                bestMove =  chance;
                break;
            }
        }

        int c,score;    // column
        for(c=0; c< 6;c++){
            if(validLocations[c] == 1){
                int row = findAvailableLocation(gameTable,c);
                int temp[][] = new int[6][7];


                for(int i=0; i<gameTable.length; i++)
                    for(int j=0; j<gameTable[i].length; j++)
                        temp[i][j]=gameTable[i][j];


                playThePiece(temp,row,c,playerPiece);
                score = evalH2(temp, playerPiece);
                System.out.println("table score is: "+ score);
                if( score> bestScore){
                    bestScore = score;
                    bestMove = c;
                    System.out.println("bestmove is (c) "+c);
                }

            }

        }


        System.out.println("score is:"+bestMove);
        return bestMove;
    }


    public static void initializeTable(int[][] gameTable){  // oyun tablosunu s??f??r ile doldur
        int i,j;
        for(i=0; i<6;i++){
            for(j=0; j<7; j++){
                gameTable[i][j]=0;
            }
        }
    }

    public static int findAvailableLocation(int[][] gameTable, int row){  // col da en a????adaki avaliable m??sait olan yeri bul
        int i;
        int avaliableLocationCol=100;
        for(i=0;i<6;i++){
            if(gameTable[i][row] == 0){
                avaliableLocationCol = i;
                break;
            }
        }
        return avaliableLocationCol;
    }
    public static void playThePiece(int[][] gameTable, int column, int row, int player){         // bu lokasyona par??ay?? b??rak??yoruz, k??rm??z?? veya mavi yuvarl?????? gibi d??????n
        gameTable[column][row] = player;
    }
    public static void printTable(int gameTable[][]) {              	// oyun tablosunun son halini ekrana bast??r
        int i,j;
        for(i=5;-1<i;i--){
            for(j=0;j<7;j++){
                System.out.print(gameTable[i][j]+" ");
            }
            System.out.println();
        }
    }
    public static boolean checkWinning(int[][] gameTable,int playerNumber){
        // horizantal yatay check yap
        int rowCount=6;
        int columnCount=7;
        int i,j;
        boolean isFinished= false;
        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumber
                        && gameTable[j][i+2]==playerNumber && gameTable[j][i+3]==playerNumber)
                    isFinished=true;
            }
        }

        // vertical
        for(i=0; i<columnCount;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                        && gameTable[j+2][i]==playerNumber && gameTable[j+3][i]==playerNumber)
                    isFinished=true;
            }
        }

        // check sa?? yukar?? ??apraz - yukar?? do??ru ????kan ??aprazlar
        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i+1]==playerNumber
                        && gameTable[j+2][i+2]==playerNumber && gameTable[j+3][i+3]==playerNumber)
                    isFinished=true;
            }
        }

// check a??a????ya do??ru giden ??apraz

        for(i=0; i<columnCount-3;i++){
            for(j=3; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j-1][i+1]==playerNumber
                        && gameTable[j-2][i+2]==playerNumber && gameTable[j-3][i+3]==playerNumber)
                    isFinished=true;
            }
        }



        return isFinished;

    }

    public static boolean terminalState(int[][] gameTable){
        boolean isTerminal=false;
        if(checkWinning(gameTable,1))    // check game is over
            isTerminal=true;
        else if(checkWinning(gameTable,2))
            isTerminal=true;
        else if(validCols(gameTable).length == 0)      // game is full
            isTerminal=true;

        return isTerminal;

    }


    public static int minimaxH2(int[][] gameTable, int depth, int maximizing){
// terminal k??sm??                                           *******************************
        int[] validLocations= validCols(gameTable);
        boolean isTerminal = terminalState(gameTable);

        if(isTerminal || depth == 0){
            if(isTerminal){   // oyun bitmi?? demektir, yada dolmu??.
                if(checkWinning(gameTable,1))
                    return -500000;
                if(checkWinning(gameTable,2))
                    return 50000000;
                else{
                    return 0;    // ggame is fulled
                }

            }
            else if(depth == 0){

                return evalH2(gameTable,2);
            }

        }
// terminal k??sm?? biti??i                                        ***********************************
//  maximizing maximizing maximizing maximizing
        if(maximizing == 1){           // **** maximizing
            int value = -999999990;
            int col,k,chance;
            for(k=0;k<100; k++){
                chance = (int)(Math.random() * 7);
                if(validLocations[chance] == 1){
                    col =  chance;
                    break;
                }
            }
            int c,newScore;
            for(c=0; c< 7;c++){
                if(validLocations[c] == 1){
                    int row = findAvailableLocation(gameTable,c);
                    int temp[][] = new int[6][7];


                    for(int i=0; i<gameTable.length; i++)
                        for(int j=0; j<gameTable[i].length; j++)
                            temp[i][j]=gameTable[i][j];

                    playThePiece(temp,row,c,2);
                    newScore = minimaxH2(temp,depth-1,0); // zero is minimazing
                    if( newScore> value){
                        if(newScore<0){
                            newScore = Math.abs(newScore);
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            newScore = newScore * (-1);
                            value = newScore;
                        }
                        else{
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            value = newScore;
                        }
                        //		System.out.println("bestmove is max(c) " + c + " value is" + value);
                    }
                }
            }
            return value;


        }
// minimizing         minimizing minimizing mini mini mini mini mini mini
        else{           // **** maximizing
            int value = 999999990;
            int col,k,chance;
            for(k=0;k<100; k++){
                chance = (int)(Math.random() * 7);
                if(validLocations[chance] == 1){
                    col =  chance;
                    break;
                }
            }
            int c,newScore;
            for(c=0; c< 7;c++){
                if(validLocations[c] == 1){
                    int row = findAvailableLocation(gameTable,c);
                    int temp[][] = new int[6][7];


                    for(int i=0; i<gameTable.length; i++)
                        for(int j=0; j<gameTable[i].length; j++)
                            temp[i][j]=gameTable[i][j];

                    playThePiece(temp,row,c,1);                          // player piece 1
                    newScore = minimaxH2(temp,depth-1,1); //       buras?? da tekrardan maximinzg e gidecek
                    if( newScore < value){
                        if(newScore<0){
                            newScore = Math.abs(newScore);
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            newScore = newScore * (-1);
                            value = newScore;
                        }
                        else{
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            value = newScore;
                        }
//			System.out.println("bestmove is minimazing (c) " + c + " value is" + value);
                    }
                }
            }
            return value;


        }

    }

    public static int minimaxH3(int[][] gameTable, int depth, int maximizing){
// terminal k??sm??                                           *******************************
        int[] validLocations= validCols(gameTable);
        boolean isTerminal = terminalState(gameTable);

        if(isTerminal || depth == 0){
            if(isTerminal){   // oyun bitmi?? demektir, yada dolmu??.
                if(checkWinning(gameTable,1))
                    return -500000;
                if(checkWinning(gameTable,2))
                    return 50000000;
                else{
                    return 0;    // game is fulled
                }

            }
            else if(depth == 0){

                return evalH3(gameTable, 2);
            }

        }
// terminal k??sm?? biti??i                                        ***********************************
//  maximizing maximizing maximizing maximizing
        if(maximizing == 1){           // **** maximizing
            int value = -999999990;
            int col,k,chance;
            for(k=0;k<100; k++){
                chance = (int)(Math.random() * 7);
                if(validLocations[chance] == 1){
                    col =  chance;
                    break;
                }
            }
            int c,newScore;
            for(c=0; c< 7;c++){
                if(validLocations[c] == 1){
                    int row = findAvailableLocation(gameTable,c);
                    int temp[][] = new int[6][7];


                    for(int i=0; i<gameTable.length; i++)
                        for(int j=0; j<gameTable[i].length; j++)
                            temp[i][j]=gameTable[i][j];

                    playThePiece(temp,row,c,2);
                    newScore = minimaxH2(temp,depth-1,0); // zero is minimazing
                    if( newScore> value){
                        if(newScore<0){
                            newScore = Math.abs(newScore);
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            newScore = newScore * (-1);
                            value = newScore;
                        }
                        else{
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            value = newScore;
                        }
                        //		System.out.println("bestmove is max(c) " + c + " value is" + value);
                    }
                }
            }
            return value;


        }
// minimizing         minimizing minimizing mini mini mini mini mini mini
        else{           // **** maximizing
            int value = 999999990;
            int col,k,chance;
            for(k=0;k<100; k++){
                chance = (int)(Math.random() * 7);
                if(validLocations[chance] == 1){
                    col =  chance;
                    break;
                }
            }
            int c,newScore;
            for(c=0; c< 7;c++){
                if(validLocations[c] == 1){
                    int row = findAvailableLocation(gameTable,c);
                    int temp[][] = new int[6][7];


                    for(int i=0; i<gameTable.length; i++)
                        for(int j=0; j<gameTable[i].length; j++)
                            temp[i][j]=gameTable[i][j];

                    playThePiece(temp,row,c,1);                          // player piece 1
                    newScore = minimaxH2(temp,depth-1,1); //       buras?? da tekrardan maximinzg e gidecek
                    if( newScore < value){
                        if(newScore<0){
                            newScore = Math.abs(newScore);
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            newScore = newScore * (-1);
                            value = newScore;
                        }
                        else{
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            value = newScore;
                        }
//			System.out.println("bestmove is minimazing (c) " + c + " value is" + value);
                    }
                }
            }
            return value;


        }

    }


    public static int minimaxH1(int[][] gameTable, int depth, int maximizing){
// terminal k??sm??                                           *******************************
        int[] validLocations= validCols(gameTable);
        boolean isTerminal = terminalState(gameTable);

        if(isTerminal || depth == 0){
            if(isTerminal){   // oyun bitmi?? demektir, yada dolmu??.
                if(checkWinning(gameTable,1))
                    return -500000;
                if(checkWinning(gameTable,2))
                    return 50000000;
                else{
                    return 0;    // ggame is fulled
                }

            }
            else if(depth == 0){

                return evalH1(gameTable,2);
            }

        }
// terminal k??sm?? biti??i                                        ***********************************
//  maximizing maximizing maximizing maximizing
        if(maximizing == 1){           // **** maximizing
            int value = -999999990;
            int col,k,chance;
            for(k=0;k<100; k++){
                chance = (int)(Math.random() * 7);
                if(validLocations[chance] == 1){
                    col =  chance;
                    break;
                }
            }
            int c,newScore;
            for(c=0; c< 7;c++){
                if(validLocations[c] == 1){
                    int row = findAvailableLocation(gameTable,c);
                    int temp[][] = new int[6][7];


                    for(int i=0; i<gameTable.length; i++)
                        for(int j=0; j<gameTable[i].length; j++)
                            temp[i][j]=gameTable[i][j];

                    playThePiece(temp,row,c,2);
                    newScore = minimaxH2(temp,depth-1,0); // zero is minimazing
                    if( newScore> value){
                        if(newScore<0){
                            newScore = Math.abs(newScore);
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            newScore = newScore * (-1);
                            value = newScore;
                        }
                        else{
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            value = newScore;
                        }
                        //		System.out.println("bestmove is max(c) " + c + " value is" + value);
                    }
                }
            }
            return value;


        }
// minimizing         minimizing minimizing mini mini mini mini mini mini
        else{           // **** maximizing
            int value = 999999990;
            int col,k,chance;
            for(k=0;k<100; k++){
                chance = (int)(Math.random() * 7);
                if(validLocations[chance] == 1){
                    col =  chance;
                    break;
                }
            }
            int c,newScore;
            for(c=0; c< 7;c++){
                if(validLocations[c] == 1){
                    int row = findAvailableLocation(gameTable,c);
                    int temp[][] = new int[6][7];


                    for(int i=0; i<gameTable.length; i++)
                        for(int j=0; j<gameTable[i].length; j++)
                            temp[i][j]=gameTable[i][j];

                    playThePiece(temp,row,c,1);                          // player piece 1
                    newScore = minimaxH2(temp,depth-1,1); //       buras?? da tekrardan maximinzg e gidecek
                    if( newScore < value){
                        if(newScore<0){
                            newScore = Math.abs(newScore);
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            newScore = newScore * (-1);
                            value = newScore;
                        }
                        else{
                            newScore = newScore / 10;
                            newScore = newScore * 10 + c;
                            value = newScore;
                        }

                        //		System.out.println("bestmove is minimazing (c) " + c + " value is" + value);
                    }
                }
            }
            return value;


        }

    }

    public static int evalH1(int[][] gameTable, int playerNumber){
        int score = (int)(Math.random() * 500);


        return score;
    }


    public static int evalH2(int[][] gameTable, int playerNumber){ //H2
        // buraya bir de bir kod d??ng??s?? ekle, en ortaya en y??ksek de??eri ver, ondan uzakla??t??k??a de??er d??????yor
        // b??ylece bizim algoritmam??z genelde orataya oynuyor, mesela tam ortas?? 14 puan, k????e 3 puan gibi


        int playerNumberReverse=2;
        if(playerNumber ==2)
            playerNumberReverse=1;

        int score = 0;
        int rowCount=6;
        int columnCount=7;
        int i,j;
        boolean inside3hor = true;
        boolean inside2hor = true;
        boolean inside3ver = true;
        boolean inside2ver = true;
        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumber
                        && gameTable[j][i+2]==playerNumber && gameTable[j][i+3]==playerNumber){
                    score = score + 3000; // oyun kazand??ran hamle i??in 100 ekle
                    inside3hor=false;
                    inside2hor=false;
                    break;
                }
            }
        }

        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j][i+1]==playerNumberReverse
                        && gameTable[j][i+2]==playerNumberReverse && gameTable[j][i+3]==playerNumber){
                    score = score + 90; // rakibin kazanmas??n?? engelleyen hamle
                    break;
                }
            }
        }

        for(i=0; i<columnCount;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j+1][i]==playerNumberReverse
                        && gameTable[j+2][i]==playerNumberReverse && gameTable[j+3][i]==playerNumber){
                    score = score + 90; // rakibin kazanmas??n?? engelleyen hamle, yan yana ve dikey
                    break; }
            }
        }



        if(inside3hor){
            for(i=0; i<columnCount-3;i++){
                for(j=0; j<rowCount; j++){
                    if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumber
                            && gameTable[j][i+2]==playerNumber){
                        score = score + 50; // ta??lar?? 3ler
                        inside2hor=false;
                        break;
                    }
                }
            }
        }
        if(inside2hor){
            for(i=0; i<columnCount-3;i++){
                for(j=0; j<rowCount; j++){
                    if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumber){
                        score = score + 20; // ta??lar?? 2ler
                        break;
                    }
                }
            }
        }


        for(i=0; i<columnCount;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                        && gameTable[j+2][i]==playerNumber && gameTable[j+3][i]==playerNumber){
                    score = score + 3000; //
                    inside3ver = false;
                    inside2ver = false;
                    break;}
            }
        }

        if(inside3ver){
            for(i=0; i<columnCount;i++){
                for(j=0; j<rowCount-3; j++){
                    if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                            && gameTable[j+2][i]==playerNumber){
                        score = score + 50;
                        inside2ver=false;
                        break;}
                }
            }
        }
        if(inside2ver){
            for(i=0; i<columnCount;i++){
                for(j=0; j<rowCount-3; j++){
                    if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                            && gameTable[j+2][i]==playerNumber){
                        score = score + 20;

                        break;
                    }}
            }
        }


        return score;
    }

    public static int evalH3(int[][] gameTable, int playerNumber){ //H3
        // buraya bir de bir kod d??ng??s?? ekle, en ortaya en y??ksek de??eri ver, ondan uzakla??t??k??a de??er d??????yor
        // b??ylece bizim algoritmam??z genelde orataya oynuyor, mesela tam ortas?? 14 puan, k????e 3 puan gibi


        int playerNumberReverse=2;
        if(playerNumber ==2)
            playerNumberReverse=1;

        int score = 0;
        int rowCount=6;
        int columnCount=7;
        int i,j;
        boolean played = false;
        boolean inside3hor = true;
        boolean inside2hor = true;
        boolean inside3ver = true;
        boolean inside2ver = true;


        for(i=0; i<columnCount-3;i++){              // yatay bak??yor
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumber
                        && gameTable[j][i+2]==playerNumber && gameTable[j][i+3]==playerNumber){
                    score = score + 3000; // oyun kazand??ran hamle i??in 100 ekle
                    inside3hor=false;
                    inside2hor=false;
                    played = true;
                    break;
                }
            }
        }


        for(i=0; i<columnCount;i++){    // yukar?? do??ru
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                        && gameTable[j+2][i]==playerNumber && gameTable[j+3][i]==playerNumber){
                    score = score + 3000; // oyun kazand??ran hamle
                    played = true;
                    inside3ver = false;
                    inside2ver = false;
                    break;
                }
            }
        }

        for(i=0; i<columnCount-3;i++){              // yukar?? ??apraz
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i+1]==playerNumber
                        && gameTable[j+2][i+2]==playerNumber && gameTable[j+3][i+3]==playerNumber)
                    score = score + 3000; // oyun kazand??ran hamle
            }
        }


        for(i=0; i<columnCount-3;i++){           // a??a???? ??apraz
            for(j=3; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j-1][i+1]==playerNumber
                        && gameTable[j-2][i+2]==playerNumber && gameTable[j-3][i+3]==playerNumber)
                    score = score + 3000; // oyun kazand??ran hamle
            }
        }












        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j][i+1]==playerNumberReverse
                        && gameTable[j][i+2]==playerNumberReverse && gameTable[j][i+3]==playerNumber){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }
            }
        }

        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j][i+1]==playerNumberReverse
                        && gameTable[j][i+2]==playerNumber && gameTable[j][i+3]==playerNumberReverse){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }
            }
        }

        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j][i+1]==playerNumber
                        && gameTable[j][i+2]==playerNumberReverse && gameTable[j][i+3]==playerNumberReverse){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }
            }
        }

        for(i=0; i<columnCount-3;i++){
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumberReverse
                        && gameTable[j][i+2]==playerNumberReverse && gameTable[j][i+3]==playerNumberReverse){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }
            }
        }














        for(i=0; i<columnCount;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j+1][i]==playerNumberReverse
                        && gameTable[j+2][i]==playerNumberReverse && gameTable[j+3][i]==playerNumber){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }

            }
        }


        for(i=0; i<columnCount;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j+1][i]==playerNumberReverse
                        && gameTable[j+2][i]==playerNumber && gameTable[j+3][i]==playerNumberReverse){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }

            }
        }

        for(i=0; i<columnCount;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j+1][i]==playerNumber
                        && gameTable[j+2][i]==playerNumberReverse && gameTable[j+3][i]==playerNumberReverse){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }

            }
        }


        for(i=0; i<columnCount;i++){
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumberReverse
                        && gameTable[j+2][i]==playerNumberReverse && gameTable[j+3][i]==playerNumberReverse){
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
                    played = true;
                    break;
                }

            }
        }













        for(i=0; i<columnCount-3;i++){              // yukar?? ??apraz
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j+1][i+1]==playerNumberReverse
                        && gameTable[j+2][i+2]==playerNumberReverse && gameTable[j+3][i+3]==playerNumber)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }

        for(i=0; i<columnCount-3;i++){              // yukar?? ??apraz
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j+1][i+1]==playerNumberReverse
                        && gameTable[j+2][i+2]==playerNumber && gameTable[j+3][i+3]==playerNumberReverse)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }

        for(i=0; i<columnCount-3;i++){              // yukar?? ??apraz
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j+1][i+1]==playerNumber
                        && gameTable[j+2][i+2]==playerNumberReverse && gameTable[j+3][i+3]==playerNumberReverse)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }

        for(i=0; i<columnCount-3;i++){              // yukar?? ??apraz
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i+1]==playerNumberReverse
                        && gameTable[j+2][i+2]==playerNumberReverse && gameTable[j+3][i+3]==playerNumberReverse)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }



        for(i=0; i<columnCount-3;i++){
            for(j=3; j<rowCount; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j-1][i+1]==playerNumberReverse
                        && gameTable[j-2][i+2]==playerNumberReverse && gameTable[j-3][i+3]==playerNumber)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }


        for(i=0; i<columnCount-3;i++){
            for(j=3; j<rowCount; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j-1][i+1]==playerNumberReverse
                        && gameTable[j-2][i+2]==playerNumber && gameTable[j-3][i+3]==playerNumberReverse)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }

        for(i=0; i<columnCount-3;i++){
            for(j=3; j<rowCount; j++){
                if(gameTable[j][i]==playerNumberReverse && gameTable[j-1][i+1]==playerNumber
                        && gameTable[j-2][i+2]==playerNumberReverse && gameTable[j-3][i+3]==playerNumberReverse)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }


        for(i=0; i<columnCount-3;i++){
            for(j=3; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j-1][i+1]==playerNumberReverse
                        && gameTable[j-2][i+2]==playerNumberReverse && gameTable[j-3][i+3]==playerNumberReverse)
                    score = score + 500; // rakibin kazanmas??n?? engelleyen hamle
            }
        }


        for(i=0; i<columnCount-3;i++){     // yatay
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumber
                        && gameTable[j][i+2]==playerNumber && gameTable[j][i+3] == 0){
                    score = score + 100; // ta??lar?? 3ler
                    played = true;
                    inside2hor=false;
                    break;
                }
            }
        }


        for(i=0; i<columnCount;i++){    // yukar?? do??ru
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                        && gameTable[j+2][i]==playerNumber && gameTable[j+3][i]==0){
                    score = score + 100;
                    break;
                }
            }
        }


        for(i=0; i<columnCount-3;i++){              // yukar?? ??apraz
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i+1]==playerNumber
                        && gameTable[j+2][i+2]==playerNumber && gameTable[j+3][i+3]==0)
                    score = score + 100;
            }
        }
        for(i=0; i<columnCount-3;i++){           // a??a???? ??apraz
            for(j=3; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j-1][i+1]==playerNumber
                        && gameTable[j-2][i+2]==playerNumber && gameTable[j-3][i+3]==0)
                    score = score + 100;
            }
        }




        for(i=0; i<columnCount-3;i++){      // yatay
            for(j=0; j<rowCount; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j][i+1]==playerNumber
                        && gameTable[j][i+2]==0 &&  gameTable[j][i+3]==0){
                    score = score + 20;    // ta??lar?? 2ler
                    break;
                }
            }
        }

        for(i=0; i<columnCount;i++){    // dikey
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                        && gameTable[j+2][i]==0 && gameTable[j+3][i]==0){
                    score = score + 20;
                    break;
                }
            }
        }


        for(i=0; i<columnCount;i++){    // yukar?? do??ru
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i]==playerNumber
                        && gameTable[j+2][i]==0 && gameTable[j+3][i]==0){
                    score = score + 20;
                    break;
                }
            }
        }

        for(i=0; i<columnCount-3;i++){              // yukar?? ??apraz
            for(j=0; j<rowCount-3; j++){
                if(gameTable[j][i]==playerNumber && gameTable[j+1][i+1]==playerNumber
                        && gameTable[j+2][i+2]==playerNumber && gameTable[j+3][i+3]==0)
                    score = score + 20;
            }
        }

        return score;
    }

}
