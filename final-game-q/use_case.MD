## **Use Cases**
### **Frontend** Endpoints are listed in the next section
**- Starts the game**<br>
When user starts the game, a request is sent to notify the backend that it can starts to periodically update the specified board. For related Backend use cases, refer to Backend 1.3<br>
**- Gets the most up-to-date (position & status wise) game board**<br>
The returned game board state will be rendered and displayed in the frontend so user can play the game. For related Backend use cases, refer to Backend 1.1<br>
**- Resets the game after GAME OVER before user starts a new game**<br>
Before user starts a new game, the frontend will notify the backend to reset user's game board so it is ready for the new game. For related Backend use cases, refer to Backend 1.2<br>
**- Makes Pac-Man moves in user specified direction**<br>
When user press a direction key, a request will be sent to backend to make Pac-Man on user's game board adopts corresponding update strategy (strategy that makes it move in specified direction). For related Backend use cases, refer to Backend 1.5<br>
**- Set the number of ghosts (Min 3, max 8 ghosts)**<br>
Notify the backend to set number of ghosts on user's game board to user specified value. For related Backend use case, refer to Backend 1.4.<br>
**- Landing. Initialize a game board** : For related Backend use cases, refer to Backend 1.6<br>
When user open the site, a request is sent automitically to create a new game board for the user. A boardId will be included in the response and frontend will record boardId, so in each subsequent requests it will be consistently referring accessing the same board. Distinguishing game boards with boardId allows multiple isers accessing the app and plays spontaneous.

### **Backend** (IF : THEN)
#### ***1. Controller and Adapter***
**1.1. Frontend requests the game board : return current board (position & status) to frontend**<br>
Adapter method first retrieves the PacmanBoard object with specified boardId and call getScore(), getLevel(), and getBoard(), on the baord to get its attributes score (indicate scores user had achieved in current game), level (indicate the level user is playing on) and board (represents the whole board and positions of each objects on the board).<br>
```
@param String boardId
@return json response
```
**1.2. Frontend requests a reset : return the default game board to frontend**<br>
Adapter method first retrieves the PacmanBoard object with specified boardId and make call related functions on the baord to restore the board to the default state (respawn Pac-Man, ghosts and dots at their initial coordinates).<br>
```
@param String boardId
@return json response
```
**1.3. Frontend starts the game : backend invokes the game board to updates**<br>
Adapter method first retrieves the PacmanBoard object with specified boardId and make call tick() method on the baord to start updating the board state. <br>
```
@param String boardId
@return json response
```
**1.4. Frontend requests to change number of ghosts : backend set number of ghosts of specified board**<br>
Adapter method first retrieves the PacmanBoard object with specified boardId and make call related functions on the baord. Refer to Backend 3.3 for details about the implementation in the model.<br>
```
@param String boardId
@return json response
```
**1.5. Frontend requests to make Pac-Man move in specified direction : backend make changes to Pac-Man accordingly**<br>
Adapter method first retrieves the PacmanBoard object with specified boardId and make call related functions on the baord. Refer to Backend 2.3 for details about the implementation in the model.<br>
```
@param String boardId
@param String direction
@return json response
```
**1.6. Frontend request to initialize a game board : backend initialize a game board**<br>
A new PacmanBoard object will be initialized and stored in the adapter. The new board has a unique synthetic key, boardId (String), and its boardId will be included in the response. All objects on the board will be invoked. Refer to Backend 2.1, 3.1, 4.1, 4.2 for details about the implementation in the model.<br>
```
@return JsonStatusResponse response
```

<br>

#### **The followings are all concrete classes. A method may not be specified for each use case as these classes are still under development**

<br>

#### ***2. Pac-Man:***
**2.1. New board initialized : Pac-Man spawns on the game board**<br>
When a new board is initialized (first-time or higher level), Pac-Man will spawn at a fixed location on game board. Pac-Man's remaining lives will be set to 3. This operation is done in PacmanBoard constructor<br>
**2.2. Pac-Man has 0 lives : GAME OVER, level failed**<br>
If the game is ongoing and Pac-Man's remaining lives become negative, game is over and updates on the board will stop. <br>
**2.3. For each board state update : Pac-Man moves according to its update strategy**<br>
Pac-Man is a derived class of PropertyChangeListener, so it is updated by calling firePropertyChange() method. Pac-Man's coordinates is updated, and it moves one unit in the direction specified by its updateStrategy, if not blocked by a wall, each update. The last coordinates Pac-Man is at before the update will be set to empty<br>
**2.4. Pac-Man go into the exit on right side of the map : teleport to the left side exit**<br>
If during a state update, Pac-Man's coordinate on the board is marked as right exit and Pac-Man is moving to right, Pac-Man will be teleported to the coordinate that represents the left exit. <br>
**2.5. Pac-Man go into the exit on left side of the map : teleport to the right side exit**<br>
If during a state update, Pac-Man's coordinate on the board is marked as left exit and Pac-Man is moving to left, Pac-Man will be teleported to the coordinate that represents the right exit. <br>
**2.6. Pac-Man eats all the dots and has lives remain : GAME OVER, level completed**<br>
If the game is ongoing and there is no more dots on the board, game is over and updates (by tick() method) on the board will stop. Increment level attribute of current PacmanBoard by 1, so that a new game on this board will be more difficult.

