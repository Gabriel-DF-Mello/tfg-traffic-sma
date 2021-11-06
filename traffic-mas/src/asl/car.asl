// Agent sample_agent in project traffic

/* Initial beliefs and rules */

/* Initial goals */


/* Plans */


+whoIsThere : true
	<-
	.print("I am here.... over").
	
+hi : true
	<- 
	   .print("I received and HI (in portuguese) and I will send HI (but in portuguese)");
	   .wait(100);
	   acaoola.