import java.util.Scanner;
class Main
{
    public static void main(String args[])
    {
        Main obj = new Main();
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of players as 2, 3 or 4 only:");
        byte n = sc.nextByte();
        if(n<2 || n>4)                                          //check if value of n is valid
        {
            System.out.println("Please enter only 2, 3 or 4.\nProgram terminated.\nI'll be back.");
            System.exit(0);
        }

        int curplay=1, i, d1, d2, sum, count=0;
        int[] tile  = new int[n];                               //Contains position of each player on the board
        int[] money = new int[n];                               //Contains money of each player
        boolean[] in_jail = new boolean[n];                     //Contains if any player is in jail

        String board[] = {"Go","Old Kent Road","Community Chest","Whitechapel Road","Income Tax","Kings Cross Station","The Angel Islington","Chance","Euston Road","Pentonville Road","Jail","Pall Mall","Electric Company","Whitehall","Northumberl'd Avenue","Marylebone Station","Bow Street","Community chest","Marlborough Street","Vine Street","Free Parking","Strand","Chance","Fleet Street","Trafalgar Square","Fenchurch ST. Station","Leicester Square","Coventry Street","Water Works","Piccadilly","Go To Jail","Regent Street","Oxford Street","Community Chest","Bond Street","Liverpool ST. Station","Chance","Park Lane","Super Tax","Mayfair"};
        int cost[]={0,60,0,60,0,200,100,0,100,120,0,140,150,140,160,200,180,0,180,200,0,220,0,220,240,200,260,260,150,280,0,300,300,0,320,200,0,350,0,400};
        int rent[]={0,2,0,4,0,0,6,0,6,8,0,10,0,10,12,0,14,0,14,16,0,18,0,18,20,0,22,22,0,24,0,26,26,0,28,0,0,35,0,50};

        int property[]={0,60,0,60,200,200,100,0,100,120,0,140,150,140,160,200,180,0,180,200,0,220,0,220,240,200,260,260,150,280,0,300,300,0,320,200,0,350,100,400};

        for(i=0; i<n; i++)                                      //Initialize arrays tile[], money[] and property[]
        {
            tile[i] = 0;
            in_jail[i] = false;
            money[i] = 1500;
        }

        for(i=0; i<cost.length; i++)                            //Set all non-properties to 1, and all properties to 0.
        {
            if(property[i] == 0)
                property[i] = 1;
            else
                property[i] = 0;
        }

        while(obj.gameNotEnded(property))
        {
            if(curplay == (n+1))
                curplay = 1;

            System.out.println("\nPlayer " + curplay + "\'s turn.");

            d1 = (int)(Math. random()*6+1);
            d2 = (int)(Math. random()*6+1);
            sum = d1 + d2;

            System.out.println("You have rolled a sum of " + sum + ".");

            if(!in_jail[curplay-1])                                                      //Restrict movement while in jail.
            {
                if((sum + tile[curplay-1]) >= 40)
                {
                    tile[curplay-1] = sum - (40 - tile[curplay-1]);
                    money[curplay-1] += 200;
                }
                else
                    tile[curplay-1] += sum;

                System.out.println("You have landed on " + board[tile[curplay-1]] + ".");

                if(property[tile[curplay-1]] == 1)                                       //1 means not a property
                    System.out.println("You have not landed on a property.");
                else if(tile[curplay-1] == 4)
                {
                    System.out.println("You have paid Income tax of 200.");
                    money[curplay-1] -= 200;
                }
                else if(tile[curplay-1] == 38)
                {
                    System.out.println("You have paid Super tax of 100.");
                    money[curplay-1] -= 100;
                }
                else if(property[tile[curplay-1]] == 0)                                  //0 means not owned by any player
                {
                    System.out.println("Do you want to buy this property? It costs " + cost[tile[curplay-1]] + ". Say either [y]es or [n]o.");
                    System.out.println("Player  has " + money[curplay-1]);
                    String str = sc.next();

                    if(str.charAt(0) == 'y' || str.charAt(0) == 'Y')
                    {
                        money[curplay-1] -= cost[tile[curplay-1]];
                        property[tile[curplay-1]] = -curplay;
                        System.out.println("Player " + curplay + " now owns " + board[tile[curplay-1]] + ".");
                    }
                }
                else if(!((-curplay) == property[tile[curplay-1]]))             //Player does not own property he has landed on
                {
                    System.out.print("You have paid a rent of ");

                    int rentpaid = rent[tile[curplay-1]], count2=0;
                    int owner = -(property[tile[curplay-1]]);

                    if(tile[curplay-1] == 12 && tile[curplay-1] == 28)          //Electric and Water
                        rentpaid = 10 * sum;
                    else if(tile[curplay-1] == 12 || tile[curplay-1] == 28)
                        rentpaid = 4 * sum;
                    else if(tile[curplay-1] == 5 || tile[curplay-1] == 15 || tile[curplay-1] == 25 || tile[curplay-1] == 35)
                    {
                        boolean howmany[] = {owner == -(property[5]), owner == -(property[15]), owner == -(property[25]), owner == -(property[35])};
                        for(int i2=0; i2<4; i2++)                                   //Train Station
                        {
                            if(howmany[i2])
                                count2++;
                        }
                        switch(count2)
                        {
                            case 1: 
                            rentpaid = 25;
                            break;
                            case 2: 
                            rentpaid = 50;
                            break;
                            case 3: 
                            rentpaid = 100;
                            break;
                            case 4: 
                            rentpaid = 200;
                        }
                    }

                    money[owner-1] += rentpaid;                                 //Common for all properties
                    money[curplay-1] -= rentpaid;

                    System.out.println(rentpaid);
                }
                else if((-curplay) == property[tile[curplay-1]])                //Player already owns property he has landed on
                {
                    System.out.println("Do you want to auction this property? Say either [y]es or [n]o.");
                    String str = sc.next();

                    if(str.charAt(0) == 'y' || str.charAt(0) == 'Y')
                    {
                        int[] answer = obj.Auction(cost[tile[curplay-1]], n, curplay);
                        money[curplay-1] += answer[0];                          //Adding money to auctioner's account
                        money[answer[1]-1] -= answer[0];                          //Subtracting money from highest bidder
                    }
                }

                if(tile[curplay-1] == 30)                                       //Send to Jail
                {
                    System.out.println("You are in Jail.");
                    tile[curplay-1] = 10;
                    in_jail[curplay-1] = true;

                    System.out.println("Do you want to pay 50 to get out of Jail? Say either [y]es or [n]o.");
                    String str = sc.next();

                    if(str.charAt(0) == 'y' || str.charAt(0) == 'Y')
                    {
                        money[curplay-1] -= 50;
                        in_jail[curplay-1] = false;
                    }
                }

                if(!obj.notBankrupt(money))
                {
                    System.out.println("One or more players have gone bankrupt.");
                    int winner=0;
                    int[] sumProp = new int[n];

                    for(i=0; i<n; i++)
                    {
                        for(int j=0; j<property.length; j++)
                        {
                            if(property[j] == (-i))
                                sumProp[i] += cost[-(property[j])];
                        }
                        sumProp[i] += money[i];
                    }

                    for(i=0; i<n; i++)
                    {
                        if(sumProp[i] > winner)
                            winner = i;
                    }

                    System.out.println("The winner is Player " + (winner+1) + ".");

                    System.exit(0);
                }
            }

            if(d1 == d2)                                        //If doubles are rolled
            {
                if(in_jail[curplay-1] == true)
                    in_jail[curplay-1] = false;

                System.out.println("You have rolled a double!");

                continue;
            }

            curplay++;
        }
    }

