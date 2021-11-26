//vehicle(1,300.0,12.0,5.0,left).
//seen(1,semaphore,100.0,10.0,left,0.0,25.0).
//seen(1,pedestrian,56.0,12.0,up,0.0,25.0).
//seen(1,pedestrian,546.0,120.0,left,2.0,25.0).

+simulation(on) : true
	<-
	.print("I am here.... ").
	
+seen(ID, AgtSeen, X, Y, Facing, Distance, Status) : vehicle(ID,_,_,SpeedAgtID,_) & 
                                                     AgtSeen = semaphore & Status = red & 
                                                     Distance < 20
	<- .print("i am seeing a RED signal so closed");
       .print("it is necessary break the car");
       break(ID).
       
+seen(ID, AgtSeen, X, Y, Facing, Distance, Status) : vehicle(ID,_,_,SpeedAgtID,_) & 
                                                     AgtSeen = semaphore & Status = red & 
                                                     Distance >= 20
	<- .print("i am seeing a RED signal");
       .print("it is necessary set the speed");
       setDown(ID).

+seen(ID, AgtSeen, X, Y, Facing, Distance, Status) : vehicle(ID,_,_,SpeedAgtID,_) & 
                                                     AgtSeen = semaphore & Status = yellow  
	<- .print("i am seeing a YELLOW signal");
       .print("it is necessary set the speed");
       setDown(ID).

+seen(ID, AgtSeen, X, Y, Facing, Distance, Status) : vehicle(ID,_,_,SpeedAgtID,_) & 
                                                     AgtSeen = pedestrian & Distance < 20
	<- .print("i am seeing a PEDESTRIAN so closed");
       .print("it is necessary break the car");
       break(ID).

+seen(ID, AgtSeen, X, Y, Facing, Distance, Status) : vehicle(ID,_,_,SpeedAgtID,_) & 
                                                     AgtSeen = pedestrian & Distance < 20
	<- .print("i am seeing a PEDESTRIAN");
       .print("it is necessary set the speed");
       setDown(ID).
       
       
       
       