#### ***3. Ghost:***
**3.1. New board initialized : A specified number (min 3, max 8) of ghosts spawn in the middle of the game board**<br>
When a new board is initialized (first-time or higher level), all ghosts will spawn at a designated area in the middle of the game board. This operation is done in PacmanBoard constructor<br>
**3.2. For each board state update : Each Ghost moves in a unique behavior**<br>
Ghost is a derived class of PropertyChangeListener, so it is updated by calling firePropertyChange() method. Ghost's coordinates is updated, and it moves according to its updateStrategy. Ghost has one of the four movement patterns: towards Pac-Man, randomly, away from Pac-Man or roam along a fixed route. The ghosts' movement speed is set when they are initialized, and speed has a positive correlation with game level<br>
**3.3. Set the number of ghosts : number of ghost is set to specified number**<br>
Before the game starts, number of ghosts may be modified. The maximum number of ghosts allowed is 8. When user changes the number *N* of ghosts, the ghost store of PacmanBoard object will be reinitialized to size *N*, and *N* new ghost objects will be added to the store. Each ghost will be assigned a random initial behavior. 


#### ***4. Consumables***
**4.1. New board initialized : spawn 240 small dots on the game board**<br>
When a new board is initialized (first-time or higher level), all 240 small dots will spawn at fixed locations throughout the game board, i.e. 240 coordinates on board in PacmanBoard object will be set to the integer that represents the small dot. This operation is done in PacmanBoard constructor<br>
**4.2. New board initialized : spawn 4 large dots on four corners of game board**<br>
When a new board is initialized (first-time or higher level), all 4 large dots will spawn at fixed locations on four corners of the game board, i.e. 4 coordinates on board in PacmanBoard object will be set to the integer that represents the large dot. This operation is done in PacmanBoard constructor<br>
**4.3. After every *N* board state updates : fruit spawn on random empty location on the game board**<br>
After every *T* board state updates, a fruit will spawn at a random empty location on the game board. One coordinate on board in PacmanBoard object will be set to the integer that represents fruit. Value *T* is determined by level. <br>
**4.4. At Most once during the game, randomly : Quicksilver charm spawn on random empty location on the game board**<br>
Every board state update a random number generator will produce a number, and if number equals a pre-set value *V*, a quicksilver charm will spawn at a random empty location on the game board. One coordinate on board in PacmanBoard object will be set to the integer that represents Quicksilver charm. In each game, the charm will only spawn once. To prevent the charm from spawning again, *V* will be set to a value outside of the range of random number generated after charm spawns for the first time.

#### ***5. Pac-Man ghost interaction:***
**5.1. Pac-Man collides with a NON-dark blue/NON-flashing ghost : Pac-Man loses 1 life**<br>
Pac-Man's remaining life field decrement by 1. Freeze the game (i.e. pause board state update) for a few seconds. Then if Pac-Man's remaining life is less than 0, GAME OVER and refer to Backend 2.2. Else, respawn Pac-Man at a random empty coordinate on board.<br>
**5.2. Pac-Man collides with a dark blue/flashing ghost : the ghosts become 2 eyes and quickly moves to the square box in the middle of the game board**<br>
The integers that represents this particular ghost on the board will be replaced by one that represents a pair of eyes. Change the update strategy of this to "move quickly back to ghost respawn area". After the ghost arrives at respawn area, change the integer back to one that represent a normal ghost and reassign a update strategy to this ghost. <br>
**5.3. Pac-Man collides with a dark blue/flashing ghost : score increment by 200 (1st time in current period), 400 (2nd), 800 (3rd), 1600 (4th)**<br>
Increment score attribute of the current PacmanBoard by a value "S". Double the value of "S". Refer to Backend 5.2, 6.3, and 7.1 for more use cases related to this one.

#### ***6. Pac-Man consumables interaction:***
**6.1. Pac-Man eats small dots : small dots disappear and score increment by 10**<br>
When Pac-Man moves onto the coordinate of a small dot, the dot will be considered eaten. No modification on board is necessary here as it is done by update of Pac-Man coordinate. Increment score attribute of the current PacmanBoard by 10.<br>
**6.2. Pac-Man eats fruit : fruit disappear and score increment by 100**<br>
When Pac-Man moves onto the coordinate of a fruit, the fruit will be considered eaten. No modification on board is necessary here as it is done by update of Pac-Man coordinate. Increment score attribute of the current PacmanBoard by 100.<br>
**6.3. Pac-Man eats LARGE dots : large dots disappear and score increment by 50**<br>
When Pac-Man moves onto the coordinate of a large dot, the dot will be considered eaten. No modification on board is necessary here as it is done by update of Pac-Man coordinate. Increment score attribute of the current PacmanBoard by 50. Set value of "S" to 200. Refer to Backend 5.2, 5.3, and 7.1 for more use cases related to this one.<br>
**6.4. Pac-Man eats QUICKSILVER CHARM : Quicksilver CHARM disappears**<br>
When Pac-Man moves onto the coordinate of Quicksilver charm, the charm will be considered eaten. Refer to Backend 7.2 for more use cases related to this one.

#### ***7. Pac-Man ghost consumables interaction:***
**7.1. Pac-Man eats LARGE dots : the ghosts turn DARK BLUE and then start FLASHING for a short period of time**<br>
After Pac-Man eats a large dot, all the ghosts' update strategy will be changed to "away from Pac-Man." The integers that represents ghost on the board will be replaced by one that represents a frightened ghost, so frontend will render dark blue/flashing ghosts. After a short period time (a few state updates), all the ghosts' update strategy will be changes to one that is not "away from Pac-Man." The integers that represents the frightened ghost on the board will be replaced by one that represents a normal ghost. Refer to Backend 5.2, 5.3, and 6.3 for more use cases related to this one.<br>
**7.2. Pac-Man eats Quicksilver charm : Pac-Man becomes untouchable by ghost**<br>