# Monopoly

---
## Variables

> _Note: All these variables are declared and initialised in `main()` itself. The variables used in other parts of the program are for local calculations only._
>


1. `byte n` : Taken as input from the user. Stores the number of players, can only be 2, 3 or 4.
2. `int curplay` : Stores the current player number. Used to determine which player's chance it is.
3. `int d1, d2, sum` : `d1` and `d2` store the value of the first dice and second dice for that turn respectively. They are "rolled" each time the next player's turn comes. `sum` stores the sum of both `d1` and `d2`.
4. `int i` : Used as per convention, the index variable in a loop.
5. `int[] tile` : Stores each player's position on the board. For example, Player 1's location on the board will be stored in `tile[0]`. All the values in this array are initialised to 0, since all the players start at position 0 (or `Go`) on the board.
6. `int[] money` : Stores the amount of money each player has in their bank account. For example, Player 1's money will be stored in `money[0]`. All the values in this array are initialised to 1500, since all the players are supposed to have 1500 in the beginning.
7. `boolean[] injail` : Stores if the player is in jail. For example, if Player 1 is in jail, `in_jail[0]` will be `true`. All the values in this array are initialised to `false`, since none of the players are in jail at the start.
8. `String board[]` : Stores all the 40 locations on the board, for displaying which location the player has landed on their turn.
9. `int cost[]` : Stores the cost of all the buyable properties. Used for modifying the `money[]` array accordingly, when a player lands on a property he does not own.
10. `int rent[]` : Stores the rent of all the buyable properties. Used for modifying the `money[]` array accordingly, when a player lands on a property that nobody owns.
11. `int property[]` : Stores the list of all properties. Following are the list of values in the array for different kinds of properties:
    - If a property is not buyable: `1`
    - If a property is buyable, but not owned by any player: `0`
    - If a property is owned by a player: `player number in negative`

---
## Functions

```
static void main()
```
> Most of the program code is in the `main()` function. It does not return any value. It calls the other functions when and where necessary. It contains a `while()` loop to switch between players when their turn comes. The loop exits when the `gameNotEnded()` method returns `false`. All the important variables and arrays are declared in this method, and passed to other methods at the time of calling, if required. This is also the only method which is `public` in the entire program, so that abstraction is implemented and the user does not get confused as to which program he has to run.
>


```
private boolean gameNotEnded(int[] property)
```
> The `gameNotEnded()` method checks whether the game has ended. This is done by essentially checking if all of the properties have been bought by the players. There are 28 total properties, hence if the sum of properties owned by all of the players is 28, there are no properties left to buy, and the game ends here by returning `false` from this method.
>


```
private boolean notBankrupt(int[] money)
```
> There is also an alternate way that the game can end, i.e. if any of the players have less than or equal to 0 money. This is done by simply iterating through the `money[]` array, and checking if any player's money is less than or equal to 0.
>


```
private int[] Auction(int startprice, int n,int startedauction)
```
> In case of an auction, the main() method calls this method. It handles all of the auction features, including:
>
> - Exiting from the auction. 
> - Increases value of property to be auctioned by 10 every time a player bids.
> - Asks the auctioner if he is pleased with the price of the property every time.
>
> This function returns two values in the array:
>
> - Firstly, the final amount of money which the property was sold for after the auction. 
> - Secondly, the player who bought the auction at the highest price is returned. These values are returned so that the `main()` function can modify the `money[]` array accordingly.
>


```
private void winner(int[] property,int[] money,int[] cost,intn)
```
> This function determines the winner in case the game ends. It does not return any value, since it directly displays the winner in the terminal, and exits the program after that. It adds up the sum of the cost of the properties and the players' current money in the accounts. The player with the most amount of money, in properties and in money added, will be the winner.
>

---
