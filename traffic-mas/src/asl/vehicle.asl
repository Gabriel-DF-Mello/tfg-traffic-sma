
+simulation(on) : true
	<-
	.print("I am here.... ").
	
+noObstacles : vehicle(ID,_,_,Facing,AgtSpeed)
	<- setUp(ID).

//veiculo -> name(id,x,y,facing,speed)
//id, name, position_x, position_y, facing, speed, distance, state, seen, around
	
+seen(IDs, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(IDv,_,_,Facing,AgtSpeed) & 
                                                           AgtSeen = semaphore & State = red & 
                                                           Distance < 20 & 
                                                           IDs != IDv            
	<- .print("I am seeing a RED signal");
       .print("it is necessary to break the car");
       break(ID).
       
+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                           AgtSeen = semaphore & State = red & 
                                                           Distance >= 20
	<- .print("I am seeing a RED signal");
       .print("it is necessary to set the speed");
       setDown(ID).

+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                           AgtSeen = semaphore & State = yellow  
	<- .print("I am seeing a YELLOW signal");
       .print("it is necessary to set the speed");
       setDown(ID).

+seen(ID, AgtSeen, X, Y, Facing, Spped, Distance, Status) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                            AgtSeen = pedestrian & Distance < 20
	<- .print("I am seeing a PEDESTRIAN so closed");
       .print("it is necessary to break the car");
       break(ID).

+seen(ID, AgtSeen, X, Y, Facing, Spped, Distance, Status) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                            AgtSeen = pedestrian & Distance >= 20
	<- .print("I am seeing a PEDESTRIAN");
       .print("it is necessary to set the speed");
       setDown(ID).
       
       
       