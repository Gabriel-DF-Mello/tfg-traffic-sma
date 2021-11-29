
+simulation(on) : true
	<-
	.print("I am here.... ").

//veiculo -> name(id,x,y,facing,speed)
//id, name, position_x, position_y, facing, speed, distance, state, seen, around
	
+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                           AgtSeen = semaphore & State = red & 
                                                           Distance < 20
	<- .print("i am seeing a RED signal so closed");
       .print("it is necessary break the car");
       break(ID).
       
+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                           AgtSeen = semaphore & State = red & 
                                                           Distance >= 20
	<- .print("i am seeing a RED signal");
       .print("it is necessary set the speed");
       setDown(ID).

+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                           AgtSeen = semaphore & State = yellow  
	<- .print("i am seeing a YELLOW signal");
       .print("it is necessary set the speed");
       setDown(ID).

+seen(ID, AgtSeen, X, Y, Facing, Spped, Distance, Status) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                            AgtSeen = pedestrian & Distance < 20
	<- .print("i am seeing a PEDESTRIAN so closed");
       .print("it is necessary break the car");
       break(ID).

+seen(ID, AgtSeen, X, Y, Facing, Spped, Distance, Status) : vehicle(ID,_,_,Facing,AgtSpeed) & 
                                                            AgtSeen = pedestrian & Distance >= 20
	<- .print("i am seeing a PEDESTRIAN");
       .print("it is necessary set the speed");
       setDown(ID).
       
       
       
       