    private boolean gameNotEnded(int[] property)
    {
        boolean gamenotended = false;

        for(int i=0; i<property.length; i++)
        {
            if(property[i] == 0)                        //When property is found which is not owned by any player
                gamenotended = true;
        }

        return gamenotended;
    }

    private boolean notBankrupt(int[] money)
    {
        boolean notbankrupt = true;

        for(int i=0; i<money.length; i++)
        {
            if(money[i] <= 0)
                notbankrupt = false;
        }

        return notbankrupt;
    }

    private int[] Auction(int startprice, int n, int startedauction)
    {
        Scanner sc = new Scanner(System.in);
        int finalprice=startprice, curplay=1;
        String str;

        int[] answer = new int[2];

        System.out.println("Enter yes to bid more money, no to skip your turn. Auctioner must type \"exit\" to exit the auction without selling.");
        System.out.println("Starting price is " + startprice + ".");

        while(true)
        {
            if(curplay == (n+1))
                curplay = 1;

            if(startedauction != curplay)
            {
                System.out.println("Player " + curplay + "\'s turn.");
                str = sc.next();

                if(str.charAt(0) == 'y' || str.charAt(0) == 'Y')                    //If input is yes, then continue bidding.
                {
                    finalprice += 10;

                    System.out.println("Is the Auctioner pleased with selling the property for " + finalprice + "?");
                    String mtr = sc.next();

                    if(mtr.charAt(0) == 'y' || mtr.charAt(0) == 'Y')
                    {
                        answer[1] = curplay;
                        break;
                    }
                }
                else if(str.equalsIgnoreCase("exit"))  //If input is exit, then exit auction.
                {
                    answer[1] = startedauction;
                    finalprice = 0;
                    break;
                }
            }

            curplay++;
        }
        answer[0] = finalprice;
        return answer;
    }
}