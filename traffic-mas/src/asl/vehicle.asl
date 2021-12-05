
+simulation(on) : true
	<-
	.print("I am here.... ").
	
+vehicle(ID, X, Y, facing, speed, distance, state) : true
	<-
	.print("I am a vehicle ").
	
+noObstacles(ID) : vehicle(ID,_,_,_,AgtSpeed,_,_)
	<- 
	.print("I see no obstacles ");
	setUp(ID).

//veiculo -> name(id,x,y,facing,speed)
//id, name, position_x, position_y, facing, speed, distance, state, seen, around
	
+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,_,AgtSpeed,_,_) & 
                                                           AgtSeen = semaphore & State = red & 
                                                           Distance < 20 
                                                           
	<- .print("I am seeing a RED signal");
       .print("it is necessary to break the car");
       break(ID).
       
+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,_,_,_,_) & 
                                                           AgtSeen = semaphore & State = red & 
                                                           Distance >= 20 
	<- .print("I am seeing a RED signal");
       .print("it is necessary to set the speed");
       setDown(ID).

+seen(ID, AgtSeen, X, Y, Facing, Speed, Distance, State) : vehicle(ID,_,_,_,_,_,_) & 
                                                           AgtSeen = "semaphore" & State = yellow 
	<- .print("I am seeing a YELLOW signal");
       .print("it is necessary to set the speed");
       setDown(ID).

+seen(ID, AgtSeen,_,_,_,_,Distance,_) : vehicle(ID,_,_,_,_,_,_) & 
                                                            AgtSeen = pedestrian & Distance < 20 
	<- .print("I am seeing a PEDESTRIAN really close");
       .print("it is necessary to break the car");
       break(ID).

+seen(ID, AgtSeen,_,_,_,_,Distance,_) : vehicle(ID,_,_,_,_,_,_) & 
                                                            AgtSeen = pedestrian & Distance >= 20
	<- .print("I am seeing a PEDESTRIAN");
       .print("it is necessary to set the speed");
       setDown(ID).
       
       
       